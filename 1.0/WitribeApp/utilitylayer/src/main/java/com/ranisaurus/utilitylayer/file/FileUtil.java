package com.ranisaurus.utilitylayer.file;


//import com.andorid.components.afilechooser.localstorage.LocalStorageProvider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.ranisaurus.utilitylayer.view.ImageUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by muzammilpeer on 10/2/15.
 */
public class FileUtil {

    // Image File Name Format = IMG_yyyyMMdd_HHmmss.png

    private static String getTempDirectoryPath(Context ctx) {
        File cache;

        // SD Card Mounted
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            cache = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + "/Android/data/"
                    + ctx.getPackageName() + "/cache/");
        }
        // Use internal storage
        else {
            cache = ctx.getCacheDir();
        }

        // Create the cache directory if it doesn't exist
        if (!cache.exists()) {
            cache.mkdirs();
        }

        return cache.getAbsolutePath();
    }

    public static Uri getOutputImageFileUri(Context ctx) {
        // TODO: check the presence of SDCard

        String tstamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH)
                .format(new Date());
        File file = new File(getTempDirectoryPath(ctx), "IMG_" + tstamp
                + ".png");

        return Uri.fromFile(file);
    }


    public static Bitmap getResizeBitmapObject(String filePath, int sampleSize) throws Exception
    {
        Bitmap bitmap = null;
        File imgFile = new File(filePath);

        if (imgFile.exists()) {

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = sampleSize;
            bitmap = ImageUtil.rotateImage(filePath);
        }else {
            throw new Exception("File not exist");
        }
        return bitmap;
    }



    public static String getFileNameFromURI(Uri uri)
    {
        String filePath = uri.getPath();
        if (filePath != null && filePath.length() > 0 )
        {
            return filePath.substring(
                    filePath.lastIndexOf('/') + 1,
                    filePath.length());
        }

        return filePath + "";
    }

}