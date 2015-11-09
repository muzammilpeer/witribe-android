package com.ranisaurus.utilitylayer.file;


//import com.andorid.components.afilechooser.localstorage.LocalStorageProvider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.ranisaurus.utilitylayer.file.model.FileInfoModel;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.view.ImageUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * Created by muzammilpeer on 10/2/15.
 */
public class FileUtil {

    // Image File Name Format = IMG_yyyyMMdd_HHmmss.png
    public static String createFolder(String appName)
    {

        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        rootPath += "/"+ appName;

        File dir = new File(rootPath);
        try{
            if(dir.mkdir()) {
                Log4a.e("Directory created","True");
            } else {
                Log4a.e("Directory created", "False");
            }
        }catch(Exception e){
            Log4a.printException(e);
        }

        return rootPath;
    }

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


    public static ArrayList<FileInfoModel> listFiles(String path) {
        ArrayList<FileInfoModel> listDataItems = new ArrayList<FileInfoModel>();
        ArrayList<String> paths;
        try {
            File file = new File(path);
            paths = new ArrayList<String>(Arrays.asList(file.list()));
            for (String _path : paths) {
                Log.v("AllFiles", _path);
                Log.v("AllFiles", path + File.separator + _path);
                listDataItems.add(readFileDescription(path + File.separator + _path));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDataItems;
    }


    public static FileInfoModel readFileDescription(String path) {

        FileInfoModel stickyModel = new FileInfoModel();
        try {
            File file = new File(path);

            stickyModel.setDateCreated(file.lastModified() +"");
            stickyModel.setDateModifed(file.lastModified() + "");
            stickyModel.setFileName(file.getName());
            stickyModel.setFileSize(file.length() + "");
            stickyModel.setFullPath(path);
            Log4a.e("File Path =",path+"");

        } catch (Exception e) {
            Log4a.printException(e);
        }
        return stickyModel;
    }

}