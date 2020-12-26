package com.namaskar.namaskar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);


        Thread td=new Thread(){
            public void run(){
                try {
                    sleep(4000);
                }catch (Exception exp){
                    exp.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(WelcomeScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };td.start();




    }
}
