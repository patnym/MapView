package com.onlylemi.mapview.library.navigation;

import android.graphics.PointF;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PathInfo {

    private List<PointF> path;
    private boolean pathFound;

    public PathInfo() {
        this.path = new ArrayList<>();
        this.pathFound = false;
    }

    public PathInfo(List<PointF> path) {
        this.path = path;
        this.pathFound = true;
    }

    public List<PointF> getPath() {
        return path;
    }

    public boolean isPathFound() {
        return pathFound;
    }

    @NonNull
    public static PathInfo SinglePathTo(PointF destination) {
        List<PointF> path = new ArrayList<>();
        path.add(destination);
        return new PathInfo(path);
    }

    @NonNull
    public static PathInfo NoPathFound() {
        return new PathInfo();
    }
}
