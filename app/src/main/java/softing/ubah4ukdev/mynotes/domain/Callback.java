package softing.ubah4ukdev.mynotes.domain;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.domain

 Created by Ivan Sheynmaer

 2021.03.30
 v1.0
 */
public interface Callback<T> {
    void onResult(T value);
}
