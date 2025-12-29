package com.example.personalcard.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static final int REQUEST_CAMERA = 200;
    public static final int REQUEST_GALLERY = 201;

    private static Uri cameraImageUri;

    public static void openCamera(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = createImageFile(activity);
        if (photoFile != null) {
            cameraImageUri = FileProvider.getUriForFile(activity,
                    activity.getApplicationContext().getPackageName() + ".fileprovider",
                    photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
            activity.startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    public static void openGallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_GALLERY);
    }

    public static Uri getCameraImageUri() {
        return cameraImageUri;
    }

    private static File createImageFile(Activity activity) {
        String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
        File storageDir = activity.getFilesDir();
        return new File(storageDir, fileName);
    }

    public static String compressAndSaveImage(Activity activity, Uri imageUri) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(
                    activity.getContentResolver().openInputStream(imageUri));

            if (bitmap == null) {
                return null;
            }

            int maxSize = 800;
            if (bitmap.getWidth() > maxSize || bitmap.getHeight() > maxSize) {
                float scale = Math.min(
                        (float) maxSize / bitmap.getWidth(),
                        (float) maxSize / bitmap.getHeight()
                );
                bitmap = Bitmap.createScaledBitmap(bitmap,
                        (int) (bitmap.getWidth() * scale),
                        (int) (bitmap.getHeight() * scale),
                        true);
            }

            File file = new File(activity.getFilesDir(), "avatar_" + System.currentTimeMillis() + ".jpg");
            try (FileOutputStream out = new FileOutputStream(file)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);
            }

            bitmap.recycle();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteImageFile(String path) {
        if (path != null && !path.isEmpty()) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
