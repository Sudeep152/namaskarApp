package com.namaskar.namaskar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Score6Activity extends AppCompatActivity {
private Button don,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score6);
        don=findViewById(R.id.button6);
        next=findViewById(R.id.nextbtn1);
        don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info=new Intent(Score6Activity.this,InformationActivity.class);
                startActivity(info);
            }
        });
    }
}
