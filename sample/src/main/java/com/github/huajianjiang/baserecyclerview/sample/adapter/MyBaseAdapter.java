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

import com.github.huajianjiang.baserecyclerview.sample.model.Test;
import com.github.huajianjiang.baserecyclerview.widget.BaseAdapter;


/**
 * Created by jhj_Plus on 2016/11/2.
 */
public class MyBaseAdapter extends BaseAdapter<MyViewHolder, Test> {
    private static final String TAG = "MyBaseAdapter";

    public MyBaseAdapter(Context context) {
        super(context);
    }

    @Override
    public void onPopulateViewHolder(MyViewHolder vh, Test data, int position) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

}
