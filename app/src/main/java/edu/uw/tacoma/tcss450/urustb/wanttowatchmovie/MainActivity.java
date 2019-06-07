package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.model.Movies;

public class MainActivity extends AppCompatActivity
        implements
        MoviesListFragment.OnListFragmentInteractionListener {

    private MoviesDetailFragment mDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MoviesListFragment())
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        ShareActionProvider shareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent send = new Intent();
        send.setAction(Intent.ACTION_SEND);
        send.setType("text/plain");
        if(shareAction != null) {
            shareAction.setShareIntent(send);
        }

        return super.onCreateOptionsMenu(menu);

    }

    /**
     * override method where  it has an option to logout of app.
     * @param item logout
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        } else if(id == R.id.action_share) {

        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * MovieListFragment's interface, where it shows the detail
     * @param item movie detail
     */
    @Override
    public void onListFragmentInteraction(Movies item) {
        mDetail = new MoviesDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(MoviesDetailFragment.MOVIE_ITEM_PARAM, item);
        mDetail.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mDetail)
                .addToBackStack(null)
                .commit();

    }


}

