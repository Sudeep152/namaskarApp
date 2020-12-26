package com.namaskar.namaskar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Scroe3Activity extends AppCompatActivity {
private Button done,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroe3);

        done=findViewById(R.id.button5);
        next=findViewById(R.id.nxt1);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info=new Intent(Scroe3Activity.this,InformationActivity.class);
                startActivity(info);
            }
        });
    }
}
