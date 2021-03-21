package softing.ubah4ukdev.mynotes.servicess;

import softing.ubah4ukdev.mynotes.model.Note;
import softing.ubah4ukdev.mynotes.model.Notes;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.servicess

 Created by Ivan Sheynmaer

 2021.03.20
 v1.0
 */
public interface IDataHelper {

    Notes load();

    Notes loadExample(int count);

    void saveDate(Note note, long dateNew);

}
