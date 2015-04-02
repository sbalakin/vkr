package com.example.interface3.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.interface3.R;

/**
 * Created by Дмитрий on 17.03.2015.
 */
public class ScreenFour extends Fragment {

    public ScreenFour() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.screenfour, container,
                false);

        return rootView;
    }

}