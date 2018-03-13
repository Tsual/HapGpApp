package com.example.asus.gp1.Helper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class RequestUtil {
    public static final String IP = "192.168.0.118";
    public static final String Port = "5555";
    public static final String ApiRoute = "/api/API";

    public static void post(final String postStr, final Handler handler) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    String strurl = "http://" + IP +":"+ Port + ApiRoute;
                    HttpPost request = new HttpPost(strurl);
                    request.addHeader("Content-Type", "application/json");
                    StringEntity se = new StringEntity(postStr);
                    request.setEntity(se);
                    HttpResponse httpResponse = httpClient.execute(request);
                    String result = EntityUtils.toString(httpResponse.getEntity());
                    Bundle data = new Bundle();
                    data.putString("value", result);
                    Message msg = new Message();
                    msg.setData(data);
                    handler.handleMessage(msg);
                } catch (Exception ex) {
                    Bundle data = new Bundle();
                    data.putString("value", "网络请求出错:" + ex.getMessage());
                    Message msg = new Message();
                    msg.setData(data);
                    handler.handleMessage(msg);
                }
            }
        }).start();
    }

    public static void QueryClassTeacher(final Map m, final Handler handler) throws IOException {
        String poststring = "{\t\n" +
                "    \"lid\": \"" + m.get("lid") + "\",\n" +
                "    \"pwd\": \"" + m.get("pwd") + "\",\n" +
                "    \"operation\": \"TeacherQueryClass\",\n" +
                "    \"Params\": {\n" +
                "    }\n" +
                "}";
        post(poststring,handler);
    }

    public static void Login(final Map m, final Handler handler) throws IOException {
        String poststring = "{\t\n" +
                "    \"lid\": \"" + m.get("lid") + "\",\n" +
                "    \"pwd\": \"" + m.get("pwd") + "\",\n" +
                "    \"operation\": \"Login\",\n" +
                "    \"Params\": {\n" +
                "    }\n" +
                "}";
        post(poststring,handler);
    }

    public static void Regist(final Map m, final Handler handler) throws IOException {
        String tt = m.containsKey("teacher") ? "teacher" : "temp";
        String str = "{\n" +
                "    \"lid\": \"" + m.get("lid") + "\",\n" +
                "    \"pwd\": \"" + m.get("pwd") + "\",\n" +
                "    \"operation\": \"regist\",\n" +
                "    \"Params\": {\n" +
                "        \"" + tt + "\": true,\n" +
                "        \"name\":\"" + m.get("name") + "\"\n" +
                "    }\n" +
                "}";
        post(str,handler);

    }

    public static void AddProject(final Map m, final Handler handler) throws IOException {
        String str = "{\n" +
                "    \"lid\": \"" + m.get("lid") + "\",\n" +
                "    \"pwd\": \"" + m.get("pwd") + "\",\n" +
                "    \"operation\": \"addproject\",\n" +
                "    \"Params\": {\n" +
                "        \"projectname\": \"" + m.get("projectname") + "\",\n" +
                "        \"subtitle\":\"" + m.get("subtitle") + "\" ,\n" +
                "        \"starttime\":\"" + m.get("starttime") + "\",\n" +
                "        \"endtime\":\"" + m.get("endtime") + "\",\n" +
                "        \"dayofweek\":\"" + m.get("dayofweek") + "\",\n" +
                "        \"west\":\"" + m.get("west") + "\",\n" +
                "        \"east\":\"" + m.get("east") + "\",\n" +
                "        \"south\":\"" + m.get("south") + "\",\n" +
                "        \"north\":\"" + m.get("north") + "\"\n" +
                "        \n" +
                "    }\n" +
                "}";
        post(str,handler);
    }

    public static void SelectClass(final Map m, final Handler handler) throws IOException {
        String str = "{\t\n" +
                "    \"lid\": \"" + m.get("lid") + "\",\n" +
                "    \"pwd\": \"" + m.get("pwd") + "\",\n" +
                "    \"operation\": \"SelectClass\",\n" +
                "    \"Params\": {\n" +
                "        \"projectname\": \"" + m.get("projectname") + "\"\n" +
                "    }\n" +
                "}";
        post(str, handler);

    }

    //学生获取已选课程
    public static void GetClass(final Map m, final Handler handler) throws IOException {
        String str = "{\t\n" +
                "    \"lid\": \"" + m.get("lid") + "\",\n" +
                "    \"pwd\": \"" + m.get("pwd") + "\",\n" +
                "    \"operation\": \"GetClass\",\n" +
                "    \"Params\": {\n" +
                "    }\n" +
                "}";
        post(str, handler);

    }

    //获取指定课程信息
    public static void QueryClass(final Map m, final Handler handler) throws IOException {
        String str = "{\t\n" +
                "    \"lid\": \"" + m.get("lid") + "\",\n" +
                "    \"pwd\": \"" + m.get("pwd") + "\",\n" +
                "    \"operation\": \"QueryClass\",\n" +
                "    \"Params\": {\n" +
                "        \"projectname\": \"" + m.get("projectname") + "\"\n" +
                "    }\n" +
                "}";
        post(str, handler);

    }

    public static void SignIn(final Map m, final Handler handler) throws IOException {
        String str = "{\t\n" +
                "    \"lid\": \"" + m.get("lid") + "\",\n" +
                "    \"pwd\": \"" + m.get("pwd") + "\",\n" +
                "    \"operation\": \"SignIn\",\n" +
                "    \"Params\": {\n" +
                "    \t\"x\":\"" + m.get("x") + "\",\n" +
                "    \t\"y\":\"" + m.get("y") + "\"\n" +
                "    }\n" +
                "}";
        post(str, handler);

    }

    public static void QuerySignIn(final Map m, final Handler handler) throws IOException {
        String str = "{\t\n" +
                "    \"lid\": \"" + m.get("lid") + "\",\n" +
                "    \"pwd\": \"" + m.get("pwd") + "\",\n" +
                "    \"operation\": \"QuerySignIn\",\n" +
                "    \"Params\": {\n" +
                "    }\n" +
                "}";
        post(str, handler);


    }

    public static void TeacherSignIn(final Map m, final Handler handler) throws IOException {

        String str = "{\t\n" +
                "    \"lid\": \"" + m.get("lid") + "\",\n" +
                "    \"pwd\": \"" + m.get("pwd") + "\",\n" +
                "    \"operation\": \"TeacherSignIn\",\n" +
                "    \"Params\": {\n" +
                "        \"names\": \"" + m.get("names") + "\"\n" +
                "    }\n" +
                "}";
        post(str, handler);

    }
}
