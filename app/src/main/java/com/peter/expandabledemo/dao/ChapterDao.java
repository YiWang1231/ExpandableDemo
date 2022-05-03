package com.peter.expandabledemo.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.peter.expandabledemo.bean.Chapter;
import com.peter.expandabledemo.bean.ChapterItem;

import java.util.ArrayList;
import java.util.List;

public class ChapterDao  {
    @SuppressLint("Range")
    public List<Chapter> loadFromDb(Context context) {
        List<Chapter> chapterList = new ArrayList<>();
        ChapterDbHelper chapterDbHelper =  ChapterDbHelper.getInstance(context);
        SQLiteDatabase db = chapterDbHelper.getReadableDatabase();
        List<Chapter> chapterList1 = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + Chapter.TABLE_NAME, null);
        Chapter chapter = null;
        while (c.moveToNext()) {
            chapter = new Chapter();
            chapter.setChapterId(c.getInt(c.getColumnIndex(Chapter.COL_ID)));
            chapter.setChapterName(c.getString(c.getColumnIndex(Chapter.COL_NAME)));
            chapterList.add(chapter);
        }
        c.close();
        // 获取item
        ChapterItem chapterItem = null;
        for (Chapter parent: chapterList) {
           String  sqlItem = "SELECT * FROM " + ChapterItem.TABLE_NAME + " where " + ChapterItem.COL_CID + " = ? ";
           Cursor cItem = db.rawQuery(sqlItem, new String[] {String.valueOf(parent.getChapterId())});
           while (cItem.moveToNext()) {
               chapterItem = new ChapterItem();
               chapterItem.setItemId(cItem.getInt(cItem.getColumnIndex(ChapterItem.COL_ID)));
               chapterItem.setChapterItemName(cItem.getString(cItem.getColumnIndex(ChapterItem.COL_NAME)));
               parent.addChild(chapterItem);
           }
           cItem.close();
        }
        return chapterList;
    }

    public void insert2Db(Context context, List<Chapter> chapterList) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null");
        }
        if (chapterList == null || chapterList.isEmpty()) {
            return;
        }
        ChapterDbHelper chapterDbHelper =  ChapterDbHelper.getInstance(context);
        SQLiteDatabase db = chapterDbHelper.getReadableDatabase();
        db.beginTransaction();
        for (Chapter chapter: chapterList) {
            ContentValues chapterValues = new ContentValues();
            chapterValues.put(Chapter.COL_ID, chapter.getChapterId());
            chapterValues.put(Chapter.COL_NAME, chapter.getChapterName());
            db.insertWithOnConflict(Chapter.TABLE_NAME, null,  chapterValues, SQLiteDatabase.CONFLICT_IGNORE);
            if (!chapter.getChapterItemList().isEmpty()) {
                List<ChapterItem> chapterItemList = chapter.getChapterItemList();
                Log.e("length", String.valueOf(chapterItemList.size()));
                for (ChapterItem chapterItem : chapterItemList) {
                    ContentValues chapterItemValues = new ContentValues();
                    chapterItemValues.put(ChapterItem.COL_ID, chapterItem.getItemId());
                    chapterItemValues.put(ChapterItem.COL_NAME, chapterItem.getChapterItemName());
                    chapterItemValues.put(ChapterItem.COL_CID, chapterItem.getChapterId());
                    db.insertWithOnConflict(ChapterItem.TABLE_NAME, null, chapterItemValues, SQLiteDatabase.CONFLICT_IGNORE);
                }
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
