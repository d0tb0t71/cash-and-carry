package com.example.cashcarry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<CategoryModel> categoryModel;
    Context context;

    public CategoryAdapter(Context context,ArrayList<CategoryModel> categoryModel){

        this.context = context;
        this.categoryModel = categoryModel;

    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        holder.cat_image.setImageResource(categoryModel.get(position).getCategoryLogo());
        holder.cat_name.setText(categoryModel.get(position).getCategoryName());

    }

    @Override
    public int getItemCount() {
        return categoryModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView cat_image;
        TextView cat_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cat_image = itemView.findViewById(R.id.cat_image);
            cat_name = itemView.findViewById(R.id.cat_name);

        }
    }
}
