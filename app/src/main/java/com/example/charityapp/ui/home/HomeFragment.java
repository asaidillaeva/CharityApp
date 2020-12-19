package com.example.charityapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.charityapp.R;
import com.example.charityapp.ui.application.ApplicationActivity;

import java.util.zip.Inflater;

public class HomeFragment extends Fragment {

    private Button btnSignUp;
    private Button btnLogIn;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        btnLogIn = root.findViewById(R.id.btn_logIn);
        btnSignUp = root.findViewById(R.id.btn_SignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), ApplicationActivity.class);
                myIntent.putExtra("string", "signup");
                startActivity(myIntent);
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), ApplicationActivity.class);
                myIntent.putExtra("string", "login");
                startActivity(myIntent);
            }
        });

        return root;
    }
}