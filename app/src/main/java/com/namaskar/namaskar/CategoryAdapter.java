package com.namaskar.namaskar;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namaskar.namaskar.CategoryModel;
import com.namaskar.namaskar.SetsActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {
    private List<CategoryModel>categoryModelsList;

    public CategoryAdapter(List<CategoryModel> categoryModelsList) {
        this.categoryModelsList = categoryModelsList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
       holder.setData(categoryModelsList.get(position).getUrl(),categoryModelsList.get(position).getName(),categoryModelsList.get(position).getSets());
    }

    @Override
    public int getItemCount() {
        return categoryModelsList.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
             private CircleImageView imageview;
             private TextView title;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageview=itemView.findViewById(R.id.image_view);
            title=itemView.findViewById(R.id.title);
        }
    private void setData(String url, final String title, final int sets){
        Glide.with(itemView.getContext()).load(url).into(imageview);
        this.title.setText(title);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setIntent=new Intent(itemView.getContext(), SetsActivity.class);
                setIntent.putExtra("title",title);
                setIntent.putExtra("sets",sets);
                itemView.getContext().startActivity(setIntent);
            }
        });
    }

    }

}
