package edu.uw.tacoma.tcss450.urustb.wanttowatchmovie.login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {

    private String mRegisterFirst;
    private String mRegisterLast;
    private String mRegisterUsername;
    private String mRegisterEmail;
    private String mRegisterPassword;

    public static final String FIRST = "first";
    public static final String LAST = "last";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public Account(String mRegisterFirst, String mRegisterLast, String mRegisterUsername, String mRegisterEmail, String mRegisterPassword) {

        this.mRegisterFirst = mRegisterFirst;
        this.mRegisterLast = mRegisterLast;
        this.mRegisterUsername = mRegisterUsername;
        this.mRegisterEmail = mRegisterEmail;
        this.mRegisterPassword = mRegisterPassword;

    }


    public static List<Account> parseCourseJson(String courseJson) throws JSONException {
        List<Account> accountList = new ArrayList<>();

        if(courseJson != null) {
            JSONArray arr = new JSONArray(courseJson);

            for(int i = 0; i<arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Account account = new Account(obj.getString(Account.FIRST), obj.getString(Account.LAST)
                        , obj.getString(Account.USERNAME),
                        obj.getString(Account.EMAIL), obj.getString(Account.PASSWORD));
                accountList.add(account);
            }
        }
        return accountList;
    }



    //getter

    public String getmRegisterFirst() {
        return mRegisterFirst;
    }
    public String getmRegisterLast() {
        return mRegisterLast;
    }
    public String getmRegisterUsername() {
        return mRegisterUsername;
    }
    public String getmRegisterEmail() {
        return mRegisterEmail;
    }
    public String getmRegisterPassword() {
        return mRegisterPassword;
    }

    ///setter
    public void setmRegisterFirst(String mRegisterFirst) {

        this.mRegisterFirst = mRegisterFirst;
    }

    public void setmRegisterLast(String mRegisterLast) {
        this.mRegisterLast = mRegisterLast;
    }

    public void setmRegisterUsername(String mRegisterUsername) {
        this.mRegisterUsername = mRegisterUsername;
    }
    public void setmRegisterEmail(String mRegisterEmail) {
        this.mRegisterEmail = mRegisterEmail;
    }

    public void setmRegisterPassword(String mRegisterPassword) {
        this.mRegisterPassword = mRegisterPassword;
    }

}
