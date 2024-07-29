package com.example.semproject.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.semproject.R;
import com.example.semproject.model.Helper;
import com.example.semproject.ui.dashboard.DashboardActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupUsername, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    private FirebaseAuth firebaseAuth;

    //user entered fields
    String name = "";
    String email = "";
    String password="";
    String username="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize FirebaseApp
        FirebaseApp.initializeApp(this);

        //firebase refeerence
        database = FirebaseDatabase.getInstance("https://medicineremainder-8f85b-default-rtdb.firebaseio.com/");
        reference = database.getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(view -> {
            //name check
            if (!signupName.getText().toString().isEmpty())
                name = signupName.getText().toString();
            else
                signupName.setError("Enter name");

            //email check
            if (!signupEmail.getText().toString().isEmpty())
                email = signupEmail.getText().toString();
            else
                signupEmail.setError("Enter email");

            //password check
            if (!signupPassword.getText().toString().isEmpty())
                password = signupPassword.getText().toString();
            else
                signupPassword.setError("Enter password");

            //username check
            if (!signupUsername.getText().toString().isEmpty())
                username = signupUsername.getText().toString();
            else
                signupUsername.setError("Enter username");

            if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !name.isEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {

                    //database operation
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        String uid = firebaseUser.getUid();
                        reference.child(uid).setValue(new Helper(uid, name, email, username, password));
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        //toast
                        Toast.makeText(SignupActivity.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    // Handle failure
                    Toast.makeText(SignupActivity.this, "Signup failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        loginRedirectText.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
