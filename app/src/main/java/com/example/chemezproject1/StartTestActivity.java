package com.example.chemezproject1;

import androidx.appcompat.app.AppCompatActivity;
import com.example.chemezproject1.DbQuery;
import com.example.chemezproject1.MyCompleteListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StartTestActivity extends AppCompatActivity {

    private TextView catName, testNo, totalQ, bestScore, time;
    private Button startTestB;
    private ImageView backB;
    private Dialog progressDialog;
    private TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);


        init();

        progressDialog = new Dialog(StartTestActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading....");

        progressDialog.show();

        DbQuery.loadquestions(new MyCompleteListener() {
            @Override
            public void onSuccess() {

                setData();

                progressDialog.dismiss();

            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast.makeText(StartTestActivity.this, "asasSomething went wrong ! Please try again later !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        catName = findViewById(R.id.st_cat_name);
        testNo = findViewById(R.id.st_test_no);
        totalQ = findViewById(R.id.st_total_ques);
        bestScore = findViewById(R.id.st_best_score);
        time = findViewById(R.id.st_time);
        startTestB = findViewById(R.id.start_testB);
        backB = findViewById(R.id.st_backB);


        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTestActivity.this.finish();

            }
        });

        startTestB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartTestActivity.this, QuestionsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setData() {
       if (DbQuery.g_catList.size() > 0 && DbQuery.g_testList.size() > 0 && DbQuery.g_quesList.size() > 0) {

            catName.setText(DbQuery.g_catList.get(DbQuery.g_selected_cat_index).getName());
            testNo.setText("Test No: " + String.valueOf(DbQuery.g_selected_test_index + 1));
            totalQ.setText(String.valueOf(DbQuery.g_quesList.size()));
            bestScore.setText(String.valueOf(DbQuery.g_testList.get(DbQuery.g_selected_test_index).getTopScore()));
            time.setText(String.valueOf(DbQuery.g_testList.get(DbQuery.g_selected_test_index).getTime()));


        } else {
           Toast.makeText(StartTestActivity.this, "Failed to load data. Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}

