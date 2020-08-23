package com.example.eshopstop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eshopstop.domain.BestSale;
import com.example.eshopstop.domain.Feature;
import com.example.eshopstop.domain.Items;

// 7/15/2020
// TODO: Fix subtotal result
public class DetailActivity extends AppCompatActivity {

    private ImageView mDetailImage;
    private TextView mDetailItemName, mDetailPrice, mDetailRatingNumber, mDetailRatingType,
            mDetailDesc;
    private Button mDetailAddToCartBtn, mDetailBuyBtn;
    Feature feature;
    BestSale bestSale;
    Items items;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mToolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDetailImage = findViewById(R.id.detail_imageView);
        mDetailItemName = findViewById(R.id.detail_name_textView);
        mDetailPrice = findViewById(R.id.detail_price_textView);
        mDetailRatingNumber = findViewById(R.id.detail_rating_number_textView);
        mDetailRatingType = findViewById(R.id.detail_ratingType_textView);
        mDetailDesc = findViewById(R.id.detail_desc_textView);
        mDetailAddToCartBtn = findViewById(R.id.detail_add_to_cart_button);
        mDetailBuyBtn = findViewById(R.id.detail_buy_button);

        feature = null;
        bestSale = null;

        final Object obj = getIntent().getSerializableExtra("detail");
        if (obj instanceof Feature) {
            feature = (Feature) obj;
        } else if (obj instanceof  BestSale) {
            bestSale = (BestSale) obj;
        } else {
            items = (Items) obj;
        }

        if (feature != null) {
            Glide.with(getApplicationContext()).load(feature.getImg_url()).into(mDetailImage);
            mDetailItemName.setText(feature.getName());
            mDetailPrice.setText("$"+ feature.getPrice());
            mDetailRatingNumber.setText(String.valueOf(feature.getRating()));

            if (feature.getRating() >= 4) {
                mDetailRatingType.setText("Very Good");
            } else {
                mDetailRatingType.setText("Good");
            }

            mDetailDesc.setText(feature.getDesc());
        }

        if (bestSale != null) {
            Glide.with(getApplicationContext()).load(bestSale.getImg_url()).into(mDetailImage);
            mDetailItemName.setText(bestSale.getName());
            mDetailPrice.setText("$"+ bestSale.getPrice());
            mDetailRatingNumber.setText(String.valueOf(bestSale.getRating()));

            if (bestSale.getRating() >= 4) {
                mDetailRatingType.setText("Very Good");
            } else {
                mDetailRatingType.setText("Good");
            }

            mDetailDesc.setText(bestSale.getDesc());
        }

        if (items != null) {
            Glide.with(getApplicationContext()).load(items.getImg_url()).into(mDetailImage);
            mDetailItemName.setText(items.getName());
            mDetailPrice.setText("$"+ items.getPrice());
            mDetailRatingNumber.setText(String.valueOf(items.getRating()));

            if (items.getRating() >= 4) {
                mDetailRatingType.setText("Very Good");
            } else {
                mDetailRatingType.setText("Good");
            }

            mDetailDesc.setText(items.getDesc());
        }

        mDetailAddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mDetailBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, AddressActivity.class);
                if (feature != null) {
                    intent.putExtra("item", feature);
                }

                if (bestSale != null) {
                    intent.putExtra("item", bestSale);
                }

                if (items != null) {
                    intent.putExtra("item", items);
                }
                startActivity(intent);

            }
        });


    }
}
