package com.example.interface3.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.interface3.R;
import com.google.android.gms.maps.MapFragment;

/**
 * Created by Дмитрий on 17.03.2015.
 */
public class FragmentForm extends Fragment {

    private Spinner spinner;
    private Button mapButton;
    private ImageButton photoButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_form, container,
                false);
        spinner = (Spinner) rootView.findViewById(R.id.fragment_form_spinner_animal_type);
        mapButton  = (Button) rootView.findViewById(R.id.button3);
        photoButton  = (ImageButton) rootView.findViewById(R.id.imageButton2);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo();
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runMap();
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,new String[]{"Кошки", "Собаки", "Прочие"});
        spinner.setAdapter(arrayAdapter);

        return rootView;

    }


    private void photo(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 2);
        }
    }

    private void runMap(){
        MapFragment  mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();
    }
}