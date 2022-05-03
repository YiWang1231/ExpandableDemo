package com.peter.expandabledemo.biz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.peter.expandabledemo.bean.Chapter;
import com.peter.expandabledemo.bean.ChapterItem;
import com.peter.expandabledemo.dao.ChapterDao;
import com.peter.expandabledemo.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;
public class ChapterBiz {
    public static GetChapterTask getChapterTask;
    public  static String URL = "https://www.imooc.com/api/expandablelistview";
    public void loadDatas(Context context, Callback callback, boolean useCache) {
        getChapterTask = new GetChapterTask(context, callback);
        getChapterTask.execute(useCache);
    }

    public static class GetChapterTask extends AsyncTask<Boolean, Void, List<Chapter>> {
        private ChapterDao mChapterDao = new ChapterDao();
        private Context context;
        private Callback callback;
        private Exception ex;

        public GetChapterTask(Context context, Callback callback) {
            this.context = context;
            this.callback = callback;
        }

        @Override
        protected List<Chapter> doInBackground(Boolean... booleans) {
            List<Chapter> chapterList= new ArrayList<>();
            boolean useCache = booleans[0];
            try{
                if (useCache) {
                    // TODO loadData from db
                    List<Chapter> chapterListFromDb = mChapterDao.loadFromDb(context);
                    chapterList.addAll(chapterListFromDb);
                }

                if (chapterList.isEmpty()){
                    // TODO getData from net
                    List<Chapter> chapterFromNet = loadFromNet(context);
//                    Log.e("length", String.valueOf(chapterFromNet.size()));
                    mChapterDao.insert2Db(context, chapterFromNet);
                    // Add all data to db
                    chapterList.addAll(chapterFromNet);
                }
            } catch (Exception ex) {
                this.ex = ex;
            }
            return chapterList;
        }

        @Override
        protected void onPostExecute(List<Chapter> chapterList) {
            if (ex != null) {
                callback.onFailed(ex);
                return;
            }
            callback.onSuccess(chapterList);
        }

        public List<Chapter> loadFromNet(Context context) {
            // getData
            String content = HttpUtils.doGet(URL);
            // tranfer string to model
            List<Chapter> chapterList = parseContent(content);
            return chapterList;
        }

        private List<Chapter> parseContent(String content) {
            List<Chapter> chapterList = new ArrayList<>();

            if (content!=null) {
                try {
                    JSONObject root = new JSONObject(content);
                    int errorCode = root.optInt("errorCode");
                    if (errorCode == 0) {
                        JSONArray dataJsonArray =  root.optJSONArray("data");
                        for (int i=0; i < dataJsonArray.length();i++ ) {
                            JSONObject chapterJsonObject = dataJsonArray.getJSONObject(i);
                            Chapter chapter = new Chapter(chapterJsonObject.getInt("id"), chapterJsonObject.getString("name"));
                            chapterList.add(chapter);
                            // parse chapterItem
                            JSONArray itemJsonArray = chapterJsonObject.getJSONArray("children");
                            List<ChapterItem> chapterItemList = new ArrayList<>();
                            for (int j=0; j < itemJsonArray.length(); j++) {
                                JSONObject itemJsonObject = itemJsonArray.getJSONObject(j);
                                ChapterItem chapterItem = new ChapterItem(itemJsonObject.getInt("id"), itemJsonObject.getString("name"));
                                chapter.addChild(chapterItem);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return chapterList;
        }
    }



    public  static interface Callback {
        void onSuccess(List<Chapter> chapterList);
        void onFailed(Exception e);
    }
}
