package com.example.asus.gp1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
 * {@link InfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @SuppressLint("HandlerLeak")
    @Override
    public void onStart() {
        super.onStart();
        final List<Map<String, Object>>[] res = new ArrayList[]{new ArrayList<Map<String, Object>>()};
        HashMap m = new HashMap();
        m.put("lid", MetaData.LID);
        m.put("pwd", MetaData.PWD);
        try {
            RequestUtil.QuerySignIn(m, new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    JSONObject json = null;

                    try {
                        String msgg = (String) msg.getData().get("value");
                        if (msgg.startsWith("网络请求出错")) {
                            return;
                        }
                        json = new JSONObject(msgg);
                        if ("Success".equals(json.get("excuteResult"))) {
                            JSONObject jsob = (JSONObject) ((JSONObject) json.get("extResult")).get("结果");
                            Iterator<String> it = jsob.keys();
                            while (it.hasNext()) {
                                String key=it.next();
                                HashMap m=new HashMap();
                                m.put("t1",key);
                                m.put("t2",jsob.get(key).toString());
                                res[0].add(m);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //tinfo_listview
        deallist(res[0]);

//        TextView tv = (TextView) getActivity().findViewById(R.id.tv);
//        tv.setText(res[0]);
        FloatingActionButton bt2=(FloatingActionButton)getActivity(). findViewById(R.id.floatingActionButton1);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setClassName( getContext(), "com.example.asus.gp1.teachersignActivity" );
                startActivity( in );
            }
        });
    }


    private void deallist(List<Map<String, Object>> listItem){
        ListView listView_main_news = (ListView) getActivity().findViewById(R.id.tinfo_listview);

        if(listItem.size()==0){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("t1", "您没有创建任何课程，请添加");
            map.put("t1", "");
            listItem.add(map);
        }

        // 定义SimpleAdapter适配器。
        // 使用SimpleAdapter来作为ListView的适配器，比ArrayAdapter能展现更复杂的布局效果。为了显示较为复杂的ListView的item效果，需要写一个xml布局文件，来设置ListView中每一个item的格式。
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), listItem,
                android.R.layout.simple_list_item_2
                , new String[]{"t1", "t2"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView_main_news.setAdapter(adapter);
    }

    private OnFragmentInteractionListener mListener;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
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
        return inflater.inflate(R.layout.fragment_info, container, false);
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
