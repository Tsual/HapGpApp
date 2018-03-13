package com.example.asus.gp1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

public class addclassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加课程");
        setContentView(R.layout.activity_addclass);
        ((Button)findViewById(R.id.bt)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onClick(View view) {
                HashMap m=new HashMap();
                m.put("lid", MetaData.LID);
                m.put("pwd",MetaData.PWD);
                m.put("projectname",((EditText)findViewById(R.id.class_name)).getText().toString());
                m.put("subtitle",((EditText)findViewById(R.id.class_name1)).getText().toString());
                m.put("dayofweek",((EditText)findViewById(R.id.class_name2)).getText().toString());
                m.put("starttime",((EditText)findViewById(R.id.class_name3)).getText().toString());
                m.put("endtime",((EditText)findViewById(R.id.class_name4)).getText().toString());
                m.put("west","0");
                m.put("east","0");
                m.put("south","0");
                m.put("north","0");

                final boolean[] dealtoken = {false};
                final String[] msgstr = {""};

                try {
                    RequestUtil.AddProject(m,new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            JSONObject json=null;
                            String msgg=(String)msg.getData().get("value");
                            try {
                                json=new JSONObject(msgg);
                                if("Success".equals(json.get("excuteResult"))){
                                    Intent in = new Intent();
                                    in.setClassName( getApplicationContext(), "com.example.asus.gp1.MainActivity" );
                                    startActivity( in );
                                }else
                                {
                                    dealtoken[0] =true;
                                    msgstr[0] =(String)json.get("message");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    return;
                }

                if(dealtoken[0]){
                    AlertDialog.Builder builder  = new AlertDialog.Builder(addclassActivity.this);
                    builder.setMessage(msgstr[0] ) ;
                    builder.setPositiveButton("是" ,  null );
                    builder.show();
                }
            }
        });
    }
}
