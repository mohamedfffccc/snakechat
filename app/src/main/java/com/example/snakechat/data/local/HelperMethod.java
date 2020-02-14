package com.example.snakechat.data.local;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.snakechat.R;

public class HelperMethod {

    private static ProgressDialog checkDialog;
    public static AlertDialog alertDialog;
    Context context;



//    public static MultipartBody.Part convertFileToMultipart(String pathImageFile, String Key) {
//        if (pathImageFile != null) {
//            File file = new File(pathImageFile);
//
//            RequestBody reqFileselect = RequestBody.create(MediaType.parse("image/*"), file);
//
//            MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, file.getName(), reqFileselect);
//
//            return Imagebody;
//        } else {
//            return null;
//        }
//    }
//

//    public static RequestBody convertToRequestBody(String part) {
//        try {
//            if (!part.equals("")) {
//                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
//                return requestBody;
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public static void onLoadImageFromUrl(ImageView imageView, String URl, Context context, int drId) {
//        Glide.with(context)
//                .load(URl)
//                .into(imageView);
//    }
//



    public static void showProgressDialog(Activity activity, String title) {
        try {
            checkDialog = new ProgressDialog(activity);
            checkDialog.setIcon(R.drawable.social);
            checkDialog.setMessage(title);
            checkDialog.setIndeterminate(false);
            checkDialog.setCancelable(false);
            checkDialog.show();

        } catch (Exception e) {

        }
    }

    public static void dismissProgressDialog() {
        try {
            if (checkDialog != null && checkDialog.isShowing()) {
                checkDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }

    public static void ReplaceFragment(FragmentManager supportFragmentManager, Fragment fragment, int container_id
            , TextView toolbarTitle, String title) {

        FragmentTransaction transaction = supportFragmentManager.beginTransaction();

        transaction.replace(container_id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }

    }



    public static void disappearKeypad(Activity activity, View v) {
        try {
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }
    public static void showAlert(String message , String url , Context con)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setTitle("Alert")
                .setIcon(R.drawable.snake_chat2)
                .setMessage(message)
                .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_VIEW , Uri.parse("market://details?id=" + url));
                        con.startActivity(i);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
    public static void notify(Context context , String msg)
    {
        int id = 0;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.snakelogoic)
                .setContentText(msg);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        manager.notify(id,builder.build());
        id++;

    }
//
}
//
//}
//
