package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.R;
import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.login.Account;

public class SignInActivity extends AppCompatActivity implements
        SignInFragment.OnSignInFragmentInteractionListener {

    private JSONObject mArguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.SignIn_content, new SignInFragment())
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
    public void onSignInFragmentInteraction(Account account) {
        StringBuilder url = new StringBuilder(getString(R.string.login_backend));

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
                    response = "Unable to Sign In, Reason: "
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
                    Toast.makeText(getApplicationContext(), "You have Signed In successfully!", Toast.LENGTH_SHORT)
                            .show();
                    getSupportFragmentManager().popBackStackImmediate();
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Sign In is not possible: Reason: "
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
