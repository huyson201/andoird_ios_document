package com.edu.vn.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.edu.vn.multimedia.widgets.IPlayer;
import com.edu.vn.multimedia.widgets.Player;

public class MainActivity extends AppCompatActivity implements IPlayer {
    private Player controlPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlPlayer = findViewById(R.id.control_player);
        controlPlayer.setiPlayer(this);

    }

    @Override
    public void onPlayAction() {

    }

    @Override
    public void onNextAction() {

    }

    @Override
    public void onStopAction() {

    }

    @Override
    public void onPauseAction() {

    }

    @Override
    public void onPrevAction() {

    }
}