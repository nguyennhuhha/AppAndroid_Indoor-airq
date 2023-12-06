package com.example.myapplication.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.Model.Feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackHandler extends SQLiteOpenHelper {
    private Context context;
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Feedback_Database";
    //Table name
    private static final String TABLE_FB = "feedback";
    //Column name
    private static final String KEY_ID = "id";
    private static final String KEY_USR = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_OVERALL = "overall";
    private static final String KEY_TIME = "time";
    private static final String KEY_DEG = "degree";
    private static final String KEY_WIND = "wind";
    private static final String KEY_OTHER = "other";
    public FeedbackHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " +
                TABLE_FB + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USR + " TEXT, " +
                KEY_EMAIL + " TEXT, " +
                KEY_OVERALL + " TEXT, " +
                KEY_TIME + " TEXT, " +
                KEY_DEG + " TEXT, " +
                KEY_WIND + " TEXT, " +
                KEY_OTHER + " TEXT);";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FB);
        // Create tables again
        onCreate(db);
    }
    public void addFB(Feedback fb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_USR, fb.getUsername());
        cv.put(KEY_EMAIL, fb.getEmail());
        cv.put(KEY_OVERALL, fb.getOverall());
        cv.put(KEY_TIME, fb.getTime());
        cv.put(KEY_DEG, fb.getDegree());
        cv.put(KEY_WIND, fb.getWind());
        cv.put(KEY_OTHER, fb.getOther());
        long res = db.insert(TABLE_FB, null, cv);
        if (res==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Sent successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public List<Feedback> getAllFb(){
            List<Feedback> fbs = new ArrayList<>();
            String query ="SELECT * FROM " + TABLE_FB;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            if (db != null){
                cursor = db.rawQuery(query, null);
            }
            if (cursor.getCount()==0){
                Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
            }
            else {
                while (cursor.moveToNext()){
                    Feedback fb = new Feedback(cursor.getString(1), cursor.getString(2),
                            cursor.getString(4), cursor.getString(3),
                            cursor.getString(5), cursor.getString(6),
                            cursor.getString(7));
                    fb.setId(cursor.getInt(0));
                    fbs.add(fb);}
            }
            return fbs;
    }
    public void deleteFb(Feedback fb) {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete(TABLE_FB, "id=?", new String[]{String.valueOf(fb.getId())});
        if (res==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
