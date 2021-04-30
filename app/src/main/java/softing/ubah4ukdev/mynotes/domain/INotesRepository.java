package softing.ubah4ukdev.mynotes.domain;

import java.util.List;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.servicess

 Created by Ivan Sheynmaer

 2021.03.20
 v1.0
 */
public interface INotesRepository {

    //Получение заметок
    void getNotes(Callback<List<Note>> callback);

    //Получение заметки по индексу
    void getNote(int index, Callback<Note> callback);

    //Генерация заметок, пока нет настоящих, загруженных откуда-либо
    void getNotesExample(int count, Callback<List<Note>> callback);

    //Изменение заметки
    void updateNote(Note oldNote, Note note, Callback<Boolean> callback);

    //Удаление заметки
    void deleteNote(Note note, Callback<Boolean> callback);

    //Добавление заметки
    void addNote(Note note, Callback<Boolean> callback);

}
