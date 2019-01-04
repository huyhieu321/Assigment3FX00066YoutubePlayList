package com.example.huypham.assigment3_fx00066_youtubeplaylist.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huypham.assigment3_fx00066_youtubeplaylist.R;
import com.example.huypham.assigment3_fx00066_youtubeplaylist.sqlite.SQliteHelper;
import com.example.huypham.assigment3_fx00066_youtubeplaylist.activity.PlayYoutubeVideoActivity;
import com.example.huypham.assigment3_fx00066_youtubeplaylist.model.Video;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private ArrayList<Video> listVideo;
    private Activity activity;
    String userName;
    public VideoAdapter(Activity activity,ArrayList<Video> listVideo,String userName) {
        this.listVideo = listVideo;
        this.activity = activity;
        this.userName = userName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.video_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        // bind data
        final Video video =listVideo.get(position);
        viewHolder.txtTitle.setText(video.getVideoTitle());
        viewHolder.txtDes.setText(video.getVideoDescription());
        if(video.getVideoThumbnails()!=null) Glide.with(activity).load(video.getVideoThumbnails()).into(viewHolder.imgThumbnails);
        viewHolder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Video",video);
                SQliteHelper helper = new SQliteHelper(activity);
                if (helper.isVideoExistHistory(video.getVideoID())==false){
                    helper.addVideoHistory(userName,video.getVideoID());
                }

                Intent intent = new Intent(activity, PlayYoutubeVideoActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if(listVideo !=null)
        return listVideo.size();
        else  return 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgThumbnails;
        TextView txtTitle,txtDes;
        public MyViewHolder(View itemView) {
            super(itemView);
            /// xu li event o day
            imgThumbnails = itemView.findViewById(R.id.imgThumbnails);
            txtDes = itemView.findViewById(R.id.txtDes);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
