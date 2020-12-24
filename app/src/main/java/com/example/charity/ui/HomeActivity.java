package com.example.charity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charity.R;
import com.example.charity.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private ArrayList<User> userList = new ArrayList<User>();
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerview);
        progressbar = findViewById(R.id.progress_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bnav_view);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_donators);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menu_item_help:
                        startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_item_donators:
                        return true;
                }
                return false;
            }
        });

        getUsers();
        initList();

    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, userList);
//        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent =new Intent(AllNumbersActivity.this, ChatActivity.class);
//                intent.putExtra("user", list.get(position));
//                startActivity(intent);
//            }
//        });
        recyclerView.setAdapter(adapter);
    }


    private void getUsers() {
        FirebaseFirestore.getInstance()
                .collection("user")
                .whereEqualTo("role", "donator")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            User user = snapshot.toObject(User.class);
                            if (user != null) {
                                userList.add(user);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                });
        progressbar.setVisibility(View.GONE);
    }
}


