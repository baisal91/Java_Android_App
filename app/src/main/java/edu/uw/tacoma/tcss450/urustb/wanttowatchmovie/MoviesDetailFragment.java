package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.model.Movies;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class MoviesDetailFragment extends Fragment {
    public static final String MOVIE_ITEM_PARAM = "movieitemparam";

    private Movies mMovies;
    private TextView mMovieTitle;
    private TextView mMovieOverview;
    private TextView mMovieReleaseDate;
//    private TextView mMoviePoster;
    private ImageView mMoviePoster;

//    private OnFragmentInteractionListener mListener;

    public MoviesDetailFragment() {
        // Required empty public constructor
    }

    /**
     * the onCreateView can connect each textView with the layout
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @SuppressLint("WrongViewCast")
    @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_movies_detail, container, false);
    mMovieTitle = (TextView) view.findViewById(R.id.movie_item_title);
    mMovieOverview = (TextView) view.findViewById(R.id.movie_item_overview);
    mMovieReleaseDate = (TextView) view.findViewById(R.id.movie_item_releaseDate);
 //   mMoviePoster = (TextView) view.findViewById(R.id.movie_item_poster);
        mMoviePoster = (ImageView) view.findViewById(R.id.movie_item_poster);

    return view;
}

    /**
     * to update the information in the text view
     * After set the text the fragment can be showed correctly
     * @param movie
     */
    public void updateView(Movies movie) {
        if (movie != null) {
            mMovieTitle.setText("Title:   " + movie.getmTitle());
//            mMovieTitle.setPadding(0,0,0,0);
<<<<<<< HEAD
            mMovieOverview.setText("Over View:"+movie.getmOverView());
            mMovieOverview.setPadding(0,500,0,0);
            mMovieReleaseDate.setText("Release Date:  " + movie.getmReleaseDate());
            mMovieReleaseDate.setPadding(0,200,600,0);
 //           mMoviePoster.setText(movie.getmPoster());
     //       mMoviePoster.setImageResource(movie.getmPoster());
      //      mMoviePoster.setPadding(0,1200,0,0);
=======
            mMovieOverview.setText("Over View: "+movie.getmOverView());
            mMovieOverview.setPadding(0,50,0,0);
            mMovieReleaseDate.setText("Release Date: " + movie.getmReleaseDate());
//            mMovieReleaseDate.setPadding(0,200,0,0);
            mMoviePoster.setText(movie.getmPoster());
//            mMoviePoster.setPadding(0,300,0,0);
>>>>>>> 8eeda8bc17d25f9cb5d25ea3b3407508d89970de
        }
    }

    /**
     * Override the on Resume method so that call the updateView
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.i("detail", "resume");
        Bundle args = getArguments();
        if (args != null) {
            // Set course information based on argument passed
            updateView((Movies) args.getSerializable(MOVIE_ITEM_PARAM));
        } else {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
