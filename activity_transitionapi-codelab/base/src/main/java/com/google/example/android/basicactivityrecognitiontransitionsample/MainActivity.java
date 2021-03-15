/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.example.android.basicactivityrecognitiontransitionsample;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.android.common.logger.LogFragment;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Demos enabling/disabling Activity Recognition transitions, e.g., starting or stopping a walk,
 * run, drive, etc.).
 */
public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    // TODO: Review check for devices with Android 10 (29+).
    private boolean runningQOrLater =
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    private boolean activityTrackingEnabled;

    private List<ActivityTransition> activityTransitionList;

    // Action fired when transitions are triggered.
    private final String TRANSITIONS_RECEIVER_ACTION =
            BuildConfig.APPLICATION_ID + "TRANSITIONS_RECEIVER_ACTION";

    private PendingIntent mActivityTransitionsPendingIntent;

//    private boolean isLogged = false;
    private LogFragment mLogFragment;
    private String logContent = "";
    String logFilename = "";
    EditText transitionType;
    Button createLogFile;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        if(logFilename.length() != 0) {
            savedInstanceState.putString("activityLogFilename", logFilename);
            System.out.println("[onSaveInstanceState] " + logFilename + " is saved");
            System.out.println("[onSaveInstanceState] " + savedInstanceState.getString("activityLogFilename") + " is saved");
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        logFilename = savedInstanceState.getString("activityLogFilename");
        System.out.println("[onRestoreInstanceState] is called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("app created");
        System.out.println("[onCreate] "+ logFilename);
        if(savedInstanceState != null) {
            System.out.println("[onCreate] savedInstanceState not null");
            logFilename = savedInstanceState.getString("activityLogFilename");
        }

        // View
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLogFragment =
                (LogFragment) getSupportFragmentManager().findFragmentById(R.id.log_fragment);
        transitionType = (EditText) findViewById(R.id.transition_type);
        createLogFile = (Button) findViewById(R.id.create_log_file);
        createLogFile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String transType = transitionType.getText().toString();
                if (transType.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the transition type", Toast.LENGTH_SHORT).show();
                } else {
                    logFilename = transType + "-" + new SimpleDateFormat("MM-dd-HH-mm-ss", Locale.US).format(new Date()) + ".txt";
                    transitionType.getText().clear();
                    logContent = "";
                    Toast.makeText(getApplicationContext(), "Created the file: " + logFilename, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Permission
        if (activityRecognitionPermissionApproved()) {
            startForegroundService(new Intent(this, MyService.class));
        } else {
            // Request permission and start activity for result. If the permission is approved, we
            // want to make sure we start activity recognition tracking.
            Intent startIntent = new Intent(this, PermissionRationalActivity.class);
            startActivityForResult(startIntent, 0);

        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("app started");
//        if(!isLogged){
//            String logInfo;
//            Intent intent = getIntent();
//            logInfo = intent.getStringExtra("activityLogInfo");
//            System.out.println("logInfo: "+logInfo);
//            if(logInfo != null)
//                logContent += logInfo;
//            isLogged = true;
//        }
        System.out.println("logContent: "+logContent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("app resumed");
        String logInfo;
        Intent intent = getIntent();
        logInfo = intent.getStringExtra("activityLogInfo");
        System.out.println("[onResume] logInfo: "+logInfo);
        if(logInfo != null)
            logContent += logInfo;
        System.out.println("logContent: "+logContent);
    }

    @Override
    protected void onPause() {
        System.out.println("app paused");
        super.onPause();
    }


    @Override
    protected void onStop() {

        // TODO: Unregister activity transition receiver when user leaves the app.
//        unregisterReceiver(mTransitionsReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("[onDestroy] is called");
    }

//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState){
//        super.onRestoreInstanceState(savedInstanceState);
//        String savedLogFilename = savedInstanceState.getString("activityLogFilename");
//        if(savedLogFilename != null)
//            logFilename = savedLogFilename;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Start activity recognition if the permission was approved.
//        if (activityRecognitionPermissionApproved() && !activityTrackingEnabled) {
//            enableActivityTransitions();
//        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * On devices Android 10 and beyond (29+), you need to ask for the ACTIVITY_RECOGNITION via the
     * run-time permissions.
     */
    private boolean activityRecognitionPermissionApproved() {

        // TODO: Review permission check for 29+.
        if (runningQOrLater) {

            return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
            );
        } else {
            return true;
        }
    }

    public void onClickSaveLogToFile(View view){
        Path filePath = Paths.get(getApplicationContext().getFilesDir().toString(), logFilename);
//        Path filePath = Paths.get(getApplicationContext().getExternalFilesDir().toString(), logFilename);
        System.out.println(filePath.toString());
        if(logFilename.length() != 0) {
            File logFile = new File(filePath.toString());
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try
            {
                //BufferedWriter for performance, true to set append to file flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(logContent);
                buf.close();
                Toast.makeText(getApplicationContext(), "Activity transition log saved!", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else
            Toast.makeText(getApplicationContext(), "Filename not specified!", Toast.LENGTH_SHORT).show();
        logContent = "";
        logFilename = "";
    }

    private void printToScreen(@NonNull String message) {
        mLogFragment.getLogView().println(message);
        Log.d(TAG, message);
    }
}
