package com.example.charity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.charity.model.User;
import com.example.charity.ui.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private View whoView, loginView, signupView, codeCheckView, mainView;
    private EditText usernameSignup, giftOrNeeds, mobileEditText, addressEditText, passwordSignup, usernameLogin, passwordLogin, codeEditText;
    private String username, address, mobile, needsOrGift, password, who, code, what;
    private ProgressBar progressBar;

    private FirebaseFirestore database;
    private DatabaseReference mDatabase;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String verificationId;
    private boolean userExist;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseFirestore.getInstance();

        whoView = findViewById(R.id.who);
        loginView = findViewById(R.id.login_layout);
        signupView = findViewById(R.id.signup_layout);
        codeCheckView = findViewById(R.id.code_check_layout);
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
        what = "signup";
        mainView.setVisibility(View.GONE);
        whoView.setVisibility(View.VISIBLE);
    }

    public void logInClicked(View view) {
        what = "login";
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
        username = usernameSignup.getText().toString().trim();
        address = addressEditText.getText().toString().trim();
        mobile = mobileEditText.getText().toString().trim();
        needsOrGift = giftOrNeeds.getText().toString().trim();
        password = passwordSignup.getText().toString().trim();
        signupView.setVisibility(View.GONE);
        codeCheckView.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobile,
                60, TimeUnit.SECONDS, this, callbacks);
    }

    public void submitLoginClicked(View view) {
        username = usernameLogin.getText().toString().trim();
        password = passwordLogin.getText().toString().trim();
        exist(username, password);
        loginView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void checkCodeClicked(View view) {
        codeCheckView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        code = codeEditText.getText().toString().trim();
        signIn(PhoneAuthProvider.getCredential(verificationId, code));
    }

    private boolean exist(String userna, String password) {
        userExist = false;
        FirebaseFirestore.getInstance().collection("user")
                .whereEqualTo("username", userna)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        if (snapshots != null) {
                            userExist = true;
                            for (DocumentSnapshot snapshot : snapshots) {
                                user = (User) snapshot.toObject(User.class);
                                mobile = user.getPhone();
                            }
                            progressBar.setVisibility(View.GONE);
                            codeCheckView.setVisibility(View.VISIBLE);
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(mobile,
                                    60, TimeUnit.SECONDS, MainActivity.this, callbacks);
                        }
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        whoView.setVisibility(View.VISIBLE);
                        what = "signup";
                        Toast.makeText(getApplicationContext(),
                                "You haven't registered yet. Please Sign Up first!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        return userExist;
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (what.equals("signup")) {
                                saveUser();
                            }
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Welcome !", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        codeEditText.setText("");
                        Toast.makeText(getApplicationContext(),
                                "Incorrect Code !" + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
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
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Your information saved successfully!", Toast.LENGTH_SHORT);
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Retry saving" + e.getMessage(), Toast.LENGTH_SHORT);

                    }
                });
    }

}