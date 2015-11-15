package com.tgs.radioplayer;

import android.media.AudioTrack;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.spoledge.aacdecoder.MultiPlayer;
import com.spoledge.aacdecoder.PlayerCallback;

import java.net.URI;
import java.net.URISyntaxException;
import android.os.SystemClock;

public class MainActivity extends AppCompatActivity {

    private MultiPlayer multiPlayer;
    private String TAG_STREAM_TITLE = "StreamTitle";

    private TextView tvTitle;
    private TextView tvSocketData;

    private TextView tvContent;

    private int index = 0;
    private ListView lv;
    private TextView tvStation;
    private String mCurrentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tvTitle = (TextView) findViewById(R.id.title);
        tvStation = (TextView) findViewById(R.id.station);
        tvSocketData = (TextView) findViewById(R.id.socketData);
        tvContent = (TextView) findViewById(R.id.content);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (index) {
                    case 0: //case "Rock":
                        mCurrentUrl = "http://aac32.rock.chameleon.fm";
                        break;
                    case 1: //case "Hitporusski":
                        mCurrentUrl = "http://aac32.hitporusski.chameleon.fm";
                        break;
                    case 2: //case "Fresh":
                        mCurrentUrl = "http://aac32.fresh.chameleon.fm";
                        break;
                    case 3: //case "Edmotion":
                        mCurrentUrl = "http://aac32.edmotion.chameleon.fm";
                        break;
                    case 4: //case "Retro":
                        mCurrentUrl = "http://aac32.retro.chameleon.fm";
                        break;
                    case 5: //case "Dorognoe":
                        mCurrentUrl = "http://aac32.dorognoe.chameleon.fm";
                        break;
                    case 6: //case "Europaplus":
                        mCurrentUrl = "http://aac32.europaplus.chameleon.fm";
                        break;
                    case 7: //case "RDD":
                        mCurrentUrl = "http://aac32.rdd.chameleon.fm";
                        break;
                    case 8: //case "KEKS":
                        mCurrentUrl = "http://aac32.keks.chameleon.fm";
                        break;
                    case 9: //case "Radio7":
                        mCurrentUrl = "http://aac32.radio7.chameleon.fm";
                        break;
                    case 10: //case "zvezdyclassiki":
                        mCurrentUrl = "http://aac32.zvezdyclassiki.chameleon.fm";
                        break;
                    case 11: //case "musykaif":
                        mCurrentUrl = "http://aac32.musykaif.chameleon.fm";
                        break;
                    case 12: //case "hiphopstation":
                        mCurrentUrl = "http://aac32.hiphopstation.chameleon.fm";
                        break;
                }

                start();

                index++;
                if (index == 12) index = 0;
            }
        });
    }

    private void start() {
        stop();

        tvTitle.setText("Prepare...");
        tvContent.setText("");
        tvStation.setText("");
        multiPlayer = new MultiPlayer(); //this, getInt( txtBufAudio ), getInt( txtBufDecode ));

        multiPlayer.setPlayerCallback(new PlayerCallback() {
            @Override
            public void playerStarted() {
            }

            @Override
            public void playerPCMFeedBuffer(boolean b, int i, int i1) {

            }

            @Override
            public void playerStopped(int i) {

            }

            @Override
            public void playerException(Throwable throwable) {

            }

            @Override
            public void playerMetadata(String tag, String value) {
                setData(tag, value);
            }

            @Override
            public void playerAudioTrackCreated(AudioTrack audioTrack) {

            }
        });

        multiPlayer.playAsync(mCurrentUrl);
    }

    private void stop() {
        tvStation.setText("");
        tvTitle.setText("Stop.");
        tvContent.setText("");
        if (multiPlayer != null) {
            multiPlayer.stop();
            multiPlayer = null;
        }
    }

    private void setData(final String tag, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tag != null) {
                    if (tag.toLowerCase().compareTo(TAG_STREAM_TITLE.toLowerCase()) == 0) {
                        tvTitle.setText("Stream: " + value);
                    } else {
                        String newLine = tvContent.getText().length() > 0 ? "\n" : "";
                        tvContent.setText(tvContent.getText() + newLine + tag + ": " + value);
                    }
                } else {
                    if (value != null) {
                        if (!value.isEmpty()) {
                            String newLine = tvContent.getText().length() > 0 ? "\n" : "";
                            tvContent.setText(tvContent.getText() + newLine + value);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_start) {
            start();
            return true;
        }

        if (id == R.id.action_stop) {
            stop();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
