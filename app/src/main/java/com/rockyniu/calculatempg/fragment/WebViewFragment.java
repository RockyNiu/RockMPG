package com.rockyniu.calculatempg.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.rockyniu.calculatempg.R;
import com.rockyniu.calculatempg.database.RecordDataSource;
import com.rockyniu.calculatempg.listener.OnFragmentInteractionListener;
import com.rockyniu.calculatempg.model.Record;
import com.rockyniu.calculatempg.webapp.WebAppInterface;

import java.util.List;

public class WebViewFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CAR_ID = "carId";
    private static final String HOME_ID = "homeId";

    // TODO: Rename and change types of parameters
    private String carId;
    private String homeId;

    private RecordDataSource recordDataSource;
    private List<Record> records;

    private OnFragmentInteractionListener mListener;

    private WebView mWebView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WebViewFragment() {
    }

    // TODO: Rename and change types of parameters
    public static WebViewFragment newInstance(String param1, String param2) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(CAR_ID, param1);
        args.putString(HOME_ID, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            carId = getArguments().getString(CAR_ID);
            homeId = getArguments().getString(HOME_ID);
        }

        recordDataSource = new RecordDataSource(getActivity());
        records = recordDataSource.getList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        mWebView = (WebView) rootView.findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        mWebView.addJavascriptInterface(new WebAppInterface(this.getActivity()), "Android");

        RecordDataLoader dl = new RecordDataLoader(recordDataSource);

// For passing the data
        mWebView.addJavascriptInterface(dl, "accessor");
        mWebView.loadUrl("file:///android_asset/views/home.html");

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}

class RecordDataLoader {
    private RecordDataSource recordDataSource;

    public RecordDataLoader(RecordDataSource recordDataSource) {
        this.recordDataSource = recordDataSource;
    }

    @JavascriptInterface
    public String getRecords() {
        List<Record> result = recordDataSource.getList();
        Gson gson = new Gson();
        String data = gson.toJson(result);
        return data;
    }
}
