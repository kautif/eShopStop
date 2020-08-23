package com.example.eshopstop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eshopstop.adapter.ItemsRecyclerAdapter;
import com.example.eshopstop.domain.Items;
import com.example.eshopstop.fragments.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Fragment homeFragment;
    private Toolbar mToolbar;
    private EditText searchField;
    private ImageView searchIcon;
    private FirebaseFirestore mStore;
    private List<Items> mItemsList;
    private RecyclerView searchRecyclerView;
    private LinearLayout searchResultsLinearLayout;
    private Button clearBtn;
    private ItemsRecyclerAdapter itemsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        searchField = findViewById(R.id.search_editText);
        searchIcon = findViewById(R.id.search_imageView);

        searchResultsLinearLayout = findViewById(R.id.search_results_linearLayout);
        searchRecyclerView = findViewById(R.id.search_recyclerView);
        clearBtn = findViewById(R.id.clear_button);
//        searchRecyclerView.setVisibility(View.GONE);
        mItemsList = new ArrayList<>();
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        itemsRecyclerAdapter = new ItemsRecyclerAdapter(this, mItemsList);
        searchRecyclerView.setAdapter(itemsRecyclerAdapter);
//        logoutBtn = findViewById(R.id.home_logout_button);
//        logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuth.signOut();
//                startActivity(new Intent(HomeActivity.this, MainActivity.class));
//                finish();
//            }
//        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchResultsLinearLayout.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = searchResultsLinearLayout.getLayoutParams();
                params.height = LinearLayout.LayoutParams.MATCH_PARENT;
                searchItems(searchField.getText().toString());
                Log.i("searchField", searchField.getText().toString());
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemsList.clear();
                itemsRecyclerAdapter.notifyDataSetChanged();
                searchResultsLinearLayout.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = searchResultsLinearLayout.getLayoutParams();
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            }
        });

//        searchField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (editable.toString().isEmpty()) {
//                    mItemsList.clear();
//                    itemsRecyclerAdapter.notifyDataSetChanged();
//                } else {
//
//                }
//            }
//        });

        homeFragment = new HomeFragment();
        loadFragment(homeFragment);
        mToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(mToolbar);
    }

    public void searchItems(String search) {
            mStore.collection("All").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            if (doc.getData().get("name").toString().toLowerCase().contains(search.toLowerCase())) {
                                Items items = doc.toObject(Items.class);
                                mItemsList.add(items);
                                itemsRecyclerAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home_logout_menu_item) {
            mAuth.signOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }
        return true;
    }
}
