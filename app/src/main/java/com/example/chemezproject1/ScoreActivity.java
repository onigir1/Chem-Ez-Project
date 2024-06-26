package com.example.chemezproject1;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.color.utilities.Score;
import com.google.protobuf.StringValue;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

import Models.QuestionModel;

public class ScoreActivity extends AppCompatActivity {
    private TextView scoreTV, timeTV, totalQTV, correctQTV, wrongQTV, unattemptedQTV;
    Button leaderb, reAttemptB, viewAnsB;
    private long timeTaken;
    private Dialog progressDialog;
    private TextView dialogText;
    private int finalScore;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //showing the progress dialog
        progressDialog = new Dialog(ScoreActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading....");
        progressDialog.show();


        init();

        loadData();

        setBookMarks();

        viewAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ScoreActivity.this, AnswersActivity.class);
                startActivity(intent);

            }
        });

        leaderb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bottomNavigationView.setSelectedItemId(R.id.navigation_leaderboard);
            }
        });

        reAttemptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reAttempt();
            }
        });

        saveResult();

    }

    private void init() {
        scoreTV = findViewById(R.id.score);
        timeTV = findViewById(R.id.time);
        totalQTV = findViewById(R.id.totalQ);
        correctQTV = findViewById(R.id.correctQ);
        wrongQTV = findViewById(R.id.wrongQ);
        unattemptedQTV = findViewById(R.id.un_attemptedQ);
        leaderb = findViewById(R.id.leaderB);
        reAttemptB = findViewById(R.id.reattemptB);
        viewAnsB = findViewById(R.id.view_answerB);


    }

    private void loadData()
    {
        int correctQ =0, wrongQ = 0, unattemptQ = 0;

        for(int i = 0; i<DbQuery.g_quesList.size(); i++)
        {
            if(DbQuery.g_quesList.get(i).getSelectedAns() == -1)
            {
                unattemptQ ++;

            }
            else
            {
                if(DbQuery.g_quesList.get(i).getSelectedAns() == DbQuery.g_quesList.get(i).getCorrectAns())
                {
                    correctQ++;
                }
                else
                {
                    wrongQ++;
                }

            }

        }

        correctQTV.setText(String.valueOf(correctQ));
        wrongQTV.setText(String.valueOf(wrongQ));
        unattemptedQTV.setText(String.valueOf(unattemptQ));

        totalQTV.setText(String.valueOf(DbQuery.g_quesList.size()));


        finalScore = (correctQ*100)/DbQuery.g_quesList.size();
        scoreTV.setText(String.valueOf(finalScore));

        timeTaken = getIntent().getLongExtra("TIME TAKEN", 0);

        String time = String.format("%02d:%02d min",
                TimeUnit.MILLISECONDS.toMinutes(timeTaken),
                TimeUnit.MILLISECONDS.toSeconds(timeTaken) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken))
        );

        timeTV.setText(time);



    }

    private void reAttempt()
    {
        for(int i = 0; i < DbQuery.g_quesList.size(); i++ )
        {
            DbQuery.g_quesList.get(i).setSelectedAns(-1);
            DbQuery.g_quesList.get(i).setStatus(DbQuery.NOT_VISITED);

        }

        Intent intent = new Intent(ScoreActivity.this, StartTestActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveResult()
    {
        DbQuery.saveResult(finalScore, new MyCompleteListener() {
            @Override
            public void onSuccess() {

                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {

                Toast.makeText(ScoreActivity.this, "Something went wrong ! Please try again later", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void setBookMarks()
    {
        for( int i = 0; i < DbQuery.g_quesList.size(); i++ )
        {
            QuestionModel question = DbQuery.g_quesList.get(i);

            if( question.isBookmarked())
            {
                if( ! DbQuery.g_bmIdList.contains(question.getqID()) )
                {
                    DbQuery.g_bmIdList.add(question.getqID());
                    DbQuery.myProfile.setBookmarksCount(DbQuery.g_bmIdList.size());
                }
            }
            else
            {
                if(DbQuery.g_bmIdList.contains(question.getqID()))
                {
                    DbQuery.g_bmIdList.remove(question.getqID());
                    DbQuery.myProfile.setBookmarksCount(DbQuery.g_bmIdList.size());
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            ScoreActivity.this.finish();
            //return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
