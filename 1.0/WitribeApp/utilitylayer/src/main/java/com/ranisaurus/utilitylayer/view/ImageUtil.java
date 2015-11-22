package com.ranisaurus.utilitylayer.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ranisaurus.utilitylayer.R;
import com.ranisaurus.utilitylayer.file.FileUtil;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by muzammilpeer on 10/2/15.
 */
public class ImageUtil {


    public static void getImageFromUrl(CGSize resizeImage, final ImageView imageView, final ProgressBar progressBar, String imageURL) {
        if (imageView != null && imageURL != null && imageURL.contains("http")) {

            //make html encode the white spaces for network operation
            imageURL = imageURL.replaceAll(" ", "%20");

            Picasso.with(imageView.getContext())
                    .load(imageURL)
                    .resize(resizeImage.WIDTH, resizeImage.HEIGHT)
                    .centerInside()
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (progressBar != null) {
                                progressBar.setVisibility(GONE);
                            }
                            if (imageView != null) {
                                imageView.setVisibility(VISIBLE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (progressBar != null) {
                                progressBar.setVisibility(GONE);
                            }
                            if (imageView != null) {
                                imageView.setVisibility(VISIBLE);

                            }
                        }
                    });
        }

    }

    public static String saveImageFromUrl(CGSize resizeImage, final ImageView imageView, final ProgressBar progressBar, String imageURL) {
        String folderPath = FileUtil.createFolder(imageView.getResources().getString(R.string.app_name));
        String cachefolderPath = FileUtil.createFolder(imageView.getResources().getString(R.string.app_name) + "/cache/");
        final String notification_image = cachefolderPath + "/" + "notification_large_icon.png";

        if (imageView != null && imageURL != null && imageURL.contains("http")) {

            //make html encode the white spaces for network operation
            imageURL = imageURL.replaceAll(" ", "%20");


            Picasso.with(imageView.getContext())
                    .load(imageURL)
                    .resize(resizeImage.WIDTH, resizeImage.HEIGHT)
                    .centerInside()
                    .into(new Target() {
                              @Override
                              public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {


                                  File file = new File(notification_image);
                                  try {
                                      file.createNewFile();
                                      FileOutputStream ostream = new FileOutputStream(file);
                                      bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                                      ostream.close();
                                      if (bitmap != null) {
                                          if (progressBar != null) {
                                              progressBar.setVisibility(GONE);
                                          }
                                          if (imageView != null) {
                                              imageView.setVisibility(VISIBLE);
                                              imageView.setImageBitmap(bitmap);
                                          }
                                      }
                                  } catch (Exception e) {
                                      Log4a.printException(e);
                                  }
                              }

                              @Override
                              public void onBitmapFailed(Drawable errorDrawable) {
                                  if (progressBar != null) {
                                      progressBar.setVisibility(GONE);
                                  }
                                  if (imageView != null) {
                                      imageView.setVisibility(VISIBLE);

                                  }
                              }

                              @Override
                              public void onPrepareLoad(Drawable placeHolderDrawable) {

                              }
                          }
                    );
        }
        return notification_image;
    }


    //camera testing
    public static void captureCameraImage(Activity activity, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtil.getOutputImageFileUri(activity));
        activity.startActivityForResult(intent, requestCode);
    }


    //rotoate image for samsung devices
    public static Bitmap rotateImage(String filePath) {

        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, bounds);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 8;
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inDither = true;
        Bitmap bm = BitmapFactory.decodeFile(filePath, opts);

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;

        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = null;
        try {
            //rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
            rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth() - 1, bm.getHeight() - 1, matrix, true);
        } catch (Exception e) {
            Log4a.e("Bitmap", e.toString() + "");
        }

        return rotatedBitmap;
    }
}
