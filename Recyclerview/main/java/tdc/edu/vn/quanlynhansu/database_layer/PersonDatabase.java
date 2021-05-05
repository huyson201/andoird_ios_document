package tdc.edu.vn.quanlynhansu.database_layer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import tdc.edu.vn.quanlynhansu.data_models.Person;

public class PersonDatabase extends SQLiteOpenHelper {
    private static String DB_NAME = "database";
    private static int DB_VERSION = 1;

    // Parameter's Table
    private static String TABLE_NAME = "members";
    private static String ID = "_id";
    private static String NAME = "name";
    private static String DEGREE = "degree";
    private static String HOPPIES = "hoppies";

    public PersonDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Primitives Define
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, "+
                DEGREE + " TEXT, "+
                HOPPIES + " TEXT);";
        db.execSQL(sql);
    }

    // API Define
    public void savePerson (Person person) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, person.getName());
        values.put(DEGREE, person.getDegree());
        values.put(HOPPIES, person.getHobbies());
        // Insert into the database
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void savePersons (ArrayList<Person> persons) {
        SQLiteDatabase db = getWritableDatabase();

        for (Person person: persons) {
            ContentValues values = new ContentValues();
            values.put(NAME, person.getName());
            values.put(DEGREE, person.getDegree());
            values.put(HOPPIES, person.getHobbies());
            // Insert into the database
            db.insert(TABLE_NAME, null, values);
        }

        db.close();
    }

    public void getPersons (ArrayList<Person> persons) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{NAME, DEGREE, HOPPIES},
                null,null,null,null, NAME);
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                String name = cursor.getString(cursor.getColumnIndex(NAME));
                String degree = cursor.getString(cursor.getColumnIndex(DEGREE));
                String hoppies = cursor.getString(cursor.getColumnIndex(HOPPIES));
                person.setName(name);
                person.setDegree(degree);
                person.setHobbies(hoppies);
                persons.add(person);
            } while (cursor.moveToNext());
        }

        db.close();
    }

    public void removePerson (Person person) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_NAME, NAME + " =? AND "+ DEGREE + " =? AND "+ HOPPIES + " =?",
                new String[]{person.getName(), person.getDegree(), person.getHobbies()});

        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
