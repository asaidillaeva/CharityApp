package com.example.charityapp.ui.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.charityapp.R;

public class ApplicationActivity extends AppCompatActivity {

    View whoView, loginView, signupView, codeCheckView;
    Button btnAsNeedy, btnAsSponsor, btnSignUp, btnLogIn, btnCheck;
    private String who;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_activity);

        whoView = findViewById(R.id.who);
        loginView = findViewById(R.id.login_layout);
        signupView = findViewById(R.id.signup_layout);
        codeCheckView = findViewById(R.id.checkCode);

        getWho();
        Intent intent = getIntent();
        String extraValue = intent.getStringExtra("string");

        if (extraValue.equals("signup")) {
            getSignUpView();
        } else {
            getLogInView();
        }
    }

    private void getLogInView() {
        loginView.setVisibility(View.VISIBLE);

    }

    private void getSignUpView() {

        String fullname, address, mobile, needsOrGift;
        EditText fullnameEditText = findViewById(R.id.user_fullname);
        EditText addressEditText = findViewById(R.id.user_address);
        EditText mobileEditText = findViewById(R.id.user_phone);
        EditText giftOrNeeds = findViewById(R.id.gift_or_needs);
        btnSignUp = findViewById(R.id.btn_submit_signup);

        signupView.setVisibility(View.VISIBLE);
        if (who.equals("needy"))
            giftOrNeeds.setHint("Needs");
        else
            giftOrNeeds.setHint("Will Give");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        signupView.setVisibility(View.GONE);

    }

    private void getWho() {
        who = "";

        btnAsNeedy = findViewById(R.id.btn_as_needy);
        btnAsSponsor = findViewById(R.id.btn_as_sponsor);

        whoView.setVisibility(View.VISIBLE);

        btnAsNeedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                who.equals("needy");
            }
        });

        btnAsSponsor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                who.equals("sponsor");
            }
        });

        whoView.setVisibility(View.GONE);
    }

}