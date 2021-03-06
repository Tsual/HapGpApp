package com.example.asus.gp1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.asus.gp1.Helper.MetaData;
import com.example.asus.gp1.Helper.PostUtil;
import com.example.asus.gp1.Helper.RequestUtil;

import org.json.JSONArray;
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
 * {@link TeacherClassFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeacherClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherClassFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TeacherClassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherClassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherClassFragment newInstance(String param1, String param2) {
        TeacherClassFragment fragment = new TeacherClassFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        final ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

        HashMap m=new HashMap();
        m.put("lid", MetaData.LID);
        m.put("pwd",MetaData.PWD);
        try {
            RequestUtil.QueryClassTeacher(m,new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    JSONObject json=null;
                    String msgg=(String)msg.getData().get("value");
                    if(msgg.startsWith("网络请求出错")){
                        return;
                    }
                    try {
                        json=new JSONObject(msgg);
                        JSONObject jslist=(JSONObject)json.get("extResult");
                        JSONArray jsar=(JSONArray)jslist.get("class");


                        for(int i=0;i<jsar.length();i++){
                            JSONObject jsobj=jsar.getJSONObject(i);
                            Iterator<String> iterator=jsobj.keys();
                            HashMap<String,Object> m=new HashMap<>();
                            while (iterator.hasNext()){
                                String key=iterator.next();
                                m.put(key,jsobj.get(key));
                            }
                            listItem.add(m);
                        }
                    } catch (JSONException e) {
                        return;
                    }


                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        deallist(listItem);
        FloatingActionButton bt=(FloatingActionButton)getActivity(). findViewById(R.id.floatingActionButton);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setClassName( getContext(), "com.example.asus.gp1.addclassActivity" );
                startActivity( in );
            }
        });


//        ListView list =getActivity().findViewById(R.id.ListView1);
//
////{"课程ID":"5","课程日程":"Saturday","课程名称":"test class 2","课程描述":"test subtitle","课程开始时间":"1:00","课程结束时间":"4:00"}
//
//        BaseAdapter listItemAdapter = new BaseAdapter(){
//            @Override
//            public int getCount() {
//                return listItem.size();
//            }
//
//            @Override
//            public Object getItem(int i) {
//                return listItem.get(i);
//            }
//
//            @Override
//            public long getItemId(int i) {
//                return i;
//            }
//
//            @Override
//            public View getView(int i, View view, ViewGroup viewGroup) {
//                List<String> strs=new ArrayList<>();
//                for(String str:listItem.get(i).keySet())
//                {
//                    strs.add(str+":"+(String)listItem.get(i).get(str));
//                }
//                return new ClassView(getContext(),strs.get(0),strs.get(1),strs.get(2),strs.get(3),strs.get(4),strs.get(5));
//            }
//        };

//        list.setAdapter(listItemAdapter);
//                        //添加点击
//                        list.setOnItemClickListener(new OnItemClickListener() {
//
//                            @Override
//                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                                    long arg3) {
//                                setTitle("点击第"+arg2+"个项目");
//                            }
//                        });
//
//                        //添加长按点击
//                        list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
//
//                            @Override
//                            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
//                                menu.setHeaderTitle("长按菜单-ContextMenu");
//                                menu.add(0, 0, 0, "弹出长按菜单0");
//                                menu.add(0, 1, 0, "弹出长按菜单1");
//                            }
//                        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void deallist(ArrayList<HashMap<String, Object>> listItem){
        ListView listView_main_news = (ListView) getActivity().findViewById(R.id.tcf_listview);
        final List<Map<String, String>> list = new ArrayList<>();

        for (HashMap<String, Object> m : listItem) {
            String str1 = "";
            String str2 = "";
            Map<String, String> map = new HashMap<String, String>();
            for (String key : m.keySet()) {
                if("课程ID".equals(key))
                    map.put("ID",m.get(key)+"");
                if ("课程名称".equals(key))
                    str1 += key + ":" + m.get(key) + "\n";
                else
                    str2 += key + ":" + m.get(key) + "\n";
            }
            map.put("t1", str1);
            map.put("t2", str2);
            list.add(map);
        }

        if(list.size()==0){
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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MetaData.MissionID=Integer.parseInt(list.get(i).get("ID"));
                new AlertDialog.Builder(getContext())
                        .setPositiveButton("返回", null)
                        .setNegativeButton("创建", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int ii) {
                                Intent in = new Intent();
                                in.setClassName(getActivity().getApplicationContext(), "com.example.asus.gp1.MissionCreateActivity");
                                startActivity(in);
                            }
                        })
                        .setTitle("为课程（ID："+MetaData.MissionID+"）创建任务")
                        .show();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_class, container, false);
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
}
