package tetris.system;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import tetris.Task;
import tetris.ui.annotations.OnMessage;
import tetris.ui.message.Post;

public class MessageBroker {

    private static final ConcurrentHashMap<Class<? extends Post<?>>,
        ConcurrentHashMap<Integer, WeakReference<Object>>> subs = new ConcurrentHashMap<>();

    private MessageBroker() {
    }

    public synchronized static void publish(Post<?> post) {
        broadCast(post);
    }

    public static void subscribe(Class<? extends Post<?>> post, Object subscriber) {
        subs.putIfAbsent(post, new ConcurrentHashMap<>());
        subs.get(post).putIfAbsent(subscriber.hashCode(), new WeakReference<>(subscriber));
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
        if (subscriber == null || post == null) {
            return;
        }
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
}
