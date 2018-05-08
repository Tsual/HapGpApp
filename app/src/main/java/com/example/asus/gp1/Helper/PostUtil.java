package com.example.asus.gp1.Helper;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostUtil {
    public static final String IP = RequestUtil.IP;
    public static final String Port = RequestUtil.Port;
    public static final String ApiRoute = RequestUtil.ApiRoute;
    public Context AlertContext;

    public PostUtil(Context ctx) {
        this.AlertContext = ctx;
    }

    private void ApiInvoke(final Handler handler, final RequestBody body) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://" + IP + ":" + Port + ApiRoute)
                            .post(body)
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Cache-Control", "no-cache")
                            .build();
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    if (result == null || "".equals(result)) {
                        Bundle data = new Bundle();
                        data.putString("result", Boolean.FALSE.toString());
                        data.putString("cause", "远程调用失败:" + response.message());
                        Message msg = new Message();
                        msg.setData(data);
                        handler.handleMessage(msg);
                    } else {
                        Bundle data = new Bundle();
                        data.putString("result", Boolean.TRUE.toString());
                        data.putString("value", result);
                        Message msg = new Message();
                        msg.setData(data);
                        handler.handleMessage(msg);
                    }
                } catch (IOException ex) {
                    Bundle data = new Bundle();
                    data.putString("result", Boolean.FALSE.toString());
                    data.putString("cause", ex.getMessage());
                    Message msg = new Message();
                    msg.setData(data);
                    handler.handleMessage(msg);
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String Alertstr = handler.toString();
        if (Alertstr != null && !Alertstr.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AlertContext);
            builder.setMessage(Alertstr);
            builder.setPositiveButton("是", null);
            builder.show();
        }
    }

    //QueryMission
    public void missionQueryStudent(final Handler handler, String LID, String PWD) {
        String jsonstr = "{\t\n" +
                "    \"lid\": \"" + LID + "\",\n" +
                "    \"pwd\": \"" + PWD + "\",\n" +
                "    \"operation\": \"QueryMission\",\n" +
                "    \"Params\": {\n" +
                "    }\n" +
                "}";
        ApiInvoke(handler, RequestBody.create(MediaType.parse("application/json"), jsonstr));
    }

    //QueryMission
    public void missionQueryTeacher(final Handler handler, String LID, String PWD) {
        String jsonstr = "{\t\n" +
                "    \"lid\": \"" + LID + "\",\n" +
                "    \"pwd\": \"" + PWD + "\",\n" +
                "    \"operation\": \"QueryMission\",\n" +
                "    \"Params\": {\n" +
                "    }\n" +
                "}";
        ApiInvoke(handler, RequestBody.create(MediaType.parse("application/json"), jsonstr));
    }

    //FinishMission
    public void missionFinishStudent(final Handler handler, String LID, String PWD, String missionID) {
        String jsonstr = "{\t\n" +
                "    \"lid\": \"" + LID + "\",\n" +
                "    \"pwd\": \"" + PWD + "\",\n" +
                "    \"operation\": \"FinishMission\",\n" +
                "    \"Params\": {\n" +
                "    \"MissionID\":\"" + missionID + "\"" +
                "    }\n" +
                "}";
        ApiInvoke(handler, RequestBody.create(MediaType.parse("application/json"), jsonstr));
    }

    //CreateMission
    public void missionCreateTeacher(final Handler handler, String LID, String PWD, int classID, String missionName) {
        String jsonstr = "{\t\n" +
                "    \"lid\": \"" + LID + "\",\n" +
                "    \"pwd\": \"" + PWD + "\",\n" +
                "    \"operation\": \"CreateMission\",\n" +
                "    \"Params\": {\n" +
                "    \"classID\": \"" + classID + "\",\n" +
                "    \"Name\":\"" + missionName + "\"" +
                "    }\n" +
                "}";
        ApiInvoke(handler, RequestBody.create(MediaType.parse("application/json"), jsonstr));
    }

    //QueryLeave
    public void leaveQueryStudent(final Handler handler, String LID, String PWD) {
        String jsonstr = "{\t\n" +
                "    \"lid\": \"" + LID + "\",\n" +
                "    \"pwd\": \"" + PWD + "\",\n" +
                "    \"operation\": \"QueryLeave\",\n" +
                "    \"Params\": {\n" +
                "    }\n" +
                "}";
        ApiInvoke(handler, RequestBody.create(MediaType.parse("application/json"), jsonstr));
    }

    //QueryLeave
    public void leaveQueryTeacher(final Handler handler, String LID, String PWD) {
        String jsonstr = "{\t\n" +
                "    \"lid\": \"" + LID + "\",\n" +
                "    \"pwd\": \"" + PWD + "\",\n" +
                "    \"operation\": \"QueryLeave\",\n" +
                "    \"Params\": {\n" +
                "    }\n" +
                "}";
        ApiInvoke(handler, RequestBody.create(MediaType.parse("application/json"), jsonstr));
    }

    //CancelLeave
    public void leaveCancelStudent(final Handler handler, String LID, String PWD, int leaveID) {
        String jsonstr = "{\t\n" +
                "    \"lid\": \"" + LID + "\",\n" +
                "    \"pwd\": \"" + PWD + "\",\n" +
                "    \"operation\": \"CancelLeave\",\n" +
                "    \"Params\": {\n" +
                "    \"LeaveID\":\"" + leaveID + "\"" +
                "    }\n" +
                "}";
        ApiInvoke(handler, RequestBody.create(MediaType.parse("application/json"), jsonstr));
    }

    //RequestLeave
    public void leaveRequestStudent(final Handler handler, String LID, String PWD, String classSelectID) {
        String jsonstr = "{\t\n" +
                "    \"lid\": \"" + LID + "\",\n" +
                "    \"pwd\": \"" + PWD + "\",\n" +
                "    \"operation\": \"RequestLeave\",\n" +
                "    \"Params\": {\n" +
                "    \"ClassSelectID\":\"" + classSelectID + "\"" +
                "    }\n" +
                "}";
        ApiInvoke(handler, RequestBody.create(MediaType.parse("application/json"), jsonstr));
    }

    //ApproveLeave
    public void leaveApproveTeacher(final Handler handler, String LID, String PWD, int leaveID) {
        String jsonstr = "{\t\n" +
                "    \"lid\": \"" + LID + "\",\n" +
                "    \"pwd\": \"" + PWD + "\",\n" +
                "    \"operation\": \"ApproveLeave\",\n" +
                "    \"Params\": {\n" +
                "    \"LeaveID\":\"" + leaveID + "\"" +
                "    }\n" +
                "}";
        ApiInvoke(handler, RequestBody.create(MediaType.parse("application/json"), jsonstr));
    }
}
