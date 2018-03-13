package com.example.asus.gp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.asus.gp1.Helper.RequestUtil;

public class RegistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Button)findViewById(R.id.email_regist_button)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {


//                RequestUtil.Regist()
            }
        });

        setContentView(R.layout.activity_regist);
    }
}
