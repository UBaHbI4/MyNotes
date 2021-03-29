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
    List<Note> getNotes();

    //Получение заметки по индексу
    Note getNote(int index);

    //Генерация заметок, пока нет настоящих, загруженных откуда-либо
    List<Note> getNotesExample(int count);

    //Изменение заметки
    void updateNote(Note oldNote, Note note);

    //Удаление заметки
    void deleteNote(Note note);

    //Добавление заметки
    void addNote(Note note);

}
