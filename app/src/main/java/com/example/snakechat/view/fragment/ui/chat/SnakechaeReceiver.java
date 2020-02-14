package com.example.snakechat.view.fragment.ui.chat;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.snakechat.R;
import com.example.snakechat.data.local.HelperMethod;

public class SnakechaeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle b = intent.getExtras();

        if (intent.getAction().equalsIgnoreCase("com.example.snakechat")) {

                String message = b.getString("message");
                Toast.makeText(context , message , Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context,SnakechatService.class);
            i.putExtra("message" , message);
            context.startService(i);

        }
    }

}
