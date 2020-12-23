package com.example.charity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private View whoView, loginView, signupView, codeCheckView, mainView;
    private EditText usernameSignup, giftOrNeeds, mobileEditText, addressEditText, passwordSignup, usernameLogin, passwordLogin, codeEditText;
    private String username, address, mobile, needsOrGift, password, who, code;
    private ProgressBar progressBar;

    private FirebaseFirestore database;
    private DatabaseReference mDatabase;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseFirestore.getInstance();

        whoView = findViewById(R.id.who);
        loginView = findViewById(R.id.login_layout);
        signupView = findViewById(R.id.signup_layout);
        codeCheckView = findViewById(R.id.checkCode);
        mainView = findViewById(R.id.layout_home);
        progressBar = findViewById(R.id.progressbar);

        usernameSignup = findViewById(R.id.user_username);
        addressEditText = findViewById(R.id.user_address);
        mobileEditText = findViewById(R.id.user_phone);
        giftOrNeeds = findViewById(R.id.gift_or_needs);
        passwordSignup = findViewById(R.id.user_password);
        usernameLogin = findViewById(R.id.username_login);
        passwordLogin = findViewById(R.id.password_login);
        codeEditText = findViewById(R.id.code_edit_text);


        mainView.setVisibility(View.VISIBLE);

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codeCheckView.setVisibility(View.VISIBLE);
                verificationId = s;
            }
        };

    }

    public void signUpClicked(View view) {
        mainView.setVisibility(View.GONE);
        whoView.setVisibility(View.VISIBLE);
    }

    public void logInClicked(View view) {
        mainView.setVisibility(View.GONE);
        loginView.setVisibility(View.VISIBLE);
    }

    public void needyClicked(View view) {
        whoView.setVisibility(View.GONE);
        who = "needy";
        signupView.setVisibility(View.VISIBLE);
        giftOrNeeds.setHint("Needs");

    }

    public void donatorClicked(View view) {
        whoView.setVisibility(View.GONE);
        who = "donator";
        signupView.setVisibility(View.VISIBLE);
        giftOrNeeds.setHint("Will Give");

    }

    public void submitSignUpClicked(View view) {
        username = usernameSignup.getText().toString();
        address = addressEditText.getText().toString();
        mobile = mobileEditText.getText().toString();
        needsOrGift = giftOrNeeds.getText().toString();
        password = passwordSignup.getText().toString();
        signupView.setVisibility(View.GONE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobile,
                60, TimeUnit.SECONDS, this, callbacks);
    }

    public void submitLoginClicked(View view) {
        username = usernameLogin.getText().toString();
        password = passwordLogin.getText().toString();

    }

    public void checkCodeClicked(View view) {
        codeCheckView.setVisibility(View.VISIBLE);
        code = codeEditText.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signIn(credential);
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    saveUser();
                    codeCheckView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Authentication failed!" + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUser() {
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("address", address);
        user.put("needsOrGift", needsOrGift);
        user.put("phone", mobile);
        user.put("password", password);
        user.put("role", who);
        String userId = FirebaseAuth.getInstance().getUid();
        user.put("userId", userId);

        FirebaseFirestore.getInstance().collection("user")
                .document(userId)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                            intent.putExtra("username", username);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "You entered successfully!", Toast.LENGTH_SHORT);

                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Retry saving", Toast.LENGTH_SHORT);

                        }
                    }
                });
    }

}