package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.login;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.R;
import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.RegisterFragment;

public class SignInDialogFragment extends DialogFragment {

    private SignInListenerInterface mListener;
    public static final  String SIGN_IN_EMAIL = "email";
    private RegisterFragment mRegisterFragment;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(linearLayout.VERTICAL);
        final EditText emailText = new EditText(getActivity());
        emailText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailText.setHint(R.string.email_hint);
        Bundle bundle = getArguments();
        if(bundle != null) {
            String email = bundle.getString(SIGN_IN_EMAIL);
            emailText.setText(email);
        }
        final EditText pwdText = new EditText(getActivity());
        pwdText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        pwdText.setHint(R.string.pwd_hint);
        linearLayout.addView(emailText);
        linearLayout.addView(pwdText);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(linearLayout)
                .setMessage(R.string.login_message)
                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener = (SignInListenerInterface) getActivity();
                        mListener.login(emailText.getText().toString(), pwdText.getText().toString());

                    }
                })
                .setNegativeButton(R.string.register, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mRegisterFragment = new RegisterFragment();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, mRegisterFragment)
                                .addToBackStack(null)
                                .commit();


                    }
                });
        return builder.create();
    }


    public interface SignInListenerInterface {
        void login(String email, String pwd);
    }

}

