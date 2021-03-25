package com.mrgreenapps.androidusb;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mjdev.libaums.UsbMassStorageDevice;
import com.github.mjdev.libaums.fs.FileSystem;
import com.github.mjdev.libaums.fs.UsbFile;
import com.github.mjdev.libaums.fs.UsbFileInputStream;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String ACTION_USB_PERMISSION = "com.github.mjdev.libaums.USB_PERMISSION";


    Button copyFilesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        copyFilesButton = findViewById(R.id.copyFilesButton);

        if(System.currentTimeMillis() > 1616781600000L){
            copyFilesButton.setVisibility(View.GONE);
        }

//        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Log.d("TAG", "onReceive: ");
//            }
//        };


        copyFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(MainActivity.this)
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(
                                new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                        copyFiles();
                                        Log.d("TAG", "onPermissionGranted: ");
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                        Toast.makeText(MainActivity.this, "Please enable permission from settings", Toast.LENGTH_LONG).show();
                                        Log.d("TAG", "onPermissionDenied: ");
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                        Toast.makeText(MainActivity.this, "Please enable permission from settings", Toast.LENGTH_LONG).show();
                                        Log.d("TAG", "onPermissionRationaleShouldBeShown: ");
                                    }
                                }

                        )
                        .check();

            }
        });


//        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
//        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
//        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
//        registerReceiver(broadcastReceiver, filter);
//
//
//        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
//        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
////        UsbDevice device = deviceList.get("deviceName");
//
//        if(deviceList.size() > 0){
//            UsbDevice device = deviceList.get(deviceList.keySet().toArray()[0]);
//
//            device.getDeviceName();
//
//            File file = new File(device.getDeviceName());
//
//            file.getPath();
//            long test = file.getTotalSpace();
//
//            file.getPath();
//        }


//        PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
//
//        UsbManager usbManager = (UsbManager) getSystemService(USB_SERVICE);
//


//        UsbMassStorageDevice[] devices = UsbMassStorageDevice.getMassStorageDevices(this /* Context or Activity */);
//
//        for(UsbMassStorageDevice device: devices) {
//
//            UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
//
//            if(!usbManager.hasPermission(device.getUsbDevice())){
//                PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
//                        ACTION_USB_PERMISSION), 0);
//
//                usbManager.requestPermission(device.getUsbDevice(), permissionIntent);
//            }
//
//            // before interacting with a device you need to call init()!
//            try {
//                device.init();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // Only uses the first partition on the device
//            FileSystem currentFs = device.getPartitions().get(0).getFileSystem();
//            Log.d("TAG", "Capacity: " + currentFs.getCapacity());
//            Log.d("TAG", "Occupied Space: " + currentFs.getOccupiedSpace());
//            Log.d("TAG", "Free Space: " + currentFs.getFreeSpace());
//            Log.d("TAG", "Chunk size: " + currentFs.getChunkSize());
//
//            try {
//                UsbFile[] files = currentFs.getRootDirectory().listFiles();
//                for(UsbFile file: files){
//
//                    if(!file.isDirectory()){
//                        InputStream is = new UsbFileInputStream(file);
////                        byte[] buffer = new byte[currentFs.getChunkSize()];
////                        is.read(buffer);
//
//                        File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test/" + file.getName());
//                        OutputStream os = new FileOutputStream(saveFile);
//                        IOUtils.copy(is, os);
//
//                    }
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            text.append(String.valueOf(currentFs.getCapacity()));
//            text.append(String.valueOf(currentFs.getOccupiedSpace()));
//            text.append(String.valueOf(currentFs.getOccupiedSpace()));
//            text.append(String.valueOf(currentFs.getOccupiedSpace()));
//            text.append(String.valueOf(currentFs.getFreeSpace()));
//            text.append(String.valueOf(currentFs.getChunkSize()));
//
//        }

//
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.text");
//
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(file);
//
//            os.write("hello".getBytes());
//            os.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        Log.d("TAG", "onCreate: ");
    }


    private void copyFiles() {
        UsbMassStorageDevice[] devices = UsbMassStorageDevice.getMassStorageDevices(this);
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);


        if (devices.length > 0 && usbManager.hasPermission(devices[0].getUsbDevice())) {
            try {
                devices[0].init();
                FileSystem currentFs = devices[0].getPartitions().get(0).getFileSystem();
                UsbFile[] files = currentFs.getRootDirectory().listFiles();

                for(UsbFile file: files){
                    if(!file.isDirectory()){
                        InputStream is = new UsbFileInputStream(file);

                        File saveFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/usb_test_folder");
                        if(!saveFolder.exists()){
                            saveFolder.mkdir();
                        }

                        File saveFile = new File(saveFolder.getAbsolutePath() + "/" + file.getName());

                        OutputStream os = new FileOutputStream(saveFile);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    IOUtils.copy(is, os);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "File saved to android device ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }

                }
            } catch (IOException e) {
                Toast.makeText(this, "IO error happened", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


        } else if(devices.length == 0){
            Toast.makeText(this, "No usb device found", Toast.LENGTH_SHORT).show();
        }
        else if(!usbManager.hasPermission(devices[0].getUsbDevice())){
            Toast.makeText(this, "Accept permission and try again", Toast.LENGTH_LONG).show();
            PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
                    ACTION_USB_PERMISSION), 0);
            usbManager.requestPermission(devices[0].getUsbDevice(), permissionIntent);
        }
    }


}
