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

public class CamerasFragment extends ListFragment {
    OnCameraSelectedListener mCallback;
    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnCameraSelectedListener {
        /** Called by CamerasFragment when a list item is selected */
        public void onCameraSelected(int position);
    }
    final static String LINE_POSITION = "linePosition";
    final static String STATION_POSITION = "stationPosition";
    final static String CAMERA_POSITION = "cameraPosition";

    int _linePosition = -1;
    int _stationPosition = -1;
    int mCurrentCameraPosition = -1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        if (savedInstanceState != null) {
            this._linePosition = savedInstanceState.getInt(LINE_POSITION);
            this._stationPosition = savedInstanceState.getInt(STATION_POSITION);
            this.mCurrentCameraPosition = savedInstanceState.getInt(CAMERA_POSITION);
        } else {
            Bundle bundle=this.getArguments();
            this._linePosition = bundle.getInt(LINE_POSITION);
            this._stationPosition = bundle.getInt(STATION_POSITION);
            this.mCurrentCameraPosition = bundle.getInt(CAMERA_POSITION);
        }

        Level station = new Level(1,"Station");
        if ((this._linePosition==0) && (this._stationPosition==0)){
            int j = 20;
            for (String camname:Ipsum.L1_Observatorio_cameras) {
                Camera cam = new Camera(j++, camname, camname);
                station.addCamera(cam);
            }
        }
        else {
            int j = 40;
            for (String camname:Ipsum.StationX_cameras) {
                Camera cam = new Camera(j++, camname, camname);
                station.addCamera(cam);
            }

        }

        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, station.getCameras()));
    }

    @Override
    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.cameras_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnCameraSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCameraSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        mCallback.onCameraSelected(position);

        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }

    public void updateCamerasView(int line, int station) {
        this._linePosition = line;
        this._stationPosition = station;
        Level lev = new Level(1,"Station");
        if ((this._linePosition==0) && (this._stationPosition==0)){
            int j = 20;
            for (String camname:Ipsum.L1_Observatorio_cameras) {
                Camera cam = new Camera(j++, camname, camname);
                lev.addCamera(cam);
            }
        }
        else {
            int j = 40;
            for (String camname:Ipsum.StationX_cameras) {
                Camera cam = new Camera(j++, camname, camname);
                lev.addCamera(cam);
            }

        }
        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, lev.getCameras()));
    }
}