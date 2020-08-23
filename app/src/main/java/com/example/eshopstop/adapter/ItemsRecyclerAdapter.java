package com.example.eshopstop.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eshopstop.DetailActivity;
import com.example.eshopstop.R;
import com.example.eshopstop.domain.Items;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder> {
    Context context;
    List<Items>itemsList;
    public ItemsRecyclerAdapter(Context context, List<Items> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    Log.i("ItemsRecyclerAdapter", "setting context");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("ItemsRecyclerAdapter", "onCreateViewHolder");
         View view = LayoutInflater.from(context).inflate(R.layout.single_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Values coming from Items.java
        holder.mName.setText(itemsList.get(position).getName());
        holder.mCost.setText("$" + itemsList.get(position).getPrice());
        Glide.with(context).load(itemsList.get(position).getImg_url()).into(holder.mItemImage);

        holder.mItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail", itemsList.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mItemImage;
        private TextView mCost;
        private TextView mName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemImage = itemView.findViewById(R.id.item_imageView);
            mName = itemView.findViewById(R.id.item_name_textView);
            mCost = itemView.findViewById(R.id.item_cost_textView);
        }
    }

}
