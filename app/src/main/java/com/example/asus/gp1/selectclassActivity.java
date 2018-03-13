package com.example.asus.gp1;

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

import com.example.asus.gp1.Helper.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class selectclassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectclass);
        setTitle("选择课程");

        final boolean[] exeRes = new boolean[]{true};
        final String[] ErrMsg=new String[]{""};
        Button bt=(Button)findViewById(R.id.bt3);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                HashMap m=new HashMap();
                m.put("lid",MetaData.LID);
                m.put("pwd",MetaData.PWD);
                m.put("projectname",((EditText)findViewById(R.id.et3)).getText().toString());
                try {
                    RequestUtil.SelectClass(m,new Handler(){
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
                                    exeRes[0]=false;
                                    ErrMsg[0]=(String) json.get("message");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!exeRes[0]){
                    AlertDialog.Builder builder  = new AlertDialog.Builder(selectclassActivity.this);
                    builder.setMessage(ErrMsg[0] ) ;
                    builder.setPositiveButton("是" ,  null );
                    builder.show();
                }
            }
        });
    }
}
