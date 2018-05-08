package com.example.asus.gp1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.gp1.Helper.MetaData;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor

    }

    Button missionBt;
    Button leaveBt;

    @Override
    public void onStart() {
        super.onStart();
        missionBt = getActivity().findViewById(R.id.home_teacher_checkmission);
        leaveBt = getActivity().findViewById(R.id.home_teacher_checkleave);
        TextView tv1 = (TextView) getView().findViewById(R.id.name);
        tv1.setText(MetaData.Name);
        TextView tv2 = (TextView) getView().findViewById(R.id.subview);
        if (MetaData.Role.equals("Teacher")) {
            missionBt.setVisibility(View.VISIBLE);
            leaveBt.setVisibility(View.VISIBLE);
            missionBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent();
                    in.setClassName(getActivity().getApplicationContext(), "com.example.asus.gp1.MissionActivity");
                    startActivity(in);
                }
            });
            leaveBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent();
                    in.setClassName(getActivity().getApplicationContext(), "com.example.asus.gp1.LeaveViewActivity");
                    startActivity(in);
                }
            });
            tv2.setText("四度春风化绸缪，几番秋雨洗鸿沟。\n" +
                    "黑发积霜织日月，粉笔无言写春秋。\n" +
                    "蚕丝吐尽春未老，烛泪成灰秋更稠。\n" +
                    "春播桃李三千圃，秋来硕果满神州。");
        } else {
            missionBt.setVisibility(View.INVISIBLE);
            leaveBt.setVisibility(View.INVISIBLE);
            tv2.setText("同学你好");
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

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
