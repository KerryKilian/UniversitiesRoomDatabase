package com.kerrykilian.studentuniversityroomdb.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.kerrykilian.studentuniversityroomdb.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class ImageStorage {
//    public static boolean saveImageToStorage(Bitmap bitmap, String matrikel, Context context) {
//        String filename = matrikel + ".jpg"; // Name of the image file
//        File directory = Environment.getExternalStorageDirectory(); // Get the external storage directory
//
//        // Create a new file in the specified directory
//        File file = new File(directory, filename);
//
//        try {
//            FileOutputStream outputStream = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream); // Compress and save the bitmap as a JPEG
//            outputStream.flush();
//            outputStream.close();
//
//            // Optionally, you can notify the MediaScanner to add the saved image to the device's gallery
//            MediaScannerConnection.scanFile(
//                    context,
//                    new String[]{file.getAbsolutePath()},
//                    null,
//                    null
//            );
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    public static Bitmap loadImageFromStorage(String matrikel, Context context) {
//        try {
//            File directory = Environment.getExternalStorageDirectory();
//            File file = new File(directory, matrikel + ".jpg");
//            if (file.exists()) {
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public static Uri saveImageToStorage(Bitmap bitmap, String matrikel, Context context){
////        ContextWrapper cw = new ContextWrapper(context);
////        // path to /data/data/yourapp/app_data/imageDir
////        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
////        // Create imageDir
////        File mypath=new File(directory,matrikel + ".jpg");
////
////        FileOutputStream fos = null;
////        try {
////            fos = new FileOutputStream(mypath);
////            // Use the compress method on the BitMap object to write image to the OutputStream
////            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
////        } catch (Exception e) {
////            e.printStackTrace();
////        } finally {
////            try {
////                fos.close();
////                return true;
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        }
////        return false;
//        ContentResolver contentResolver = context.getContentResolver();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "image.jpg");
//        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//
//        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//
//
//        try {
//            OutputStream outputStream = contentResolver.openOutputStream(imageUri);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return imageUri;
//    }
//
//    public static Bitmap loadImageFromStorage(String matrikel, Context context, Uri uri) {
////        ContextWrapper cw = new ContextWrapper(context);
////        // path to /data/data/yourapp/app_data/imageDir
////        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
////        try {
////            File f=new File(directory, matrikel + ".jpg");
////            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
////
////            return b;
////        }
////        catch (FileNotFoundException e)
////        {
////            e.printStackTrace();
////        }
////    return null;
//        ContentResolver contentResolver = context.getContentResolver();
//
//        try {
//            InputStream inputStream = contentResolver.openInputStream(uri);
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            inputStream.close();
//
//            // Use the bitmap as needed
////            imageView.setImageBitmap(bitmap);
//            return bitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static void saveImageToStorage(Bitmap bitmap, String name, Context context) {
        try {
            OutputStream fos;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name + ".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                    fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));

                } else{
                    String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                    File image = new File(imagesDir, name + ".jpg");
                    fos = new FileOutputStream(image);
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos).close();
            }
         catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
