package com.radiance.chatstats;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements StatsFragment.OnFragmentInteractionListener, ContactsFragment.OnFragmentInteractionListener {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ContactsFragment contactsFragment;
    StatsFragment statsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsFragment = new ContactsFragment();//initializing first fragment being used

        //initialize transaction and add to viewgroup
        fragmentManager = getFragmentManager();
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

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String address) {//only one case: number has been selected
        //create new statsFragment and a Bundle with the address parameter
        statsFragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putString("address", address);
        statsFragment.setArguments(args);

        //swap fragments
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, statsFragment);
        fragmentTransaction.addToBackStack(null);//so the back button works
        fragmentTransaction.commit();

    }
}
