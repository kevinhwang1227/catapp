package com.example.user.catmap;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCat extends AppCompatActivity {
    ImageView mImageView;
    boolean mPermissionDenied= false;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);
        mImageView = (ImageView) findViewById(R.id.imageView);
        goToPhotos();
    }

    private void goToPhotos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Access to Camera", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            // Access to the location has been granted to the app.
            dispatchTakePictureIntent();
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.user.catmap.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            setPic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goToPhotos();
                } else {
                    mPermissionDenied = true;
                }
            }
        }
    }

    private void setPic() throws IOException {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();
        int scaleFactor = 1;
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        if (targetW > 0 && targetH > 0) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
                break;
        }
        mImageView.setImageBitmap(rotatedBitmap);

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


}
