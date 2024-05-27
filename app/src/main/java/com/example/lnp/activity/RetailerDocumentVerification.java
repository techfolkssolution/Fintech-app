package com.example.lnp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lnp.R;

public class RetailerDocumentVerification extends AppCompatActivity {
    ImageView imageViewAadharCard,imageViewPanCard,currentImageView ;
    private static final int REQUEST_CODE_READ_STORAGE = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_document_verification);

        //FindViewBy Id
        imageViewAadharCard=findViewById(R.id.imageViewAadharCard);
        imageViewPanCard=findViewById(R.id.imageViewPanCard);

        imageViewAadharCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImageView= (ImageView) view;
                openGallery();
            }
        });

        imageViewPanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImageView= (ImageView) view;
                openGallery();
            }
        });

       if(checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES)!= PackageManager.PERMISSION_GRANTED){
           requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES},REQUEST_CODE_READ_STORAGE);
       }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_READ_STORAGE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                // Permission granted
            }

        }else{
            // Permission denied
            Toast.makeText(this, "Permission to access storage is required to select images.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_SELECT_IMAGE && resultCode==RESULT_OK && data!=null){
            Uri selectedImage = data.getData();
            currentImageView.setImageURI(selectedImage);
        }
    }
}