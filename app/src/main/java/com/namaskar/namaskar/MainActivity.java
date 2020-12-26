package com.namaskar.namaskar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.namaskar.namaskar.TextRecognition;
import com.yasic.library.particletextview.MovingStrategy.BidiHorizontalStrategy;
import com.yasic.library.particletextview.Object.ParticleTextViewConfig;
import com.yasic.library.particletextview.View.ParticleTextView;

public class MainActivity extends AppCompatActivity {
     private Button scan,textReco,startBtn;
     private  MediaPlayer player;


    public static final int MY_CAMERA_REQUEST_CODE = 100;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }


         player =  MediaPlayer.create(this,R.raw.backround);
        player.setLooping(true);
        player.start();
        scan = findViewById(R.id.scanner);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BarcodeActivity.class);
                startActivity(intent);
                player.pause();

            }
        });

       textReco=findViewById(R.id.text_recognizer);
       textReco.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              Toast.makeText(getApplicationContext(),"कृपया बारकोड स्कैनर को पहले ओपन करके परमिशन Allow करे  ",Toast.LENGTH_SHORT).show();
           Intent intent=new Intent(MainActivity.this, TextRecognition.class);
           startActivity(intent);
               player.pause();
           }
       });

       startBtn=findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CategoriesActivity.class);
                startActivity(intent);
                player.pause();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        player.start();

    }
    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
    }
    @Override
    public void onBackPressed() {
        final android.app.AlertDialog.Builder quizEnd=new AlertDialog.Builder(this);
        quizEnd.setCancelable(false);
        quizEnd.setTitle("नमस्कार एप्प आपका धन्यवाद करता है  ");
        quizEnd.setPositiveButton(" बहार जाने के लिए ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "कृपया एक बार फिर बैक बटन दबाये ", Toast.LENGTH_SHORT).show();
                finish();
                System.exit(0);
            }
        });
        quizEnd.setNegativeButton("रद्द करे ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                   quizEnd.setCancelable(true);
            }
        });
        quizEnd.show();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "अगर आप Permission Deny करते है तो आपको एप्प का इस्तेमाल  करने में  कठनाई होगी ", Toast.LENGTH_LONG).show();
            }
        }
    }

}



