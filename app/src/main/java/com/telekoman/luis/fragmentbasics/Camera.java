package com.telekoman.luis.fragmentbasics;

/**
 * Created by Luis on 01/03/2015.
 */
public class Camera {
    public Camera(Integer id, String name, String connstring) {
        this._idCamera = id;
        this._cameraName = name;
        this._cameraConnString = connstring;
    }

    private String _cameraName;
    private String _cameraConnString;
    private Integer _idCamera;

    public String getCameraName() {
        return _cameraName;
    }

    public String getCameraConnString() {
        return _cameraConnString;
    }

    @Override
    public String toString() {
        return this._cameraName;
    }
}
