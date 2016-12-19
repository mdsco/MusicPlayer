package com.example.mike.musicplayer;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class SongListAdapter extends ArrayAdapter<HashMap<String, String>> {

    private final Context context;
    List<HashMap<String, String>> songList;

    public SongListAdapter(Context context, int listview, List<HashMap<String, String>> songlist) {
        super(context, listview, songlist);

        this.context = context;
        this.songList = songlist;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song_list_item, parent, false);
        }

        String song = songList.get(position).get("song_title");
        String path = songList.get(position).get("song_path");

        TextView songTextView = (TextView) convertView.findViewById(R.id.song_text);
        songTextView.setText(song);
        songTextView.setTag(path);

        return convertView;
    }
}
