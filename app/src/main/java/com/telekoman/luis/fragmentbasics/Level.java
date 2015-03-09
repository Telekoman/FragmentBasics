package com.telekoman.luis.fragmentbasics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luis on 01/03/2015.
 */
public class Level {
    public Level(Integer id, String name) {
        this._idLevel = id;
        this._levelName = name;
        this._subLevels = new ArrayList<Level>(10);
        this._cameras = new ArrayList<Camera>(10);
    }

    public String getLevelName() {
        return this._levelName;
    }

    public ArrayList<String> getSubLevels() {
        ArrayList<String> sub = new ArrayList<String>(this._subLevels.size());

        for (Level temp: this._subLevels) {
            sub.add(temp.toString());
        }

        return sub;
    }

    public ArrayList<String> getCameras() {
        ArrayList<String> sub = new ArrayList<String>(this._cameras.size());

        for (Camera temp: this._cameras) {
            sub.add(temp.toString());
        }

        return sub;
    }

    public void addCamera(Camera cam) {
        this._cameras.add(cam);
    }
    public void addCamera(Integer id, String name, String connstring) {
        this._cameras.add(new Camera(id, name, connstring));
    }
    public void clearCameras() {
        this._cameras.clear();
    }

    public void addSubLevel(Level subLevel) {
        this._subLevels.add(subLevel);
    }
    public void addCamera(Integer id, String name) {
        this._subLevels.add(new Level(id, name));
    }
    public void clearSubLevels() {
        this._subLevels.clear();
    }

    @Override
    public String toString() {
        return this._levelName;
    }

    private String _levelName;
    private Integer _idLevel;
    private ArrayList<Level> _subLevels;

    private ArrayList<Camera> _cameras;

}
