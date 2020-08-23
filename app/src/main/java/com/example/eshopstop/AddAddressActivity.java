package com.example.eshopstop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mStreet;
    private EditText mCity;
    private EditText mCode;
    private EditText mPhone;
    private Button mCreateAddress;

    private String name;
    private String street;
    private String city;
    private String code;
    private String phone;
    private String fullAddress;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        mName = findViewById(R.id.add_address_name_editText);
        mStreet = findViewById(R.id.add_address_street_editText);
        mCity = findViewById(R.id.add_address_city_editText);
        mCode = findViewById(R.id.add_address_zip_editText);
        mPhone = findViewById(R.id.add_address_phone_editText);
        mCreateAddress = findViewById(R.id.create_address_button);

        mToolbar = findViewById(R.id.add_address_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();


        mCreateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = mName.getText().toString();
                street = mStreet.getText().toString();
                city = mCity.getText().toString();
                code = mCode.getText().toString();
                phone = mPhone.getText().toString();

                if (!name.isEmpty()) {
                    fullAddress = name + ", ";
                }

                if (!street.isEmpty()) {
                    fullAddress += street + ", ";
                }

                if (!city.isEmpty()) {
                    fullAddress += city + ", ";
                }

                if (!code.isEmpty()) {
                    fullAddress += code + ", ";
                }

                if (!phone.isEmpty()) {
                    fullAddress += phone + ", ";
                }

                Map<String, String> mMap = new HashMap<>();
                mMap.put("address", fullAddress);

                mStore.collection("User").document(mAuth.getCurrentUser().getUid()).collection("Address").add(mMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddAddressActivity.this, "Address added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

            }
        });
    }
}
