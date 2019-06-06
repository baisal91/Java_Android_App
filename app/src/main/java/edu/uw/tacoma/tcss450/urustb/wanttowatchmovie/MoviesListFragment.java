package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.data.MovieDB;
import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.model.Movies;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MoviesListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Movies> mCourseList;
    private RecyclerView mRecyclerView;
    private MovieDB movieDB;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MoviesListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MoviesListFragment newInstance(int columnCount) {
        MoviesListFragment fragment = new MoviesListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    /**
     * crate the onCreatView and call the downloadMoviesTask
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
//        mLoadingView = getActivity().findViewById(R.id.loading_spinner);
        // Retrieve and cache the system's default "short" animation time.

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            ConnectivityManager connMgr = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()) {
                new DownloadMoviesTask().execute(getString(R.string.get_movies));
            }
            else {
                Toast.makeText(view.getContext(),
                        "No network connection available. Display locally stored data",
                        Toast.LENGTH_SHORT).show();

                if(movieDB == null) {
                    movieDB = new MovieDB(getActivity());
                }
                if(mCourseList == null) {
                    mCourseList = movieDB.getCourses();
                }
                mRecyclerView.setAdapter(new MyMoviesRecyclerViewAdapter(mCourseList, mListener));
            }
            //mRecyclerView.setAdapter(new MyItemRecyclerViewAdapter(mCourseList, mListener));
            //  new DownloadCoursesTask().execute(getString(R.string.get_courses));

        }

        //new DownloadCoursesTask().execute(getString(R.string.get_courses));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * the listener interface
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Movies item);
    }

    /**
     * the downloadMoviesTask class

     */
    private class DownloadMoviesTask extends AsyncTask<String, Void, String> {

        /**
         * override the doInbackground for AsyncTask
         * @param urls
         * @return response
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    InputStream content = urlConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to download the list of courses, Reason: "
                            + e.getMessage();
                }        finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * override the onPostExecute method in AsyncTask
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            try{
                JSONObject  resultObject = new JSONObject(result);
                mCourseList = Movies.parseCourseJSON(resultObject.getString("results"));

                if(!mCourseList.isEmpty()) {

                        if(movieDB == null){
                            movieDB = new MovieDB(getActivity());
                        }

                        movieDB.deleteCourses();

                        for(int i=0; i<mCourseList.size(); i++) {
                            Movies course = mCourseList.get(i);
                            movieDB.insertCourse(course.getmTitle(),
                                    course.getmPoster(),
                                    course.getmOverView(),
                                    course.getmReleaseDate());
                        }

                        mRecyclerView.setAdapter(new MyMoviesRecyclerViewAdapter(mCourseList, mListener));
                    }

            } catch (JSONException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }
}

