package com.stk.a15830app2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class SQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "usuarios.db";
    private static final int DATABASE_VERSION = 2; // Incrementado para forzar actualización
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT UNIQUE,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_USERNAME + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Método para registrar usuario
    public boolean registrarUsuario(String name, String email, String phone, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // Método para logear usuario
    public boolean logearUsuario(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Método para cambiar contraseña
    public boolean cambiarContrasena(String username, String nuevaPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, nuevaPassword);

        int rows = db.update(TABLE_USERS, values, COLUMN_USERNAME + "=?", new String[]{username});
        db.close();
        return rows > 0;
    }

    // Método para obtener información del usuario
    public UserInfo obtenerInfoUsuario(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserInfo userInfo = null;

        String query = "SELECT " + COLUMN_NAME + ", " + COLUMN_EMAIL + ", " + COLUMN_PHONE + 
                      " FROM " + TABLE_USERS + 
                      " WHERE " + COLUMN_USERNAME + "=?";
        
        Cursor cursor = db.rawQuery(query, new String[]{username});
        
        if (cursor.moveToFirst()) {
            userInfo = new UserInfo(
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
            );
        }
        
        cursor.close();
        db.close();
        return userInfo;
    }

    // Clase para almacenar la información del usuario
    public static class UserInfo {
        private String name;
        private String email;
        private String phone;

        public UserInfo(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }
    }
}
