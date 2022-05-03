package com.peter.expandabledemo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.peter.expandabledemo.bean.Chapter;
import com.peter.expandabledemo.bean.ChapterItem;

public class ChapterDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_chapter.db";
    private static final int VERSION = 1;

    private static ChapterDbHelper sInstance;
    public static synchronized ChapterDbHelper getInstance(Context context) {
        if (sInstance==null) {
            sInstance = new ChapterDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public ChapterDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createChapter = "CREATE TABLE IF NOT EXISTS " + Chapter.TABLE_NAME + " (" +
                Chapter.COL_ID + " integer primary key, "
                + Chapter.COL_NAME + " varchar" + ")";
        String createChapterItem = "CREATE TABLE IF NOT EXISTS " + ChapterItem.TABLE_NAME + " (" +
                ChapterItem.COL_ID + " integer primary key,"
                + Chapter.COL_NAME + " varchar, " +
                ChapterItem.COL_CID + " integer" + ")";
        sqLiteDatabase.execSQL(createChapter);
        sqLiteDatabase.execSQL(createChapterItem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
