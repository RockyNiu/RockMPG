package com.rockyniu.calculatempg.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rockyniu.calculatempg.R;
import com.rockyniu.calculatempg.calculator.Calculator;
import com.rockyniu.calculatempg.database.RecordDataSource;
import com.rockyniu.calculatempg.listener.OnFragmentInteractionListener;
import com.rockyniu.calculatempg.model.Record;
import com.rockyniu.calculatempg.util.StringHelper;

import java.util.Calendar;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends BaseFragment {
    private static final String TAG = "CalculatorFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CAR_ID = "carId";
    private static final String HOME_ID = "homeId";

    // TODO: Rename and change types of parameters
    private String carId;
    private String homeId;

    private float miles;
    private float gas;
    private float mpg;

    private static EditText milesEditText;
    private static EditText gasEditText;
    private static Button mpgButton;
    private static TextView mpgTextView;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
        Bundle args = new Bundle();
        args.putString(CAR_ID, param1);
        args.putString(HOME_ID, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            carId = getArguments().getString(CAR_ID);
            homeId = getArguments().getString(HOME_ID);
        }

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        miles = sharedPreferences.getFloat(getString(R.string.miles_title), 0);
        gas = sharedPreferences.getFloat(getString(R.string.gas_title), 0);
        mpg = sharedPreferences.getFloat(getString(R.string.mpg_title), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calculator, container, false);

        milesEditText = (EditText) rootView.findViewById(R.id.editText_miles);
        gasEditText = (EditText) rootView.findViewById(R.id.editText_gas);
        mpgButton = (Button) rootView.findViewById(R.id.btn_mpg);
        mpgTextView = (TextView) rootView.findViewById(R.id.textView_mpg);

        if (miles != 0) {
            milesEditText.setText(StringHelper.formatFloat(miles));
        }
        if (gas != 0) {
            gasEditText.setText(StringHelper.formatFloat(gas));
        }
        try {
            milesEditText.addTextChangedListener(new TextChangedListener(miles));
            gasEditText.addTextChangedListener(new TextChangedListener(gas));
            mpgButton.setOnClickListener(new MPGButtonOnClickListener());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, StringHelper.formatFloatWithLabel("miles", miles) + StringHelper.formatFloatWithLabel("gas", gas) + StringHelper.formatFloatWithLabel("mpg", mpg));
            //TODO
        }
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(id);
        }
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
    public void onPause() {
        super.onPause();
        miles = getNewValueFromEditText(miles, milesEditText, getString(R.string.miles_title));
        gas = getNewValueFromEditText(gas, gasEditText, getString(R.string.gas_title));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class MPGButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            try {
                float newMiles = Float.parseFloat(milesEditText.getText().toString());
                float newGas = Float.parseFloat(gasEditText.getText().toString());
                float newMpg = Calculator.getQuotient(newMiles, newGas);
                mpgTextView.setText(StringHelper.formatFloat(newMpg));

                if ((miles != newMiles || gas != newGas || mpg != newMpg) && Calculator.isInRange(newMiles) && Calculator.isInRange(newGas) && Calculator.isInRange(newMpg)) {
                    SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putFloat(getString(R.string.miles_title), newMiles);
                    editor.putFloat(getString(R.string.gas_title), newGas);
                    editor.putFloat(getString(R.string.mpg_title), newMpg);
                    editor.commit();

                    Record record = new Record();
                    record.setId(UUID.randomUUID().toString());
                    record.setMiles(newMiles);
                    record.setGas(newGas);
                    long modifiedTime = Calendar.getInstance().getTimeInMillis();
                    record.setModifiedTime(modifiedTime);
                    record.setRecordMilesTime(modifiedTime);
                    RecordDataSource recordDataSource = new RecordDataSource(getActivity());
                    recordDataSource.insertItemWithId(record);
                    miles = newMiles;
                    gas = newGas;
                    mpg = newMpg;
                }
            } catch (IllegalArgumentException e) {

            }
        }
    }

    private class TextChangedListener implements TextWatcher {

        float value;

        TextChangedListener(float value) {
            this.value = value;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0)
                return;
            try {
                float value = Float.parseFloat(s.toString());
                if (!Calculator.isInRange(value)) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                if (this.value != 0) {
                    s.replace(0, s.length(), StringHelper.formatFloat(value));
                }
            }
        }
    }

    private float getNewValueFromEditText(float value, EditText editText, String label) {
        float newValue;
        try {
            String valueString = editText.getText().toString();
            newValue = valueString.length() == 0 ? 0 : Float.parseFloat(valueString);
            if (newValue != value && (Calculator.isInRange(newValue) || newValue == 0)) {
                SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat(label, newValue);
                editor.commit();
            }
        } catch (IllegalArgumentException e) {
            newValue = value;
        }
        return newValue;
    }
}
