package com.example.lnp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnp.API.API;
import com.example.lnp.DataModel.AdminAccessInformation;
import com.example.lnp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Admin extends AppCompatActivity {
    private ArrayList<AdminAccessInformation> adminAccessInformation;
    private CardView cardViewAllUserInformation, cardViewVerificationRequest, cardViewCustomerSupport, cardViewAddCustomerSupport;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        cardViewAllUserInformation = findViewById(R.id.cardViewAllUserInformation);
        cardViewVerificationRequest = findViewById(R.id.cardViewVerificationRequest);
        cardViewCustomerSupport = findViewById(R.id.cardViewCustomerSupport);
        cardViewAddCustomerSupport = findViewById(R.id.cardViewAddCustomerSupport);

        cardViewAllUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin.this, AllUserInformation.class);
                startActivity(i);
                finish();
            }
        });

        cardViewVerificationRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin.this, VerificationRequest.class);
                startActivity(i);
                finish();
            }
        });


        getData(new Callback() {
            @Override
            public void onSuccess(ArrayList<AdminAccessInformation> adminAccessInformations) {
                adminAccessInformation = adminAccessInformations;
            }
        });


        cardViewCustomerSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayUpdateContactSupportDialog(adminAccessInformation);
            }
        });
        cardViewAddCustomerSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAddContactSupportDialog();
            }
        });


    }

    public void displayUpdateContactSupportDialog(ArrayList<AdminAccessInformation> adminAccessInformationsArrayList) {
        Dialog dialog = new Dialog(Admin.this);
        dialog.setContentView(R.layout.customer_support_dialog);

        AutoCompleteTextView spinnerCustomerServiceType = dialog.findViewById(R.id.customerServiceType);
        EditText editText = dialog.findViewById(R.id.editText);
        AppCompatButton btnSubmit = dialog.findViewById(R.id.btnSubmit);

        ArrayList<String> customerService = new ArrayList<>();
        for (AdminAccessInformation admin : adminAccessInformationsArrayList) {
            customerService.add(admin.getAdminAccessInformationKey());
        }
        ArrayAdapter<String> customerServiceTypeAdapter = new ArrayAdapter<>(Admin.this, R.layout.drop_down_item, customerService);
        spinnerCustomerServiceType.setAdapter(customerServiceTypeAdapter);


        spinnerCustomerServiceType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(adminAccessInformationsArrayList.get(i).getAdminAccessInformationValue());
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = spinnerCustomerServiceType.getText().toString();
                String value = editText.getText().toString().trim();
                updateData(key, value);
            }
        });


        dialog.show();
    }

    public void displayAddContactSupportDialog() {
        Dialog dialog = new Dialog(Admin.this);
        dialog.setContentView(R.layout.add_customer_support_dialog);
        EditText editTextKey = dialog.findViewById(R.id.editTextKey);
        EditText editTextValue = dialog.findViewById(R.id.editTextValue);
        AppCompatButton btnSubmit = dialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminAccessInformation adminAccessInformation1 = new AdminAccessInformation();
                adminAccessInformation1.setAdminAccessInformationKey(editTextKey.getText().toString().trim());
                adminAccessInformation1.setAdminAccessInformationValue(editTextValue.getText().toString().trim());
                saveContactSupportInformation(adminAccessInformation1);
            }
        });
        dialog.show();
    }

    public void saveContactSupportInformation(AdminAccessInformation adminAccessInformation) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("adminAccessInformationKey", adminAccessInformation.getAdminAccessInformationKey());
            jsonObject.put("adminAccessInformationValue", adminAccessInformation.getAdminAccessInformationValue());
        } catch (JSONException jsonException) {

        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API.API_SAVE_CONTACT_INFORMATION, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(Admin.this, "Data Is Uploaded", Toast.LENGTH_SHORT).show();
                Log.d("APIContact", "Done");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APIContact", error.toString());
            }
        });
        queue.add(request);
    }

    public void getData(Callback callback) {
        ArrayList<AdminAccessInformation> adminAccessInformations = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API.API_CONTACT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    AdminAccessInformation adminAccessInformation = new AdminAccessInformation();
                    try {
                        JSONObject data = response.getJSONObject(i);
                        adminAccessInformation.setId(data.getInt("id"));
                        adminAccessInformation.setAdminAccessInformationKey(data.getString("adminAccessInformationKey"));
                        adminAccessInformation.setAdminAccessInformationValue(data.getString("adminAccessInformationValue"));
                        adminAccessInformations.add(adminAccessInformation);
                    } catch (JSONException e) {

                    }
                }
                callback.onSuccess(adminAccessInformations);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(jsonArrayRequest);

    }



    public void updateData(String key, String value) {
        String apiUrl = API.API_CONTACT_UPDATE;
        HashMap<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("value", value);
        new HttpPutRequestTask().execute(apiUrl,params);
    }

    public class HttpPutRequestTask extends AsyncTask<Object, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Object... objects) {
            String urlString = (String) objects[0];
            HashMap<String, String> params = (HashMap<String, String>) objects[1];
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("PUT");
                urlConnection.setDoOutput(true);
                OutputStream os = urlConnection.getOutputStream();
                os.write(getPostDataString(params).getBytes());
                os.flush();
                os.close();

                int responseCode = urlConnection.getResponseCode();
                return responseCode == HttpURLConnection.HTTP_OK;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success) {
                Toast.makeText(Admin.this, "Data Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Admin.this, "Error Updating Data", Toast.LENGTH_SHORT).show();
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

    public interface Callback {
        public void onSuccess(ArrayList<AdminAccessInformation> adminAccessInformations);
    }

}
