package com.ryandro.sqlitedatabasedemo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.ryandro.sqlitedatabasedemo.sqlite.DataBaseHelper;
import com.ryandro.sqlitedatabasedemo.sqlite.DataBaseManager;

public class MainActivity extends AppCompatActivity {
    private ListView lv_countryDetail;
    private DataBaseManager dataBaseManager;
    private SimpleCursorAdapter adapter;
    final String[] from = new String[]{DataBaseHelper._ID,
            DataBaseHelper.SUBJECT, DataBaseHelper.DESC};

    final int[] to = new int[]{R.id.tv_id, R.id.title, R.id.tv_desription};
    private Cursor cursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_countryDetail = findViewById(R.id.lv_countryDetail);
        Button btn_addCountry = findViewById(R.id.btn_addCountry);

        cursor = getdata();
        adapter = new SimpleCursorAdapter(this, R.layout.listview_cell, cursor, from, to, 0);
        lv_countryDetail.setAdapter(adapter);

        btn_addCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dataBaseManager = new DataBaseManager(MainActivity.this);
                        dataBaseManager.open();
                        dataBaseManager.insert("USA", "A big Country");
                        dataBaseManager.close();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               cursor = getdata();
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();

            }
        });



    }

    private Cursor getdata() {

        dataBaseManager = new DataBaseManager(MainActivity.this);
        dataBaseManager.open();
        Cursor cursor = dataBaseManager.fetch();
        return cursor;
    }
}
