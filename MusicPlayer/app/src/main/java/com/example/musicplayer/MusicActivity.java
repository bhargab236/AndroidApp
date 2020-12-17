package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.IconCompat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {
    SeekBar sb;
    TextView tv1, tv2;
    ImageButton ib1, ib2, ib3, ib4, ib5;
    ArrayList al;
    MediaPlayer mp;
    Thread t;
    int[] music = {R.raw.song, R.raw.aajkalzindagi, R.raw.iktara};
    int flag = 0, i, dur, tim, sec, min=0, m, s, cPos, st = 0, temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        sb = findViewById(R.id.seekBar);
        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        ib1 = findViewById(R.id.imageButton);
        ib2 = findViewById(R.id.imageButton2);
        ib3 = findViewById(R.id.imageButton3);
        ib4 = findViewById(R.id.imageButton4);
        ib5 = findViewById(R.id.imageButton5);
        al = new ArrayList();
        al.add(music);
        mp = MediaPlayer.create(this, R.raw.iktara);
        temp = 1;
        dur = mp.getDuration();
        tim = dur/1000;
        sec = tim;
        while(sec >= 60)
        {
            min = min+1;
            sec = sec-60;
        }
        tv2.setText(min+":"+sec);
        sb.setMax(tim);
        ib1.setOnClickListener(this);
        ib2.setOnClickListener(this);
        ib3.setOnClickListener(this);
        ib4.setOnClickListener(this);
        ib5.setOnClickListener(this);
        mp.setOnCompletionListener(this);
        sb.setOnSeekBarChangeListener(this);
        t = new Thread(){
          public void run(){
              for (i = 0; i <= tim;){
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              m = 0;
                              s = 0;
                              s = i;
                              while (s >= 60) {
                                  m = m + 1;
                                  s = s - 60;
                              }
                              tv1.setText(m + ":" + s);
                          }
                      });
                      sb.setProgress(i);
                      try {
                          Thread.sleep(1000);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                      if(flag == 1)
                          i++;
              }
          }
        };
    }

    @Override
    public void onClick(View v) {
        if(v == ib1)
        {
            mp.stop();
            if(temp == 1){
                mp = MediaPlayer.create(this, R.raw.song);
                temp = 3;
            }
            else if(temp == 2){
                mp = MediaPlayer.create(this, R.raw.iktara);
                temp = 1;
            }
            else{
                mp = MediaPlayer.create(this, R.raw.aajkalzindagi);
                temp = 2;
            }
            dur = 0;
            tim = 0;
            sec = 0;
            min = 0;
            dur = mp.getDuration();
            tim = dur/1000;
            sb.setMax(tim);
            sec = tim;
            while(sec >= 60)
            {
                min = min+1;
                sec = sec-60;
            }
            tv2.setText(min+":"+sec);
            i = 0;
            if(flag == 1) {
                ib3.setImageResource(R.drawable.ic_pause_black_31dp);
                mp.start();
            }
        }
        else if(v == ib2)
        {
            cPos = mp.getCurrentPosition();
            cPos = cPos/1000;
            if(cPos >= 10){
                mp.seekTo((cPos - 10)*1000);
                this.i = this.i - 10;
            }
        }
        else if(v == ib3)
        {
            if(flag == 0) {
                mp.start();
                flag = 1;
                if(st == 0) {
                    t.start();
                    st++;
                }
                ib3.setImageResource(R.drawable.ic_pause_black_31dp);
            }
            else{
                mp.pause();
                flag = 0;
                ib3.setImageResource(R.drawable.ic_play_arrow_black_31dp);
            }
        }
        else if(v == ib4)
        {
            cPos = mp.getCurrentPosition();
            cPos = cPos/1000;
            if(cPos <= tim-10) {
                mp.seekTo((cPos + 10) * 1000);
                this.i = this.i + 10;
            }
        }
        else
        {
            mp.stop();
            if(temp == 1) {
                mp = MediaPlayer.create(this, R.raw.aajkalzindagi);
                temp = 2;
            }
            else if(temp == 2){
                mp = MediaPlayer.create(this, R.raw.song);
                temp = 3;
            }
            else{
                mp = MediaPlayer.create(this, R.raw.iktara);
                temp = 1;
            }
            dur = 0;
            tim = 0;
            sec = 0;
            min = 0;
            dur = mp.getDuration();
            tim = dur/1000;
            sb.setMax(tim);
            sec = tim;
            while(sec >= 60)
            {
                min = min+1;
                sec = sec-60;
            }
            tv2.setText(min+":"+sec);
            i = 0;
            if(flag == 1) {
                ib3.setImageResource(R.drawable.ic_pause_black_31dp);
                mp.start();
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.i = progress;
        mp.seekTo(progress*1000);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(mp != null)
            mp.reset();
    }
}
