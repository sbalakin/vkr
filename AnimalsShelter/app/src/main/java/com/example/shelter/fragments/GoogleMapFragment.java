package com.example.shelter.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shelter.R;

/**
 * Created by Ultra on 23.03.2015.
 */
public class GoogleMapFragment extends Fragment{

    View mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lenta, container, false);
        mapFragment = rootView.findViewById(R.id.map);
        return rootView;
    }
}
