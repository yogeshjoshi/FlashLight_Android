package com.example.joshiyogesh.flashlight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Joshi Yogesh on 16/12/2016.
 */

public class Flash_spalsh extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_spalsh);
         Thread thread = new Thread(){

            public void run(){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e(null,"Interrupted");
                }
                Intent intent = new Intent(Flash_spalsh.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        thread.start();
    }
}
