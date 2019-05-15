package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.login.SignInDialogFragment;
import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.model.Movies;

public class MainActivity extends AppCompatActivity
        implements SignInDialogFragment.SignInListenerInterface,
        MoviesListFragment.OnListFragmentInteractionListener {

    //DataLab
    private SharedPreferences mSharedPreferences;
    private MoviesListFragment mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mSharedPreferences  = getSharedPreferences(getString(R.string.LOGIN_PREFS),
                Context.MODE_PRIVATE);

        if(!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
            SignInDialogFragment signInDialogFragment = new SignInDialogFragment();
            signInDialogFragment.show(fragmentTransaction, "Sing In");
        } else {

        }



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mList = new MoviesListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mList)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            SharedPreferences sharedPreferences =
                    getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                    .commit();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            SignInDialogFragment signInDialogFragment = new SignInDialogFragment();
            signInDialogFragment.show(fragmentTransaction, "Sign   In");
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void login(String email, String pwd) {

        boolean isLoggedIn = false;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SignInDialogFragment signInDialogFragment = new SignInDialogFragment();
        if(TextUtils.isEmpty(email) || !email.contains("@")) {
            Toast.makeText(this, "Enter valid email address",
                    Toast.LENGTH_SHORT)
                    .show();

        } else if (TextUtils.isEmpty(pwd) || pwd.length() < 4){
            Bundle bundle  = new Bundle();
            bundle.putString(SignInDialogFragment.SIGN_IN_EMAIL, email);
            signInDialogFragment.setArguments(bundle);

            signInDialogFragment.setArguments(bundle);
            Toast.makeText(this, "Enter valid password (at least 4 characters)"
                    , Toast.LENGTH_SHORT)
                    .show();

        } else {
            mSharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), true).commit();
            isLoggedIn = true;
//            fragmentTransaction.add(R.id.fragment_course_container, new CourseListFragment())
//                    .commit();
        }

        if(!isLoggedIn) {
            signInDialogFragment.show(fragmentTransaction, "Sign In");
        }

    }
    @Override
    public void onListFragmentInteraction(Movies item) {

    }
}

