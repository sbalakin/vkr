package com.example.interface3;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.interface3.fragments.ScreenFour;
import com.example.interface3.fragments.FragmentLenta;
import com.example.interface3.fragments.ScreenThree;
import com.example.interface3.fragments.FragmentForm;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    private String[] mScreenTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    public FrameLayout frameLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private EditText nameEditText;
    private EditText breedEditText;
    private EditText loveEditText;
    private EditText dnloveEditText;
    private EditText ageEditText;
    private EditText numberEditText;
    private EditText noteEditText;
    private EditText recommendationsEditText;
    private EditText vaccinationEditText;
    private EditText healthEditText;
    private CheckBox steriasable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        mScreenTitles = getResources().getStringArray(R.array.screen_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mScreenTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.mipmap.ic_drawer, /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Initialize the first fragment when the application first loads.
        if (savedInstanceState == null) {
            selectItem(0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_search:
                // Show toast about click.
                Toast.makeText(this, R.string.action_search, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentLenta();
                break;
            case 1:
                fragment = new FragmentForm();
                break;
            case 2:
                fragment = new ScreenThree();
                break;
            case 3:
                fragment = new ScreenFour();
                break;
            default:
                break;
        }

        // Insert the fragment by replacing any existing fragment
        if (fragment != null) {
            android.app.FragmentManager fragmentManager = getFragmentManager();

            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(mScreenTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(frameLayout.getId(), fragment, "");
            transaction.commit();
        } else {
            // Error
            Log.e(this.getClass().getName(), "Error. Fragment is not created");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    public void OnClickSendJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        nameEditText = (EditText) findViewById(R.id.fragment_form_edit_text_name);
        breedEditText = (EditText) findViewById(R.id.fragment_form_edit_text_breed);
        loveEditText = (EditText) findViewById(R.id.fragment_form_edit_text_animal_like);
        dnloveEditText = (EditText) findViewById(R.id.fragment_form_edit_text_animal_dont_like);
        ageEditText = (EditText) findViewById(R.id.fragment_form_edit_text_age);
        noteEditText = (EditText) findViewById(R.id.fragment_form_edit_text_note);
        recommendationsEditText = (EditText) findViewById(R.id.fragment_form_edit_text_recommendations);
        vaccinationEditText = (EditText) findViewById(R.id.fragment_form_edit_text_vaccination);
        healthEditText = (EditText) findViewById(R.id.fragment_form_edit_text_health);
        steriasable = (CheckBox) findViewById(R.id.fragment_form_text_view_steriasable);

        jsonObject.put("name",nameEditText.getText());
        jsonObject.put("breed",breedEditText.getText());
        jsonObject.put("love",loveEditText.getText());
        jsonObject.put("dnlove",dnloveEditText.getText());
        jsonObject.put("age",ageEditText.getText());
        jsonObject.put("note",noteEditText.getText());
        jsonObject.put("reccomendation",recommendationsEditText.getText());
        jsonObject.put("vaccination",vaccinationEditText.getText());
        jsonObject.put("health",healthEditText.getText());
        jsonObject.put("steriasable",steriasable.isChecked() ? 1 : 0);
    }

}