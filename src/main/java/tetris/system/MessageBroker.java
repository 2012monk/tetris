package tetris.system;

import java.lang.annotation.IncompleteAnnotationException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import tetris.Task;
import tetris.annotations.OnMessage;
import tetris.message.Post;

public class MessageBroker implements Runnable {

    private static final ConcurrentHashMap<Class<? extends Post<?>>,
        ConcurrentHashMap<Integer, WeakReference<Object>>> subs = new ConcurrentHashMap<>();
    private static final BlockingDeque<Post<?>> messageQueue = new LinkedBlockingDeque<>();
    private static final long DEFAULT_TIMEOUT = 5000;
    private static MessageBroker instance;
    private static Thread thread;
    private static boolean isRunning;

    private MessageBroker() {
    }

    public static void publish(Post<?> post) {
        messageQueue.add(post);
        thread.interrupt();
    }

    public static void subscribe(Class<? extends Post<?>> post, Object subscriber) {
//        verifySubscriber(subscriber);
        subs.putIfAbsent(post, new ConcurrentHashMap<>());
        subs.get(post).putIfAbsent(subscriber.hashCode(), new WeakReference<>(subscriber));
    }

    private static void verifySubscriber(Object subscriber) {
        Method[] methods = subscriber.getClass().getDeclaredMethods();
        if (Arrays.stream(methods)
            .anyMatch(method -> method.getAnnotation(OnMessage.class) != null)) {
            return;
        }
        throw new IncompleteAnnotationException(OnMessage.class, subscriber.getClass().getName());
    }

    private static void broadCast(Post<?> post) {
        if (post == null || !subs.containsKey(post.getClass())) {
            return;
        }
        subs.get(post.getClass()).values()
            .forEach(w -> sendTask(() -> broadCast(w.get(), post)));
    }

    public static void sendTask(Task task) {
        TaskManager.addTask(task);
    }

    public static void broadCast(Object subscriber, Post<?> post) {
        Arrays.stream(subscriber.getClass().getDeclaredMethods())
            .filter(m -> m.getAnnotation(OnMessage.class) != null)
            .forEach(m -> broadCast(subscriber, m, post));

    }

    private static void broadCast(Object subscriber, Method method, Post<?> post) {
        if (!isMatchedParameter(method, post)) {
            return;
        }
        try {
            method.setAccessible(true);
            method.invoke(subscriber, post);
        } catch (Exception ignore) {
        }
    }

    private static boolean isMatchedParameter(Method method, Post<?> post) {
        return Arrays.stream(method.getParameterTypes()).anyMatch(t -> t.equals(post.getClass()));
    }

    public static MessageBroker getInstance() {
        if (instance == null) {
            instance = new MessageBroker();
        }
        return instance;
    }

    public static synchronized void init() {
        isRunning = true;
        if (thread == null) {
            thread = new Thread(getInstance());
            thread.start();
        }
    }

    public static synchronized void shutDown() {
        isRunning = false;
        messageQueue.clear();
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
            thread = null;
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            if (messageQueue.isEmpty()) {
                try {
                    Thread.sleep(DEFAULT_TIMEOUT);
                } catch (InterruptedException ignore) {
                }
                continue;
            }
            broadCast(messageQueue.removeFirst());
        }
    }
}
