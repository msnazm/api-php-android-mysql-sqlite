package com.portal.full.appmozoou;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScrollingActivity extends AppCompatActivity {

    public String post_title;
    private EditText editTextId;
    private Toolbar toolbar;
    private EditText editTextDesg;
    private WebView web,top;
    public Menu test;
    private String htmlText;
    private String ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // ActionBar actionBar = getSupportActionBar(); // or getActionBar();
      // set the top title
       //getSupportActionBar().hide();

        Intent intent = getIntent();

        ID = intent.getStringExtra(Config.EMP_ID);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextId = (EditText) findViewById(R.id.seditTextId);
//        editTextName = (Toolbar) findViewById(R.id.seditTextName);
        editTextDesg = (EditText) findViewById(R.id.seditTextDesg);
        web = (WebView) findViewById(R.id.top);
        editTextId.setText(ID);
        getJSON_contact();

    }

    private void getJSON_contact(){
        class getJSON_adv extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //  loading = ProgressDialog.show(ViewNews.this,"Fetching...","لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                contact(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_CONTACT);
                return s;
            }
        }
        getJSON_adv ge = new getJSON_adv();
        ge.execute();
    }
    private void contact(String json){
        try {
          /*  int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){
                fixNewAndroid(webView);
            }*/
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            htmlText = c.getString(Config.TAG_DESG);
            String ad_today = "<html><head><style> p {margin:0;padding:0;font-size:20; text-align:justify; color:#000000;\n" +
                    "\" +\n" +
                    "                    \"\\\" +\\n\" +\n" +
                    "                    \"                        /*    \\\"    text-indent: -0px;\\\\n\\\" +*/\\n\" +\n" +
                    "                    \"                           /* \\\"  moisrexnumber();\\\\n\\\" +*/\\n\" +\n" +
                    "                    \"                          /* \\\" display: inline;\\\"+\\n\" +\n" +
                    "                    \"                            \\\"  margin: auto;\\\"+*/\\n\" +\n" +
                    "                    \"                            \\\" line-height: 2;\\\\n\\\"+\\n\" +\n" +
                    "                    \"              /*      \\\" position: absolute;\\\\n\\\"+*/\\n\" +\n" +
                    "                    \"                            \"}</style></head><body>"+htmlText+"</body></html>";
            //   adv = (WebView) findViewById(R.id.adv);
            web.getSettings();


            //  editTextBanner.setText(ad_banner_url);

            //  htmladv = "<html><body>"+"<a href=\""+editTextTarget.getText().toString()+"\">"+"<center><img class=\"alignnone size-full wp-image-637\" align=\"middle\" alt=\"Smiley face\" src=\""+editTextBanner.getText().toString()+"\" /></center></a>"+"</body></html>";


            web.loadData(ad_today, "text/html; charset=UTF-8",null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
