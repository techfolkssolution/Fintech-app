package com.example.lnp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lnp.R;
import com.google.android.material.textfield.TextInputLayout;

public class RequestService extends AppCompatActivity {
    private String serviceName;
    private TextInputLayout layoutLoanType, layoutLoanCategory, layoutCAType,layoutEngineerBuildingType,layoutEngineerServiceType,layoutTenure;
    private AutoCompleteTextView spinnerLoanCategory, spinnerLoanType,spinnerCAType,spinnerEngineerBuildingType,spinnerEngineerServiceType,spinnerTenure;
    private TextView textViewHeading;
    private EditText editTextLoanAmount, editTextComment,editTextSavingAmount;
    String[] loanCategory = {
            "Business Loan", "Housing Loan", "Vehicle Loan", "Gold Loan", "Education Loan", "Other Loan"
    };
    String[] loanType = {
            "Construction Loan", "Extension/Renovation Loan", "Purchase Loan", "Balance Transfer Loan", "Mortgage(LAP)", "NRI"
    };
    String[] caType={
            "IT Returns", "GST", "MSME", "Company Formation", "FASSAI Registration", "ISO Certificate", "Digital Signature Certificate"
    };
    String[] engineerBuildingType={
            "Residential", "Semi Commercial", "Commercial", "Industrial"
    };
    String[] engineerServiceType={
            "Planning", "Estimation", "Valuation"
    };
    String[] tenure={
            "3 Months", "6 Months", "12 Months", "18 Months", "24 Months"
    };
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
        layoutCAType=findViewById(R.id.layoutCAType);
        spinnerCAType=findViewById(R.id.CAType);
        layoutEngineerBuildingType=findViewById(R.id.layoutEngineerBuildingType);
        layoutEngineerServiceType=findViewById(R.id.layoutEngineerServiceType);
        spinnerEngineerBuildingType=findViewById(R.id.engineerBuildingType);
        spinnerEngineerServiceType=findViewById(R.id.engineerServiceType);
        layoutTenure=findViewById(R.id.layoutTenure);
        spinnerTenure=findViewById(R.id.tenure);
        editTextSavingAmount=findViewById(R.id.editTextSavingAmount);

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
    }

    public void visibleInformationLayout(String serviceName) {
        if (serviceName.equals("Loans")) {
            layoutLoanCategory.setVisibility(View.VISIBLE);
            editTextLoanAmount.setVisibility(View.VISIBLE);
            editTextComment.setVisibility(View.VISIBLE);

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
}