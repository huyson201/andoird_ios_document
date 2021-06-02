package com.edu.vn.multimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.edu.vn.multimedia.services.MultiMediaService;
import com.edu.vn.multimedia.widgets.IPlayer;
import com.edu.vn.multimedia.widgets.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IPlayer {
    private Player controlPlayer;
    private final int REQUEST_CODE = 1;
    private MultiMediaService multiMediaService;
    private TextView lblMusicName;

    // define service connection
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            multiMediaService = ((MultiMediaService.MultiMediaBinder) service).getMultiMediaService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "Disconnected MultiMediaService", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // request permission if false
        if (!checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        } else {
            init();

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        }
    }


    //check permission
    private boolean checkPermission(String permission) {
        int check = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            check = checkSelfPermission(permission);
        }

        return (check == PackageManager.PERMISSION_GRANTED);
    }

    private void init() {
        controlPlayer = findViewById(R.id.control_player);
        controlPlayer.setIPlayer(this);
        lblMusicName = findViewById(R.id.lbl_music_name);

        //connecting service
        Intent intent = new Intent(MainActivity.this, MultiMediaService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPlayAction() {

        if (multiMediaService != null) {
            multiMediaService.play();
            String musicName = multiMediaService.getMusicName();
            String[] array = musicName.split("/");
            lblMusicName.setText(array[array.length - 1]);
        }

    }

    @Override
    public void onNextAction() {
        if (multiMediaService != null) {
            multiMediaService.next();
        }

    }

    @Override
    public void onStopAction() {
        if (multiMediaService != null) {
            multiMediaService.stop();
        }

    }

    @Override
    public void onPauseAction() {
        if (multiMediaService != null) {
            multiMediaService.pause();
        }

    }

    @Override
    public void onPrevAction() {
        if(multiMediaService != null){
            multiMediaService.prev();
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}