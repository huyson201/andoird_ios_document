package com.edu.vn.multimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import android.Manifest;
import com.edu.vn.multimedia.widgets.IPlayer;
import com.edu.vn.multimedia.widgets.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IPlayer {
    private Player controlPlayer;
    private ArrayList<String> listMusics;
    private final int REQUEST_CODE = 1;
    private int currentMusic = 0;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // request permission if false
        if(!checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
            }
        }else {
            controlPlayer = findViewById(R.id.control_player);
            controlPlayer.setIPlayer(this);
            mediaPlayer = new MediaPlayer();
            // get music
            listMusics = getMusicPaths();
            Log.d("musicPath", listMusics.toString());
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            mediaPlayer = new MediaPlayer();
            controlPlayer = findViewById(R.id.control_player);
            controlPlayer.setIPlayer(this);

            // get music
            listMusics = getMusicPaths();
            Log.d("musicPath", listMusics.toString());
        }
    }

    private ArrayList<String> getMusicPaths() {
        ArrayList<String> musics = new ArrayList<String>();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath());

        if(file.listFiles() != null){
            for (File fileItem: file.listFiles()) {
                musics.add(fileItem.getAbsolutePath());
            }
        }else{
            Toast.makeText(this, "Folder empty",Toast.LENGTH_LONG).show();
        }
        return musics;
    }

    //check permission
    private boolean checkPermission(String permission){
        int check = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            check = checkSelfPermission(permission);
        }

        return (check == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onPlayAction() {
        if(mediaPlayer != null){
            if(listMusics.size() > 0){
                Uri uri = Uri.parse(listMusics.get(currentMusic ));
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(this, uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();

            }else{
                Toast.makeText(this, "there aren't any music file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNextAction() {

    }

    @Override
    public void onStopAction() {

    }

    @Override
    public void onPauseAction() {
        mediaPlayer.pause();
    }

    @Override
    public void onPrevAction() {

    }
}