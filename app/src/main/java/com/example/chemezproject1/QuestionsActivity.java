package com.example.chemezproject1;

import static com.example.chemezproject1.DbQuery.g_quesList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import Adapters.QuestionGridAdapter;
import Adapters.QuestionsAdapter;

public class QuestionsActivity extends AppCompatActivity {

    private RecyclerView questionsView;
    private TextView tvQuesID, timerTV, catNameTV;
    private Button submitB, markB, clearSelB;
    private ImageButton prevQuesB, nextQuesB;
    private ImageView quesListB;
    private int quesID;
    private DrawerLayout drawer;
    private ImageButton drawerCloseB;
    private GridView quesListGV;
    private ImageView markImage;
    private QuestionGridAdapter gridAdapter;
    private CountDownTimer timer;
    private long timeLeft;
    QuestionsAdapter quesAdapter;
    private ImageView bookmarkB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_list_layout);

        init();

        quesAdapter = new QuestionsAdapter(g_quesList);
        questionsView.setAdapter(quesAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionsView.setLayoutManager(layoutManager);

        gridAdapter = new QuestionGridAdapter(this, g_quesList.size());
        quesListGV.setAdapter(gridAdapter);

        setSnapHelper();



        setClickListeners();

        startTimer();
    }





    private void init()
    {
            questionsView = findViewById(R.id.questions_view);
            tvQuesID = findViewById(R.id.tv_quesID);
            timerTV = findViewById(R.id.tv_timer);
            catNameTV = findViewById(R.id.qa_catName);
            submitB = findViewById(R.id.submitB);
            markB = findViewById(R.id.markB);
            clearSelB = findViewById(R.id.clear_selB);
            prevQuesB = findViewById(R.id.prev_quesB);
            nextQuesB = findViewById(R.id.next_quesB);
            quesListB = findViewById(R.id.ques_list_gridB);
            drawer = findViewById(R.id.drawer_layout);
            drawerCloseB = findViewById(R.id.drawerCloseB);
            markImage = findViewById(R.id.mark_image);
            quesListGV = findViewById(R.id.ques_list_gv);
            bookmarkB = findViewById(R.id.qa_bookmarkB);

            quesID = 0;

            tvQuesID.setText("1/" + String.valueOf(g_quesList.size()));
            catNameTV.setText(DbQuery.g_catList.get(DbQuery.g_selected_cat_index).getName());

            g_quesList.get(0).setStatus(DbQuery.UNANSWERED);

            if( g_quesList.get(0).isBookmarked())
            {
                bookmarkB.setImageResource(R.drawable.ic_bookmark_selected);
            }
            else
            {
                bookmarkB.setImageResource(R.drawable.ic_bookmark);
            }


    }

    private void setSnapHelper()
    {
        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionsView);

        questionsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                quesID = recyclerView.getLayoutManager().getPosition(view);

                if(g_quesList.get(quesID).getStatus() == DbQuery.NOT_VISITED)
                    g_quesList.get(quesID).setStatus(DbQuery.UNANSWERED);
                {
                    if(g_quesList.get(quesID).getStatus() == DbQuery.REVIEW)
                    {
                        markImage.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        markImage.setVisibility(View.GONE);
                    }
                }

                tvQuesID.setText(String.valueOf(quesID + 1) + "/" + String.valueOf(g_quesList.size()));

                if( g_quesList.get(quesID).isBookmarked())
                {
                    bookmarkB.setImageResource(R.drawable.ic_bookmark_selected);
                }
                else
                {
                    bookmarkB.setImageResource(R.drawable.ic_bookmark);
                }


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setClickListeners()
    {
        prevQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quesID > 0)
                {
                    questionsView.smoothScrollToPosition(quesID - 1);
                }
            }
        });

        nextQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quesID < g_quesList.size() - 1)
                {
                    questionsView.smoothScrollToPosition(quesID + 1);
                }
            }
        });

        clearSelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g_quesList.get(quesID).setSelectedAns(-1);
                g_quesList.get(quesID).setStatus(DbQuery.UNANSWERED);
                markImage.setVisibility(View.GONE);
                quesAdapter.notifyDataSetChanged();
            }
        });

        quesListB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ! drawer.isDrawerOpen(GravityCompat.END))
                {
                    gridAdapter.notifyDataSetChanged();
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        drawerCloseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( drawer.isDrawerOpen(GravityCompat.END))
                {
                    drawer.closeDrawer(GravityCompat.END);
                }
            }
        });

        markB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(markImage.getVisibility() != View.VISIBLE)
                {
                    markImage.setVisibility(View.VISIBLE);
                    g_quesList.get(quesID).setStatus(DbQuery.REVIEW);

                }
                else
                {
                    markImage.setVisibility(View.GONE);

                    if(g_quesList.get(quesID).getSelectedAns() != -1 )
                    {
                        g_quesList.get(quesID).setStatus(DbQuery.ANSWERED);
                    }
                    else
                    {
                        g_quesList.get(quesID).setStatus(DbQuery.UNANSWERED);
                    }
                }

            }
        });

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTest();
            }
        });

        bookmarkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToBookmark();

            }
        });
    }

    private void submitTest()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivity.this);
        builder.setCancelable(true);

        View view = getLayoutInflater().inflate(R.layout.alert_dialog_layout, null);

        Button cancelB = view.findViewById(R.id.cancelB);
        Button confirmB = view.findViewById(R.id.confirmB);

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                alertDialog.dismiss();

                Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);

                //in video there is no DbQuery for testList and selected Ans
                long totalTime = DbQuery.g_testList.get(DbQuery.g_selected_test_index).getTime()*60*1000;

                intent.putExtra("TIME TAKEN", totalTime - timeLeft);
                startActivity(intent);
                QuestionsActivity.this.finish();
            }
        });

        alertDialog.show();

    }

    public void goToQuestion(int position)
    {
        questionsView.smoothScrollToPosition(position);

        if(drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawer(GravityCompat.END);
        }

    }

    private void startTimer()
    {
        long totalTime = DbQuery.g_testList.get(DbQuery.g_selected_test_index).getTime()*60*1000;

        timer = new CountDownTimer(totalTime + 1000, 1000) {
            @Override
            public void onTick(long remainingTime) {

                timeLeft = remainingTime;

                String time = String.format("%02d:%02d min",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))
                );

                timerTV.setText(time);

            }

            @Override
            public void onFinish()
            {
                Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);

                //in video there is no DbQuery for testList and selected Ans
                long totalTime = DbQuery.g_testList.get(DbQuery.g_selected_test_index).getTime()*60*1000;

                intent.putExtra("TIME TAKEN", totalTime - timeLeft);
                startActivity(intent);
                QuestionsActivity.this.finish();

            }
        };

        timer.start();
    }

    private void addToBookmark()
    {
        if(g_quesList.get(quesID).isBookmarked())
        {
            g_quesList.get(quesID).setBookmarked(false);
            bookmarkB.setImageResource(R.drawable.ic_bookmark);
        }
        else
        {
            g_quesList.get(quesID).setBookmarked(true);
            bookmarkB.setImageResource(R.drawable.ic_bookmark_selected);
        }

    }

}