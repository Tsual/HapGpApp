package com.example.asus.gp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testActivity extends AppCompatActivity {
    private ListView listView_main_news;
    private List<Map<String, String>> list = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ListView listView_main_news=(ListView)findViewById(R.id.test_listview);



        list = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 5; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("username", "wangxiangjun_" + i);
            map.put("password", "123456_" + i);
            map.put("password1", "123456_" + i);
            map.put("password2", "123456_" + i);
            list.add(map);
        }
        // 定义SimpleAdapter适配器。
        // 使用SimpleAdapter来作为ListView的适配器，比ArrayAdapter能展现更复杂的布局效果。为了显示较为复杂的ListView的item效果，需要写一个xml布局文件，来设置ListView中每一个item的格式。
        SimpleAdapter adapter = new SimpleAdapter(this, list,
                android.R.layout.simple_expandable_list_item_2
                , new String[] { "username",
                "password" ,"password1","password2"}, new int[] {
                android.R.id.text1,
                android.R.id.text2,
                android.R.id.textAssist,
                android.R.id.textAssist});
        listView_main_news.setAdapter(adapter);


    }
}
