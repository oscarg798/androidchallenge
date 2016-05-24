package com.knomatic.weather.presentation.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.knomatic.weather.R;
import com.knomatic.weather.model.Callbacks;

import java.io.Serializable;

public class SplashFragment extends Fragment
        implements Callbacks.IFragmentUpdate {
    private TextView tvTitle;

    private LinearLayout splashFragmentMainLayout;

    private ImageView ivIcon;

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
        View v = inflater.inflate(R.layout.fragment_splash, container, false);
        initViewComponents(v);
        return v;
    }

    private void initViewComponents(View view) {
        ivIcon = (ImageView) view.findViewById(R.id.imageView);
        splashFragmentMainLayout = (LinearLayout) view.findViewById(R.id.splash_fragment_main_layout);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(getActivity()
                .getApplicationContext().getString(R.string.getting_forecast_message));
        ImageViewAnimatedChange();
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
        if (serializable instanceof String && tvTitle != null) {
            tvTitle.setText((String) serializable);
        }
    }

    public void ImageViewAnimatedChange() {
        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(getActivity().getResources().getDrawable(R.drawable.ic_sun_white), 500);
        animation.addFrame(getActivity().getResources().getDrawable(R.drawable.ic_cloud_white), 500);
        animation.addFrame(getActivity().getResources().getDrawable(R.drawable.ic_cloud_drizzle_alt_white), 500);
        animation.setOneShot(false);
        animation.setEnterFadeDuration(100);
        animation.setExitFadeDuration(100);
        ivIcon.setBackground(animation);
        animation.start();
    }


}
