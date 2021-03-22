package softing.ubah4ukdev.mynotes.ui.notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.model

 Created by Ivan Sheynmaer

 2021.03.21
 v1.0
 */
public class Publisher {

    private List<INoteObserver> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    // Подписать
    public void subscribe(INoteObserver observer) {
        observers.add(observer);
    }

    // Отписать
    public void unsubscribe(INoteObserver observer) {
        observers.remove(observer);
    }

    public void startUpdate() {
        for (INoteObserver observer: observers) {
            observer.update();
        }
    }
}