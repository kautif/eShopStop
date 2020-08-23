package com.example.eshopstop.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eshopstop.DetailActivity;
import com.example.eshopstop.R;
import com.example.eshopstop.domain.Feature;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.ViewHolder> {
    Context context;
    List<Feature> mFeatureList;
    public FeatureAdapter(Context context, List<Feature> mFeatureList) {
        this.context = context;
        this.mFeatureList = mFeatureList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_feature_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mFeatureCost.setText("$" + mFeatureList.get(position).getPrice());
        holder.mFeatureName.setText(mFeatureList.get(position).getName());
        Glide.with(context).load(mFeatureList.get(position).getImg_url()).into(holder.mFeatureImage);
        holder.mFeatureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail", mFeatureList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFeatureList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mFeatureImage;
        TextView mFeatureCost, mFeatureName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFeatureImage = itemView.findViewById(R.id.feature_imageView);
            mFeatureCost = itemView.findViewById(R.id.feature_cost);
            mFeatureName = itemView.findViewById(R.id.feature_name);

        }
    }
}
