package com.knomatic.weather.presentation.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knomatic.weather.R;
import com.knomatic.weather.model.Callbacks;

import java.io.Serializable;

public class SplashFragment extends Fragment
        implements Callbacks.IFragmentUpdate {
    private TextView tvTitle;

    public SplashFragment() {
        // Required empty public constructor
    }


    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_splash, container, false);
        initViewComponents(v);
        return v;
    }

    private void initViewComponents(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void receiveUpdated(Serializable serializable) {
        if (serializable instanceof String && tvTitle!=null) {
                tvTitle.setText((String) serializable);
        }
    }
}
