package com.example.asus.gp1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asus.gp1.Helper.MetaData;
import com.example.asus.gp1.Helper.PostUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class LeaveRequestActivity extends AppCompatActivity {
    Handler leaveRequestHandler = new Handler() {
        String alert = "";

        @Override
        public String toString() {
            return alert;
        }

        @Override
        public void handleMessage(Message message) {
            Bundle data = message.getData();
            if (Boolean.FALSE.toString().equals(data.getString("result"))) {
                alert = data.getString("cause");
            }
            String jsonString = data.getString("value");
            try {
                JSONObject json = new JSONObject(jsonString);
                if ("Error".equals(json.getString("excuteResult"))) {
                    alert = json.getString("message");
                } else {
                    Intent in = new Intent();
                    in.setClassName( getApplicationContext(), "com.example.asus.gp1.MainActivity" );
                    startActivity( in );
                }
            } catch (Exception ex) {
                alert = "返回报文出错:" + ex.getMessage();
            }




        }
    };

    EditText request_content;
    EditText name;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_request);
        setTitle("请假申请");

        request_content = findViewById(R.id.leave_request_et);
        name = findViewById(R.id.leave_request_et_name);
        bt = findViewById(R.id.leave_request_bt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String reason=request_content.getText().toString();
                final String namestr=name.getText().toString();
                PostUtil post = new PostUtil(LeaveRequestActivity.this);
                post.leaveRequestStudent(leaveRequestHandler, MetaData.LID, MetaData.PWD, MetaData.SSSID);
            }
        });

    }
}
