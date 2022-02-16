package com.portal.full.appmozoou;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ViewAv extends AppCompatActivity {

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                Intent intent = getIntent();

                //ID = intent.getStringExtra(Config.EMP_ID);

                editTextId = (EditText) findViewById(R.id.editTextId);
              //  editTextName = (EditText) findViewById(R.id.editTextName);
                editTextDesg = (EditText) findViewById(R.id.editTextDesg);

                // editTextId.setText(ID);

                getJSON();
            }
        }
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewAv.this,null,"لطفا صبر کنید...",false,false);
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
                String s = rh.sendGetRequest(Config.URL_GET_AV);
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
            //   String post_title = c.getString(Config.TAG_NAME);
            String post_content = c.getString(Config.TAG_DESG);
            web = (WebView) findViewById(R.id.webView);

            //   editTextName.setText(post_title);
            editTextDesg.setText(post_content);
            WebSettings settings = web.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLoadsImagesAutomatically(true);
            settings.setDefaultTextEncodingName("UTF-8");

            settings.setBuiltInZoomControls(true);
            settings.setLoadWithOverviewMode(true);
            settings.setAllowFileAccess(true);
            web.requestFocusFromTouch();
            String replaced = editTextDesg.getText().toString().replace("\n", "<br>");
            htmlText =
                    "<html dir='rtl' lang=\\\"fa\\\"><head><style>img {\n" +
                            "   display: block;\n" +
                            " border-radius: 7%;\n"+
                          /* "width: 80%;"+*/
                            " background-color: white;"+
                            " box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);"+
                          /*  "width: 50%;"+*/
                            "  opacity: 1.0;\n"+
                            "  align: middle;"+
                            " filter: alpha(opacity=100);\n"+
                            "    border: 1px solid #ccc;\n" +
                            "   margin-bottom:10px;\n" +
                            "   margin-top:10px;\n" +
                            "<script>"+
                            "// www.moisrex.rozblog.com\n" +
                            "function replace(txt, replacer, withthis){return txt.replace(new RegExp(replacer, \"gim\"),withthis);}\n" +
                            "function moisrexnumber(){\n" +
                            "a=replace(document.body.innerHTML,'1','۱');\n" +
                            "b=replace(a,'2','۲');\n" +
                            "c=replace(b,'3','۳');\n" +
                            "d=replace(c,'4','۴');\n" +
                            "e=replace(d,'5','۵');\n" +
                            "f=replace(e,'6','۶');\n" +
                            "g=replace(f,'7','۷');\n" +
                            "h=replace(g,'8','۸');\n" +
                            "i=replace(h,'9','۹');\n" +
                            "moisrex=replace(i,'0','۰');\n" +
                            "document.body.innerHTML=moisrex;\n" +
                            "}\n" +
                          /*  "window.onload=moisrexnumber;\n" +*/
                            "</script>"+

                            "} p {margin:0;padding:0;font-size:20; text-align:justify; color:#000000;\n" +
                        /*    "    text-indent: -0px;\n" +*/
                           /* "  moisrexnumber();\n" +*/
                          /* " display: inline;"+
                            "  margin: auto;"+*/
                            " line-height: 2;\n"+
              /*      " position: absolute;\n"+*/
                            "}</style></head><body><p>"+replaced+"</p></body></html>";

            web.loadData(htmlText, "text/html; charset=UTF-8", "UTF-8");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





}
