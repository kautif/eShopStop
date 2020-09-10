package com.example.eshopstop.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eshopstop.ItemsActivity;
import com.example.eshopstop.R;
import com.example.eshopstop.adapter.BestSaleAdapter;
import com.example.eshopstop.adapter.CategoryAdapter;
import com.example.eshopstop.adapter.FeatureAdapter;
import com.example.eshopstop.domain.BestSale;
import com.example.eshopstop.domain.Category;
import com.example.eshopstop.domain.Feature;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private FirebaseFirestore mStore;
    private static String TAG = "HomeFragment";

    // Category section
    private List<Category> mCategoryList;
    private CategoryAdapter mCategoryAdapter;
    private TextView mSeeAll;
    private RecyclerView mCategoryRecyclerView;

    // Feature section
    private List<Feature> mFeatureList;
    private FeatureAdapter mFeatureAdapter;
    private TextView mFeatureSeeAll;
    private RecyclerView mFeatureRecyclerView;

    // Best Sale section
    private List<BestSale> mBestSaleList;
    private BestSaleAdapter mBestSaleAdapter;
    private TextView mBestSaleSeeAll;
    private RecyclerView mBestSaleRecyclerView;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mStore = FirebaseFirestore.getInstance();

        mSeeAll = view.findViewById(R.id.see_all_textView);
        mFeatureSeeAll = view.findViewById(R.id.feature_see_all_textView);
        mBestSaleSeeAll = view.findViewById(R.id.bestSale_see_all_textView);

        mCategoryRecyclerView = view.findViewById(R.id.category_recyclerView);
        mFeatureRecyclerView = view.findViewById(R.id.feature_recyclerView);
        mBestSaleRecyclerView = view.findViewById(R.id.bestSale_recyclerView);

        mCategoryList = new ArrayList<>();
        mCategoryAdapter = new CategoryAdapter(getContext(), mCategoryList);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mCategoryRecyclerView.setAdapter(mCategoryAdapter);

        mFeatureList = new ArrayList<>();
        mFeatureAdapter = new FeatureAdapter(getContext(), mFeatureList);
        mFeatureRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mFeatureRecyclerView.setAdapter(mFeatureAdapter);

        mBestSaleList = new ArrayList<>();
        mBestSaleAdapter = new BestSaleAdapter(getContext(), mBestSaleList);
        mBestSaleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mBestSaleRecyclerView.setAdapter(mBestSaleAdapter);

        mSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ItemsActivity.class));
            }
        });

        mFeatureSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ItemsActivity.class));
            }
        });

        mBestSaleSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ItemsActivity.class));
            }
        });

        mStore.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Category category = document.toObject(Category.class);
                                mCategoryList.add(category);
                                mCategoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        mStore.collection("Feature")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Feature feature = document.toObject(Feature.class);
                                mFeatureList.add(feature);
                                mFeatureAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        mStore.collection("BestSale")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                BestSale bestSale = document.toObject(BestSale.class);
                                mBestSaleList.add(bestSale);
                                mBestSaleAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return view;
    }
}
