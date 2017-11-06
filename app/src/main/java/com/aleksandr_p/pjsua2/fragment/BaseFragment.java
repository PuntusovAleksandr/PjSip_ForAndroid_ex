package com.aleksandr_p.pjsua2.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.aleksandr_p.pjsua2.activity.ContentView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(getTag(), "onActivityCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getTag(), "onResume");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(getTag(), "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(getTag(), "onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getTag(), "onDestroy");
    }

    public ContentView getContentView() {
        return (ContentView) getActivity();
    }

    public boolean hasContentView() {
        return getActivity() != null;
    }
}
