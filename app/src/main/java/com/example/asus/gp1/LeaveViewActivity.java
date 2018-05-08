package com.example.asus.gp1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.asus.gp1.Helper.MetaData;
import com.example.asus.gp1.Helper.PostUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LeaveViewActivity extends AppCompatActivity {
    final ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_view);
        setTitle("请假单");
        lv = findViewById(R.id.leave_listview);
        refreshLeave();
    }

    void refreshLeave() {
        listItem.clear();
        PostUtil post = new PostUtil(LeaveViewActivity.this);
        post.leaveQueryTeacher(leaveQueryHandler, MetaData.LID, MetaData.PWD);
        dealList();
    }

    void dealList() {
        final List<Map<String, String>> list = new ArrayList<>();

        for (HashMap<String, Object> m : listItem) {
            String str1 = "";
            String str2 = "";
            Map<String, String> map = new HashMap<String, String>();
            for (String key : m.keySet()) {
                if ("LeaveID".equals(key)) {
                    str1 += "ID:" + m.get(key) + "\n";
                    map.put("ID", m.get(key) + "");
                } else if ("StudentID".equals(key)) {
                    str2 += "學生ID:" + m.get(key) + "\n";
                } else if ("ClassID".equals(key)) {
                    str2 += "課程ID:" + m.get(key) + "\n";
                }
            }
            map.put("t1", str1);
            map.put("t2", str2);
            list.add(map);
        }
        boolean Isnone = false;
        if (list.size() == 0) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("t1", "沒人請假");
            map.put("t2", "none");
            list.add(map);
            Isnone = true;
        }
        final boolean ISnone = Isnone;
        // 定义SimpleAdapter适配器。
        // 使用SimpleAdapter来作为ListView的适配器，比ArrayAdapter能展现更复杂的布局效果。为了显示较为复杂的ListView的item效果，需要写一个xml布局文件，来设置ListView中每一个item的格式。
        SimpleAdapter adapter = new SimpleAdapter(LeaveViewActivity.this, list,
                android.R.layout.simple_list_item_2
                , new String[]{"t1", "t2"},
                new int[]{android.R.id.text1, android.R.id.text2});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if (!ISnone)
                    new AlertDialog.Builder(LeaveViewActivity.this)
                            .setPositiveButton("返回", null)
                            .setNegativeButton("批准", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int ii) {
                                    PostUtil post = new PostUtil(LeaveViewActivity.this);
                                    post.leaveApproveTeacher(leaveApproveHandler, MetaData.LID, MetaData.PWD, Integer.parseInt(list.get(i).get("ID")));
                                }
                            })
                            .setTitle("同意请假？")
                            .show();
            }
        });
    }

    Handler leaveQueryHandler = new Handler() {
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
                    JSONObject jslist = (JSONObject) json.get("extResult");
                    Iterator<String> iterator = jslist.keys();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        JSONObject missionjson = (JSONObject) jslist.get(key);
                        Iterator<String> iterator2 = missionjson.keys();
                        HashMap<String, Object> m2 = new HashMap<>();

                        while (iterator2.hasNext()) {
                            String key2 = iterator2.next();
                            m2.put(key2, missionjson.get(key2));
                        }
                        listItem.add(m2);

                    }
//                    Intent in = new Intent();
//                    in.setClassName( getApplicationContext(), "com.example.asus.fpappdemo.DetailActivity" );
//                    startActivity( in );
                }
            } catch (Exception ex) {
                alert = "返回报文出错:" + ex.getMessage();
            }
        }
    };

    Handler leaveApproveHandler = new Handler() {
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
        }
    };
}
