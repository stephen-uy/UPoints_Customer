package com.android.stephen.upoints_customer.utils;

import android.content.ContentValues;
import android.content.Context;

import com.android.stephen.upoints_customer.callback.VolleyCallback;

/**
 * Created by Stephen Uy on 3/3/2017.
 */

public class CustomerAPI {

    Context context;
    public CustomerAPI(Context c){
        this.context = c;
    }

    public void generateCustomerID(VolleyCallback callback, String custIDTemp){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.CUSTOMER_ID_TEMP.getValue(),custIDTemp);
        con.wGet(context, callback, API.CUSTOMER, Task.GENERATE_CUSTOMER_ID, contentValues, false);
    }

    public void getUpline(VolleyCallback callback, String customerID){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.CUSTOMER_ID_UP.getValue(),customerID);
        con.wGet(context, callback, API.CUSTOMER, Task.GET_UPLINE, contentValues, false);
    }

    public void saveNewCustomer(VolleyCallback callback, String customerEN, String customerPictureEN, String customerUplineEN){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.CUSTOMER_EN.getValue(),customerEN);
        contentValues.put(Parameters.CUSTOMER_PICTURE_EN.getValue(),customerPictureEN);
        contentValues.put(Parameters.CUSTOMER_UPLINE_EN.getValue(),customerUplineEN);
        con.wPost(context, callback, API.CUSTOMER, Task.SAVE_NEW_CUSTOMER, contentValues, false);
    }

    public void getCustomerLoginDetails(VolleyCallback callback, String custID, String password){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.CUST_ID.getValue(),custID);
        contentValues.put(Parameters.PASSWORD.getValue(),password);
        con.wGet(context, callback, API.CUSTOMER, Task.CUSTOMER_LOGIN_DETAILS, contentValues, false);
    }

    public void getCustomerReceivedPoints(VolleyCallback callback, String storeID, String from, String to){
        HttpVolleyConnector con = new HttpVolleyConnector();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Parameters.TO.getValue(),to);
        contentValues.put(Parameters.FROM.getValue(),from);
        contentValues.put(Parameters.CUST_ID.getValue(),storeID);
        con.wGet(context, callback, API.CUSTOMER, Task.CUSTOMER_RECEIVED_POINTS, contentValues, true);
    }
}
