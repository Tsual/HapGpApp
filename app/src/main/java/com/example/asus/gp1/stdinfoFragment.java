package com.example.asus.gp1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.asus.gp1.Helper.MetaData;
import com.example.asus.gp1.Helper.PostUtil;
import com.example.asus.gp1.Helper.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link stdinfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link stdinfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class stdinfoFragment extends Fragment {
    private LocationManager locationManager;
    private double latitude = 0.0;
    private double longitude = 0.0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public stdinfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment stdinfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static stdinfoFragment newInstance(String param1, String param2) {
        stdinfoFragment fragment = new stdinfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stdinfo, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、临时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // Provider被enable时触发此函数，比方GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }

        // Provider被disable时触发此函数，比方GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }

        // 当坐标改变时触发此函数，假设Provider传进同样的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                latitude = location.getLatitude(); // 经度
                longitude = location.getLongitude(); // 纬度
            }
        }
    };

    @SuppressLint("MissingPermission")
    private void getLocation() {
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        }
    }


    @SuppressLint("MissingPermission")
    private void toggleGPS() {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(getContext(), 0, gpsIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location1 != null) {
                latitude = location1.getLatitude(); // 经度
                longitude = location1.getLongitude(); // 纬度
            }
        }
    }

    final ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();


    @Override
    public void onStart() {
        super.onStart();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
            //gps已打开
        } else {
            toggleGPS();
            new Handler() {
            }.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getLocation();
                }
            }, 2000);
        }

        Button bt = (Button) getActivity().findViewById(R.id.bt4);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                HashMap m = new HashMap();
                m.put("lid", MetaData.LID);
                m.put("pwd", MetaData.PWD);
                m.put("x", "" + longitude);
                m.put("y", "" + latitude);
                final boolean[] b = {false};
                final String[] extmsg = {""};
                try {
                    RequestUtil.SignIn(m, new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            JSONObject json = null;
                            String msgg = (String) msg.getData().get("value");
                            try {
                                json = new JSONObject(msgg);
                                if ("Success".equals(json.get("excuteResult"))) {
                                    b[0] = true;
                                } else {
                                    extmsg[0] = (String) json.get("message");
                                    b[0] = false;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String msgheader = "当前签到位置[" + longitude + ":" + latitude + "]\n";
                if (!b[0]) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(msgheader + extmsg[0]);
                    builder.setPositiveButton("是", null);
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(msgheader + "签到成功");
                    builder.setPositiveButton("是", null);
                    builder.show();
                }
            }
        });
        refreshMission();

    }

    void refreshMission() {
        listItem.clear();
        PostUtil post = new PostUtil(getContext());
        post.missionQueryStudent(missionQueryHandler, MetaData.LID, MetaData.PWD);
        deallist();
    }

    Handler missionQueryHandler = new Handler() {
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

    private void deallist() {
        ListView listView_main_news = (ListView) getActivity().findViewById(R.id.std_mission_listview);
        final List<Map<String, String>> list = new ArrayList<>();

        for (HashMap<String, Object> m : listItem) {
            String str1 = "";
            String str2 = "";
            Map<String, String> map = new HashMap<String, String>();
            for (String key : m.keySet()) {
                if ("Name".equals(key))
                    str1 += "任务名称:" + m.get(key) + "\n";
                else if ("MissionID".equals(key)){
                    str2 += "任务ID:" + m.get(key) + "\n";
                    map.put("ID",m.get(key)+"");
                }
                else if ("IsFinish".equals(key)) {
                    if ("false".equals(m.get(key)))
                        str2 += "完成情况:未完成\n";
                    else
                        str2 += "完成情况:已完成\n";
                }
            }
            map.put("t1", str1);
            map.put("t2", str2);
            list.add(map);
        }

        if (list.size() == 0) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("t1", "您没有创建任何课程，请添加");
            map.put("t1", "");
            list.add(map);
        }

        // 定义SimpleAdapter适配器。
        // 使用SimpleAdapter来作为ListView的适配器，比ArrayAdapter能展现更复杂的布局效果。为了显示较为复杂的ListView的item效果，需要写一个xml布局文件，来设置ListView中每一个item的格式。
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), list,
                android.R.layout.simple_list_item_2
                , new String[]{"t1", "t2"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView_main_news.setAdapter(adapter);
        listView_main_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                new AlertDialog.Builder(getContext())
                        .setPositiveButton("点错了", null)
                        .setNegativeButton("完成！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int ii) {
                                PostUtil post = new PostUtil(getContext());
                                post.missionFinishStudent(missionFinishHandler, MetaData.LID, MetaData.PWD, ((Map<String, String>)list.get(i)).get("ID"));
                            }
                        })
                        .setTitle("完成任务？")
                        .show();
            }
        });
    }

    Handler missionFinishHandler = new Handler() {
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
                    alert=json.getString("message");
                } else {

                }
            } catch (Exception ex) {
                alert="返回报文出错:" + ex.getMessage();
            }
        }
    };
}
