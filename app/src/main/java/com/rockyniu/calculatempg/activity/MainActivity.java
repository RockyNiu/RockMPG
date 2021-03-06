package com.rockyniu.calculatempg.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.rockyniu.calculatempg.R;
import com.rockyniu.calculatempg.fragment.CalculatorFragment;
import com.rockyniu.calculatempg.fragment.RecordFragment;
import com.rockyniu.calculatempg.fragment.WebViewFragment;
import com.rockyniu.calculatempg.listener.MainTabListener;
import com.rockyniu.calculatempg.listener.OnFragmentInteractionListener;

import static com.rockyniu.calculatempg.util.DialogHelper.showNeedClickDialog;


public class MainActivity extends BaseActivity implements OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";
    String carId = "";
    String homeId = "";
    ActionBar.Tab calculatorTab;
    ActionBar.Tab recordTab;
    ActionBar.Tab webViewTab;
    Fragment calculatorFragment = CalculatorFragment.newInstance(carId, homeId);
    Fragment recordFragment = RecordFragment.newInstance(carId, homeId);
    Fragment webViewFragment = WebViewFragment.newInstance(carId, homeId);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
//        requestWindowFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

        // Asking for the default ActionBar element that our platform supports.
        final ActionBar actionBar = getSupportActionBar();

        // Screen handling while hiding ActionBar icon.
//        actionBar.setDisplayShowHomeEnabled(false);

        // Screen handling while hiding Actionbar title.
//        actionBar.setDisplayShowTitleEnabled(false);

        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Setting custom tab icons.
        calculatorTab = actionBar.newTab().setText(R.string.tab_calculator_title);
        recordTab = actionBar.newTab().setText(R.string.tab_record_title);
        webViewTab = actionBar.newTab().setText(R.string.tab_webView_title);

        // Setting tab listeners.
        calculatorTab.setTabListener(new MainTabListener(calculatorFragment));
        recordTab.setTabListener(new MainTabListener(recordFragment));
        webViewTab.setTabListener(new MainTabListener(webViewFragment));

        // Adding tabs to the ActionBar.
        actionBar.addTab(calculatorTab);
        actionBar.addTab(recordTab);
        actionBar.addTab(webViewTab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the com.rockyniu.mpgcalculator.model.Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit " + getResources().getString(R.string.app_name) + "?")
                .setMessage(null)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
//    }
}
