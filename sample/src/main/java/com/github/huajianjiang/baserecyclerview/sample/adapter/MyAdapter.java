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

package com.github.huajianjiang.baserecyclerview.sample.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.github.huajianjiang.baserecyclerview.sample.R;
import com.github.huajianjiang.baserecyclerview.widget.BaseAdapter;

import java.util.List;

/**
 * Created by jhj_Plus on 2016/10/10.
 */
public class MyAdapter extends BaseAdapter<MyViewHolder,String> {
    private static final String TAG = "MyAdapter";

    public MyAdapter(Context context, List<String> items) {
        super(context, items);
    }

    @Override
    public MyViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(MyViewHolder holder, int position) {
    }

}
