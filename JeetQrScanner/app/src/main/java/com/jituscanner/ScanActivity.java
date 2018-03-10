package com.jituscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import com.jituscanner.R;

public class ScanActivity extends AppCompatActivity {
    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;
    SurfaceHolder.Callback mSurfaceHolderCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jituscanner.R.layout.activity_scan);
        cameraView = (SurfaceView) findViewById(R.id.cameraView);
        cameraView.setZOrderMediaOverlay(true);
        holder = cameraView.getHolder();
        barcode = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE | Barcode.ALL_FORMATS)
                .build();
        if(!barcode.isOperational()){
            Toast.makeText(getApplicationContext(), "Sorry, Couldn't setup the detector", Toast.LENGTH_LONG).show();
            this.finish();
        }


         mSurfaceHolderCallback =  new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    if(ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

                        cameraSource.start(cameraView.getHolder());

                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        };
        iTest = 1;
        releaseCameraAndPreview();

        cameraSource = new CameraSource.Builder(this, barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                //.setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(300, 300)
                .setRequestedFps(30.0f)
                .build();
        cameraView.getHolder().addCallback(mSurfaceHolderCallback);
        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

              /*  Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Barcode> barcodes = detections.detect(frame);
*/
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {

                }


              Log.i("iTest","=====>"+iTest);
                if (iTest == 1) {

                   // final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                    if (barcodes.size() > 0) {

                     //  bmp = null;
                        cameraSource.takePicture(null, new CameraSource.PictureCallback() {
                            @Override
                            public void onPictureTaken(byte[] bytes) {


                                if(bmp !=null)
                                {
                                    bmp.recycle();
                                }









                                bmp = getResizedBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length),300);

                                Log.d("BITMAP", bmp.getWidth() + "x" + bmp.getHeight());

                                Intent intent = new Intent();
                                intent.putExtra("barcode", barcodes.valueAt(0));

                                if (bmp != null)
                                    intent.putExtra("bitmap", bmp);

                                iTest = iTest + 1;

                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });




                    }
                }


            }

        });
    }
    Bitmap bmp = null;
    int iTest = 1;

    private void releaseCameraAndPreview() {
        if (cameraSource != null) {
            //cameraView.setPreviewCallback(null);
            cameraView.getHolder().removeCallback(mSurfaceHolderCallback);
            cameraSource.release();
            cameraSource = null;
        }
    }


    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
            cameraSource = null;
        }
        if (cameraView !=null) {
            cameraView = null;
        }

        barcode = null;
        holder = null;
    }*/



}
