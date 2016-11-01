package com.example.ansan.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v){

        if(isStoragePermissionGranted() == false){
            Toast.makeText(getApplicationContext(), "SD Card 사용 불가",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = path + "/myPics" + "/test.txt";

        switch (v.getId()){

            case R.id.button: //폴더생성
                File file = new File(path + "/myPics");
                file.mkdir();
                Toast.makeText(getApplicationContext(), "myPics 폴더 생성",
                        Toast.LENGTH_SHORT).show();
            break;

            case R.id.button2: //폴더삭제
                File file2 = new File(path + "/myPics");
                file2.mkdir();
                Toast.makeText(getApplicationContext(), "myPics 폴더 삭제",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.button3: //파일생성
                String content = "안녕하세요.";
                try {
                    FileOutputStream fos = new FileOutputStream(filename);
                    fos.write(content.getBytes());
                    fos.close();

                    Toast.makeText(getApplicationContext(),"파일 생성", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(),"파일 에러", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {

                }
                break;

            case R.id.button4: //파일읽기

                try {
                    FileInputStream fos = new FileInputStream(filename);
                    byte arr[] = new byte[fos.available()];
                    fos.close();

                    Toast.makeText(getApplicationContext(),new String(arr), Toast.LENGTH_SHORT).show();
                }

                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    String TAG="[SDCard]";

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
}
