package com.example.lnp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnp.API.API;
import com.example.lnp.DataModel.ImportantLinkUpdateDto;
import com.example.lnp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RetailerDocumentVerification extends AppCompatActivity {
    ImageView imageViewAadharCard, imageViewPanCard, currentImageView;
    private static final int REQUEST_CODE_READ_STORAGE = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private Uri selectedImage;
    private AppCompatButton btnUploadAadharCard, btnUploadPanCard;
    private FirebaseStorage firebaseStorage;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_document_verification);

        //FindViewBy Id
        imageViewAadharCard = findViewById(R.id.imageViewAadharCard);
        imageViewPanCard = findViewById(R.id.imageViewPanCard);
        btnUploadAadharCard = findViewById(R.id.btnUploadAadharCard);
        btnUploadPanCard = findViewById(R.id.btnUploadPanCard);


        sharedPreferences = getSharedPreferences("userInformation", MODE_PRIVATE);

        imageViewAadharCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImageView = (ImageView) view;
                openGallery();

            }
        });


        imageViewPanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImageView = (ImageView) view;
                openGallery();

            }
        });


        btnUploadAadharCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                ImportantLinkUpdateDto importantLinkUpdateDto=new ImportantLinkUpdateDto();
//                importantLinkUpdateDto.setId((long)sharedPreferences.getInt("userId",0));
//                importantLinkUpdateDto.setAadhaarUrl("http://avaga");
//                importantLinkUpdateDto.setUpdateType("aadhar");
//                Log.d("imageUri","Aadhar Card : "+selectedImage.toString());
//                upload(importantLinkUpdateDto);
                uploadAadharCardImage(selectedImage);
            }
        });

        btnUploadPanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ImportantLinkUpdateDto importantLinkUpdateDto=new ImportantLinkUpdateDto();
//                importantLinkUpdateDto.setId((long) sharedPreferences.getInt("userId",0));
//                importantLinkUpdateDto.setPanUrl(selectedImage.toString());
//                importantLinkUpdateDto.setUpdateType("pan");
//                Log.d("imageUri","Pan Card Uri : "+selectedImage.toString());
//                upload(importantLinkUpdateDto);
                uploadPanCardImage(selectedImage);
            }
        });

        if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_CODE_READ_STORAGE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            }

        } else {
            // Permission denied
            Toast.makeText(this, "Permission to access storage is required to select images.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            currentImageView.setImageURI(selectedImage);
        }
    }


    public void uploadAadharCardImage(Uri imageUri) {
        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference aadharImageRef = firebaseStorage.getReference().child("Aadhar Card");
        StorageReference upload = aadharImageRef.child("AadharCardImage" + System.currentTimeMillis());
        upload.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                upload.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap<String,String> params=new HashMap<>();
                        params.put("id", String.valueOf(sharedPreferences.getInt("userId",0)));
                        params.put("pan","null");
                        params.put("aadhar",uri.toString());
                        params.put("update","aadhar");
                        new HttpPostRequestTask().execute(API.UPLOAD_IMPORTANT_DOC,params);
                        Toast.makeText(RetailerDocumentVerification.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        Log.d("imageUri", "Image Uri : " + uri.toString());
                    }
                });
            }
        });
    }

    public void uploadPanCardImage(Uri imageUri) {
        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference aadharImageRef = firebaseStorage.getReference().child("Pan Card");
        StorageReference upload = aadharImageRef.child("PanCardImage" + System.currentTimeMillis());
        upload.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                upload.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap<String,String> params=new HashMap<>();
                        params.put("id", String.valueOf(sharedPreferences.getInt("userId",0)));
                        params.put("pan",uri.toString());
                        params.put("aadhar","null");
                        params.put("update","pan");
                        new HttpPostRequestTask().execute(API.UPLOAD_IMPORTANT_DOC,params);
                        Toast.makeText(RetailerDocumentVerification.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        Log.d("imageUri", "Image Uri : " + uri.toString());
                    }
                });
            }
        });
    }

    public class HttpPostRequestTask extends AsyncTask<Object, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Object... objects) {
            String urlString = (String) objects[0]; // URL to call
            HashMap<String, String> postDataParams = (HashMap<String, String>) objects[1]; // POST data
            try{
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                OutputStream os = urlConnection.getOutputStream();
                os.write(getPostDataString(postDataParams).getBytes());
                os.flush();
                os.close();

                int responseCode = urlConnection.getResponseCode();
                return responseCode == HttpURLConnection.HTTP_OK;

            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success) {
                Toast.makeText(RetailerDocumentVerification.this, "Data Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RetailerDocumentVerification.this, "Error Updating Data", Toast.LENGTH_SHORT).show();
            }
        }
        private String getPostDataString(HashMap<String, String> params) throws Exception {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            return result.toString();
        }
    }
}