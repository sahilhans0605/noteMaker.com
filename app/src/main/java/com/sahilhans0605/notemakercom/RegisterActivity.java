package com.sahilhans0605.notemakercom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sahilhans0605.notemakercom.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    FirebaseAuth Auth;
    String emailPattern;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Auth = FirebaseAuth.getInstance();
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(Auth.getCurrentUser()!=null){
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        dialog = new ProgressDialog(this);

        binding.loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        binding.registerButtonnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentication();
            }
        });
    }

    private void authentication() {
        String email = binding.usernameRegister.getText().toString();
        String password = binding.passwordRegister.getText().toString();
        String confirmPassword = binding.confirmPasswordRegister.getText().toString();

        if (!email.matches(emailPattern)) {
            binding.usernameRegister.setError("Enter Correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            binding.passwordRegister.setError("Add a strong Password");
        } else if (!password.equals(confirmPassword)) {
            binding.confirmPasswordRegister.setError("Password Does Not Match");
        } else {

            dialog.setMessage("Please wait while we are Registering you!");
            dialog.setTitle("Registering...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (Auth.getCurrentUser() != null) {
                        Toast.makeText(RegisterActivity.this, "User Already Exists", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                }
            });
        }
    }
}

