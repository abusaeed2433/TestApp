package com.example.testapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.testapp.databinding.ActivityAddInfoBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddInfo extends AppCompatActivity {

    private ActivityResultLauncher<String> mGetContent;
    private ActivityAddInfoBinding binding;
    private Uri photoUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mGetContent = registerForActivityResult(
                new ActivityResultContracts.GetContent(), result -> {
                    if (result != null) {
                        photoUri = result;
                        Glide.with(this)
                                .load(photoUri)
                                .into(binding.imageViewPhoto);
                    }
                }
        );

        binding.imageViewPhoto.setOnClickListener((View v)->{
            mGetContent.launch("image/*");
        });

        binding.buttonSave.setOnClickListener((View v)->{
            String name = String.valueOf(binding.editTextHeartRate.getText());
            String email = String.valueOf(binding.editTextSysPressure.getText());
            String phone = String.valueOf(binding.editTextDysPressure.getText());

            FirebaseAuth auth = FirebaseAuth.getInstance();
            String userId = auth.getUid();

            StorageReference ref = FirebaseStorage.getInstance().getReference().child(userId);

            ref.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = String.valueOf(uri);

                            Map<String,Object> map = new HashMap<>();
                            map.put("heart_rate",name);
                            map.put("sys",email);
                            map.put("dys",phone);
                            map.put("photo",url);

                            DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child(userId);
                            dataRef.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AddInfo.this, "Added", Toast.LENGTH_SHORT).show();
                                }

                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AddInfo.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    });
                }
            });

        });

    }


}