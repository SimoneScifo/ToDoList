package simone.it.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import simone.it.todolist.Note;

/**
 * Created by Simone on 22/02/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String COLUMN_NAME_ID ="ID";
    private static final String COLUMN_NAME_TITLE = "title";
    private static final String COLUMN_NAME_BODY = "body";
    private static final String COLUMN_NAME_DATE = "date";
    private static final String COLUMN_NAME_SPECIAL = "special";
    //Incrementare la versione se cambio lo schema del database
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Notes.db";
    public static final String TABLE_NAME = "Note";

    //DatabaseHandler myDatabase = new DatabaseHandler(getContext());

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ DatabaseHandler.TABLE_NAME+ "("+ DatabaseHandler.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
            DatabaseHandler.COLUMN_NAME_TITLE + " TEXT,"+ DatabaseHandler.COLUMN_NAME_BODY + " TEXT," + DatabaseHandler.COLUMN_NAME_DATE + " TEXT," + DatabaseHandler.COLUMN_NAME_SPECIAL + " INTEGER)";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseHandler.TABLE_NAME;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addNote (Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.COLUMN_NAME_TITLE, note.getTitle());
        values.put(DatabaseHandler.COLUMN_NAME_BODY, note.getBody());
        values.put(DatabaseHandler.COLUMN_NAME_DATE, note.getExpiration_date());
        values.put(DatabaseHandler.COLUMN_NAME_SPECIAL, note.getSpecial());

// Insert the new row, returning the primary key value of the new row
       long i=db.insert(DatabaseHandler.TABLE_NAME, null, values);
        note.setId((int)i);
        db.close();
    }

// Getting All Notes
        public ArrayList<Note> getAllNotes() {
            ArrayList<Note> notesList = new ArrayList<>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_NAME;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Note note = new Note();
                    note.setId(Integer.parseInt(cursor.getString(0)));
                    note.setTitle(cursor.getString(1));
                    note.setBody(cursor.getString(2));
                    note.setExpiration_date(cursor.getString(3));
                    note.setSpecial(cursor.getInt(4));
                    // Adding note to list
                    notesList.add(note);
                } while (cursor.moveToNext());
            }

            // return notes list
            return notesList;
        }

        // Updating single note
        public int updateNote(Note note) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_TITLE, note.getTitle());
            values.put(COLUMN_NAME_BODY, note.getBody());
            values.put(COLUMN_NAME_DATE, note.getExpiration_date());
            values.put(COLUMN_NAME_SPECIAL, note.getSpecial());
            // updating row
                return db.update(TABLE_NAME, values, COLUMN_NAME_ID + " = ?",
                    new String[]{String.valueOf(note.getId())});
        }

    public void deleteNote(Note note) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, COLUMN_NAME_ID + " = ?",
                    new String[]{String.valueOf(note.getId())});
            db.close();
    }
    }

