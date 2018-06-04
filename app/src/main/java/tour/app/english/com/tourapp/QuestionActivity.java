package tour.app.english.com.tourapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppCompatActivity{

    private String input_text = "";
    private String englishQuestion;
    private String answer;

    private int click = 0;
    private TextView textview;

    private LinearLayout questionlayout;
    private FragmentManager fm;

    //프로그레스바 코드
    private ProgressBar progressBar;
    private TextView timertxt;
    private BackgroundTask task;
    private int value;
    //

    private List<String> savesplit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();

        englishQuestion = intent.getStringExtra("englishQuestion");
        answer = intent.getStringExtra("answer");

        textview = findViewById(R.id.inputtext);

        fm = getSupportFragmentManager();

        questionlayout = (LinearLayout) findViewById(R.id.layout_infragment);

        createbutton();

        ImageView timeoutimage = findViewById(R.id.timeoutimage);
        timeoutimage.setVisibility(View.GONE);

        //프로그레스바 코드
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        timertxt = (TextView) findViewById(R.id.txt);

        task = new BackgroundTask();
        task.execute(100);
    }

    //하단에 버튼 랜덤으로 뿌려주기
    public void createbutton() {

        LinearLayout qbuttonlayout1 = findViewById(R.id.qbuttonLayout1);
        LinearLayout qbuttonlayout2 = findViewById(R.id.qbuttonLayout2);

        SplitSentence splitSentence = new SplitSentence();
        String inputquestion = englishQuestion;
        savesplit = splitSentence.splitString(inputquestion);

        Collections.shuffle(savesplit);

        for (int i = 0; i < savesplit.size(); i++) {
            final Button btn = new Button(this);
            btn.setText(savesplit.get(i));

            if (i < 4) {
                qbuttonlayout1.addView(btn);
            } else {
                qbuttonlayout2.addView(btn);
            }
            //버튼 누를때마다 텍스트뷰에 띄우기
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (click == 0) {
                        input_text += btn.getText();
                        textview.setText(input_text);
                        click++;
                    } else {
                        if (!textview.getText().toString().contains(btn.getText().toString())) {
                            input_text += btn.getText();
                            textview.setText(input_text);
                            click++;

                            if (click == savesplit.size()){
                                task.cancel(true);
                                confirmDialog(QuestionActivity.this);
                            }

                        }
                    }
                }
            });
        }
    }

    private void confirmDialog(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("정답 확인.");
        final TextView textView = new TextView(context);
        if (input_text.equals(answer)) {
            textView.setText("정답입니다.");
        } else {
            textView.setText("틀렸습니다.");
        }
        dialog.setView(textView);
        dialog.setPositiveButton("다시 풀기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("다른 문제", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = getIntent();
                finish();
            }
        });
        dialog.show();
    }

    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {

        ImageView timeoutimage = findViewById(R.id.timeoutimage);

        SoundPool soundPool = new SoundPool(savesplit.size() + 5, AudioManager.STREAM_MUSIC, 0);
        int SoundId = soundPool.load(QuestionActivity.this, R.raw.timeoutsound, 1);

        @Override
        protected void onPreExecute() {
            value = savesplit.size() + 5;
            progressBar.setProgress(value);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            while (isCancelled() == false) {
                value--;
                if (value <= 0) {
                    break;
                } else {
                    publishProgress(value);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return value;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0].intValue());
            timertxt.setText(values[0].toString() + "초");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressBar.setProgress(0);
            timertxt.setText("시간초과!");

            timeoutimage.setVisibility(View.VISIBLE);
            soundPool.play(SoundId, 3f, 3f, 0, 0, 1f);
            AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
            builder.setTitle("시간초과 알림창");
            builder.setMessage("조금 더 분발하세요");
            builder.setPositiveButton("메인화면", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    soundPool.stop(SoundId);
                    moveTaskToBack(true);

                    Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                    startActivity(intent);

                }

            }).setNegativeButton("다시 볼래요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }).show();
        }
    }
}