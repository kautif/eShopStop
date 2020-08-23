package com.example.eshopstop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.eshopstop.adapter.AddressAdapter;
import com.example.eshopstop.domain.Address;
import com.example.eshopstop.domain.BestSale;
import com.example.eshopstop.domain.Feature;
import com.example.eshopstop.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {

    private RecyclerView mAddressRecyclerView;
    private AddressAdapter mAddressAdapter;
    private Button mAddressBtn;
    private Button mPaymentBtn;
    private List<Address> mAddressList;

    private double amount;
    private String img_url;
    private String name;
    private String address;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        final Object obj = getIntent().getSerializableExtra("item");

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        mToolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddressRecyclerView = findViewById(R.id.address_recyclerView);
        mAddressBtn = findViewById(R.id.add_address_button);
        mPaymentBtn = findViewById(R.id.address_payment_button);
        mAddressBtn = findViewById(R.id.add_address_button);
        mAddressList = new ArrayList<>();
        mAddressAdapter = new AddressAdapter(getApplicationContext(), mAddressList, this);
        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAddressRecyclerView.setAdapter(mAddressAdapter);

mStore.collection("User").document(mAuth.getCurrentUser().getUid())
        .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            for (DocumentSnapshot doc:task.getResult().getDocuments()) {
                Address address = doc.toObject(Address.class);
                mAddressList.add(address);
                mAddressAdapter.notifyDataSetChanged();
            }
        }
    }
});

        mAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
            }
        });

        mPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = 0.0;
                img_url = "";
                name = "";

                if (obj instanceof Feature) {
                    Feature feature = (Feature) obj;
                    amount = feature.getPrice();
                    img_url = feature.getImg_url();
                    name = feature.getName();
                }

                if (obj instanceof BestSale) {
                    BestSale bestSale = (BestSale) obj;
                    amount = bestSale.getPrice();
                    img_url = bestSale.getImg_url();
                    name = bestSale.getName();
                }

                if (obj instanceof Items) {
                    Items items = (Items) obj;
                    amount = items.getPrice();
                    img_url = items.getImg_url();
                    name = items.getName();
                }
                Intent intent = new Intent(AddressActivity.this, PaymentActivity.class);
                intent.putExtra("amount", amount);
                intent.putExtra("img_url", img_url);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });


    }

    @Override
    public void setAddress(String s) {
        address = s;
    }
}
