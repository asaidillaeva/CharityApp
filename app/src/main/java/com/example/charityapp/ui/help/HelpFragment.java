package com.example.charityapp.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charityapp.R;
import com.example.charityapp.model.Needy;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HelpFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_help, container, false);

        recyclerView = root.findViewById(R.id.recyclerview);
        setUpRecyclerView();


        return root;
    }

    private void setUpRecyclerView() {
        Query query = FirebaseDatabase.getInstance().getReference("needy");

        FirebaseRecyclerOptions<Needy> options = new FirebaseRecyclerOptions.Builder<Needy>()
                .setQuery(query, Needy.class)
                .build();

        adapter = new RecyclerViewAdapter(options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

}