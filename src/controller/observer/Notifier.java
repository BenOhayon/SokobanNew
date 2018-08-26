package controller.observer;

import java.util.ArrayList;

public abstract class Notifier {

    private ArrayList<Observer> observers;
    private Object change;

    public Notifier() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void setChange(Object o) {
        this.change = o;
    }

    public void notifyObservers() {
        for(Observer o : observers) {
            o.update(this, this.change);
        }
    }
}
