package com.namaskar.namaskar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.namaskar.namaskar.CategoryAdapter;
import com.namaskar.namaskar.CategoryModel;
import com.namaskar.namaskar.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private Dialog loading;

    private  List<CategoryModel>list;
private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      loading=new Dialog(this);
      loading.setContentView(R.layout.loading_dailog);
         loading.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
         loading.setCancelable(false);

        recyclerView=findViewById(R.id.rv);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        list=new ArrayList<>();




        final CategoryAdapter adapter=new CategoryAdapter(list);
        recyclerView.setAdapter(adapter);

        loading.show();
        myRef.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(CategoriesActivity.this,dataSnapshot.getValue().toString(),Toast.LENGTH_SHORT).show();
            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                list.add(dataSnapshot1.getValue(CategoryModel.class));
              }
            adapter.notifyDataSetChanged();
            loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CategoriesActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            loading.dismiss();
            finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    ////////////////////////////////////////////Sound//////////////
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
