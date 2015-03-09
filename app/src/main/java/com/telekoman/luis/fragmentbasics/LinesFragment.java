/*
 * Copyright (C) 2012 The Android Open Source Project
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
 */
package com.telekoman.luis.fragmentbasics;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LinesFragment extends ListFragment {
    OnLineSelectedListener mCallback;
    private Level _rootLevel;
    final static String LINE_POSITION = "linePosition";
    int mCurrentLinePosition = -1;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnLineSelectedListener {
        /** Called by LinesFragment when a list item is selected */
        public void onLineSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        if (savedInstanceState != null) {
            mCurrentLinePosition = savedInstanceState.getInt(LINE_POSITION);
        } else {
            Bundle bundle=this.getArguments();
            if (bundle!=null) {
                mCurrentLinePosition = bundle.getInt(LINE_POSITION);
            }
        }

        this._rootLevel = new Level(1, "STC");
        int i = 1;
        for (String linename:Ipsum.STC_lines) {
            Level line = new Level(i++,linename);
            if (i==2) {
                int j = 20;
                for (String stname:Ipsum.line1_stations) {
                    Level station = new Level(j++, stname);
                    line.addSubLevel(station);
                }
            }
            else if (i==3) {
                int j = 40;
                for (String stname:Ipsum.line2_stations) {
                    Level station = new Level(j++, stname);
                    line.addSubLevel(station);
                }
            }
            this._rootLevel.addSubLevel(line);
        }
        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, this._rootLevel.getSubLevels()));
    }

    @Override
    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.stations_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnLineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        mCallback.onLineSelected(position);
        
        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }
}