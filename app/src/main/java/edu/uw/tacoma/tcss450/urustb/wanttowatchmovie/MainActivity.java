package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.login.Account;
import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.login.SignInDialogFragment;
import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.model.Movies;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements SignInDialogFragment.SignInListenerInterface,
        MoviesListFragment.OnListFragmentInteractionListener,
        RegisterFragment.OnRegisterFragmentInteractionListener {

    private SharedPreferences mSharedPreferences;
    private MoviesListFragment mList;
    private MoviesDetailFragment mDetail;
    private RegisterFragment mRegisterFragment;
    private JSONObject mArguments;



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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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


    /**
     * login method where it checks the user whether it has provided with right email address and password
     * @param email user email
     * @param pwd user password
     */
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


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MoviesListFragment())
                    .addToBackStack(null)
                    .commit();
            //fragmentTransaction.add(R.id.fragment_container, new MoviesListFragment());
        }

        if(!isLoggedIn) {
            signInDialogFragment.show(fragmentTransaction, "Sign In");
        }

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


    /**
     * method where it sends the object into webservice
     * @param account
     */
    @Override
    public void onRegisterFragmentInteraction(Account account) {

        StringBuilder url = new StringBuilder(getString(R.string.register_send_button));

        //Construct a JSONObject to build a formatted message to send.
        mArguments = new JSONObject();
        try {
            mArguments.put(Account.FIRST, account.getmRegisterFirst());
            mArguments.put(Account.LAST, account.getmRegisterLast());
            mArguments.put(Account.USERNAME, account.getmRegisterUsername());
            mArguments.put(Account.EMAIL, account.getmRegisterEmail());
            mArguments.put(Account.PASSWORD, account.getmRegisterPassword());
            new AddCourseAsyncTask().execute(url.toString());
        } catch (JSONException e) {
            Toast.makeText(this, "Error with JSON creation: " + e.getMessage()
                    , Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Inner AsyncTask where it helps  to connect with URL
     */
    private class AddCourseAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);
                    OutputStreamWriter wr =
                            new OutputStreamWriter(urlConnection.getOutputStream());
                    Log.i("UPSS", mArguments.toString());
                    wr.write(mArguments.toString());
                    wr.flush();
                    wr.close();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add the new course, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * Method where it generates some Toast messages after registration whether it was
         * successful or not
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            try{
                JSONObject resultObject = new JSONObject(result);
                if(resultObject.getBoolean("success") == true) {
                    Toast.makeText(getApplicationContext(), "You have registered successfully!", Toast.LENGTH_SHORT)
                            .show();
                    getSupportFragmentManager().popBackStackImmediate();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "You were not able to register: Reason: "
                    + resultObject.getString("error"), Toast.LENGTH_SHORT)
                            .show();
                    Log.e("ERROR", resultObject.getString("error"));
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),  e.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }



}

