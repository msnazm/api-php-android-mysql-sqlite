package com.portal.full.appmozoou;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListComment extends AppCompatActivity {

    private ListView listView;

    private String JSON_STRING;
    private EditText test;
    private String comment_post_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                setContentView(R.layout.list_comment);
                listView = (ListView) findViewById(R.id.listView);

                Intent intent = getIntent();
                comment_post_ID = intent.getStringExtra("ID");


                get_posts();


            }
        }
    }

    private void msn(String json){

        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String ID = jo.getString(Config.TAG_comment_post_ID);
                String post_titles = jo.getString(Config.TAG_comment_author);
                String TAG_comment_date = jo.getString(Config.TAG_comment_date);
                String TAG_comment_content = jo.getString(Config.TAG_comment_content);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_comment_post_ID,ID);
                employees.put(Config.TAG_comment_author,post_titles);
                employees.put(Config.TAG_comment_date,TAG_comment_date);
                employees.put(Config.TAG_comment_content,TAG_comment_content);
                list.add(employees);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ListComment.this, list, R.layout.list_item_comment,
                new String[]{Config.TAG_comment_post_ID,Config.TAG_comment_author,Config.TAG_comment_date,Config.TAG_comment_content},
                new int[]{R.id.id, R.id.comment_author, R.id.comment_date, R.id.comment_content});

        listView.setAdapter(adapter);
    }
    private void get_posts(){
        class Get_posts extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ListComment.this,"اطلاعات...","لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                msn(s);
            }

            @Override
            protected String doInBackground(Void... v) {

                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_comment_post_ID,comment_post_ID);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.URL_GET_Comment_ALL,params);
                return s;
            }
        }
        Get_posts ge = new Get_posts();
        ge.execute();
    }





}
