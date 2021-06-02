package com.edu.vn.multimedia.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MultiMediaService extends Service {
    private IBinder binder = new MultiMediaBinder();
    private ArrayList<String> listMusics;
    private int currentMusic = 0;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    //default constructor
    public MultiMediaService() {
    }

    //define class of binder object
    public class MultiMediaBinder extends Binder {
        public MultiMediaService getMultiMediaService() {
            return MultiMediaService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        // get music
        listMusics = getMusicPaths();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return this.binder;
    }

    public void play() {
        if (!this.isPlaying) {
            if (mediaPlayer != null) {
                if (listMusics.size() > 0) {
                    Uri uri = Uri.parse(listMusics.get(currentMusic));
                    // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    );
                    mediaPlayer.setVolume(1.0f, 1.0f);

                    //play complete listen

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next();
                            play();
                        }
                    });
                    try {
                        mediaPlayer.reset();
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
                    this.isPlaying = true;
                } else {
                    Toast.makeText(this, "there aren't any music file", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            mediaPlayer.start();
            this.isPlaying = true;
        }
    }

    public void pause() {
        if (isPlaying) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPlaying = false;
        }
    }

    public void next() {
        if (isPlaying) {
            mediaPlayer.stop();
            isPlaying = false;
        }

        if (currentMusic < listMusics.size() - 1) {
            currentMusic++;
        } else {
            currentMusic = 0;
        }
    }

    public void prev() {
        if (isPlaying) {
            mediaPlayer.stop();
            isPlaying = false;
        }

        if (currentMusic > 0) {
            currentMusic--;
        } else {
            currentMusic = listMusics.size() - 1;
        }
    }

    private ArrayList<String> getMusicPaths() {
        ArrayList<String> musics = new ArrayList<String>();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath());

        if (file.listFiles() != null) {
            for (File fileItem : file.listFiles()) {
                musics.add(fileItem.getAbsolutePath());
            }
        } else {
            Toast.makeText(this, "Folder empty", Toast.LENGTH_LONG).show();
        }
        return musics;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    public String getMusicName(){
        return  listMusics.get(currentMusic);
    }
}