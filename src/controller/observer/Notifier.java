package controller.observer;

import java.util.ArrayList;

public abstract class Notifier {

    private ArrayList<Observer> observers;

    void registerObserver(Observer observer) {
        observers.add(observer);
    }

    void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    void notifyObservers() {
        for(Observer o : observers) {
            o.update(this);
        }
    }
}
