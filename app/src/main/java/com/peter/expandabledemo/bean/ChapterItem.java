package com.peter.expandabledemo.bean;

public class ChapterItem {
    private int chapterId;
    private int itemId;
    private String chapterItemName;

    public static final String TABLE_NAME = "tb_chapter_item";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_CID = "chapterId";

    public ChapterItem() {

    }

    public ChapterItem(int itemId, String chapterItemName) {
        this.itemId = itemId;
        this.chapterItemName = chapterItemName;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getChapterItemName() {
        return chapterItemName;
    }

    public void setChapterItemName(String chapterItemName) {
        this.chapterItemName = chapterItemName;
    }


}
