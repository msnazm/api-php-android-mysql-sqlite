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

public class Business extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listView;
    private SearchView mSearchView;

    private String post_title;
    private String JSON_STRING;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                setContentView(R.layout.share_temp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                listView = (ListView) findViewById(R.id.listView);
                listView.setOnItemClickListener(this);
                getJSON();


            }
        }
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        getJSON();
    }

    /*  @Override
      public void onBackPressed() {
          getJSON();
      }
      @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          getJSON();
      }
      @Override
      public void onRestart(){
          super.onRestart();
          getJSON();
      }*/
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maintemp, menu);
        MenuItem searchItem = menu.findItem(R.id.find);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setQueryHint("جستجو:");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                // Reset SearchView
                // mSearchView.clearFocus();
                //  mSearchView.setQuery("", false);
                //  mSearchView.setIconified(true);

                // Set activity title to search query
                //  Find.this.setTitle(s);
                // HashMap<String, String> map = new HashMap<String, String>();
                // post_title = map.get(Config.KEY_EMP_NAME).toString();


                post_title = s.toString();
                // post_title.equals(s);
                get_post_find();

                return true;

            }

            @Override
            public boolean onQueryTextChange(String s) {

                //    Input = (TextView) findViewById (R.id.txtviewFind);
                //post_title = Input.toString();
                // Input.setText(mSearchView.getQuery());

                //  post_title = mSearchView.getQuery().toString();


                // post_title = map.get(Config.KEY_EMP_NAME).toString();
                //post_title = s.toString().trim();

                post_title = s.toString();
                // get_posts();
               /* if (post_title.contains(s)){
                    post_title = s.toString().trim();

                }*/
                // post_title.equals(s);

                return false;

            }

        });

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        // Check Data (MemberID exists)


        switch(item.getItemId()) {
            case R.id.sortword:

                getJSONSortL();
                return(true);

            case R.id.sortt:
                getJSONSortT();
                return(true);
            case R.id.sorts:
                getJSONSortS();
                return(true);
            case R.id.sorte:
                getJSONSortE();
                return(true);

        }
        return(super.onOptionsItemSelected(item));
    }

    private void get_post_find(){
        class Get_posts extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Business.this,"اطلاعات...","لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                msn_find(s);
            }

            @Override
            protected String doInBackground(Void... v) {

                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_NAME,post_title);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.URL_GET_FIND_Business,params);
                return s;
            }
        }
        Get_posts ge = new Get_posts();
        ge.execute();
    }




    private void msn_find(String json){

        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String ID = jo.getString(Config.TAG_ID);
                String post_titles = jo.getString(Config.TAG_NAME);
                String comment_count = jo.getString(Config.TAG_comment_count);
                String eye = jo.getString(Config.TAG_eye);
                String star = jo.getString(Config.TAG_star);
                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,ID);
                employees.put(Config.TAG_NAME,post_titles);
                employees.put(Config.TAG_comment_count,comment_count);
                employees.put(Config.TAG_eye,eye);
                employees.put(Config.TAG_star,star);
                list.add(employees);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Business.this, list, R.layout.list_item,
                new String[]{Config.TAG_ID,Config.TAG_NAME,Config.TAG_comment_count,Config.TAG_eye,Config.TAG_star},
                new int[]{R.id.id, R.id.name, R.id.numbercomment, R.id.eye, R.id.starv});

        listView.setAdapter(adapter);
    }

    private void SortT(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String ID = jo.getString(Config.TAG_ID);
                String post_title = jo.getString(Config.TAG_NAME); String comment_count = jo.getString(Config.TAG_comment_count);
                String eye = jo.getString(Config.TAG_eye);
                String star = jo.getString(Config.TAG_star);
                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,ID);
                employees.put(Config.TAG_NAME,post_title); employees.put(Config.TAG_comment_count,comment_count);
                employees.put(Config.TAG_eye,eye);
                employees.put(Config.TAG_star,star);

                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Business.this, list, R.layout.list_item,
                new String[]{Config.TAG_ID,Config.TAG_NAME,Config.TAG_comment_count,Config.TAG_eye,Config.TAG_star},
                new int[]{R.id.id, R.id.name, R.id.numbercomment, R.id.eye, R.id.starv});

        listView.setAdapter(adapter);
    }
    private void getJSONSortT(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Business.this,null,"لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                SortT();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_BusinessSortT_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private void SortL(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String ID = jo.getString(Config.TAG_ID);
                String post_title = jo.getString(Config.TAG_NAME); String comment_count = jo.getString(Config.TAG_comment_count);
                String eye = jo.getString(Config.TAG_eye);
                String star = jo.getString(Config.TAG_star);
                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,ID);
                employees.put(Config.TAG_NAME,post_title); employees.put(Config.TAG_comment_count,comment_count);
                employees.put(Config.TAG_eye,eye);
                employees.put(Config.TAG_star,star);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Business.this, list, R.layout.list_item,
                new String[]{Config.TAG_ID,Config.TAG_NAME,Config.TAG_comment_count,Config.TAG_eye,Config.TAG_star},
                new int[]{R.id.id, R.id.name, R.id.numbercomment, R.id.eye, R.id.starv});

        listView.setAdapter(adapter);
    }

    private void getJSONSortL(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Business.this,null,"لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                SortL();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_BusinessSortL_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void SortS(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String ID = jo.getString(Config.TAG_ID);
                String post_title = jo.getString(Config.TAG_NAME); String comment_count = jo.getString(Config.TAG_comment_count);
                String eye = jo.getString(Config.TAG_eye);
                String star = jo.getString(Config.TAG_star);
                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,ID);
                employees.put(Config.TAG_NAME,post_title); employees.put(Config.TAG_comment_count,comment_count);
                employees.put(Config.TAG_eye,eye);
                employees.put(Config.TAG_star,star);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Business.this, list, R.layout.list_item,
                new String[]{Config.TAG_ID,Config.TAG_NAME,Config.TAG_comment_count,Config.TAG_eye,Config.TAG_star},
                new int[]{R.id.id, R.id.name, R.id.numbercomment, R.id.eye, R.id.starv});

        listView.setAdapter(adapter);
    }

    private void getJSONSortS(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Business.this,null,"لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                SortS();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_BusinessSortLike_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void SortE(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String ID = jo.getString(Config.TAG_ID);
                String post_title = jo.getString(Config.TAG_NAME); String comment_count = jo.getString(Config.TAG_comment_count);
                String eye = jo.getString(Config.TAG_eye);
                String star = jo.getString(Config.TAG_star);
                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,ID);
                employees.put(Config.TAG_NAME,post_title); employees.put(Config.TAG_comment_count,comment_count);
                employees.put(Config.TAG_eye,eye);
                employees.put(Config.TAG_star,star);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Business.this, list, R.layout.list_item,
                new String[]{Config.TAG_ID,Config.TAG_NAME,Config.TAG_comment_count,Config.TAG_eye,Config.TAG_star},
                new int[]{R.id.id, R.id.name, R.id.numbercomment, R.id.eye, R.id.starv});

        listView.setAdapter(adapter);
    }

    private void getJSONSortE(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Business.this,null,"لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                SortE();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_BusinessSortE_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String ID = jo.getString(Config.TAG_ID);
                String post_title = jo.getString(Config.TAG_NAME);
                String comment_count = jo.getString(Config.TAG_comment_count);
                String eye = jo.getString(Config.TAG_eye);
                String star = jo.getString(Config.TAG_star);
                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,ID);
                employees.put(Config.TAG_NAME,post_title);
                employees.put(Config.TAG_comment_count,comment_count);
                employees.put(Config.TAG_eye,eye);
                employees.put(Config.TAG_star,star);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Business.this, list, R.layout.list_item,
                new String[]{Config.TAG_ID,Config.TAG_NAME,Config.TAG_comment_count,Config.TAG_eye,Config.TAG_star},
                new int[]{R.id.id, R.id.name, R.id.numbercomment, R.id.eye, R.id.starv});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Business.this,null,"لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_Business_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         Intent intent = new Intent(this, ViewNews.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String ID = map.get(Config.TAG_ID).toString();
        String eye = map.get(Config.TAG_eye).toString();
        intent.putExtra(Config.EMP_ID,ID);
        intent.putExtra(Config.EMP_eye,eye);
        startActivity(intent);
    }


}
