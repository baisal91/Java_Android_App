package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
=======
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
>>>>>>> 8eeda8bc17d25f9cb5d25ea3b3407508d89970de
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
<<<<<<< HEAD
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.model.Movies;

=======
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.model.Movies;

import org.json.JSONObject;
>>>>>>> 8eeda8bc17d25f9cb5d25ea3b3407508d89970de
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
        Button BtPlaying = (Button) findViewById((R.id.button_play));
        BtPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                startActivity(intent);
            }
        });
        Button BtSearch;
        Button BtLocation;

<<<<<<< HEAD


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MoviesListFragment())
                .addToBackStack(null)
                .commit();
=======
>>>>>>> 8eeda8bc17d25f9cb5d25ea3b3407508d89970de


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
<<<<<<< HEAD

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        } else if(id == R.id.action_share) {
=======
            SharedPreferences sharedPreferences =
                    getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                    .commit();

>>>>>>> 8eeda8bc17d25f9cb5d25ea3b3407508d89970de

        }

        return super.onOptionsItemSelected(item);
    }
<<<<<<< HEAD
=======



>>>>>>> 8eeda8bc17d25f9cb5d25ea3b3407508d89970de


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


<<<<<<< HEAD
=======

>>>>>>> 8eeda8bc17d25f9cb5d25ea3b3407508d89970de
}

