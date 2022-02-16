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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ViewPrayer extends AppCompatActivity {

    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextDesg;
    private WebView web;
    private String htmlText;
    private String JSON_STRING;

   // private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_news);

        Intent intent = getIntent();

        //ID = intent.getStringExtra(Config.EMP_ID);

        editTextId = (EditText) findViewById(R.id.editTextId);
       // editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDesg = (EditText) findViewById(R.id.editTextDesg);

       // editTextId.setText(ID);

        getJSON();
    }

    
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewPrayer.this,null,"لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                msn(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_prayer);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void msn(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String post_title = c.getString(Config.TAG_NAME);
            String post_content = c.getString(Config.TAG_DESG);
            web = (WebView) findViewById(R.id.webView);

            editTextName.setText(post_title);
            editTextDesg.setText(post_content);
            htmlText = "<html dir='rtl'><head><style>img{display: inline;height: auto;max-width: 100%;}</style></head><body>"+editTextDesg.getText().toString()+"</h3></body></html>";
            WebSettings settings = web.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLoadsImagesAutomatically(true);
            settings.setDefaultTextEncodingName("UTF-8");
            settings.setLoadWithOverviewMode(true);
            settings.setBuiltInZoomControls(true);
            web.loadData(htmlText, "text/html; charset=UTF-8", "UTF-8");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





}
