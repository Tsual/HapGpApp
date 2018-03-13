package com.example.asus.gp1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.gp1.Helper.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link stdinfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link stdinfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class stdinfoFragment extends Fragment {
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

    @Override
    public void onStart() {
        super.onStart();

Button bt=(Button) getActivity().findViewById(R.id.bt4);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                HashMap m=new HashMap();
                m.put("lid",MetaData.LID);
                m.put("pwd",MetaData.PWD);
                m.put("x","0");
                m.put("y","0");
                final boolean[] b = {false};
                final String[] extmsg = {""};
                try {
                    RequestUtil.SignIn(m,new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            JSONObject json=null;
                            String msgg=(String)msg.getData().get("value");
                            try {
                                json=new JSONObject(msgg);
                                if ("Success".equals(json.get("excuteResult"))) {
                                    b[0] =true;
                                } else {
                                    extmsg[0] =(String)json.get("message");
                                    b[0]=false;
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
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!b[0]){
                    AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
                    builder.setMessage(extmsg[0] ) ;
                    builder.setPositiveButton("是" ,  null );
                    builder.show();
                }
            }
        });


    }
}
