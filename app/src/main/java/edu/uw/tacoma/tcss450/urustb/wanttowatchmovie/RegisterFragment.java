package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.login.Account;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnRegisterFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnRegisterFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view   = inflater.inflate(R.layout.fragment_register, container, false);
        final TextView registerFirstTextView = view.findViewById(R.id.register_first_edit_text);
        final TextView registerLastTextView = view.findViewById(R.id.register_last_edit_text);
        final TextView registerUsernameTextView = view.findViewById(R.id.register_username_edit_text);
        final TextView registerEmailTextView = view.findViewById(R.id.register_email_edit_text);
        final TextView registerPasswordTextView = view.findViewById(R.id.register_password_edit_text);


        Button sendButton = view.findViewById(R.id.register_send_button_edit_text);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registerFirst = registerFirstTextView.getText().toString();
                String registerLast = registerLastTextView.getText().toString();
                String registerUsername = registerUsernameTextView.getText().toString();
                String registerEmail = registerEmailTextView.getText().toString();
                String registerPassword = registerPasswordTextView.getText().toString();


                if(registerFirst.isEmpty() || registerLast.isEmpty() || registerUsername.isEmpty()
                     || registerEmail.isEmpty() || registerPassword.isEmpty() ) {
                    Toast.makeText(v.getContext(), "Please Fill Up all The Fields!"
                    , Toast.LENGTH_LONG).show();
                    return;
                }

                Account account = new Account(registerFirst, registerLast, registerUsername, registerEmail,
                        registerPassword);
                mListener.onRegisterFragmentInteraction(account);


            }
        });


        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Account uri) {
        if (mListener != null) {
            mListener.onRegisterFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterFragmentInteractionListener) {
            mListener = (OnRegisterFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRegisterFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRegisterFragmentInteraction(Account account);
    }
}
