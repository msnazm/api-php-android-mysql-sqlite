package com.portal.full.appmozoou;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ListView.OnItemClickListener {

    static final String KEY_TAG = "weatherdata"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_CITY = "city";
    static final String KEY_TEMP_C = "tempc";
    static final String KEY_TEMP_F = "tempf";
    static final String KEY_CONDN = "condition";
    static final String KEY_SPEED = "windspeed";
    static final String KEY_ICON = "icon";
    ListView list;
    private String moive;
    private WebView txtmoive;
    BinderData adapter = null;
    List<HashMap<String,String>> weatherDataCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        try {


            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (getAssets().open("weatherdata.xml"));

            weatherDataCollection = new ArrayList<HashMap<String,String>>();

            // normalize text representation
            doc.getDocumentElement ().normalize ();

            NodeList weatherList = doc.getElementsByTagName("weatherdata");

            HashMap<String,String> map = null;

            for (int i = 0; i < weatherList.getLength(); i++) {

                map = new HashMap<String,String>();

                Node firstWeatherNode = weatherList.item(i);

                if(firstWeatherNode.getNodeType() == Node.ELEMENT_NODE){

                    Element firstWeatherElement = (Element)firstWeatherNode;
                    //-------
                    NodeList idList = firstWeatherElement.getElementsByTagName(KEY_ID);
                    Element firstIdElement = (Element)idList.item(0);
                    NodeList textIdList = firstIdElement.getChildNodes();
                    //--id
                    map.put(KEY_ID, ((Node)textIdList.item(0)).getNodeValue().trim());

                    //2.-------
                    NodeList cityList = firstWeatherElement.getElementsByTagName(KEY_CITY);
                    Element firstCityElement = (Element)cityList.item(0);
                    NodeList textCityList = firstCityElement.getChildNodes();
                    //--city
                    map.put(KEY_CITY, ((Node)textCityList.item(0)).getNodeValue().trim());

                    //3.-------
                    NodeList tempList = firstWeatherElement.getElementsByTagName(KEY_TEMP_C);
                    Element firstTempElement = (Element)tempList.item(0);
                    NodeList textTempList = firstTempElement.getChildNodes();
                    //--city
                    map.put(KEY_TEMP_C, ((Node)textTempList.item(0)).getNodeValue().trim());

                    //4.-------
                    NodeList condList = firstWeatherElement.getElementsByTagName(KEY_CONDN);
                    Element firstCondElement = (Element)condList.item(0);
                    NodeList textCondList = firstCondElement.getChildNodes();
                    //--city
                    map.put(KEY_CONDN, ((Node)textCondList.item(0)).getNodeValue().trim());

                    //5.-------
                    NodeList speedList = firstWeatherElement.getElementsByTagName(KEY_SPEED);
                    Element firstSpeedElement = (Element)speedList.item(0);
                    NodeList textSpeedList = firstSpeedElement.getChildNodes();
                    //--city
                    map.put(KEY_SPEED, ((Node)textSpeedList.item(0)).getNodeValue().trim());

                    //6.-------
                    NodeList iconList = firstWeatherElement.getElementsByTagName(KEY_ICON);
                    Element firstIconElement = (Element)iconList.item(0);
                    NodeList textIconList = firstIconElement.getChildNodes();
                    //--city
                    map.put(KEY_ICON, ((Node)textIconList.item(0)).getNodeValue().trim());

                    //Add to the Arraylist
                    weatherDataCollection.add(map);
                }
            }
            txtmoive = (WebView) findViewById(R.id.txtmoive);

            BinderData bindingData = new BinderData(this,weatherDataCollection);


            list = (ListView) findViewById(R.id.list);

            Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");

            list.setAdapter(bindingData);

            Log.i("AFTER", "<<------------- After SetAdapter-------------->>");
            list.setOnItemClickListener(this);
            // Click event for single list row
         /*   list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent i = new Intent();
                    i.setClass(Main.this, SampleActivity.class);

                    // parameters
                    i.putExtra("position", String.valueOf(position + 1));

					*//* selected item parameters
					 * 1.	City name
					 * 2.	Weather
					 * 3.	Wind speed
					 * 4.	Temperature
					 * 5.	Weather icon
					 *//*
                    i.putExtra("city", weatherDataCollection.get(position).get(KEY_CITY));
                    i.putExtra("weather", weatherDataCollection.get(position).get(KEY_CONDN));
                    i.putExtra("windspeed", weatherDataCollection.get(position).get(KEY_SPEED));
                    i.putExtra("temperature", weatherDataCollection.get(position).get(KEY_TEMP_C));
                    i.putExtra("icon", weatherDataCollection.get(position).get(KEY_ICON));

                    // start the sample activity
                    startActivity(i);
                }
            });
*/
        }

        catch (IOException ex) {
            Log.e("Error", ex.getMessage());
        }
        catch (Exception ex) {
            Log.e("Error", "Loading exception");
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {


        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                android.support.v7.app.ActionBar actionBar = getSupportActionBar(); // or getActionBar();
                getSupportActionBar().setTitle(R.string.app_mozoou); // set the top title
                getJSON_moive();

               // getSupportActionBar().setSubtitle(txtmoive.loadData(moive, "text/html; charset=UTF-8",null));
                actionBar.setLogo(R.mipmap.ic_mozoou);
               // actionBar.setTitle("@string/app_mozoou");
                drawer.setDrawerListener(toggle);
                toggle.syncState();


//       TabLayout tab_layout = (TabLayout) findViewById(R.id.tabs);
//
//        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tab_layout.addTab(tab_layout.newTab().setText("Tab 1"));
//        tab_layout.addTab(tab_layout.newTab().setText("Tab 2"));
//        tab_layout.addTab(tab_layout.newTab().setText("Tab 3"));

 /*       if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
*/
        /*ActionBar actionBar = getSupportActionBar();
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setIcon(R.mipmap.ic_launcher);*/
       /* actionBar = getSupportActionBar();
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.title, null);
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);*/

                final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Snackbar.make(view, "نسخه جدید برای جایگزینی", Snackbar.LENGTH_LONG)
                        //   .setAction("Action", null).show();

                        startActivity(new Intent(getApplicationContext(), Find.class));

                    }
                });

             /*   final FloatingActionButton fabs = (FloatingActionButton) findViewById(R.id.fabs);
                fabs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Snackbar.make(view, "نسخه جدید برای جایگزینی", Snackbar.LENGTH_LONG)
                        //   .setAction("Action", null).show();

                        startActivity(new Intent(getApplicationContext(), SportAll.class));

                    }
                });*/

    /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(this);








            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(Gravity.RIGHT)){
        }else{
            super.onBackPressed();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        getJSON_moive();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
     /*   getMenuInflater().inflate(R.menu.main, menu);

        MenuItem playButton = menu.findItem(R.id.action);
        playButton.setVisible(false);
*/

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        if (id == R.id.action) {
            //startActivity(new Intent(this,Bookmark.class));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if(drawer.isDrawerOpen(Gravity.RIGHT)){
                drawer.closeDrawer(Gravity.RIGHT);
            } else {
                drawer.openDrawer(Gravity.RIGHT);
            }
        }


        if (id == R.id.action_search_main) {
            //startActivity(new Intent(this,Bookmark.class));
            startActivity(new Intent(this, Find.class));
            return(true);
        }




        return super.onOptionsItemSelected(item);
    }
    private void msnshare(){

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);

        sharingIntent.setType("text/plain");
        //   Uri screenshotUri = Uri.(R.mipmap.ic_mozoou);


        //  Uri file = Uri.parse("android.resource://com.portal.full.appmozoou/"+R.mipmap.ic_mozoou);
        Uri file = Uri.parse("android.resource://com.portal.full.appmozoou/" +R.mipmap.ic_mozoou);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, file);
        //  sharingIntent.putExtra(Intent.EXTRA_STREAM, file);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "msn");

        sharingIntent.putExtra(Intent.EXTRA_TEXT, "با اپلیکیشن \"موضوع \" \n" +
                "\n" +
                "\n" +
                "\n" +
                "در کوتاه ترین زمان، به روز ترین باشید\n" +
                "\n" +
                "دریافت جدید ترین اخبار ورزشی، اقتصادی، بین الملل و  پزشکی،...\n" +
                "\n" +
                "تلگرام : @mozoou\n" +
                "\n" +
                "email:info@mozoou.com");
        //  shareImage();
        // sharingIntent.putExtra(Intent.EXTRA_TEXT, "The EssexPass");
        sharingIntent.setFlags(Intent.EXTRA_DOCK_STATE_CAR);
        startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری"));

    }
    private void shareTextUrl() {



        Intent sharingIntent = new Intent(Intent.ACTION_SEND);

        sharingIntent.setType("image/*");
        //   Uri screenshotUri = Uri.(R.mipmap.ic_mozoou);


        Uri screenshotUri = Uri.parse("android.resource://com.portal.full.appmozoou/"+R.mipmap.ic_mozoou);
        sharingIntent.putExtra(Intent.EXTRA_STREAM,screenshotUri);

       // sharingIntent.putExtra(Intent.EXTRA_TEXT,R.mipmap.ic_mozoou);
        //  shareImage();
        // sharingIntent.putExtra(Intent.EXTRA_TEXT, "The EssexPass");
      //  sharingIntent.addFlags(R.mipmap.ic_mozoou);
        startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری"));
       /* Intent mailer = Intent.createChooser(sharingIntent, null);
        startActivity(mailer);*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();




        if (id == R.id.bookmark) {
            startActivity(new Intent(this,BookMark.class));
        } else if (id == R.id.friend) {
            //shareTextUrl();
            msnshare();
        } else if (id == R.id.av) {
            startActivity(new Intent(this,ViewAv.class));
       /*   } else if (id == R.id.setting) {
            startActivity(new Intent(this,SettingsActivity.class));*/
         } else if (id == R.id.about) {
            startActivity(new Intent(this,ViewAbout.class));
        }else if (id == R.id.contact) {
            startActivity(new Intent(this,AddContact.class));
        }else if (id == R.id.help) {
            startActivity(new Intent(this,ViewHelp.class));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;






    }
    private void getJSON_moive(){
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
                moive(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_MOIVE);
                return s;
            }
        }
        getJSON_adv ge = new getJSON_adv();
        ge.execute();
    }
    private void moive(String json){
        try {
          /*  int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){
                fixNewAndroid(webView);
            }*/
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            moive = c.getString(Config.TAG_DESG);
            String ad_today = "<html lang=\"fa\"><script type=\"text/javascript\" charset=\"utf-8\">\n" +
                    "\n" +
                    "String.prototype.toPersian = String.prototype.toFaDigit = function(a) {\n" +
                    "return this.replace(/\\d+/g, function(digit) {\n" +
                    "var digitArr = [], pDigitArr = [];\n" +
                    "for (var i = 0, len = digit.length; i < len; i++) {\n" +
                    "digitArr.push(digit.charCodeAt(i));\n" +
                    "}\n" +
                    "\n" +
                    "for (var j = 0, leng = digitArr.length; j < leng; j++) {\n" +
                    "pDigitArr.push(String.fromCharCode(digitArr[j]+((!!a && a == true) ? 1584 : 1728)));\n" +
                    "}\n" +
                    "\n" +
                    "return pDigitArr.join('');\n" +
                    "});\n" +
                    "};\n" +
                    "\n" +
                    "\n" +
                    "window.onload = function() {\n" +
                    "var body = document.getElementsByTagName(\"body\")[0];\n" +
                    "body.innerHTML = body.innerHTML.toPersian();\n" +
                    "};\n" +
                    "\n" +
                    "</script>\n<body>"+moive+"</body></html>";
         //   adv = (WebView) findViewById(R.id.adv);
           /* txtmoive.getSettings();
            txtmoive.setBackgroundColor(Color.GREEN);
*/
          //  editTextBanner.setText(ad_banner_url);

          //  htmladv = "<html><body>"+"<a href=\""+editTextTarget.getText().toString()+"\">"+"<center><img class=\"alignnone size-full wp-image-637\" align=\"middle\" alt=\"Smiley face\" src=\""+editTextBanner.getText().toString()+"\" /></center></a>"+"</body></html>";


            txtmoive.loadData(ad_today, "text/html; charset=UTF-8",null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

      if (weatherDataCollection.get(position).get(KEY_ID).equals("1")) {
            Intent newActivity = new Intent(Main.this, NewsMain.class);

            startActivity(newActivity);
        } else if (weatherDataCollection.get(position).get(KEY_ID).equals("2")) {
            Intent newActivity = new Intent(Main.this, LifestyleMain.class);

            startActivity(newActivity);
        } else if (weatherDataCollection.get(position).get(KEY_ID).equals("3")) {
            Intent newActivity = new Intent(Main.this, HealthMain.class);

            startActivity(newActivity);
        }else if(weatherDataCollection.get(position).get(KEY_ID).equals("4")) {
            Intent newActivity = new Intent(Main.this, CookeryMain.class);

            startActivity(newActivity);
        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("5")) {
            Intent newActivity = new Intent(Main.this, SampleActivity.class);

            startActivity(newActivity);
        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("6")) {
            Intent newActivity = new Intent(Main.this, ScienceMain.class);

            startActivity(newActivity);
        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("7")) {
            Intent newActivity = new Intent(Main.this, FunMain.class);

            startActivity(newActivity);
        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("8")) {
            Intent newActivity = new Intent(Main.this, BiographyMain.class);

            startActivity(newActivity);


        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("9")) {
        Intent newActivity = new Intent(Main.this, WayOfSuccessMain.class);

        startActivity(newActivity);
        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("10")) {
            Intent newActivity = new Intent(Main.this, LearningEnglishMain.class);

            startActivity(newActivity);
        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("11")) {
          Intent newActivity = new Intent(Main.this, DownloadMain.class);

          startActivity(newActivity);
      }

    }
}
