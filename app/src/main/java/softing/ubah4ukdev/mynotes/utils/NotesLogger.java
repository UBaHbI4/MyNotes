package softing.ubah4ukdev.mynotes.utils;

import android.util.Log;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.utils

 Created by Ivan Sheynmaer

 2021.03.20
 v1.0
 */
public class NotesLogger {
    public static void printLog(String message) {
        Log.d("notesLog", message);
    }

    public static void printLog(String message, LoggerType loggerType) {
        switch (loggerType) {
            case INFO:
                Log.i("notesLog", message);
                break;
            case WARN:
                Log.w("notesLog", message);
                break;
            case DEBUG:
                Log.d("notesLog", message);
                break;
            case ERROR:
                Log.e("notesLog", message);
                break;
            case ASSERT:
                Log.wtf("notesLog", message);
                break;
        }
    }
}
