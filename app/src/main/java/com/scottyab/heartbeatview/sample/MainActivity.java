package com.scottyab.heartbeatview.sample;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.scottyab.HeartBeatView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HeartRateViewSample";
    private HeartBeatView heartbeat;
    private HeartBeatView heartbeat2;
    private HeartBeatView heartbeat3;
    private SeekBar beatsPerMinSeek;
    private TextView bpmTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heartbeat = (HeartBeatView) findViewById(R.id.heartbeat);
        heartbeat2 = (HeartBeatView) findViewById(R.id.heartbeat2);
        heartbeat3 = (HeartBeatView) findViewById(R.id.heartbeat3);
        bpmTV = (TextView) findViewById(R.id.bpm);
        beatsPerMinSeek = (SeekBar) findViewById(R.id.beatsPerMinSeek);


        heartbeat2.setDurationBasedOnBPM(50);
        beatsPerMinSeek.setMax(150);
        beatsPerMinSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //this gives use a BPM of 50 to 200 (roughly the lower and upper limits)
                final int bpm = progress + 50;
                Log.d(TAG, "BPM recorded as " + bpm);
                heartbeat2.setDurationBasedOnBPM(bpm);
                bpmTV.setText("BPM: " + bpm);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void heart1onClick(View view) {
        heartbeat.toggle();
    }

    public void heart2onClick(View view) {
        heartbeat2.toggle();
    }

    public void heart3onClick(View view) {
        heartbeat3.toggle();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
            showInfoDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showInfoDialog() {
        AlertDialog infoDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.info_details)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Github link", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/scottyab/HeartBeatView")));
                    }
                })
                .create();
        infoDialog.show();
    }
}
