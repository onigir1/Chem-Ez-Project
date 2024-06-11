package com.example.chemezproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Handler;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Removed TextView initialization and usage
        // appName = findViewById(R.id.app_name);

        // Typeface typeface = ResourcesCompat.getFont(this, R.font.madimioneregular);
        // appName.setTypeface(typeface);

        // Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        // appName.setAnimation(anim);

        DbQuery.g_firestore = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        // Sign out the user explicitly
        mAuth.signOut();

        // Replacing the Handler with a Thread for delaying the splash screen transition
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000); // Delay for 3 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (mAuth.getCurrentUser() != null) {
                    DbQuery.loadData(new MyCompleteListener() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }

                        @Override
                        public void onFailure() {
                            runOnUiThread(() -> Toast.makeText(SplashActivity.this, "Something went wrong! Please try again later!", Toast.LENGTH_SHORT).show());
                        }
                    });
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }
        }.start();
    }
}
