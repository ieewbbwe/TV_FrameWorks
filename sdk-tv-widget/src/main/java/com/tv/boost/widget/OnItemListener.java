package com.tv.boost.widget;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by owen on 16/2/28.
 */
public interface OnItemListener {
    void onItemSelected(ViewGroup parent, View itemView, int position);
    void onItemClick(ViewGroup parent, View itemView, int position);
}
