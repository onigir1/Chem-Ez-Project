package com.example.chemezproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import Adapters.TestAdapter;

public class TestActivity extends AppCompatActivity {
    private RecyclerView testView;
    private Toolbar toolbar;
    private TestAdapter adapter;
    private Dialog progressDialog;
    private TextView dialogText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        // Retrieve the selected category index from the intent extras

        //selectedCatIndex old one
        //cat_index new one

        int cat_index = getIntent().getIntExtra("CAT_INDEX", 0);


        // Update DbQuery.g_selected_cat_index with the selected category index
        DbQuery.g_selected_cat_index = cat_index;

        // Set the toolbar title using the name of the selected category
        getSupportActionBar().setTitle(DbQuery.g_catList.get(DbQuery.g_selected_cat_index).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize RecyclerView and other views
        testView = findViewById(R.id.test_recycler_view);

        progressDialog = new Dialog(TestActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading....");

        progressDialog.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        testView.setLayoutManager(layoutManager);

        // Load test data
        DbQuery.loadTestData(new MyCompleteListener() {
            @Override
            public void onSuccess() {

                DbQuery.loadMyScores(new MyCompleteListener() {
                    @Override
                    public void onSuccess() {

                        adapter = new TestAdapter(DbQuery.g_testList);
                        testView.setAdapter(adapter);

                        progressDialog.dismiss();

                    }

                    @Override
                    public void onFailure() {

                        progressDialog.dismiss();
                        Toast.makeText(TestActivity.this, "Something went wrong! Please try again later.", Toast.LENGTH_SHORT).show();

                    }
                });


            }

            @Override
            public void onFailure() {

                progressDialog.dismiss();
                Toast.makeText(TestActivity.this, "Something went wrong! Please try again later.", Toast.LENGTH_SHORT).show();

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            TestActivity.this.finish();
            //return true;
        }
        return super.onOptionsItemSelected(item);
    }
}