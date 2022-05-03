package com.peter.expandabledemo.adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.peter.expandabledemo.R;
import com.peter.expandabledemo.bean.Chapter;
import com.peter.expandabledemo.bean.ChapterItem;

import java.util.List;

public class ChapterListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Chapter> chapterList;
    private LayoutInflater mInflater;

    public ChapterListAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return chapterList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return chapterList.get(i).getChapterItemList().size();
    }

    @Override
    public Object getGroup(int i) {
        return chapterList.get(i);
    }

    @Override
    public Object getChild(int i, int itemId) {
        return chapterList.get(i).getChapterItemList().get(itemId);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int itemId) {
        return itemId;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ChapterViewHolder chapterViewHolder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.listview_chapter, viewGroup, false);
            chapterViewHolder = new ChapterViewHolder();
            chapterViewHolder.chapterTvName = view.findViewById(R.id.chapter_name);
            view.setTag(chapterViewHolder);
        } else {
            chapterViewHolder = (ChapterViewHolder) view.getTag();
        }
        Chapter chapter = chapterList.get(i);
        chapterViewHolder.chapterTvName.setText(chapter.getChapterName());
        return view;
    }

    @Override
    public View getChildView(int i, int itemId, boolean b, View view, ViewGroup viewGroup) {
        ChapterItemViewHolder chapterItemViewHolder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.listview_chaptter_item, viewGroup, false);
            chapterItemViewHolder = new ChapterItemViewHolder();
            chapterItemViewHolder.chapterItemTvName = view.findViewById(R.id.chapter_item_name);
            view.setTag(chapterItemViewHolder);
        } else {
            chapterItemViewHolder = (ChapterItemViewHolder) view.getTag();
        }
        ChapterItem chapterItem = chapterList.get(i).getChapterItemList().get(itemId);
        chapterItemViewHolder.chapterItemTvName.setText(chapterItem.getChapterItemName());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public static class ChapterViewHolder {
        public TextView chapterTvName;
    }

    public static class ChapterItemViewHolder {
        public TextView chapterItemTvName;
    }
}
