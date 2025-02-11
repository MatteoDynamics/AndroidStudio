package com.example.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends Service {

    private static final String CHANNEL_ID = "download_channel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra("url");

        createNotificationChannel();

        // Pobieranie pliku w osobnym wątku
        new Thread(() -> {
            downloadFile(url);
        }).start();

        return START_NOT_STICKY;
    }

    private void downloadFile(String url) {
        try {
            URL urlObj = new URL(url);
            URLConnection connection = urlObj.openConnection();
            connection.connect();

            int fileLength = connection.getContentLength();

            InputStream input = new BufferedInputStream(urlObj.openStream());
            OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/downloaded_file");

            byte[] data = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // Publikuj postęp pobierania w notyfikacji
                updateNotificationProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

            // Wyświetl powiadomienie o zakończeniu pobierania
            showDownloadCompleteNotification();

        } catch (IOException e) {
            e.printStackTrace();
            // Obsłuż błąd pobierania
            showDownloadFailedNotification(e.getMessage());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void updateNotificationProgress(int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle("Pobieranie pliku")
                .setContentText("Pobieranie w toku...")
                .setProgress(100, progress, false);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void showDownloadCompleteNotification() {
        // TODO: Implement displaying a notification when the download is complete
        // You can show a toast message, a notification with an action to open the file, or display the file directly if it's an image or audio file.
    }

    private void showDownloadFailedNotification(String errorMessage) {
        // Show a notification to the user indicating that the download failed.
        // Include the error message in the notification for debugging purposes.
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}