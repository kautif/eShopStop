package com.example.eshopstop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eshopstop.DetailActivity;
import com.example.eshopstop.R;
import com.example.eshopstop.domain.BestSale;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BestSaleAdapter extends RecyclerView.Adapter<BestSaleAdapter.ViewHolder> {
    Context context;
    List<BestSale> mBestSaleList;
    public BestSaleAdapter(Context context, List<BestSale> mBestSaleList) {
        this.context = context;
        this.mBestSaleList = mBestSaleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_bestsale_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mBestSaleName.setText(mBestSaleList.get(position).getName());
        holder.mBestSalePrice.setText("$" + mBestSaleList.get(position).getPrice());
        Glide.with(context).load(mBestSaleList.get(position).getImg_url()).into(holder.mBestSaleImage);

        holder.mBestSaleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, DetailActivity.class);
                intent.putExtra("detail", mBestSaleList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mBestSaleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mBestSaleImage;
        TextView mBestSalePrice;
        TextView mBestSaleName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mBestSaleImage = itemView.findViewById(R.id.bestSale_imageView);
            mBestSalePrice = itemView.findViewById(R.id.bestsale_price_textView);
            mBestSaleName = itemView.findViewById(R.id.bestSale_name_textView);
        }
    }
}
