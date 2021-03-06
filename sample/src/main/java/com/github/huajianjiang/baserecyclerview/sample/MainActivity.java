/*
 * Copyright (c) 2016 HuaJian Jiang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.github.huajianjiang.baserecyclerview.sample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.huajianjiang.baserecyclerview.sample.adapter.MyAdapter;
import com.github.huajianjiang.baserecyclerview.sample.adapter.MyMultipleHeaderAdapter;
import com.github.huajianjiang.baserecyclerview.widget.BaseRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int TYPE_HEADER_1=0;
    private static final int TYPE_HEADER_2=1;
    private static final int TYPE_FOOTER_1=0;
    private static final int TYPE_FOOTER_2=1;
    private Random mRandom = new Random();
    private BaseRecyclerView mRecyclerView;
    private MyMultipleHeaderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction(
                        "Action", null).show();
            }
        });


        Locale[] availableLocales = Locale.getAvailableLocales();
        List<String> locales = new ArrayList<>();
        for (Locale locale : availableLocales) {
            locales.add(locale.getDisplayName());
        }
        locales = locales.subList(0, 28);

        mRecyclerView = (BaseRecyclerView) findViewById(R.id.rv_top);
        //
        mAdapter = new MyMultipleHeaderAdapter(MainActivity.this, new MyAdapter(this, locales));
        mRecyclerView.setAdapter(mAdapter);
      //  mRecyclerView.addItemDecoration(mAdapter.getItemDecoration());

        registerForContextMenu(mRecyclerView);

        RecyclerView rv_bottom= (RecyclerView) findViewById(R.id.rv_bottom);
        rv_bottom.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_addHeaderView:
                mAdapter.addRandomHeader();
//                View header=getRandomHeaderView();
//                mRecyclerView.addHeaderView((Integer) header.getTag(),header);
                break;
            case R.id.action_addFooterView:
                mAdapter.addRandomFooter();
//                View footer=getRandomHeaderView();
//                mRecyclerView.addHeaderView((Integer) footer.getTag(), footer);
                break;
            case R.id.action_removeHeaderView:
                mAdapter.removeHeader(0);
                break;
            case R.id.action_removeFooterView:
                mAdapter.removeFooter(0);
                break;
            case R.id.action_removeAllHeaderView:
                mAdapter.removeHeaders(mAdapter.getHeaders());
                break;
            case R.id.action_removeAllFooterView:
                mAdapter.removeFooters(mAdapter.getFooters());
                break;
            case R.id.action_moveHeaderView:
                mAdapter.moveHeader(1,2);
                break;
            case R.id.action_moveFooterView:
                mAdapter.moveFooter(1,2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    private View getRandomHeaderView() {
        View view = null;
        int i = mRandom.nextInt(2);
        switch (i) {
            case 0:
                view = getLayoutInflater().inflate(R.layout.header_1, mRecyclerView, false);
                view.setTag(TYPE_HEADER_1);
                break;
            case 1:
                view = getLayoutInflater().inflate(R.layout.header_2, mRecyclerView, false);
                view.setTag(TYPE_HEADER_2);
                break;
        }
        return view;
    }

    private View getRandomFooterView() {
        View view = null;
        int i = mRandom.nextInt(2);
        switch (i) {
            case 0:
                view = getLayoutInflater().inflate(R.layout.footer_1, mRecyclerView, false);
                view.setTag(TYPE_FOOTER_1);
                break;
            case 1:
                view = getLayoutInflater().inflate(R.layout.footer_2, mRecyclerView, false);
                view.setTag(TYPE_FOOTER_2);
                break;
        }
        return view;
    }

}
