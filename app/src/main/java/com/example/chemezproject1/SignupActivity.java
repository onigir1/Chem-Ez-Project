package com.example.chemezproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;


public class SignupActivity extends AppCompatActivity {

    private EditText name, email, pass, confirmPass;
    private Button signUpB;
    private ImageView backB;
    private FirebaseAuth mAuth;
    private String emailStr, passStr, confirmPassStr, nameStr;
    private Dialog progressDialog;
    private TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.username);
        email = findViewById(R.id.emailID);
        pass = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirm_pass);
        signUpB = findViewById(R.id.signupB);
        backB = findViewById(R.id.backB);


        progressDialog = new Dialog(SignupActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Registering User....");

        mAuth = FirebaseAuth.getInstance();


        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    signupNewUser();
                }

            }
        });
    }

    private boolean validate()
    {
        nameStr = name.getText().toString().trim();
        passStr = pass.getText().toString().trim();
        emailStr = email.getText().toString().trim();
        confirmPassStr = confirmPass.getText().toString().trim();

        if(nameStr.isEmpty())
        {
            name.setError("Enter Name");
            return false;
        }

        if (emailStr.isEmpty())
        {
            email.setError("Enter Email");
            return false;
        }
        if (passStr.isEmpty())
        {
            pass.setError("Enter Password");
            return false;
        }
        if (confirmPassStr.isEmpty())
        {
           confirmPass.setError("Enter Password");
            return false;
        }

        if(passStr.compareTo(confirmPassStr) != 0 )
        {
            Toast.makeText(SignupActivity.this, "Password and Confirm Password should be same !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void signupNewUser()
    {
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(SignupActivity.this, "Welcome aboard! Your account has been created. ", Toast.LENGTH_SHORT).show();

                            DbQuery.createUserData(emailStr, nameStr, new  MyCompleteListener(){

                                @Override
                                public void onSuccess() {

                                    //in video part 14  from loadCategories to loadData
                                    DbQuery.loadData(new MyCompleteListener() {
                                        @Override
                                        public void onSuccess() {
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            SignupActivity.this.finish();
                                            //signup,activity.class;
                                        }

                                        @Override
                                        public void onFailure() {
                                            Toast.makeText(SignupActivity.this, "Something went wrong ! Please try again later !", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    });


                                }

                                @Override
                                public void onFailure() {

                                    Toast.makeText(SignupActivity.this, "Something went wrong ! Please try again later !", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                }
                            });





                        } else {
                            //If sign in fails, display a message to them.
                            progressDialog.dismiss();
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}
