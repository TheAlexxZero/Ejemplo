package com.example.proyectov11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "habitapp.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas de usuarios
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "nombre";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // Nombre de la tabla y columnas de hábitos
    private static final String TABLE_HABITS = "habits";
    private static final String COLUMN_HABIT_ID = "id";
    private static final String COLUMN_HABIT_NAME = "nombre_habito";
    private static final String COLUMN_HABIT_FREQUENCY = "frecuencia";
    private static final String COLUMN_HABIT_TYPE = "tipo_habito";
    private static final String COLUMN_HABIT_SPECIFIC_TYPE = "tipo_especifico";
    private static final String COLUMN_HABIT_TERM = "plazo";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de usuarios
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Crear la tabla de hábitos
        String CREATE_HABITS_TABLE = "CREATE TABLE " + TABLE_HABITS + "("
                + COLUMN_HABIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_HABIT_NAME + " TEXT,"
                + COLUMN_HABIT_FREQUENCY + " TEXT,"
                + COLUMN_HABIT_TYPE + " TEXT,"
                + COLUMN_HABIT_SPECIFIC_TYPE + " TEXT,"
                + COLUMN_HABIT_TERM + " TEXT" + ")";
        db.execSQL(CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar las tablas si existen y crearlas de nuevo
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITS);
        onCreate(db);
    }

    // Método para registrar un usuario
    public boolean registerUser(String nombre, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, nombre);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1; // Retorna true si se insertó correctamente
    }

    // Método para verificar las credenciales del usuario
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE "
                + COLUMN_EMAIL + " =? AND " + COLUMN_PASSWORD + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Método para agregar un nuevo hábito
    public boolean addHabit(String habitName, String frequency, String type, String specificType, String term) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HABIT_NAME, habitName);
        values.put(COLUMN_HABIT_FREQUENCY, frequency);
        values.put(COLUMN_HABIT_TYPE, type);
        values.put(COLUMN_HABIT_SPECIFIC_TYPE, specificType);
        values.put(COLUMN_HABIT_TERM, term);

        long result = db.insert(TABLE_HABITS, null, values);
        db.close();
        return result != -1; // Retorna true si se insertó correctamente
    }

    // Método para obtener todos los hábitos
    public Cursor getAllHabits() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HABITS, null);
    }

    public boolean deleteHabit(String habitName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_HABITS, COLUMN_HABIT_NAME + " = ?", new String[]{habitName});
        db.close();
        return result > 0; // Retorna true si se eliminó correctamente
    }

    public boolean actualizarHabit(int id, String nuevoNombre, String nuevaFrecuencia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre_habito", nuevoNombre);
        contentValues.put("frecuencia_habito", nuevaFrecuencia);

        // Actualiza el registro en la base de datos
        return db.update("habitos", contentValues, "id_habito = ?", new String[]{String.valueOf(id)}) > 0;
    }
    // Método para obtener un hábito por su ID
    public Cursor getHabitById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_HABITS, null, COLUMN_HABIT_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
    }


}

