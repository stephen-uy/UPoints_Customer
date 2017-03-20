package com.android.stephen.upoints_customer.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.stephen.upoints_customer.R;
import com.android.stephen.upoints_customer.callback.VolleyCallback;
import com.android.stephen.upoints_customer.database.SQLiteDBHandler;
import com.android.stephen.upoints_customer.model.CustomerModel;
import com.android.stephen.upoints_customer.model.StoreModel;
import com.android.stephen.upoints_customer.utils.APIHelper;
import com.android.stephen.upoints_customer.utils.CustomerAPI;
import com.android.stephen.upoints_customer.utils.Helper;
import com.android.stephen.upoints_customer.utils.Task;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements VolleyCallback {

    private static final int REQUEST_READ_CONTACTS = 0;
    private static final int REQUEST_INTERNET = 1;
    private UserLoginTask mAuthTask = null;

    private static StoreModel storeModel;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        SQLiteDBHandler.getInstance(this);
//        APIHelper.insertStoreUser(this);
//        APIHelper.insertStoreDetails(this);
//        APIHelper.insertCustomerData(this);
//        APIHelper.insertItemData(this);
//        APIHelper.insertProductData(this);
//        APIHelper.insertProductItemData(this);
//        APIHelper.insertStoreStocksData(this);
//        APIHelper.insertStoreStocksRegData(this);
        mayRequestConnectivity();
    }

    private boolean mayRequestConnectivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE, ACCESS_COARSE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE}
                                    , REQUEST_INTERNET);
                        }
                    });
        } else {
            requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE, ACCESS_COARSE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE}
                    , REQUEST_INTERNET);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)){
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
//        else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void showErrorMessage(){
        Helper.showDialog(LoginActivity.this, "", getString(R.string.error_login), new OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.alertDialogCancel();
                mEmailView.getText().clear();
                mPasswordView.getText().clear();
                mEmailView.requestFocus();
            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onResponseReady(Task task, LinkedHashMap<String, String> response) {
        if (task.getValue().equalsIgnoreCase(Task.CUSTOMER_LOGIN_DETAILS.getValue()))
            parseCustomerDetails(response);

        Log.d("response"+task.getValue(),""+ response.toString());
    }

    private void parseCustomerDetails(LinkedHashMap<String, String> response) {
        CustomerModel customerModel;
        customerModel = APIHelper.setUpCustomerDetails(response);
        if (!TextUtils.isEmpty(customerModel.getCustomerID())){
            showProgress(false);
            goToNext(customerModel);
        } else {
            showProgress(false);
            showErrorMessage();
        }
    }

    @Override
    public void onResponseReady(Task task, LinkedList<LinkedHashMap<String, String>> response) {
        Log.d("response"+task.getValue(),""+ response.toString());
    }

    private void goToNext(CustomerModel model){
        Intent i;
        i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("model", model);
        startActivity(i);
        finish();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private int rowCount;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            CustomerAPI customerAPI = new CustomerAPI(LoginActivity.this);
            customerAPI.getCustomerLoginDetails(LoginActivity.this, mEmail, mPassword);

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
//                showProgress(false);
//                if (storeModel != null) {
//                    goToNext(storeModel);
//                } else {
//                    if (rowCount > 0)
//                        showErrorMessage();
//                }
            } else {
                showProgress(false);
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
                showErrorMessage();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

