package com.example.asus.gp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asus.gp1.Helper.RequestUtil;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setTitle("网络配置");

        Button bt = (Button) findViewById(R.id.login_bt);
        final EditText et1 = (EditText) findViewById(R.id.login_et1);
        final EditText et2 = (EditText) findViewById(R.id.login_et2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestUtil.IP = et1.getText().toString();
                RequestUtil.Port = et2.getText().toString();
                Intent in = new Intent();
                in.setClassName(getApplicationContext(), "com.example.asus.gp1.LoginActivity");
                startActivity(in);
            }
        });


    }
}
