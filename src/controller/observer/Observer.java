package controller.observer;

public interface Observer {
    void update(Notifier notifier, Object notifiedArg);
}
