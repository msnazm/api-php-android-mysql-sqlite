package com.portal.full.appmozoou;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.portal.full.appmozoou.db.DataBase;

import java.util.ArrayList;
import java.util.HashMap;

public class BookMark extends AppCompatActivity implements ListView.OnItemClickListener {

    ArrayList<HashMap<String, String>> MebmerList;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listbookmark);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

                DataBase myDb = new DataBase(this);
                MebmerList = myDb.SelectAllDataBookMark();
                // listView1


                SimpleAdapter sAdap;
                sAdap = new SimpleAdapter(BookMark.this, MebmerList, R.layout.clumn,
                        new String[]{"BookMarkID", "TitleBookMark"}, new int[]{R.id.ColId, R.id.ColTitle});

                ListView lisView1 = (ListView) findViewById(R.id.listView);

                lisView1.setAdapter(sAdap);
                registerForContextMenu(lisView1);
                lisView1.setOnItemClickListener(this);


            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem playButtonn = menu.findItem(R.id.action_delete);
        playButtonn.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.selectAlldelete:
                DataBase myDb = new DataBase(this);
                 myDb.DeleteAllDataBookMark();

                MebmerList = myDb.SelectAllDataBookMark();
                // listView1


                SimpleAdapter sAdap;
                sAdap = new SimpleAdapter(BookMark.this, MebmerList, R.layout.clumn,
                        new String[]{"BookMarkID", "TitleBookMark"}, new int[]{R.id.ColId, R.id.ColTitle});

                ListView lisView1 = (ListView) findViewById(R.id.listView);

                lisView1.setAdapter(sAdap);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent newActivity = new Intent(BookMark.this, ViewBookmark.class);
        newActivity.putExtra(Config.EMP_ID, MebmerList.get(position).get("BookMarkID").toString());
        startActivity(newActivity);

    }


    }





