package com.example.mike.musicplayer;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    List<HashMap<String, String>> songList;
    MediaPlayer player;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = new MediaPlayer();

        Cursor cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        songList = getMusicList(cursor);

        SongListAdapter songListAdapter =
                new SongListAdapter(this, R.id.song_list_view, songList);

        ListView songList = (ListView) findViewById(R.id.song_list_view);
        songList.setAdapter(songListAdapter);

        songList.setOnItemClickListener(getSongListClickListener());

    }

    private AdapterView.OnItemClickListener getSongListClickListener(){

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String tag = view.getTag().toString();
                String songTitle = ((TextView) view).getText().toString();

                if(player.isPlaying()){
                    stopSongPlay(view);
                }

                startSongPlay(tag, songTitle);
            }
        };
    }

    private void startSongPlay(String tag, String songTitle) {
        player = MediaPlayer.create(getBaseContext(), Uri.parse(tag));
        player.start();
        Toast.makeText(getBaseContext(), "Playing: " + songTitle, Toast.LENGTH_SHORT).show();
    }

    private List<HashMap<String, String>> getMusicList(Cursor cursor) {
        if (cursor.moveToFirst()) {

            if(songList == null) {
                songList = new ArrayList<>();
            }
//            String[] columnNames = cursor.getColumnNames();

//            for(String name : columnNames)
//                System.out.println("Column: " + name);

            int titleIndex = cursor.getColumnIndex("title");
            int pathIndex = cursor.getColumnIndex("_data");
            int isMusicIndex = cursor.getColumnIndex("is_music");

            do {

                String isMusic = cursor.getString(isMusicIndex);

                if (isMusic.equals("1")) {

                    String songTitle = cursor.getString(titleIndex);
                    String songPath = cursor.getString(pathIndex);

                    HashMap<String, String> songMap = new HashMap<String, String>();

                    songMap.put("song_title", songTitle);
                    songMap.put("song_path", songPath);

                    songList.add(songMap);

                    Log.v("Song Info", "Title: " + songTitle + " Path: "
                            + songPath + " isMusic: " + isMusic);
                }

            } while (cursor.moveToNext());

            cursor.close();
        }

        return songList;
    }

    public void stopSongPlay(View view) {

        player.stop();

    }
}
