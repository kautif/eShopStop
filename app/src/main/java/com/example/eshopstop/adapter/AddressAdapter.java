package com.example.eshopstop.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eshopstop.domain.Address;

import com.example.eshopstop.R;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context applicationContext;
    List<Address> mAddressList;
    private RadioButton mSelectedAddressBtn;
    SelectedAddress selectedAddress;

    public AddressAdapter(Context applicationContext, List<Address> mAddressList, SelectedAddress mAddress) {
        this.applicationContext = applicationContext;
        this.mAddressList = mAddressList;
        this.selectedAddress = mAddress;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.single_address_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.currentAddress.setText(mAddressList.get(position).getAddress());

        holder.mSelectedAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Address address : mAddressList) {
                    address.setSelected(false);
                }
                mAddressList.get(position).setSelected(true);

                if (mSelectedAddressBtn != null) {
                    mSelectedAddressBtn.setChecked(false);
                }

                mSelectedAddressBtn = (RadioButton) view;
                mSelectedAddressBtn.setChecked(true);
                selectedAddress.setAddress(mAddressList.get(position).getAddress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView currentAddress;
        private RadioButton mSelectedAddressBtn;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            currentAddress = itemView.findViewById(R.id.current_address_textView);
            mSelectedAddressBtn = itemView.findViewById(R.id.selected_address_radio_button);

        }
    }

    public interface SelectedAddress {
        public void setAddress(String s);
    }
}
