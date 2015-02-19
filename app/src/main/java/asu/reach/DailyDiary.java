package asu.reach;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DailyDiary extends Activity implements View.OnClickListener{

    private ImageButton respond,back,next,done,cancel,clear,voice,complete;
    private LinearLayout nav,respBtns;
    private RelativeLayout blob,resp,gjLayout;
    private ImageView message,gjView,title;
    private TextView today;
    private EditText response,date;
    private VideoView gj;
    private HorizontalScrollView therm;
    private int state = 0;
    private final int ONE_STATE = 0;
    private final int TWO_STATE = 1;
    private final int THREE_STATE = 2;
    private final int FOUR_STATE = 3;
    private static final int SPEECH_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_daily_diary);

        respond = (ImageButton)findViewById(R.id.respondBtn);
        back = (ImageButton)findViewById(R.id.backBtn);
        next = (ImageButton)findViewById(R.id.nextBtn);
        message = (ImageView)findViewById(R.id.messageView);
        response = (EditText)findViewById(R.id.responseTxt);
        date = (EditText)findViewById(R.id.dateInput);
        today = (TextView)findViewById(R.id.todayView);
        done = (ImageButton)findViewById(R.id.doneBtn);
        cancel = (ImageButton)findViewById(R.id.cancelBtn);
        clear = (ImageButton)findViewById(R.id.clearBtn);
        voice = (ImageButton)findViewById(R.id.voiceBtn);
        nav = (LinearLayout)findViewById(R.id.navLayout);
        respBtns = (LinearLayout)findViewById(R.id.respBtnLayout);
        resp = (RelativeLayout)findViewById(R.id.respLayout);
        blob = (RelativeLayout)findViewById(R.id.blobLayout);
        gjView = (ImageView)findViewById(R.id.gjView);
        gjLayout = (RelativeLayout)findViewById(R.id.gjLayout);
        complete = (ImageButton)findViewById(R.id.completeBtn);
        title = (ImageView)findViewById(R.id.titleView);
        gj = (VideoView)findViewById(R.id.gjVid);
        therm = (HorizontalScrollView)findViewById(R.id.thermView);

        response.setTypeface(Typeface.createFromAsset(getAssets(), "agentorange.ttf"));
        date.setTypeface(Typeface.createFromAsset(getAssets(), "agentorange.ttf"));
        today.setTypeface(Typeface.createFromAsset(getAssets(), "agentorange.ttf"));
        respond.setOnClickListener(this);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        done.setOnClickListener(this);
        cancel.setOnClickListener(this);
        clear.setOnClickListener(this);
        voice.setOnClickListener(this);
        complete.setOnClickListener(this);

        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yy");
        date.setText(f.format(now));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daily_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == respond.getId()){
            respond.setActivated(true);
            nav.setVisibility(View.GONE);
            blob.setVisibility(View.GONE);
            resp.setVisibility(View.VISIBLE);
            respBtns.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
        }
        if(v.getId() == done.getId()){
            nav.setVisibility(View.VISIBLE);
            blob.setVisibility(View.VISIBLE);
            resp.setVisibility(View.GONE);
            respBtns.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        }
        if(v.getId() == cancel.getId()){
            nav.setVisibility(View.VISIBLE);
            blob.setVisibility(View.VISIBLE);
            resp.setVisibility(View.GONE);
            respBtns.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        }
        if(v.getId() == clear.getId()){
            response.setText("");
        }
        if(v.getId() == voice.getId()){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }
        if(v.getId() == next.getId()){
            switch(state){
                case ONE_STATE:{
                    if(respond.isActivated()) {
                        message.setBackgroundResource(R.drawable.dd_2_message);
                        today.setVisibility(View.GONE);
                        date.setVisibility(View.GONE);
                        therm.setVisibility(View.VISIBLE);
                        respond.setVisibility(View.GONE);
                        state = TWO_STATE;
                    }else{
                        Toast.makeText(this, "Please respond first", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case TWO_STATE:{
                    if(respond.isActivated()) {
                        message.setBackgroundResource(R.drawable.dd_3_message);
                        therm.setVisibility(View.GONE);
                        respond.setVisibility(View.VISIBLE);
                        respond.setActivated(false);
                        state=THREE_STATE;
                    }else{
                        Toast.makeText(this, "Please respond first", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case THREE_STATE:{
                    if(respond.isActivated()) {
                        message.setBackgroundResource(R.drawable.dd_4_message);
                        respond.setActivated(false);
                        state=FOUR_STATE;
                    }else{
                        Toast.makeText(this, "Please respond first", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case FOUR_STATE:{
                    title.setVisibility(View.GONE);
                    blob.setVisibility(View.GONE);
                    nav.setVisibility(View.GONE);
                    gjLayout.setVisibility(View.VISIBLE);
                    gjView.setVisibility(View.VISIBLE);
                    complete.setVisibility(View.VISIBLE);
                    gj.setVideoURI(Uri.parse("android.resource://asu.reach/" + R.raw.stars));
                    gj.start();
                    gj.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setLooping(true);
                            title.setVisibility(View.GONE);
                            blob.setVisibility(View.GONE);
                            nav.setVisibility(View.GONE);
                            gjLayout.setVisibility(View.VISIBLE);
                            gjView.setVisibility(View.VISIBLE);
                            complete.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
                }
            }
        }
        if(v.getId() == back.getId()){
            switch(state){
                case ONE_STATE:{
                    finish();
                    break;
                }
                case TWO_STATE:{
                    message.setBackgroundResource(R.drawable.dd_1_message);
                    today.setVisibility(View.VISIBLE);
                    date.setVisibility(View.VISIBLE);
                    therm.setVisibility(View.GONE);
                    respond.setVisibility(View.VISIBLE);
                    respond.setActivated(true);
                    state=ONE_STATE;
                    break;
                }
                case THREE_STATE:{
                    message.setBackgroundResource(R.drawable.dd_2_message);
                    therm.setVisibility(View.VISIBLE);
                    respond.setVisibility(View.GONE);
                    respond.setActivated(true);
                    state=TWO_STATE;
                    break;
                }
                case FOUR_STATE:{
                    message.setBackgroundResource(R.drawable.dd_3_message);
                    respond.setActivated(true);
                    state=THREE_STATE;
                    break;
                }
            }
        }
        if(v.getId()==complete.getId()){
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            response.setText(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
