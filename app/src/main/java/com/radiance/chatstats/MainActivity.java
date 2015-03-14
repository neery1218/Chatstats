package com.radiance.chatstats;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements StatsFragment.OnToBeDeterminedListener, ContactPhoneNumberDialog.OnPhoneNumberSelectedListener, ContactsFragment.OnContactSelectedListener, LoadingFragment.OnFragmentInteractionListener, LoadingFragment.OnErrorListener {

    public static final String ARG_BIG_THREE = "bigThree";
    public static final String ARG_ADDRESS = "phoneNumber";
    public static final String ARG_NAME = "name";
    public static final String ARG_ID = "id";
    static Typeface typeFaceRegular, typeFaceLight, typeFaceBold, oswaldBold, oswaldLight, oswaldRegular;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ContactsFragment contactsFragment;
    StatsFragment statsFragment;
    LoadingFragment loadingFragment;
    DialogFragment contactPhoneNumberDialog;

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

        typeFaceRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        typeFaceLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        typeFaceBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
        oswaldBold = Typeface.createFromAsset(getAssets(), "Oswald-Bold.ttf");
        oswaldLight = Typeface.createFromAsset(getAssets(), "Oswald-Light.ttf");
        oswaldRegular = Typeface.createFromAsset(getAssets(), "Oswald-Regular.ttf");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (f instanceof ContactsFragment);
        else {
            finish();
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

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
        fragmentManager = getSupportFragmentManager();

        if (contact.getAddress().size() > 1) {
            contactPhoneNumberDialog = new ContactPhoneNumberDialog();
            Bundle args = new Bundle();
            args.putStringArrayList("phoneNumber", contact.getAddress());
            args.putString("name", contact.getName());
            args.putInt("id", contact.getID());
            contactPhoneNumberDialog.setArguments(args);
            contactPhoneNumberDialog.show(fragmentManager, "TAG");
        } else {
            onPhoneNumberSelected(contact, 0);
        }
    }

    @Override
    public void onLoadingFinished(Analytics analytics, Contact contact) {
        //bigThree is ouputted here
        statsFragment = new StatsFragment();
        Bundle args = new Bundle();
        ArrayList<String> input = new ArrayList<String>();
        args.putString(ARG_NAME, contact.getName());
        statsFragment.setArguments(args);
        // for (int i = 0; i < bigThree.size(); i++)
        //     input.add(bigThree.toString());

        // args.putStringArrayList(ARG_BIG_THREE, input);
        //args.putString("responseTime", bigThree.get(2).toString());
        statsFragment.setAnalytics(analytics);
        // statsFragment.setArguments(args);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, statsFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onPhoneNumberSelected(Contact contact, int which) {//calls Loading fragment
        //statsFragment = new StatsFragment();
        loadingFragment = new LoadingFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("phoneNumber", contact.getAddress());
        args.putString("name", contact.getName());
        args.putInt("id", contact.getID());
        args.putInt("phoneNumIndex", which);

        loadingFragment.setArguments(args);
        //statsFragment.setArguments(args);

        //swap fragments
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, loadingFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onError() {
        Log.d("TAG", "ERROR");
        ErrorDialog errorDialog = new ErrorDialog();
        errorDialog.show(fragmentManager, "TAG");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, contactsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onToBeDetermined(ArrayList<String> id) {

    }
}