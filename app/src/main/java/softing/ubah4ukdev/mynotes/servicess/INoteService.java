package softing.ubah4ukdev.mynotes.servicess;

import softing.ubah4ukdev.mynotes.model.Note;
import softing.ubah4ukdev.mynotes.model.NotesRepository;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.servicess

 Created by Ivan Sheynmaer

 2021.03.20
 v1.0
 */
public interface INoteService {

    //Получение заметок
    NotesRepository getNotes();

    //Генерация заметок, пока нет настоящих, загруженных откуда-либо
    NotesRepository getNotesExample(int count);

    //Сохранение даты в заметке
    void updateDateNote(Note note, long dateNew);

    void updateNote(int itemID, Note note);

    void deleteNote(Note note);

}
