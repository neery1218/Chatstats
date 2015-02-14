package com.radiance.chatstats;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements ContactPhoneNumberFragment.OnPhoneNumberSelectedListener, StatsFragment.OnToBeDeterminedListener, ContactsFragment.OnContactSelectedListener
{
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ContactsFragment contactsFragment;
    StatsFragment statsFragment;
    ContactPhoneNumberFragment contactPhoneNumberFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsFragment = new ContactsFragment();//initializing first fragment being used

        //initialize transaction and add to viewgroup
         fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,contactsFragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onBackPressed (){
      fragmentManager.popBackStack();
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

    @Override
    public void onContactSelected(ArrayList<String> address){

        contactPhoneNumberFragment = new ContactPhoneNumberFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("address", address);
        contactPhoneNumberFragment.setArguments(args);

        /*statsFragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("address", address);
        statsFragment.setArguments(args);*/

        //swap fragments
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.fragment_container,statsFragment);
        fragmentTransaction.replace(R.id.fragment_container,contactPhoneNumberFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onToBeDetermined(ArrayList<String> id) {

    }

    @Override
    public void onPhoneNumberSelected(String phoneNumber) {
        statsFragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putString("phoneNumber", phoneNumber);
        statsFragment.setArguments(args);

        //swap fragments
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.fragment_container,statsFragment);
        fragmentTransaction.replace(R.id.fragment_container,statsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
