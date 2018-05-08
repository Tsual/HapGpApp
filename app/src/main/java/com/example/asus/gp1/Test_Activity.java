package com.example.asus.gp1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.asus.gp1.Helper.PostUtil;

public class Test_Activity extends AppCompatActivity {

    Handler testHandler = new Handler() {
        String alert = "";

        @Override
        public String toString() {
            return alert;
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            if (Boolean.FALSE.toString().equals(data.getString("result"))) {
                alert = data.getString("cause");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_);
        ((Button) findViewById(R.id.test_bt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostUtil post=new PostUtil(Test_Activity.this);
                post.missionQueryStudent(testHandler,"ss","ss");
            }
        });
    }
}
