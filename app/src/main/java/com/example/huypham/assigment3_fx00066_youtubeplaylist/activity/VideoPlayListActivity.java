package com.example.huypham.assigment3_fx00066_youtubeplaylist.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.huypham.assigment3_fx00066_youtubeplaylist.R;
import com.example.huypham.assigment3_fx00066_youtubeplaylist.sqlite.SQliteHelper;
import com.example.huypham.assigment3_fx00066_youtubeplaylist.adapter.VideoAdapter;
import com.example.huypham.assigment3_fx00066_youtubeplaylist.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class VideoPlayListActivity extends AppCompatActivity {
    private ArrayList<Video> listVideo;
    boolean isHistory = false;
    VideoAdapter adapter;
    String API_URI = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=10&playlistId=";
    String PLAYLIST_ID = "PLQr2HibLEZe-yFsYsLkEfHovicdGBPulG";
    String KEY_BROWSE = "AIzaSyBFOYdKSAcey2-wS9_mGw0oeJsEf-26YZ8";
    SQliteHelper helper;
    String userName;
    public static void open(Activity activity,String userName){
        Intent intent = new Intent(activity, VideoPlayListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("userName",userName);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play_list);
        new ParseVideoYoutube().execute();
        helper = new SQliteHelper(this);
        Intent intent = getIntent();
        userName= intent.getStringExtra("userName");
        initToolBar(userName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_video,menu);
        return true;
    }

    private void initToolBar(String userName){
        getSupportActionBar().setTitle("Hi " + userName +", Welcome to Funix");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initView(ArrayList<Video> videos,String userName){
        this.listVideo = videos;
        adapter = new VideoAdapter(this,listVideo,userName);
        RecyclerView recyclerView = findViewById(R.id.playListView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if(isHistory) VideoPlayListActivity.open(VideoPlayListActivity.this,userName);

        else super.onBackPressed();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(isHistory){
            menu.getItem(0).setVisible(false); // Set history disable
            menu.getItem(1).setVisible(true); // Set delete history enable
        }else {
            menu.getItem(0).setVisible(true); // Set history enable
            menu.getItem(1).setVisible(false); // Set delete history disable
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            if (isHistory){
                VideoPlayListActivity.open(VideoPlayListActivity.this,userName);
            }
            else super.onBackPressed();
        }
        if(id == R.id.history){
            /* Get video ID from Database history, after that get List<String> videoID and find update for adapter */
            isHistory = true;
            List<String> listVideoTemp;
            ArrayList<Video> listVideoHistory = new ArrayList<>();
            SQliteHelper helper = new SQliteHelper(this);
            listVideoTemp = helper.getVideosByUser(userName);
            if(listVideoTemp.size() != 0) {
                for(String videoID : listVideoTemp){
                    for(Video video: listVideo){
                        if (video.getVideoID().equals(videoID)){
                            listVideoHistory.add(video);
                        }
                    }
                }
            }
            if(listVideoHistory.size()!=0){
                this.listVideo = listVideoHistory;
               initView(listVideo,userName);
            }
            else{
                this.listVideo = null;
                initView(listVideo,userName);
            }
            getSupportActionBar().setTitle("History");
        }
        if(id == R.id.delete){
            SQliteHelper helper = new SQliteHelper(this);
            helper.deleteVideoHistory(userName);

            VideoPlayListActivity.open(VideoPlayListActivity.this,userName);
        }
        if(id == R.id.signOut){
            // bat mainActivity
            // xoa dang nhap
            SharedPreferences sharedPreference = getSharedPreferences("dataLogin",MODE_PRIVATE);
            Editor editor = sharedPreference.edit();
            editor.putBoolean("isSignIn",false);
            editor.commit();
            MainActivity.open(VideoPlayListActivity.this);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    // Get listVideo tu API YOUTUBE va initView sau khi get duoc list.
    private class ParseVideoYoutube extends AsyncTask<Void,Void, ArrayList<Video>>{

        @Override
        protected ArrayList<Video> doInBackground(Void... params) {
            ArrayList<Video> listVideo = new ArrayList<>();
            URL jSonUrl;
            URLConnection jSonConnect;
            try {
                jSonUrl = new URL(API_URI + PLAYLIST_ID + "&key=" + KEY_BROWSE);
                jSonConnect = jSonUrl.openConnection();
                InputStream inputstream = jSonConnect.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                inputstream.close();
                String jSontxt = stringBuilder.toString();
                JSONObject jsonobject = new JSONObject(jSontxt);
                JSONArray allItem = jsonobject.getJSONArray("items");
                for (int i = 0; i < allItem.length(); i++) {
                    JSONObject item = allItem.getJSONObject(i);
                    JSONObject snippet = item.getJSONObject("snippet");
                    String title = snippet.getString("title");              // Get Title Video
                    String description = snippet.getString("description");   // Get Description
                    JSONObject thumbnails = snippet.getJSONObject("thumbnails");    //Get Url Thumnail
                    JSONObject thumbnailIMG = thumbnails.getJSONObject("medium");
                    String thumbnailUrl = thumbnailIMG.getString("url");

                    JSONObject resourceId = snippet.getJSONObject("resourceId");    //Get ID Video
                    String videoId = resourceId.getString("videoId");

                    // Set to video object
                    Video video = new Video();
                    video.setVideoTitle(title);
                    video.setVideoThumbnails(thumbnailUrl);
                    video.setVideoDescription(description);
                    video.setVideoID(videoId);

                    //Add video to List Video
                    listVideo.add(video);
                }
            }catch(IOException e){
                    e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return listVideo;
            }

        @Override
        protected void onPostExecute(ArrayList<Video> videos) {
            super.onPostExecute(videos);
            initView(videos,userName);
        }
    }


}
