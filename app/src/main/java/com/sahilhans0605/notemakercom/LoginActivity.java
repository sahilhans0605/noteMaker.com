package com.sahilhans0605.notemakercom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sahilhans0605.notemakercom.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth Auth;
    ActivityLoginBinding binding;
    String emailPattern;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Auth = FirebaseAuth.getInstance();
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        dialog = new ProgressDialog(this);

        if(Auth.getCurrentUser()!=null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        binding.registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentication();
            }
        });

    }

    private void authentication() {
        String email = binding.nameLogin.getText().toString();
        String password = binding.passwordLogin.getText().toString();
        Log.i("info", "Entered");
        if (!email.matches(emailPattern)) {
            binding.nameLogin.setError("Enter Correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            binding.passwordLogin.setError("Add a strong Password");
        } else {
            dialog.setMessage("Please wait while we are Signing you!");
            dialog.setTitle("Signing in...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


            Auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, "" + "Invalid Credentials", Toast.LENGTH_LONG).show();
                        Log.i("Info", task.getException() + "");
                    }
                }
            });

        }


    }
}
