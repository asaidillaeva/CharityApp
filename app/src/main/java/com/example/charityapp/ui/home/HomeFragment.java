package com.example.charityapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.charityapp.R;
import com.example.charityapp.ui.application.ApplicationFragment;

public class HomeFragment extends Fragment {

    private Button btnVolunteer;
    private Button btnNeedy;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        btnVolunteer = root.findViewById(R.id.btn_volunteer);
        btnNeedy = root.findViewById(R.id.btn_help);

        btnVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationFragment nextFrag = new ApplicationFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, nextFrag, "findVolunteerFrag")
                        .addToBackStack("volunteer")
                        .commit();
            }
        });
        btnNeedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationFragment nextFrag = new ApplicationFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, nextFrag, "findNeedyFrag")
                        .addToBackStack("needy")
                        .commit();
            }
        });

        return root;
    }
}