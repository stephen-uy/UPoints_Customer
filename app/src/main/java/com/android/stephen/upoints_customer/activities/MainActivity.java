package com.android.stephen.upoints_customer.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.stephen.upoints_customer.R;
import com.android.stephen.upoints_customer.callback.VolleyCallback;
import com.android.stephen.upoints_customer.fragment.ProfileFragment;
import com.android.stephen.upoints_customer.fragment.UPointsHistoryFragment;
import com.android.stephen.upoints_customer.model.CustomerModel;
import com.android.stephen.upoints_customer.model.StoreModel;
import com.android.stephen.upoints_customer.utils.APIHelper;
import com.android.stephen.upoints_customer.utils.FragmentTag;
import com.android.stephen.upoints_customer.utils.Helper;
import com.android.stephen.upoints_customer.utils.Task;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfileFragment.OnProfileFragmentInteractionListener,
        VolleyCallback, UPointsHistoryFragment.OnListUPointsFragmentInteractionListener{

    private CustomerModel customerModel;
    ProfileFragment profileFragment;
    UPointsHistoryFragment uPointsHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            customerModel = (CustomerModel) bundle.getSerializable("model");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initFragments();
    }

    private void initFragments() {
        profileFragment = new ProfileFragment();
        uPointsHistoryFragment = new UPointsHistoryFragment();
        Helper.addFragment(this, profileFragment, R.id.content_main, FragmentTag.PROFILE.toString());
        profileFragment.customerModel = customerModel;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (profileFragment.isVisible()){
                finish();
            } else {
                super.onBackPressed();
                Helper.replaceFragment(this, profileFragment, R.id.content_main, FragmentTag.PROFILE.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Helper.replaceFragment(this, profileFragment, R.id.content_main, FragmentTag.PROFILE.toString());
            profileFragment.customerModel = customerModel;
        } else if (id == R.id.nav_upoints) {
            Helper.replaceFragment(this, uPointsHistoryFragment, R.id.content_main, FragmentTag.UPOINTS.toString());
            uPointsHistoryFragment.custID = customerModel.getCustomerID();
        } else if (id == R.id.nav_load) {

        } else if (id == R.id.nav_money_transfer) {

        } else if (id == R.id.nav_bills_payment) {

        } else if (id == R.id.nav_uask) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onProfileFragmentInteraction(Uri uri) {

    }

    @Override
    public void onResponseReady(Task task, LinkedHashMap<String, String> response) {

    }

    @Override
    public void onResponseReady(Task task, LinkedList<LinkedHashMap<String, String>> response) {
        switch (task) {
            case CUSTOMER_RECEIVED_POINTS:
                parseReceivedPoints(response);
                break;
        }
    }

    private void parseReceivedPoints(LinkedList<LinkedHashMap<String, String>> hashMaps){
        LinkedList<StoreModel> modelList = new LinkedList<>();
        if (Helper.checkResponse(this, hashMaps.get(0)))
            modelList = APIHelper.setUpStoreUPointsHistoryData(hashMaps);

        uPointsHistoryFragment.setUpList(modelList);
    }

    @Override
    public void onListUPointsFragmentInteraction(StoreModel item) {

    }
}
