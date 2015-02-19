package com.radiance.chatstats;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements ContactPhoneNumberFragment.OnPhoneNumberSelectedListener, ContactsFragment.OnContactSelectedListener, LoadingFragment.OnFragmentInteractionListener {
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
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, contactsFragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
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
    public void onContactSelected(Contact contact) {

        contactPhoneNumberFragment = new ContactPhoneNumberFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("phoneNumber", contact.getAddress());
        args.putString("name", contact.getName());
        args.putInt("id", contact.getID());

        contactPhoneNumberFragment.setArguments(args);

        //swap fragments
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, contactPhoneNumberFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onLoadingFinished() {

    }

    @Override
    public void onPhoneNumberSelected(Contact contact) {
        statsFragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("phoneNumber", contact.getAddress());
        args.putString("name", contact.getName());
        args.putInt("id", contact.getID());
        statsFragment.setArguments(args);

        //swap fragments
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.fragment_container,statsFragment);
        fragmentTransaction.replace(R.id.fragment_container, statsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}