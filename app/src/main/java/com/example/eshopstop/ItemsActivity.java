package com.example.eshopstop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.eshopstop.adapter.ItemsRecyclerAdapter;
import com.example.eshopstop.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.proto.TargetGlobal;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FirebaseFirestore mStore;
    List<Items> itemsList;
    private RecyclerView itemsRecyclerView;
    private ItemsRecyclerAdapter itemsRecyclerAdapter;
    private String TAG = "ItemsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        String type = getIntent().getStringExtra("type");
        Log.d(TAG, "onCreate: " + type);
        mStore = FirebaseFirestore.getInstance();
        itemsList = new ArrayList<>();

        mToolbar = findViewById(R.id.items_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Items");

        itemsRecyclerView = findViewById(R.id.items_recyclerView);
        itemsRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        itemsRecyclerAdapter = new ItemsRecyclerAdapter(getApplicationContext(), itemsList);
        itemsRecyclerView.setAdapter(itemsRecyclerAdapter);

        if (type == null) {
            mStore.collection("All").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            Log.i(TAG, "isSuccessful" + doc.toString());
                            Items items = doc.toObject(Items.class);
                            itemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        if (type != null && type.equalsIgnoreCase("men")) {
            mStore.collection("All").whereEqualTo("type", "men").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            Log.i(TAG, "isSuccessful" + doc.toString());
                            Items items = doc.toObject(Items.class);
                            itemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("women")) {
            mStore.collection("All").whereEqualTo("type", "women").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            Log.i(TAG, "isSuccessful" + doc.toString());
                            Items items = doc.toObject(Items.class);
                            itemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("kids")) {
            mStore.collection("All").whereEqualTo("type", "kids").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            Log.i(TAG, "isSuccessful" + doc.toString());
                            Items items = doc.toObject(Items.class);
                            itemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.items_search_menuItem);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchItems(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchItems(String query) {
        itemsList.clear();
        mStore.collection("All").whereGreaterThanOrEqualTo("name", query).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        Log.i(TAG, "isSuccessful" + doc.toString());
                        Items items = doc.toObject(Items.class);
                        itemsList.add(items);
                        itemsRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
