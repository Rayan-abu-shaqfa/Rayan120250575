package com.example.rayan120250575;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

//استخدمت ai هان

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "tasks.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tasks (id INTEGER PRIMARY KEY, title TEXT NOT NULL, grade REAL, submitted INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }


    public void addTask(String title, double grade, boolean submitted) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("grade", grade);
        values.put("submitted", submitted ? 1 : 0);
        db.insert("tasks", null, values);
    }


    public ArrayList<Task> getAllTasks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM tasks ORDER BY title", null);
        return readList(c);
    }


    public ArrayList<Task> searchTasks(String word) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM tasks WHERE title LIKE ? ORDER BY title",
                new String[]{"%" + word + "%"});
        return readList(c);
    }


    public void updateSubmitted(int id, boolean submitted) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("submitted", submitted ? 1 : 0);
        db.update("tasks", values, "id = ?", new String[]{String.valueOf(id)});
    }


    public void deleteTask(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tasks", "id = ?", new String[]{String.valueOf(id)});
    }


    private ArrayList<Task> readList(Cursor c) {
        ArrayList<Task> list = new ArrayList<>();
        while (c.moveToNext()) {
            list.add(new Task(c.getInt(0), c.getString(1), c.getDouble(2), c.getInt(3) == 1));
        }
        c.close();
        return list;
    }
}