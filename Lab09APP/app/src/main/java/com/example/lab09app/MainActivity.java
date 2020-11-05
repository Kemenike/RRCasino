/*
Kanayo Emenike
Lab 09
10-22-2020
/// Description-
    This app displays "Hello World!" above a button. When the button is pressed a toast pops up at
        at the bottom of the screen with a developed by message as well as changing the
        "Hello World!" to my name.
 */

package com.example.lab09app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    Button button1, button2;
    TextView text1;
    //Context context = getApplicationContext();
    //CharSequence text = "Developed By: Kanayo Emenike";
    boolean title = true, server = false;
    int YS, YF, YD;
    int deviceHeight, deviceWidth;
    int duration = Toast.LENGTH_SHORT;
    String outText, line;
    String textConPass = "Connection Successful.";
    String textConFail = "Connection Failed.";
    String textReadFail = "Failed reading from target file.";
    //Toast toast = Toast.makeText(context, text, duration).

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView)findViewById(R.id.title);
        button1 = (Button)findViewById(R.id.click);
        button2 = (Button)findViewById(R.id.contact);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceHeight = displayMetrics.heightPixels;
        deviceWidth = displayMetrics.widthPixels;
    }

    public void displayName(View v) {
        Context context = getApplicationContext();
        String text = "Developed By: Kanayo Emenike";
        if(title) {
            text1.setText("Kanayo Emenike");
            Toast.makeText(context, text, duration).show();
            title = false;
        } else {
            text1.setText("Hello World!");
            title = true;
        }
    }

    public void contactServer(View v) {
        Thread contactThread = new Thread(new Thx());
        Context context = getApplicationContext();
        Toast toast;
        contactThread.start();
        Handler mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                final Toast toast1 = Toast.makeText(context, textConFail, duration);
                final Toast toast2 = Toast.makeText(context, textReadFail, duration);
            }
        };
    }

    public class Thx implements Runnable {
        @Override
        public void run() {
            Context context = getApplicationContext();
            String textConPass = "Connection Successful.";
            String textConFail = "Connection Failed.";
            String textReadFail = "Failed reading from target file.";
            try {
                URL url = new URL("https://cs.csub.edu/~kemenike/3350/reader.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder total = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    total.append(line + " ");
                }
                outText = total.toString();
                in.close();
            } catch (MalformedURLException e) {
                //Toast.makeText(context, textConFail, duration);
                //Message message = mHandler.obtainMessage(command, parameter);
                //message.sendToTarget();

            } catch (IOException e) {
                //Toast.makeText(context, textReadFail, duration);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();

        switch(eventaction) {
            case MotionEvent.ACTION_DOWN:
                YS = (int) event.getRawY();
            case MotionEvent.ACTION_MOVE:
                YF = (int) event.getRawY();
                YD = YF - YS;
                if(YD > (deviceHeight/2)) {
                    finish();
                    //System.exit(0);
                }
                break;
        }
        return true;
    }
}