package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movies implements Serializable {
    private  String mTitle;
    private  String mPoster;
    private  String mOverView;
    private  String mReleaseDate;

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmPoster(String mPoster) {
        this.mPoster = mPoster;
    }

    public void setmOverView(String mOverView) {
        this.mOverView = mOverView;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmPoster() {
        return mPoster;
    }

    public String getmOverView() {
        return mOverView;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public Movies(String mTitle, String mPoster, String mOverView, String mReleaseDate) {
        this.mTitle = mTitle;
        this.mPoster = mPoster;
        this.mOverView = mOverView;
        this.mReleaseDate = mReleaseDate;
    }

    public static final String TITLE = "title";
    public static final String POSTER = "poster_path";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static List<Movies> parseCourseJSON(String courseJSON) throws JSONException {
        List<Movies> courseList = new ArrayList<Movies>();
        if (courseJSON != null) {
            JSONArray arr = new JSONArray(courseJSON);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Movies course = new Movies(obj.getString(Movies.TITLE),
                        obj.getString(Movies.POSTER) ,
                        obj.getString(Movies.OVERVIEW),
                        obj.getString(Movies.RELEASE_DATE));
                courseList.add(course);
            }
        }
        return courseList;
    }
}