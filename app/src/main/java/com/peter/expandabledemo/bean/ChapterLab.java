package com.peter.expandabledemo.bean;

import java.util.ArrayList;
import java.util.List;

public class ChapterLab {
    public static List<Chapter> generateChapter() {
        List<Chapter> chapterList = new ArrayList<>();
        Chapter chapterOne = new Chapter(1, "Android");
        Chapter chapterTwo = new Chapter(2, "iOS");
        Chapter chapterThree = new Chapter(3, "Cocos");
        chapterOne.addChild(new ChapterItem(1, "Fragment"));
        chapterOne.addChild(new ChapterItem(2, "ExpandableView"));
        chapterOne.addChild(new ChapterItem(3, "Handler"));

        chapterTwo.addChild(new ChapterItem(1, "ListView"));
        chapterTwo.addChild(new ChapterItem(2, "Component"));
        chapterTwo.addChild(new ChapterItem(3, "Setting"));

        chapterThree.addChild(new ChapterItem(1, "2D"));
        chapterThree.addChild(new ChapterItem(2, "Cocos2d-x"));
        chapterThree.addChild(new ChapterItem(3, "Light"));

        chapterList.add(chapterOne);
        chapterList.add(chapterTwo);
        chapterList.add(chapterThree);

        return chapterList;
    }
}
