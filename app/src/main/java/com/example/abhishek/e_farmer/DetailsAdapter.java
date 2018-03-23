package com.example.abhishek.e_farmer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhishek on 17/03/18.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder>{

    private Context context;
    private List<Details> mData;

    public DetailsAdapter(Context context, List<Details> mData) {
        this.context = context;
        this.mData = mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.card_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.tv_category.setText(mData.get(position).getCategoryName());
        holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbNail());
        holder.tv_product.setText(mData.get(position).getProductName());
        holder.tv_quantity.setText(mData.get(position).getQuantity());
        holder.tv_baseprice.setText(mData.get(position).getBasePrice());
        holder.tv_productIdentity.setText(mData.get(position).getpId());

        holder.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pID = holder.tv_productIdentity.getText().toString();
                Log.i("PID",pID);
                Intent intent = new Intent(context,ShowRetailerInFarmer.class);
                intent.putExtra("Value",pID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_category;
        ImageView img_book_thumbnail;
        TextView tv_product, tv_quantity, tv_baseprice,tv_productIdentity;
        CardView cardView2;


        public MyViewHolder(View itemView) {
            super(itemView);

            tv_category = itemView.findViewById(R.id.category);
            img_book_thumbnail = itemView.findViewById(R.id.imageThumbNail);
            tv_product = itemView.findViewById(R.id.productName);
            tv_quantity = itemView.findViewById(R.id.productTotalQuantity);
            tv_baseprice = itemView.findViewById(R.id.productBasePrice);
            cardView2 = itemView.findViewById(R.id.cardview_id);
            tv_productIdentity = itemView.findViewById(R.id.productIdentity);
        }
    }
}
