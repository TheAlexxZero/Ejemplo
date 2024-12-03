package com.example.proyectov11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "habitapp.db";
    private static final int DATABASE_VERSION = 2; // Incrementamos la versión

    // Tabla de usuarios
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "nombre";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // Tabla de hábitos de sueño
    private static final String TABLE_SLEEP_HABITS = "sleep_habits";
    private static final String COLUMN_SLEEP_ID = "id";
    static final String COLUMN_SLEEP_NAME = "nombre_habito";
    private static final String COLUMN_SLEEP_TIME_TO_SLEEP = "hora_dormir";
    private static final String COLUMN_SLEEP_TIME_TO_WAKE = "hora_despertar";
    private static final String COLUMN_SLEEP_FREQUENCY = "frecuencia";
    private static final String COLUMN_SLEEP_TYPE = "tipo_habito";
    private static final String COLUMN_SLEEP_SPECIFIC_TYPE = "tipo_especifico";
    private static final String COLUMN_SLEEP_TERM = "plazo";

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

        // Crear la tabla de hábitos de sueño
        String CREATE_SLEEP_HABITS_TABLE = "CREATE TABLE " + TABLE_SLEEP_HABITS + "("
                + COLUMN_SLEEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SLEEP_NAME + " TEXT,"
                + COLUMN_SLEEP_TIME_TO_SLEEP + " TEXT,"
                + COLUMN_SLEEP_TIME_TO_WAKE + " TEXT,"
                + COLUMN_SLEEP_FREQUENCY + " TEXT,"
                + COLUMN_SLEEP_TYPE + " TEXT,"
                + COLUMN_SLEEP_SPECIFIC_TYPE + " TEXT,"
                + COLUMN_SLEEP_TERM + " TEXT" + ")";
        db.execSQL(CREATE_SLEEP_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLEEP_HABITS);
        onCreate(db);
    }

    // CRUD de usuarios

    // Método para registrar un usuario
    public boolean registerUser(String nombre, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, nombre);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
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

    // CRUD de hábitos de sueño

    // Método para agregar un hábito de sueño
    public boolean addSleepHabit(String nombreHabito, String horaDormir, String horaDespertar, String frecuencia,
                                 String tipoHabito, String tipoEspecifico, String plazo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SLEEP_NAME, nombreHabito);
        values.put(COLUMN_SLEEP_TIME_TO_SLEEP, horaDormir);
        values.put(COLUMN_SLEEP_TIME_TO_WAKE, horaDespertar);
        values.put(COLUMN_SLEEP_FREQUENCY, frecuencia);
        values.put(COLUMN_SLEEP_TYPE, tipoHabito);
        values.put(COLUMN_SLEEP_SPECIFIC_TYPE, tipoEspecifico);
        values.put(COLUMN_SLEEP_TERM, plazo);

        long result = db.insert(TABLE_SLEEP_HABITS, null, values);
        db.close();
        return result != -1;
    }

    // Método para obtener todos los hábitos de sueño
    public Cursor getAllSleepHabits() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SLEEP_HABITS, null);
    }

    // Método para obtener un hábito de sueño por su ID
    public Cursor getSleepHabitById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_SLEEP_HABITS, null, COLUMN_SLEEP_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null);
    }

    // Método para actualizar un hábito de sueño
    public boolean updateSleepHabit(int id, String nuevoNombre, String nuevaHoraDormir, String nuevaHoraDespertar,
                                    String nuevaFrecuencia, String nuevoTipoHabito, String nuevoTipoEspecifico, String nuevoPlazo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SLEEP_NAME, nuevoNombre);
        contentValues.put(COLUMN_SLEEP_TIME_TO_SLEEP, nuevaHoraDormir);
        contentValues.put(COLUMN_SLEEP_TIME_TO_WAKE, nuevaHoraDespertar);
        contentValues.put(COLUMN_SLEEP_FREQUENCY, nuevaFrecuencia);
        contentValues.put(COLUMN_SLEEP_TYPE, nuevoTipoHabito);
        contentValues.put(COLUMN_SLEEP_SPECIFIC_TYPE, nuevoTipoEspecifico);
        contentValues.put(COLUMN_SLEEP_TERM, nuevoPlazo);

        return db.update(TABLE_SLEEP_HABITS, contentValues, COLUMN_SLEEP_ID + " = ?", new String[]{String.valueOf(id)}) > 0;
    }

    // Método para eliminar un hábito de sueño
    public boolean deleteSleepHabit(String habitName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_SLEEP_HABITS, COLUMN_SLEEP_NAME + " = ?", new String[]{habitName});
        db.close();
        return result > 0;
    }
}