package com.example.ana.ultimatewarrior;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {

    private Camera mCamera;
    private SurfaceView mPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mPreview = (SurfaceView) findViewById(R.id.preview);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mCamera = Camera.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCamera.release();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width, selected.height);
        mCamera.setParameters(params);
        mCamera.setDisplayOrientation(90);

        mCamera.startPreview();

        // display image of object found near by
        showImageInCamera();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mPreview.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("PREVIEW", "surfaceDestroyed");
    }

    private void showImageInCamera() {
        final View parentView = findViewById(R.id.gold_mine_1);
        final ImageView effect_camera = new ImageView(this);
        effect_camera.setImageResource(R.drawable.gold_mine_1);
        effect_camera.setBackgroundColor(Color.parseColor("#80000000")); // transparent color

        /* Set object properties
        effect_camera.post(new Runnable(){
            @Override
            public void run(){
                RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams)effect_camera.getLayoutParams();
                double percentHeight = 60*.25;
                double percentWidth = 60*1;
                lp.height = (int) percentHeight;
                lp.width = (int) percentWidth;
                effect_camera.setLayoutParams(lp);
            }
        });*/

        // Show the object in camera
        parentView.setVisibility(View.VISIBLE);
    }
}