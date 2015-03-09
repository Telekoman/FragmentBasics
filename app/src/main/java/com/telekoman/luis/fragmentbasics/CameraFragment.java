package com.telekoman.luis.fragmentbasics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {
    final static String LINE_POSITION = "linePosition";
    final static String STATION_POSITION = "stationPosition";
    final static String CAMERA_POSITION = "cameraPosition";

    int _linePosition = -1;
    int _stationPosition = -1;
    int _cameraPosition = -1;

    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            this._linePosition = savedInstanceState.getInt(LINE_POSITION);
            this._stationPosition = savedInstanceState.getInt(STATION_POSITION);
            this._cameraPosition = savedInstanceState.getInt(CAMERA_POSITION);
        } else {
            Bundle bundle=this.getArguments();
            this._linePosition = bundle.getInt(LINE_POSITION);
            this._stationPosition = bundle.getInt(STATION_POSITION);
            this._cameraPosition = bundle.getInt(CAMERA_POSITION);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.camera_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateCameraView(args.getInt(LINE_POSITION), args.getInt(STATION_POSITION), args.getInt(CAMERA_POSITION));
        } else if ((this._linePosition != -1) && (this._stationPosition != -1) && (this._cameraPosition != -1))  {
            // Set article based on saved instance state defined during onCreateView
            updateCameraView(this._linePosition, this._stationPosition, this._cameraPosition);
        }
    }

    public void updateCameraView(int line, int station, int camera) {
        this._linePosition = line;
        this._stationPosition = station;
        this._cameraPosition = camera;
        TextView article = (TextView) getActivity().findViewById(R.id.camera);
        article.setText("Linea "+this._linePosition+"; Estacion " + this._stationPosition+"; Cámara "+this._cameraPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(LINE_POSITION, this._linePosition);
        outState.putInt(STATION_POSITION, this._stationPosition);
        outState.putInt(CAMERA_POSITION, this._cameraPosition);
    }


}
