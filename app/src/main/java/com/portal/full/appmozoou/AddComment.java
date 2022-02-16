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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class AddComment extends AppCompatActivity implements View.OnClickListener {

    //Defining views
    private EditText editTextcomment_post_ID;
    private EditText editTextcomment_author;
    private EditText editTextcomment_author_email;
    private EditText editTextcomment_content;
    private EditText editTextcomment_Date;
    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button buttonAdd;
    private Button buttonstar;
    private String star;
    private int stars = 0;
    private Button buttonView;
    // private TimePicker clocktext;
    private String datecomment;
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                // Intent intent = getIntent();

                //  ID = intent.getParcelableExtra("ID");
                // ID.setText(intent.getStringExtra("ID");
                //  intent.getParcelableExtra("extraclass");


                Intent intent = getIntent();
                //Initializing views
                editTextcomment_post_ID = (EditText) findViewById(R.id.editTextcomment_post_ID);
                editTextcomment_author = (EditText) findViewById(R.id.editTextcomment_author);
                editTextcomment_author_email = (EditText) findViewById(R.id.editTextcomment_author_emailary);
                editTextcomment_content = (EditText) findViewById(R.id.editTextcomment_content);
                //  clocktext = (TimePicker) findViewById(R.id.timePicker);
                editTextcomment_Date = (EditText) findViewById(R.id.editTextcomment_Date);
                //  editTextcomment_author.setText(datecomment);
                buttonAdd = (Button) findViewById(R.id.buttonAdd);
                buttonstar = (Button) findViewById(R.id.btnStar);
                SetDate();


                ID = intent.getStringExtra("ID");
                //    editTextcomment_author.setText(ID);
                //  intent.getParcelableExtra("extraclass");
                //  editTextcomment_author.setText(ID);
                //Setting listeners to button
                buttonAdd.setOnClickListener(this);
                buttonstar.setOnClickListener(this);
                addListenerOnRatingBar();
            }
        }
    }
    private void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        txtRatingValue = (TextView) findViewById(R.id.txtRatingValueAdd);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                txtRatingValue.setText(String.valueOf(rating).replace(".0", ""));

            }
        });
    }

    private void updateEmployee() {
        final String IDs = txtRatingValue.getText().toString().trim();
        // final int eye = 0;
        // final String id = Integer.toString(eye);

            stars = Integer.parseInt(txtRatingValue.getText().toString().replace(".0", ""));

            class UpdateEmployee extends AsyncTask<Void, Void, String> {
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
                   // Toast.makeText(AddComment.this, s, Toast.LENGTH_LONG).show();
                }

                @Override
                protected String doInBackground(Void... params) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.KEY_EMP_ID,ID);
                    hashMap.put(Config.KEY_EMP_star,IDs);


                    RequestHandler rh = new RequestHandler();

                    String s = rh.sendPostRequest(Config.URL_UPDATE_STAR, hashMap);

                    return s;
                }
            }

            UpdateEmployee ue = new UpdateEmployee();
            ue.execute();

    }


    //Adding an employee
    private void addEmployee(){

        final String name = editTextcomment_post_ID.getText().toString().trim();
        final String comment_author = editTextcomment_author.getText().toString().trim();
        final String comment_author_email = editTextcomment_author_email.getText().toString().trim();
        final String comment_content = editTextcomment_content.getText().toString().trim();
        final String comment_Date = editTextcomment_Date.getText().toString().trim();
        //final String k = SetDate();
        class AddEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(AddComment.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                Toast.makeText(AddComment.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_comment_post_ID,ID);
                params.put(Config.KEY_EMP_NAME,name);
                params.put(Config.KEY_EMP_comment_author,comment_author);
                params.put(Config.KEY_EMP_comment_author_email,comment_author_email);
                params.put(Config.KEY_EMP_comment_content,comment_content);
                params.put(Config.KEY_EMP_comment_date,comment_Date);
                params.put(Config.KEY_EMP_comment_approved,"0");

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_Comment, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }
    private void addStar(){

        final String id_post = editTextcomment_post_ID.getText().toString().trim();
        final String star = txtRatingValue.getText().toString().trim();

        //final String k = SetDate();
        class AddStar extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddComment.this,null,"لطفا صبر کنید...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
               // Toast.makeText(AddComment.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_id_post,ID);
                params.put(Config.KEY_EMP_star,star);




                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_Star, params);
                return res;
            }
        }

        AddStar ae = new AddStar();
        ae.execute();
        Toast.makeText(AddComment.this, "اطلاعات با موفقیت ثبت شد. ",
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {

        if(v == buttonAdd){

            String strName = editTextcomment_author.getText().toString();
            String strEmail = editTextcomment_author_email.getText().toString();
            String strContent = editTextcomment_content.getText().toString();
            String strStar = txtRatingValue.getText().toString();
            if(strName.toString().length() < 3) {
               // editTextcomment_author.setError("لطفا نام  خود را وارد کنید حدافل 3 حرف.");
                Toast.makeText(AddComment.this, "لطفا نام  خود را وارد کنید حدافل 3 حرف.",
                        Toast.LENGTH_SHORT).show();
            }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail.toString()).matches()){
              //  editTextcomment_author_email.setError("لطفا ایمیل خود را وارد کنید. مثال: sample@gmail.com");
                Toast.makeText(AddComment.this, "لطفا ایمیل خود را وارد کنید. مثال: sample@gmail.com",
                        Toast.LENGTH_SHORT).show();
            }else if(strContent.toString().length() < 3){
              //  editTextcomment_content.setError("لطفا دیدگاه خود را وارد کنید حداقل 6 حروف");
                Toast.makeText(AddComment.this, "لطفا دیدگاه خود را وارد کنید حداقل 6 حروف",
                        Toast.LENGTH_SHORT).show();
          //  }else if(strStar.toString().equals("0")){
              // txtRatingValue.setError("لطفا امتیاز خود را تعیین کنید!");
                return;
            }else {
                addEmployee();
                addStar();
                updateEmployee();
                Toast.makeText(AddComment.this, "اطلاعات با موفقیت ثبت شد. ",
                        Toast.LENGTH_SHORT).show();
                editTextcomment_author.setText("");
                editTextcomment_author_email.setText("");
                editTextcomment_content.setText("");
                txtRatingValue.setText("0");
            }
        }
        if(v == buttonstar){
            updateEmployee();
            Toast.makeText(AddComment.this, "اطلاعات با موفقیت ثبت شد. ",
                    Toast.LENGTH_SHORT).show();
            txtRatingValue.setText("0");
        }

    }
    private void SetDate(){
        java.util.Date date = new java.util.Date();
        int week = date.getDay();
        int month = date.getMonth();
        int day = date.getDate();
        int year = date.getYear();
      //  datecomment = (gregorian_to_jalali(year + 1900, month + 1, day, week));
     //   String m = clocktext.toString();
      //  String n = datecomment+" "+m;
        editTextcomment_Date.setText(gregorian_to_jalali(year + 1900, month + 1, day, week));


    }
    public  String gregorian_to_jalali(int gy1,int gm1,int gd1, int week )
    {
        int g_days_in_month[]=new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int j_days_in_month[]=new int[]{31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};
        int gy=gy1-1600;
        int gm=gm1-1;
        int gd=gd1-1;
        int g_day_no =
                365*gy+doubleToInt((gy+3)/4)-doubleToInt((gy+99)/100)+
                        doubleToInt((gy+399)/400);
        int i;
        for (i=0;i<gm;++i)
            g_day_no += g_days_in_month[i];
        if (gm>1 && ((gy%4==0 && gy%100!=0) || (gy%400==0)))
            g_day_no++;
        g_day_no += gd;
        int j_day_no = g_day_no-79;
        int j_np = doubleToInt(j_day_no/12053);
        j_day_no = j_day_no % 12053;
        int jy = 979+33*j_np+4*doubleToInt(j_day_no/1461);
        j_day_no %= 1461;
        if (j_day_no >= 366)
        {
            jy += doubleToInt((j_day_no-1)/365);
            j_day_no = (j_day_no-1)%365;
        }
        for (i = 0; i < 11 && j_day_no >= j_days_in_month[i]; ++i)
            j_day_no -= j_days_in_month[i];
        int jm = i+1;
        String s = null;
        String w = null;
        switch(week) {
            case 0:
                w = "يکشنبه";
                break;
            case 1:
                w = "دوشنبه";
                break;
            case 2:
                w = "سه شنبه";
                break;
            case 3:
                w = "چهارشنبه";
                break;
            case 4:
                w = "پنجشنبه";
                break;
            case 5:
                w = "جمعه";
                break;
            case 6:
                w = "شنبه";
                break;
        }
        switch(jm) {
            case 1:
                s = "1";
                break;
            case 2:
                s = "2";
                break;
            case 3:
                s = "3";
                break;
            case 4:
                s = "4";
                break;
            case 5:
                s = "5";
                break;
            case 6:
                s = "6";
                break;
            case 7:
                s = "7";
                break;
            case 8:
                s = "8";
                break;
            case 9:
                s = "9";
                break;
            case 10:
                s = "10";
                break;
            case 11:
                s = "11";
                break;
            case 12:
                s = "12";
                break;
        }
        String d = null;
        d = "";
        d += Integer.toString(jy) + "/";
        d += s + "/";
        d += Integer.toString(j_day_no+1);
        //d += w;

        return d;
    };
    private  int doubleToInt(double f)
    {
        Double fint=new Double(f);
        return fint.intValue();
    };
}
