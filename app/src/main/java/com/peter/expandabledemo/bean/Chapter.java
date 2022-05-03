package com.peter.expandabledemo.bean;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
    private int chapterId;
    private String chapterName;
    private List<ChapterItem> chapterItemList = new ArrayList<>();

    public static final String TABLE_NAME = "tb_chapter";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";

    public Chapter() {

    }

    public Chapter(int chapterId, String chapterName) {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
    }

    public void addChild(ChapterItem chapterItem) {
        chapterItem.setChapterId(this.getChapterId());
        chapterItemList.add(chapterItem);
    }

    public  void addChild(int itemId, String itemName) {
        ChapterItem chapterItem = new ChapterItem(itemId, itemName);
        chapterItem.setChapterId(this.getChapterId());
        chapterItemList.add(chapterItem);
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public List<ChapterItem> getChapterItemList() {
        return chapterItemList;
    }

    public void setChapterItemList(List<ChapterItem> chapterItemList) {
        this.chapterItemList = chapterItemList;
    }
}
