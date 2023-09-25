package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        downloadData();

        binding.fabAdd.setOnClickListener((View v)-> startActivity(new Intent(this,AddInfo.class)));
    }

    private void downloadData(){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String userId = auth.getUid();

        DatabaseReference ref =FirebaseDatabase.getInstance().getReference().child(userId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<EachData> list = new ArrayList<>();

                for (DataSnapshot ds : snapshot.getChildren()){
                    EachData data = ds.getValue(EachData.class);
                    list.add( data );
                }

                DataAdapter adapter = new DataAdapter(MainActivity.this,list);
                binding.rvList.setAdapter(adapter);
                //System.out.println(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
