package com.example.asus.gp1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.gp1.Helper.MetaData;
import com.example.asus.gp1.Helper.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class teachersignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("教师补签");
        setContentView(R.layout.activity_teachersign);
        Button bt=(Button)findViewById(R.id.bt2);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                HashMap m=new HashMap();
                m.put("lid", MetaData.LID);
                m.put("pwd",MetaData.PWD);
                m.put("names",((EditText)findViewById(R.id.et1)).getText().toString());
                try {
                    RequestUtil.TeacherSignIn(m,new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            JSONObject json=null;
                            String msgg=(String)msg.getData().get("value");
                            try {
                                json=new JSONObject(msgg);
                                if ("Success".equals(json.get("excuteResult"))) {
                                    Intent in = new Intent();
                                    in.setClassName(getApplicationContext(), "com.example.asus.gp1.MainActivity");
                                    startActivity(in);
                                } else {
                                    Toast.makeText(getBaseContext(), (String) json.get("message"), Toast.LENGTH_LONG);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
