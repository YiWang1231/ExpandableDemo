package com.peter.expandabledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.peter.expandabledemo.adapter.ChapterListAdapter;
import com.peter.expandabledemo.bean.Chapter;
import com.peter.expandabledemo.bean.ChapterLab;
import com.peter.expandabledemo.biz.ChapterBiz;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class MainActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private ChapterListAdapter chapterListApaapter;
    private List<Chapter> mDatas = new ArrayList<>();
    private static  final String TAG="EXPAND";
    private ChapterBiz mChapterBiz = new ChapterBiz();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_view();
        init_events();
        load_datas(true);
    }

    private void load_datas(boolean useCache) {
        mChapterBiz.loadDatas(this, new ChapterBiz.Callback() {
            @Override
            public void onSuccess(List<Chapter> chapterList) {
                mDatas.clear();
                mDatas.addAll(chapterList);
                chapterListApaapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {

            }
        }, useCache);
    }

    private void init_events() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int itemId, long l) {
                Log.e(TAG, "Click Group" + mDatas.get(i).getChapterName() + ": " + itemId);
                return false;
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Log.e(TAG, "Click Group" + mDatas.get(i).getChapterName());
                return false;
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                Log.e(TAG, "Click Group" + mDatas.get(i).getChapterName() + "collapse");
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                Log.e(TAG, "Click Group" + mDatas.get(i).getChapterName() + "expand");
            }
        });
    }

    private void init_view() {
        expandableListView = findViewById(R.id.expanded_menu);
        mDatas.clear();
//        chapterList.addAll(ChapterLab.generateChapter());
        chapterListApaapter = new ChapterListAdapter(MainActivity.this, mDatas);
        expandableListView.setAdapter(chapterListApaapter);
    }
}