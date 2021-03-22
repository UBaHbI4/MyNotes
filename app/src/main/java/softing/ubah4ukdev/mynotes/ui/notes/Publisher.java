package softing.ubah4ukdev.mynotes.ui.notes;

import java.util.ArrayList;
import java.util.List;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.model

 Created by Ivan Sheynmaer

 2021.03.21
 v1.0
 */
public class Publisher {
    private static Publisher instance;

    private List<INoteObserver> observers;

    private Publisher() {
        observers = new ArrayList<>();
    }

    public void add(INoteObserver observer) {
        observers.add(observer);
    }

    public static Publisher getInstance() {
        if (instance == null) {
            instance = new Publisher();
        }
        return instance;
    }

    public void startUpdate() {
        for (INoteObserver observer: observers) {
            observer.update();
        }
    }
}