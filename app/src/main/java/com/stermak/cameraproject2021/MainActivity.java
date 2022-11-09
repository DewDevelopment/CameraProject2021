package com.stermak.cameraproject2021;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 112;

    Button btnCamera;
    SurfaceView sv;
    SurfaceHolder holder;

    final boolean FULL_SCREEN = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamera = findViewById(R.id.btnCamera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }
        });
    }
    public void checkPermission(String permission, int requestCode) {

        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            //take permissions
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        } else {

            Toast.makeText(MainActivity.this, "Камера уже получила разрешение", Toast.LENGTH_LONG).show();


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btnCamera.setText("Камера получила разрешение");

                Camera camera = Camera.open();
                SurfaceView surfaceView = findViewById(R.id.surfaceView);
                SurfaceHolder surfaceHolder = surfaceView.getHolder();
                try {
                    camera.setPreviewDisplay(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                camera.startPreview();

            } else {
                Toast.makeText(MainActivity.this, "Камера не получила разрешение", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
