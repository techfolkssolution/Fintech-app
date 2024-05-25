package com.example.lnp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lnp.API.API;
import com.example.lnp.DataModel.UtilityServiceModel;
import com.example.lnp.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class RequestService extends AppCompatActivity {
    private String serviceName;
    private TextInputLayout layoutLoanType, layoutLoanCategory, layoutCAType, layoutEngineerBuildingType, layoutEngineerServiceType, layoutTenure;
    private AutoCompleteTextView spinnerLoanCategory, spinnerLoanType, spinnerCAType, spinnerEngineerBuildingType, spinnerEngineerServiceType, spinnerTenure;
    private TextView textViewHeading;

    private EditText editTextLoanAmount, editTextComment, editTextSavingAmount, editTextName, editTextMobileNumber, editTextAddress;
    String[] loanCategory = {
            "Business Loan", "Housing Loan", "Vehicle Loan", "Gold Loan", "Education Loan", "Other Loan"
    };
    String[] loanType = {
            "Construction Loan", "Extension/Renovation Loan", "Purchase Loan", "Balance Transfer Loan", "Mortgage(LAP)", "NRI"
    };
    String[] caType = {
            "IT Returns", "GST", "MSME", "Company Formation", "FASSAI Registration", "ISO Certificate", "Digital Signature Certificate"
    };
    String[] engineerBuildingType = {
            "Residential", "Semi Commercial", "Commercial", "Industrial"
    };
    String[] engineerServiceType = {
            "Planning", "Estimation", "Valuation"
    };
    String[] tenure = {
            "3 Months", "6 Months", "12 Months", "18 Months", "24 Months"
    };
    AppCompatButton btnSubmit;
    private UtilityServiceModel utilityServiceModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);


        spinnerLoanCategory = findViewById(R.id.loanCategory);
        textViewHeading = findViewById(R.id.txtHeading);
        spinnerLoanType = findViewById(R.id.loanType);
        layoutLoanType = findViewById(R.id.layoutLoanType);
        layoutLoanCategory = findViewById(R.id.layoutLoanCategory);
        editTextLoanAmount = findViewById(R.id.editTextLoanAmount);
        editTextComment = findViewById(R.id.editTextComment);
        layoutCAType = findViewById(R.id.layoutCAType);
        spinnerCAType = findViewById(R.id.CAType);
        layoutEngineerBuildingType = findViewById(R.id.layoutEngineerBuildingType);
        layoutEngineerServiceType = findViewById(R.id.layoutEngineerServiceType);
        spinnerEngineerBuildingType = findViewById(R.id.engineerBuildingType);
        spinnerEngineerServiceType = findViewById(R.id.engineerServiceType);
        layoutTenure = findViewById(R.id.layoutTenure);
        spinnerTenure = findViewById(R.id.tenure);
        editTextSavingAmount = findViewById(R.id.editTextSavingAmount);
        btnSubmit = findViewById(R.id.btnSubmit);
        editTextName = findViewById(R.id.editTextName);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextAddress = findViewById(R.id.editTextAddress);

        serviceName = getIntent().getStringExtra("serviceName");
        textViewHeading.setText("Apply For " + serviceName + " Service");
        visibleInformationLayout(serviceName);

        ArrayAdapter<String> loanAdapter = new ArrayAdapter<>(RequestService.this, R.layout.drop_down_item, loanCategory);
        spinnerLoanCategory.setAdapter(loanAdapter);

        ArrayAdapter<String> caAdapter = new ArrayAdapter<>(RequestService.this, R.layout.drop_down_item, caType);
        spinnerCAType.setAdapter(caAdapter);

        ArrayAdapter<String> engineerBuildingTypeAdapter = new ArrayAdapter<>(RequestService.this, R.layout.drop_down_item, engineerBuildingType);
        spinnerEngineerBuildingType.setAdapter(engineerBuildingTypeAdapter);

        ArrayAdapter<String> engineerServiceTypeAdapter = new ArrayAdapter<>(RequestService.this, R.layout.drop_down_item, engineerServiceType);
        spinnerEngineerServiceType.setAdapter(engineerServiceTypeAdapter);

        ArrayAdapter<String> tenureAdapter = new ArrayAdapter<>(RequestService.this, R.layout.drop_down_item, tenure);
        spinnerTenure.setAdapter(tenureAdapter);

        spinnerLoanCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerLoanCategory.getText().toString().trim().equals("Housing Loan")) {
                    layoutLoanType.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> loanTypeAdapter = new ArrayAdapter<>(RequestService.this, R.layout.drop_down_item, loanType);
                    spinnerLoanType.setAdapter(loanTypeAdapter);
                } else {
                    layoutLoanType.setVisibility(View.GONE);
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilityServiceModel = new UtilityServiceModel();
                utilityServiceModel.setName(editTextName.getText().toString().trim());
                utilityServiceModel.setAddress(editTextAddress.getText().toString().trim());
                utilityServiceModel.setComment(editTextComment.getText().toString().trim());
                utilityServiceModel.setMobileNumber(Long.parseLong(editTextMobileNumber.getText().toString().trim()));
                if (serviceName.equals("Loans")) {
                    utilityServiceModel.setLoanCategory(spinnerLoanCategory.getText().toString().trim());
                    utilityServiceModel.setLoanType(spinnerLoanType.getText().toString());
                    utilityServiceModel.setLoanAmount(Long.parseLong(editTextLoanAmount.getText().toString().trim()));
                    utilityServiceModel.setFormType("Loans");
                } else if (serviceName.equals("CA")) {
                    utilityServiceModel.setCAType(spinnerCAType.getText().toString().trim());
                    utilityServiceModel.setFormType("CA");
                } else if (serviceName.equals("Engineer")) {
                    utilityServiceModel.setEngineerBuildingType(spinnerEngineerBuildingType.getText().toString().trim());
                    utilityServiceModel.setEngineerServiceType(spinnerEngineerServiceType.getText().toString().trim());
                    utilityServiceModel.setFormType("Engineer");
                } else if (serviceName.equals("Savings")) {
                    utilityServiceModel.setTenure(spinnerTenure.getText().toString().trim());
                    utilityServiceModel.setSavingAmount(Long.parseLong(editTextSavingAmount.getText().toString().trim()));
                    utilityServiceModel.setFormType("Savings");
                } else if (serviceName.equals("CIBIL")) {
                    utilityServiceModel.setFormType("CIBIL");
                }
                saveData(utilityServiceModel);
            }
        });
    }

    public void visibleInformationLayout(String serviceName) {
        if (serviceName.equals("Loans")) {
            layoutLoanCategory.setVisibility(View.VISIBLE);
            editTextLoanAmount.setVisibility(View.VISIBLE);

        } else if (serviceName.equals("CA")) {
            layoutCAType.setVisibility(View.VISIBLE);

        } else if (serviceName.equals("Engineer")) {
            layoutEngineerBuildingType.setVisibility(View.VISIBLE);
            layoutEngineerServiceType.setVisibility(View.VISIBLE);

        } else if (serviceName.equals("Savings")) {
            layoutTenure.setVisibility(View.VISIBLE);
            editTextSavingAmount.setVisibility(View.VISIBLE);
        }
    }


    public void saveData( UtilityServiceModel utilityServiceModel) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", utilityServiceModel.getName());
            jsonObject.put("address", utilityServiceModel.getAddress());
            jsonObject.put("mobileNumber", utilityServiceModel.getMobileNumber());
            jsonObject.put("comments", utilityServiceModel.getComment());
            jsonObject.put("loanCategory", utilityServiceModel.getLoanCategory());
            jsonObject.put("loanType", utilityServiceModel.getLoanType());
            jsonObject.put("loanAmount", utilityServiceModel.getLoanAmount());
            jsonObject.put("caServiceType",utilityServiceModel.getCAType());
            jsonObject.put("engineerBuildingType",utilityServiceModel.getEngineerBuildingType());
            jsonObject.put("engineerServiceType",utilityServiceModel.getEngineerServiceType());
            jsonObject.put("tenure",utilityServiceModel.getTenure());
            jsonObject.put("savingAmount",utilityServiceModel.getSavingAmount());
            jsonObject.put("formType",utilityServiceModel.getFormType());

        } catch (JSONException e) {

        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST
                , API.INSERT_UTILITY_SERVICE_API, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(RequestService.this, "Data is Uploaded", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "error : " + error.toString());
            }
        });
        requestQueue.add(request);
    }


}