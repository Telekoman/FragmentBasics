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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

public class MainActivity extends FragmentActivity 
        implements LinesFragment.OnLineSelectedListener,
        StationsFragment.OnStationSelectedListener,
        CamerasFragment.OnCameraSelectedListener{

    public MainActivity() {

    }

    private boolean _isLogged;
    private String _userLogin;
    private int _idUser;
    private int _line;
    private int _station;
    private int _camera;
    private TextView _summaryViewMessage;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels_view);

        if (findViewById(R.id.summaryTextView) != null) {
            _summaryViewMessage = (TextView)findViewById(R.id.summaryTextView);
            _summaryViewMessage.setText(getString(R.string.summary_text_init));
        }
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            if (this._isLogged) {
                // Create an instance of ExampleFragment
                LinesFragment firstFragment = new LinesFragment();

                // In case this activity was started with special instructions from an Intent,
                // pass the Intent's extras to the fragment as arguments
                firstFragment.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, firstFragment).commit();
            } else {
                // Create The  Intent and Start The LoginActivity to login
                Intent intentLogin=new Intent(this,LoginActivity.class);
                startActivityForResult(intentLogin, 2);// Activity is started with requestCode 2
            }
        } else {
			if (this._isLogged) {
                // Create The  Intent and Start The LoginActivity to login
                Intent intentLogin=new Intent(this,LoginActivity.class);
                startActivityForResult(intentLogin, 2);// Activity is started with requestCode 2
			}
		}
    }

    // Call Back method  to get the Message form other Activity    override the method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);


        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            // fetch the message String
            this._isLogged=data.getBooleanExtra("LOGIN_RESULT", false);
            this._userLogin=data.getStringExtra("LOGIN");


            // Set the message string in textView
            if (this._isLogged) {
				if (findViewById(R.id.fragment_container) != null) {
					// Create an instance of ExampleFragment
					LinesFragment firstFragment = new LinesFragment();

					// In case this activity was started with special instructions from an Intent,
					// pass the Intent's extras to the fragment as arguments
					firstFragment.setArguments(getIntent().getExtras());

					// Add the fragment to the 'fragment_container' FrameLayout
					getSupportFragmentManager().beginTransaction()
							.add(R.id.fragment_container, firstFragment).commit()
				}
            }

        }

    }

    public void onLineSelected(int linePosition) {
        // The user selected a line from the LinesFragment
        this._line = linePosition;
        _summaryViewMessage.setText("Linea "+ _line);
        // Capture the station fragment from the activity layout
        StationsFragment stationFrag = (StationsFragment)
                getSupportFragmentManager().findFragmentById(R.id.stations_fragment);

        if (stationFrag != null) {
            // If station frag is available, we're in a multipane layout...

            // Call a method in the StationsFragment to update its content
            stationFrag.updateStationsView(linePosition);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            StationsFragment newFragment = new StationsFragment();
            Bundle args = new Bundle();
            args.putInt(StationsFragment.LINE_POSITION, linePosition);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    public void onStationSelected(int stationPosition) {
        // The user selected a line from the LinesFragment
        this._station = stationPosition;

        _summaryViewMessage.setText("Linea "+ _line + "/Estación " + _station);
        // Capture the station fragment from the activity layout
        CamerasFragment camFrag = (CamerasFragment)
                getSupportFragmentManager().findFragmentById(R.id.cameras_fragment);

        if (camFrag != null) {
            // If cameras frag is available, we're in a multipane layout...

            // Call a method in the CamerasFragment to update its content
            camFrag.updateCamerasView(this._line, this._station);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            CamerasFragment newFragment = new CamerasFragment();
            Bundle args = new Bundle();
            args.putInt(CamerasFragment.LINE_POSITION, this._line);
            args.putInt(CamerasFragment.STATION_POSITION, this._station);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
        /*
        try{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setComponent(new ComponentName("org.videolan.vlc.betav7neon", "org.videolan.vlc.betav7neon.gui.video.VideoPlayerActivity"));
            i.setData(Uri.parse("rtsp://Aladdin:opensesame@ralk-rom.no-ip.org:554/igmonitor/media.sdp"));
            i.putExtra("itemTitle", "My camera");
            startActivity(i);
        }
        catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=org.videolan.vlc.betav7neon");
            Intent intent = new Intent (Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        */
    }

    public void onCameraSelected(int cameraPosition) {
        // The user selected a line from the LinesFragment
        this._camera = cameraPosition;
        _summaryViewMessage.setText("Linea "+ _line + "/Estación " + _station+"/Cámara "+_camera);
        // Capture the station fragment from the activity layout
        CameraFragment camFrag = (CameraFragment)
                getSupportFragmentManager().findFragmentById(R.id.cameras_fragment);

        if (camFrag != null) {
            // If camera frag is available, we're in a multipane layout...

            // Call a method in the CameraFragment to update its content
            camFrag.updateCameraView(this._line, this._station, this._camera);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            CameraFragment newFragment = new CameraFragment();
            Bundle args = new Bundle();
            args.putInt(CameraFragment.LINE_POSITION, this._line);
            args.putInt(CameraFragment.STATION_POSITION, this._station);
            args.putInt(CameraFragment.CAMERA_POSITION, this._camera);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
}