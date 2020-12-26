package com.namaskar.namaskar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.namaskar.namaskar.MainActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class BarcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView= new ZXingScannerView(this);



        setContentView(scannerView);
       //////Sound //////////////////////////////////////////////////sound/////////////////


        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {

                Toast.makeText(BarcodeActivity.this, "Permission already granted!", Toast.LENGTH_LONG).show();

            }else
            {

                requestPermission();
                Toast.makeText(this, "आपने  कैमरा पर्मिशन Deny की हुई है कृपया Allow  कीजिये   बैक करने के लिए बैक  बटन दबाये  तीन  बार ", Toast.LENGTH_SHORT).show();


            }
        }

    }
    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(BarcodeActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void  onRequestPermissionResult(final int requestCode , String permission[], int grantResult[])
    {
        switch (requestCode)
        {
            case REQUEST_CAMERA:
                if(grantResult.length>0)
                {
                    boolean cameraAccepted = grantResult[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted)
                    {
                        Toast.makeText(BarcodeActivity.this, "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(BarcodeActivity.this, "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                          if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                          {
                              if(shouldShowRequestPermissionRationale(CAMERA))
                              {
                                  displayAlertMessage("You Need To Allow Access For Both Permisssion",
                                          new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialog, int which) {
                                                  requestPermissions(new String[]{CAMERA},REQUEST_CAMERA);
                                              }
                                          });
                                  return;
                              }
                          }
                    }
                }
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {

                requestPermission();

            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        scannerView.stopCamera();
    }




    public void   displayAlertMessage(String message, DialogInterface.OnClickListener listener)
    {
       new AlertDialog.Builder(BarcodeActivity.this)
       .setMessage(message)
       .setPositiveButton("Ok",listener)
       .setNegativeButton("Cancel",null)
       .create()
       .show();
    }
    @Override
    public void handleResult(Result result) {
        final String myResult = result.getText();
        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
           @Override
           public void onClick(DialogInterface dialogInterface ,int i){
               scannerView.resumeCameraPreview(BarcodeActivity.this);

           }
     });
     builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int i) {

             Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myResult));
             startActivity(browserIntent);
         }
     });
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();




    }
//////////////////Sound///////////////////////////////
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
