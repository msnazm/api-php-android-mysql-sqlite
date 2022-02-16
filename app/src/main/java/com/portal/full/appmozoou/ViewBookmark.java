package com.portal.full.appmozoou;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.portal.full.appmozoou.db.Bookmark;
import com.portal.full.appmozoou.db.DataBase;

import java.io.File;

public class ViewBookmark extends AppCompatActivity {

    private DataBase mHelper;
    private EditText editTextId;
    private TextView editTextName;
    private EditText editTextDesg;
    private WebView web;
    public Menu test;
    private String htmlText;
    public MenuItem playButtonOn;
    private int _Bookmark_Id=0;
    private String ID;

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);


/*
            playButtonOn = menu.findItem(R.id.action_bookmark_on);
            playButtonOn.setVisible(false);
            // editTextName.requestFocus();*/


            MenuItem playButtonn = menu.findItem(R.id.selectAlldelete);
            playButtonn.setVisible(false);

        return super.onCreateOptionsMenu(menu);
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
                _Bookmark_Id = intent.getIntExtra("bookmark_ID", 0);
                editTextId = (EditText) findViewById(R.id.editTextId);
                editTextName = (TextView) findViewById(R.id.editTextName);
                editTextDesg = (EditText) findViewById(R.id.editTextDesg);

                editTextId.setText(ID);
                final String MemID = ID;
//        BookmarkRepo repo = new BookmarkRepo(this);
//        Bookmark bookmark = new Bookmark();
//        bookmark = repo.getBookmarkById(_Bookmark_Id);


                ShowData(MemID);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        // Check Data (MemberID exists)


        switch(item.getItemId()) {


            case R.id.action_delete:

                DataBase myDb = new DataBase(this);
                myDb.DeleteDataBookMarks(ID);

                Intent newActivityy = new Intent(ViewBookmark.this, BookMark.class);

                startActivity(newActivityy);
                //   String ID = editTextId.getText().toString();



                return(true);



            case R.id.home:

                Intent newActivity = new Intent(ViewBookmark.this, Main.class);

                startActivity(newActivity);


                return(true);


        }
        return(super.onOptionsItemSelected(item));
    }

    private void shareImage() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // If you want to share a png image only, you can do:
        // setType("image/png"); OR for jpeg: setType("image/jpeg");
        share.setType("image/*");

        // Make sure you put example png image named myImage.png in your
        // directory
        String imagePath = Environment.getExternalStorageDirectory()
                + editTextDesg.getText().toString();

        File imageFileToShare = new File(imagePath);

        Uri uri = Uri.fromFile(imageFileToShare);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_SUBJECT, "اولین پورتال جامع");
        share.putExtra(Intent.EXTRA_TEXT, "https://www.asemooni.com");
        startActivity(Intent.createChooser(share, "Share Image!"));
    }
    private void shareTextUrl() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        String imagePath = editTextDesg.getText().toString();

        String htmlTexts = "<html dir='rtl'><head><style>img{display: inline;height: auto;max-width: 100%;}</style></head><body>"+imagePath.toString()+"</body></html>";



        // share.putExtra(Intent.EXTRA_SUBJECT, "اولین پورتال جامع");
        // share.putExtra(Intent.EXTRA_TEXT, "https://www.asemooni.com");
        share.putExtra(Intent.EXTRA_TEXT, htmlTexts);
        startActivity(Intent.createChooser(share, "Share link!"));
    }


    public void ShowData(String MemID)
    {
        // txtMemberID, txtName, txtTel
        //	final TextView tMemberID = (TextView) findViewById(R.id.txtMemberID);
        final TextView name = (TextView) findViewById(R.id.editTextName);
        final EditText desg = (EditText) findViewById(R.id.editTextDesg);
        final WebView msn = (WebView) findViewById(R.id.webView);

        // new Class DB
        final DataBase myDb = new DataBase(this);

        // Show Data
        String arrData[] = myDb.SelectDataBookMark(MemID);
        if(arrData != null)
        {
            //	tMemberID.setText(arrData[0]);
            name.setText(arrData[2]);
            desg.setText(arrData[3]);
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
                            "}</style></head><body><p>"+replaced+"</p></body></html>";            WebSettings settings = msn.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLoadsImagesAutomatically(true);
            settings.setDefaultTextEncodingName("UTF-8");
            settings.setLoadWithOverviewMode(true);
            settings.setBuiltInZoomControls(true);
            msn.loadData(htmlText, "text/html; charset=UTF-8", "UTF-8");
        }

    }



}
