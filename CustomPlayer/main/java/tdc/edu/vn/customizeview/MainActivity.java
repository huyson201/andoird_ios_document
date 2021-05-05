package tdc.edu.vn.customizeview;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import tdc.edu.vn.customizeview.widgets.IPlayer;
import tdc.edu.vn.customizeview.widgets.Player;

public class MainActivity extends AppCompatActivity implements IPlayer {
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = (Player) findViewById(R.id.player);
        player.setiPlayer(this);
    }


    @Override
    public void onPlayAction() {
        Toast.makeText(this, "onPlay()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNextAction() {
        Toast.makeText(this, "onNext()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopAction() {
        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPauseAction() {
        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrevAction() {
        Toast.makeText(this, "onPrev()", Toast.LENGTH_SHORT).show();
    }
}
