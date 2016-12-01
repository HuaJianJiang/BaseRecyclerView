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

import android.view.View;
import android.widget.Toast;

import com.github.huajianjiang.baserecyclerview.sample.R;
import com.github.huajianjiang.baserecyclerview.viewholder.BaseViewHolder;

/**
 * Created by jhj_Plus on 2016/10/10.
 */
public class MyViewHolder extends BaseViewHolder {
    private static final String TAG = "MyViewHolder";

    public MyViewHolder(View itemView) {
        super(itemView);

    }

    @Override
    protected void onItemClick(BaseViewHolder vh, View v, int position) {
        Toast.makeText(v.getContext(), "onItemClick=>"+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int[] onRegisterLongClickEvent() {
        return new int[]{R.id.item};
    }

    @Override
    protected boolean onItemLongClick(BaseViewHolder vh, View v, int adapterPosition) {
        Toast.makeText(v.getContext(), "onItemLongClick=>" + adapterPosition, Toast.LENGTH_SHORT)
                .show();
        return true;
    }
}
