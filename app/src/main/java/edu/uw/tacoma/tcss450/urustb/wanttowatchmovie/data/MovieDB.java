package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.R;
import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.model.Movies;

public class MovieDB {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Course.db";
    private CourseDBHelper mCourseDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public MovieDB(Context context) {
        mCourseDBHelper = new CourseDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mCourseDBHelper.getWritableDatabase();
    }



    /** * Inserts the course into the local sqlite table. Returns true if successful, false otherwise.
     * * @param id
     * * @param shortDesc
     * * @param longDesc
     * * @param prereqs
     * * @return true or false */
    public boolean insertCourse(String title, String poster_path, String overview, String release_date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("poster_path", poster_path);
        contentValues.put("overview", overview);
        contentValues.put("release_date", release_date);
        long rowId = mSQLiteDatabase.insert("Course", null, contentValues);
        return rowId != -1;
    }

    /** * Delete all the data from the Course */
    public void deleteCourses() {

        mSQLiteDatabase.delete("Course", null, null);

    }




    /**
     *
     * * Returns the list of courses from the local Course table.
     * * @return list */

    public List<Movies> getCourses() {

        String[] columns = {
                "title", "poster_path", "overview", "release_date"
        };

        Cursor c = mSQLiteDatabase.query(
                "Course",                    // The table to query
                columns,                      // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        c.moveToFirst();
        List<Movies> list = new ArrayList<Movies>();
        for (int i=0; i<c.getCount(); i++) {
            String title = c.getString(0);
            String poster_path = c.getString(1);
            String overview = c.getString(2);
            String release_date = c.getString(3);
            Movies course = new Movies(title, poster_path, overview, release_date);
            list.add(course);
            c.moveToNext();
        }
        return list;
    }

    class CourseDBHelper extends SQLiteOpenHelper {
        private final String CREATE_COURSE_SQL;
        private final String DROP_COURSE_SQL;
        public CourseDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            CREATE_COURSE_SQL = context.getString(R.string.CREATE_COURSE_SQL);
            DROP_COURSE_SQL = context.getString(R.string.DROP_COURSE_SQL);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_COURSE_SQL);
        }
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_COURSE_SQL);
            onCreate(sqLiteDatabase);
        }
    }

}
