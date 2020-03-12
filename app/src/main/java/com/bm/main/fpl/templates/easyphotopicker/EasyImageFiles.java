package com.bm.main.fpl.templates.easyphotopicker;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.bm.main.fpl.utils.PreferenceClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jacek Kwiecień on 14.12.15.
 */
class EasyImageFiles implements Constants {

    private static String getFolderName(@NonNull Context context) {
        return EasyImage.configuration(context).getFolderName();
    }

    private static File tempImageDirectory(@NonNull Context context) {
        File privateTempDir = new File(context.getCacheDir(), DEFAULT_FOLDER_NAME);
        if (!privateTempDir.exists()) privateTempDir.mkdirs();
        return privateTempDir;
    }

    private static void writeToFile(Context context, InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();

            ContentValues values = new ContentValues();
//
            values.put(MediaStore.Images.Media.LATITUDE, Double.parseDouble(PreferenceClass.getString("lat","0")));
            values.put(MediaStore.Images.Media.LONGITUDE, Double.parseDouble(PreferenceClass.getString("long","0")));
            //values.put(.,
            //		System.currentTimeMillis());
            values.put(MediaStore.Images.Media.DATE_TAKEN,
                    System.currentTimeMillis());

            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.MediaColumns.DATA,
                    file.getAbsolutePath());

            context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(Context context, File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        writeToFile(context,in, dst);
    }

    static void copyFilesInSeparateThread(final Context context, final List<File> filesToCopy) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<File> copiedFiles = new ArrayList<>();
                int i = 1;
                for (File fileToCopy : filesToCopy) {
                    File dstDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getFolderName(context));
                    if (!dstDir.exists()) dstDir.mkdirs();

                    String[] filenameSplit = fileToCopy.getName().split("\\.");
//                    String extension = "." + filenameSplit[filenameSplit.length - 1];
                    String extension = filenameSplit[filenameSplit.length - 1];
                    String filename = String.format("IMG_%s_%d.%s", new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()), i, extension);


                    File dstFile = new File(dstDir, filename);
                    ContentValues values = new ContentValues();
//
                    values.put(MediaStore.Images.Media.LATITUDE, Double.parseDouble(PreferenceClass.getString("lat","0")));
                    values.put(MediaStore.Images.Media.LONGITUDE, Double.parseDouble(PreferenceClass.getString("long","0")));
                    //values.put(.,
                    //		System.currentTimeMillis());
                    values.put(MediaStore.Images.Media.DATE_TAKEN,
                            System.currentTimeMillis());

                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    values.put(MediaStore.MediaColumns.DATA,
                            filename);

                    context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    try {
                        dstFile.createNewFile();
                        copyFile(context,fileToCopy, dstFile);
                        copiedFiles.add(dstFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    i++;

                }

                scanCopiedImages(context, copiedFiles);
            }
        }).run();
    }

    static List<File> singleFileList(File file) {
        List<File> list = new ArrayList<>();
        list.add(file);
        return list;
    }

    static void scanCopiedImages(final Context context, List<File> copiedImages) {
        String[] paths = new String[copiedImages.size()];
        for (int i = 0; i < copiedImages.size(); i++) {
            paths[i] = copiedImages.get(i).toString();
        }

        MediaScannerConnection.scanFile(context,
                paths, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d(getClass().getSimpleName(), "Scanned " + path + ":");
                        Log.d(getClass().getSimpleName(), "-> uri=" + uri);

                    }
                });




    }

    static File pickedExistingPicture(@NonNull Context context, Uri photoUri) throws IOException {
        InputStream pictureInputStream = context.getContentResolver().openInputStream(photoUri);
        File directory = tempImageDirectory(context);
        File photoFile = new File(directory, UUID.randomUUID().toString() + "." + getMimeType(context, photoUri));
        photoFile.createNewFile();
        writeToFile(context,pictureInputStream, photoFile);
        return photoFile;
    }

    static File getCameraPicturesLocation(@NonNull Context context) throws IOException {
        File dir = tempImageDirectory(context);
        Log.d("ImageFile", "getCameraPicturesLocation: "+ UUID.randomUUID().toString());
//        return File.createTempFile(UUID.randomUUID().toString(), ".jpg", dir);
        String imageName= UUID.randomUUID().toString();
        File imageFile = File.createTempFile(imageName, ".jpg", dir);
        FileOutputStream fos;
        try{
            fos = new FileOutputStream(imageFile);
            String imagePath = imageFile.getAbsolutePath();
            //scan the image so show up in album
            MediaScannerConnection.scanFile(context, new String[]{imagePath},
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });

            // b.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
            //b.recycle();
//bmpUri=Uri.fromFile(file);

            ContentValues values = new ContentValues();
//
            values.put(MediaStore.Images.Media.LATITUDE, Double.parseDouble(PreferenceClass.getString("lat","0")));
            values.put(MediaStore.Images.Media.LONGITUDE, Double.parseDouble(PreferenceClass.getString("long","0")));
            //values.put(.,
            //		System.currentTimeMillis());
            values.put(MediaStore.Images.Media.DATE_TAKEN,
                    System.currentTimeMillis());

            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.MediaColumns.DATA,
                    imageFile.getAbsolutePath());

            context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    static File getCameraVideoLocation(@NonNull Context context) throws IOException {
        File dir = tempImageDirectory(context);
        return File.createTempFile(UUID.randomUUID().toString(), ".mp4", dir);
    }

    /**
     * To find out the extension of required object in given uri
     * Solution by http://stackoverflow.com/a/36514823/1171484
     */
    private static String getMimeType(@NonNull Context context, @NonNull Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }

    static Uri getUriToFile(@NonNull Context context, @NonNull File file) {
        String packageName = context.getApplicationContext().getPackageName();
        String authority = packageName + ".fileprovider";
        //FileProvider.getUriForFile(CetakUlangActivity.this, "com.bm.main.fpl.fileprovider"
        return FileProvider.getUriForFile(context, authority, file);
    }

}
