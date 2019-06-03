package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.login.Account;

public class LoginActivity extends AppCompatActivity implements
        RegisterFragment.OnRegisterFragmentInteractionListener, SignInFragment.OnSignInFragmentInteractionListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn1 = (Button) findViewById((R.id.button_Reg));
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, RegisterrrActivity.class);
                startActivity(intent);




            }
        });

        Button btn2 = (Button) findViewById((R.id.button2));
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRegisterFragmentInteraction(Account account) {

    }


    @Override
    public void onSignInFragmentInteraction(Account account) {

    }
}
