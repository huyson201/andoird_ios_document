package com.edu.vn.multimedia.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.edu.vn.multimedia.R;


public class Player extends LinearLayout {
    private ImageView preBtn, playBtn, pauseBtn, stopBtn, nextBtn;
    private ViewGroup playerLayout;
    private IPlayer iPlayer;

    public Player(Context context) {
        super(context);
        init();
    }

    public Player(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Player(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int countChild = playerLayout.getChildCount();
            View child = null;

            if (v.isSelected()){
                v.setSelected(false);
            }
            else {

                v.setSelected(true);

                for(int i = 0; i < countChild; ++i){
                    if ((child = playerLayout.getChildAt(i)).getId() != v.getId()){
                        child.setSelected(false);
                    }
                }
            }

            if(iPlayer == null){
                Toast.makeText(getContext(), "iplayer null", Toast.LENGTH_SHORT).show();
            }

            if(iPlayer != null){

                switch (v.getId()){
                    case R.id.playBtn:
                        if(playBtn.isSelected()){
                            iPlayer.onPlayAction();
                        }else{
                            pauseBtn.setSelected(true);
                            iPlayer.onPauseAction();
                        }
                        break;
                    case R.id.stopBtn:

                        if(stopBtn.isSelected()){
                            iPlayer.onStopAction();
                        }else{
                            iPlayer.onPlayAction();
                            playBtn.setSelected(true);
                        }
                        break;
                    case R.id.preBtn:
                        if(preBtn.isSelected()){
                            iPlayer.onPrevAction();
                        }else{
                            iPlayer.onNextAction();
                            nextBtn.setSelected(true);
                        }

                        break;
                    case R.id.nextBtn:
                        if(nextBtn.isSelected()){
                            iPlayer.onNextAction();
                        }else{
                            iPlayer.onPrevAction();
                            preBtn.setSelected(true);
                        }
                        break;
                    case R.id.pauseBtn:
                        if(pauseBtn.isSelected()){
                            iPlayer.onPauseAction();
                        }else{
                            iPlayer.onPlayAction();
                            playBtn.setSelected(true);
                        }
                        break;
                }
            }
        }

    };

    public ImageView getPreBtn() {
        return preBtn;
    }

    public ImageView getPlayBtn() {
        return playBtn;
    }

    public ImageView getPauseBtn() {
        return pauseBtn;
    }

    public ImageView getStopBtn() {
        return stopBtn;
    }

    public ImageView getNextBtn() {
        return nextBtn;
    }

    private void init(){

        inflate(getContext(), R.layout.control_layout,this);

        playerLayout = (ViewGroup) getChildAt(0);

        preBtn = playerLayout.findViewById(R.id.preBtn);
        nextBtn = playerLayout.findViewById(R.id.nextBtn);
        stopBtn = playerLayout.findViewById(R.id.stopBtn);
        playBtn = playerLayout.findViewById(R.id.playBtn);
        pauseBtn = playerLayout.findViewById(R.id.pauseBtn);
        preBtn.setOnClickListener(clickListener);
        nextBtn.setOnClickListener(clickListener);
        stopBtn.setOnClickListener(clickListener);
        playBtn.setOnClickListener(clickListener);
        pauseBtn.setOnClickListener(clickListener);


    }

    public void setIPlayer(IPlayer iPlayer) {
        this.iPlayer = iPlayer;
    }

}
