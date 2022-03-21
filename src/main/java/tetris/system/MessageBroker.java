package tetris.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import tetris.components.Component;
import tetris.window.TaskManager;

public class MessageBroker implements Runnable {

    private static final Map<Class<? extends Post<?>>, List<Component>> subscribers = new ConcurrentHashMap<>();
    private static final BlockingDeque<Post<?>> messageQueue = new LinkedBlockingDeque<>();
    private static MessageBroker instance;
    private static Thread thread;
    private static boolean isRunning;

    private MessageBroker() {
    }

    public static void publish(Post<?> post) {
        messageQueue.add(post);
    }

    public static void subscribe(Class<? extends Post<?>> post, Component subscriber) {
        if (!subscribers.containsKey(post)) {
            subscribers.put(post, new ArrayList<>());
        }
        subscribers.get(post).add(subscriber);
    }

    private static void sendMessage(Post<?> post) {
        if (!subscribers.containsKey(post.getClass())) {
            return;
        }
        subscribers.get(post.getClass()).forEach(c ->
            TaskManager.addTask(() ->
                c.onMessage(post)));
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
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            if (messageQueue.isEmpty()) {
                continue;
            }
            sendMessage(messageQueue.removeFirst());
        }
    }
}
