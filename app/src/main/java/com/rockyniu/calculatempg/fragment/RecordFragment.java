package com.rockyniu.calculatempg.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rockyniu.calculatempg.R;
import com.rockyniu.calculatempg.adapter.RecordListAdapter;
import com.rockyniu.calculatempg.database.RecordDataSource;
import com.rockyniu.calculatempg.listener.OnFragmentInteractionListener;
import com.rockyniu.calculatempg.listener.SwipeDismissListViewTouchListener;
import com.rockyniu.calculatempg.model.Record;
import com.rockyniu.calculatempg.util.ToastHelper;

import java.util.Calendar;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class RecordFragment extends BaseFragment implements AbsListView.OnItemClickListener {

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

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private RecordListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static RecordFragment newInstance(String param1, String param2) {
        RecordFragment fragment = new RecordFragment();
        Bundle args = new Bundle();
        args.putString(CAR_ID, param1);
        args.putString(HOME_ID, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecordFragment() {
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

        // Adapter to display content
        mAdapter = new RecordListAdapter(getActivity(), records);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_record, container, false);

        // Set the adapter
        mListView = (AbsListView) rootView.findViewById(R.id.list_records);
        mListView.setAdapter(mAdapter);

//        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        // swipe to delete tasks
        SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
                (ListView) mListView,
                new SwipeDismissListViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(ListView listView,
                                          int[] reverseSortedPositions) {

                        // Delete all dismissed tasks
                        for (int position : reverseSortedPositions) {
                            RecordListAdapter tasksAdapter = (RecordListAdapter) mListView
                                    .getAdapter();
                            Record currentItem = tasksAdapter
                                    .getItem(position);
                            // label delete
                            currentItem.setModifiedTime(Calendar.getInstance()
                                    .getTimeInMillis());
                            recordDataSource
                                    .labelItemDeletedWithModifiedTime(currentItem);
                        }
                        ToastHelper.showToastInternal(
                                getActivity(),
                                "Task deleted.");
                        refreshView();
                    }
                });
        mListView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during
        // ListView scrolling,
        // we don't look for swipes.
        mListView.setOnScrollListener(touchListener.makeScrollListener());

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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
            mListener.onFragmentInteraction(records.get(position).getId());
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    private void refreshView() {
        records = recordDataSource.getList();
        mAdapter.updateList(records);
        mAdapter.notifyDataSetChanged();
    }
}
