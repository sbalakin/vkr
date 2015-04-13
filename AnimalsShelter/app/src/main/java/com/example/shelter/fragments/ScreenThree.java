package com.example.shelter.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shelter.R;

/**
 * Created by Дмитрий on 17.03.2015.
 */
public class ScreenThree extends Fragment {

    public ScreenThree() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.screenthree, container,
                false);

        return rootView;
    }

}