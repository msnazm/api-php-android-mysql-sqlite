package com.portal.full.appmozoou;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.portal.full.appmozoou.db.DataBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

public class ViewNews extends AppCompatActivity {


    Handler mHandler;
    private String JSON_STRING;
    private DataBase mHelper;
    private EditText editTextId;
    private TextView editTextName;
    private EditText editTextDesg;
    private EditText editTextTarget;
    private EditText editTextBanner;
    private EditText editTextIdad;
    private WebView adv;
    private TextView starl;
    private WebView web;
    public Menu test;
    private String htmlText;
    private String htmladv;
    public MenuItem playButtonOn;
    private int _Bookmark_Id=0;
    private String ID;
    private String eye;
    private String id_post;
    private String ad_id;
   private int myNum = 0;
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bookmark, menu);
        final DataBase myDb = new DataBase(this);
        String arrData[] = myDb.SelectDataBookMark(editTextId.getText().toString());
        if(arrData != null)
        {
            playButtonOn = menu.findItem(R.id.action_bookmark_on);
            playButtonOn.setVisible(true);
            playButtonOn.setEnabled(false);
            return true;
        }else{
            MenuItem playButtonoff = menu.findItem(R.id.action_bookmark_off);
            playButtonoff.setVisible(true);

        }
        return super.onCreateOptionsMenu(menu);
    }
    private void refresh(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_news);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Intent intent = getIntent();

        ID = intent.getStringExtra(Config.EMP_ID);
        eye = intent.getStringExtra(Config.EMP_eye);
        ad_id = intent.getStringExtra(Config.EMP_ADID);
        id_post = ID;
        _Bookmark_Id =intent.getIntExtra("bookmark_ID", 0);
        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextName = (TextView) findViewById(R.id.editTextName);
        editTextDesg = (EditText) findViewById(R.id.editTextDesg);
        editTextTarget = (EditText) findViewById(R.id.editTextTarget);
        editTextBanner = (EditText) findViewById(R.id.editTextBanner);
        editTextIdad = (EditText) findViewById(R.id.editTextIdad);
        starl = (TextView) findViewById(R.id.star);
        //adv = (WebView) findViewById(R.id.adv);

        editTextId.setText(ID);

//        BookmarkRepo repo = new BookmarkRepo(this);
//        Bookmark bookmark = new Bookmark();
//        bookmark = repo.getBookmarkById(_Bookmark_Id);
        get_post_id();
        get_posts();
        getJSON_adv();

     // get_post_star();
        updateEmployee();
       // ad_id.toString(editTextIdad.getText().toString());

        mHelper = new DataBase(this);


    }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.mHandler = new Handler();
        m_Runnable.run();
        get_post_star();

    }
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            get_post_id();
            getJSON_adv();

            ViewNews.this.mHandler.postDelayed(m_Runnable,10000);
        }

    };

    private void updateEmployee(){
       final String IDs = editTextId.getText().toString().trim();
       // final int eye = 0;
       // final String id = Integer.toString(eye);
//       final int eyes = Integer.parseInt(eye.toString());
        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               // loading = ProgressDialog.show(ViewNews.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
              //  loading.dismiss();
                //Toast.makeText(ViewNews.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_EMP_ID,IDs);
                hashMap.put(Config.KEY_EMP_eye,eye);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_EYE,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }
    private void get_posts(){
        class Get_posts extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(ViewNews.this,null,"لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                msn(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_EMP,ID);
                return s;
            }
        }
        Get_posts ge = new Get_posts();
        ge.execute();
    }
    private void getJSON_adv(){
        class getJSON_adv extends AsyncTask<Void,Void,String>{
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
                adv(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_adv, ad_id);
                return s;
            }
        }
        getJSON_adv ge = new getJSON_adv();
        ge.execute();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        // Check Data (MemberID exists)


        switch(item.getItemId()) {
            case R.id.action_bookmark_off:
               SaveDataBookMark();

                return(true);

            case R.id.action_share:
                shareTextUrl();
              // share();
                return(true);

            case R.id.action_new_comment:

                Intent intent = new Intent(this, AddComment.class);


             //   String ID = editTextId.getText().toString();
                intent.putExtra("ID",editTextId.getText().toString());
                startActivity(intent);


                return(true);
            case R.id.action_all_comment:

                Intent i = new Intent(this, ListComment.class);


                //   String ID = editTextId.getText().toString();
                i.putExtra("ID",editTextId.getText().toString());
                startActivity(i);


                return(true);
            case R.id.home:

                Intent newActivity = new Intent(ViewNews.this, Main.class);

                startActivity(newActivity);


                return(true);


        }
        return(super.onOptionsItemSelected(item));
    }
    private void share() {
        Uri imageUri = Uri.parse("android.resource://com.portal.full.appmozoou/drawable/actors.png");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");

        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(intent , "Share"));
    }
    private void shareImage() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);

        sharingIntent.setType("image/*");
        //   Uri screenshotUri = Uri.(R.mipmap.ic_mozoou);

        Uri file = Uri.parse("android.resource://com.portal.full.appmozoou/"+R.mipmap.ic_mozoou);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, file);
    //    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "msn");

      //  sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<strong><p style=\"color: #333399;\">اپلیکیشن موضوع</p>@mozoou</p></p>تلگرام</p></strong><br><p>" + temp + "</p>"));
        //  shareImage();
        // sharingIntent.putExtra(Intent.EXTRA_TEXT, "The EssexPass");

        startActivity(sharingIntent);
    }
    private void shareTextUrl() {



        Intent sharingIntent = new Intent(Intent.ACTION_SEND);

        sharingIntent.setType("text/plain");
     //   Uri screenshotUri = Uri.(R.mipmap.ic_mozoou);
       String temp= editTextDesg.getText().toString();
        temp=   temp.replaceAll("<img .+?/>", "");
      //  Uri file = Uri.parse("android.resource://com.portal.full.appmozoou/"+R.mipmap.ic_mozoou);
        Uri file = Uri.parse("android.resource://com.portal.full.appmozoou/" +R.mipmap.ic_mozoou);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, file);
      //  sharingIntent.putExtra(Intent.EXTRA_STREAM, file);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "msn");

        sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<strong><p style=\"color: #333399;\">اپلیکیشن موضوع</p></strong><br><p>@mozoou:تلگرام</p><p>" + temp + "</p>"));
       //  shareImage();
       // sharingIntent.putExtra(Intent.EXTRA_TEXT, "The EssexPass");
        sharingIntent.setFlags(Intent.EXTRA_DOCK_STATE_CAR);
        startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری"));

    }

    private void msn(String json){
        try {
          /*  int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){
                fixNewAndroid(webView);
            }*/
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String post_title = c.getString(Config.TAG_NAME);
            String post_content = c.getString(Config.TAG_DESG);
            String star = c.getString(Config.TAG_star);
            web = (WebView) findViewById(R.id.webView);

            editTextName.setText(post_title);
            editTextDesg.setText(post_content);
            starl.setText(star);
            WebSettings settings = web.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLoadsImagesAutomatically(true);
            settings.setDefaultTextEncodingName("UTF-8");

            settings.setBuiltInZoomControls(true);
            settings.setLoadWithOverviewMode(true);
            settings.setAllowFileAccess(true);
            web.requestFocusFromTouch();
            String replaced = editTextDesg.getText().toString().replace("\n", "<br>");
            String replace = replaced.replace("\\\n", "" +
                    "");

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

           // editTextName.setText(Html.fromHtml(htmlText));
            //web.loadData(htmlText,htmlText,null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void get_post_id(){
        class get_post_id extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               // loading = ProgressDialog.show(ViewNews.this,"Fetching...","لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
               // loading.dismiss();
                advid(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_id);
                return s;
            }
        }
        get_post_id ge = new get_post_id();
        ge.execute();
    }
    private void get_post_star(){
        class get_post_star extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(ViewNews.this,null,"لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
             //   loading.dismiss();

                star(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_STAR,id_post);
                return s;
            }
        }
        get_post_star ge = new get_post_star();
        ge.execute();
    }
    private void star(String json){
        try {
          /*  int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){
                fixNewAndroid(webView);
            }*/
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);


                String star = c.getString(Config.TAG_star);
            starl = (TextView) findViewById(R.id.star);
            if (star == "null") {
                starl.setText("0");
            }else{



                //starl.setText(star);
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void advid(String json){
        try {
          /*  int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){
                fixNewAndroid(webView);
            }*/
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            ad_id = c.getString(Config.TAG_ADID);



            editTextIdad.setText(ad_id);




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void adv(String json){
        try {
          /*  int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){
                fixNewAndroid(webView);
            }*/
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String ad_target_url = c.getString(Config.TAG_TARGET);
            String ad_banner_url = c.getString(Config.TAG_BANNER);
            adv = (WebView) findViewById(R.id.adv);

            editTextTarget.setText(ad_target_url);
            editTextBanner.setText(ad_banner_url);

            htmladv = "<html><body>"+"<a href=\""+editTextTarget.getText().toString()+"\">"+"<center><img align=\"middle\" src=\""+editTextBanner.getText().toString()+"\" /></center></a>"+"</body></html>";
            WebSettings settings = web.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLoadsImagesAutomatically(true);
            settings.setDefaultTextEncodingName("UTF-8");
            settings.setLoadWithOverviewMode(true);
            settings.setBuiltInZoomControls(true);

            adv.loadData(htmladv,"text/html; charset=UTF-8",null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public boolean SaveDataBookMark()
    {
        // txtvTitleViewStudent, txtvTitleViewCompany, txtDateCompany
        //final EditText tNameStudent = (EditText) findViewById(R.id.txtvTitleViewStudent);
        //final TextView tTitleBookmark = (TextView) findViewById(R.id.txtvTitleView);
        //final EditText tDateCompany = (EditText) findViewById(R.id.txtDateCompany);

        // Dialog
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        AlertDialog ad = adb.create();

        // Check MemberID
       /* if(editTextName.getText().length() == 0)
        {
            ad.setMessage("");
            ad.show();
            editTextName.requestFocus();
            return false;
        }*/


        // new Class DB
        final DataBase myDb = new DataBase(this);

        // Check Data (MemberID exists)
        String arrData[] = myDb.SelectDataBookMark(editTextId.getText().toString());
        if(arrData != null)
        {
            ad.setMessage("این موضوع ثبت شده است ...");
            ad.show();
            // editTextName.requestFocus();
            return true;
        }

        // Save Data
        long saveStatus = myDb.InsertDataBookMark(
                editTextId.getText().toString(),
                editTextName.getText().toString(),
                editTextDesg.getText().toString());

      /*  if(saveStatus <=  0)
        {

            ad.setMessage("لطفا را وارد کنید!! ");
            ad.show();
            return true;
        }*/

        Toast.makeText(ViewNews.this, "اطلاعات با موفقیت ثبت شد. ",
                Toast.LENGTH_SHORT).show();

        return true;
    }




}
