package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.model.Movies;

public class MovieActivity extends AppCompatActivity
        implements MoviesListFragment.OnListFragmentInteractionListener{

    private MoviesDetailFragment mDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Movie_content, new MoviesListFragment())
                .addToBackStack(null)
                .commit();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public void onListFragmentInteraction(Movies item) {
        mDetail = new MoviesDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(MoviesDetailFragment.MOVIE_ITEM_PARAM, item);
        mDetail.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Movie_content, mDetail)
                .addToBackStack(null)
                .commit();

    }
}
