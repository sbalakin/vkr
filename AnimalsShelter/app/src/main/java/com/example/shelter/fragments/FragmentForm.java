package com.example.shelter.fragments;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.shelter.R;
import com.example.shelter.http.ServerRequest;
import com.google.android.gms.maps.MapFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Дмитрий on 17.03.2015.
 */
public class FragmentForm extends Fragment {

    private final String NAME_JSON_REQUEST_KEY = "name";
    private final String BREED_JSON_REQUEST_KEY = "breed";
    private final String LOVE_JSON_REQUEST_KEY = "love";
    private final String DNLOVE_JSON_REQUEST_KEY = "dnlove";
    private final String AGE_JSON_REQUEST_KEY = "age";
    private final String NOTE_JSON_REQUEST_KEY = "note";
    private final String RECOMMENDATION_JSON_REQUEST_KEY = "recommendation";
    private final String VACCINATION_JSON_REQUEST_KEY = "vaccination";
    private final String HEALTH_JSON_REQUEST_KEY = "health";
    private final String STERILIZED_JSON_REQUEST_KEY = "sterilized";

    private Spinner spinner;
    private Button mapButton;
    private Button sendButton;
    private ImageButton photoButton;

    private EditText nameEditText;
    private EditText breedEditText;
    private EditText loveEditText;
    private EditText dnloveEditText;
    private EditText ageEditText;
    private EditText numberEditText;
    private EditText noteEditText;
    private EditText recommendationsEditText;
    private EditText vaccinationEditText;
    private EditText healthEditText;
    private CheckBox sterilizedCheckBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_form, container,
                false);
        spinner = (Spinner) rootView.findViewById(R.id.fragment_form_spinner_animal_type);
        mapButton  = (Button) rootView.findViewById(R.id.button3);
        sendButton  = (Button) rootView.findViewById(R.id.button_send);
        photoButton  = (ImageButton) rootView.findViewById(R.id.imageButton2);
        nameEditText = (EditText) rootView.findViewById(R.id.fragment_form_edit_text_name);
        breedEditText = (EditText) rootView.findViewById(R.id.fragment_form_edit_text_breed);
        loveEditText = (EditText) rootView.findViewById(R.id.fragment_form_edit_text_animal_like);
        dnloveEditText = (EditText) rootView.findViewById(R.id.fragment_form_edit_text_animal_dont_like);
        ageEditText = (EditText) rootView.findViewById(R.id.fragment_form_edit_text_age);
        noteEditText = (EditText) rootView.findViewById(R.id.fragment_form_edit_text_note);
        recommendationsEditText = (EditText) rootView.findViewById(R.id.fragment_form_edit_text_recommendations);
        vaccinationEditText = (EditText) rootView.findViewById(R.id.fragment_form_edit_text_vaccination);
        healthEditText = (EditText) rootView.findViewById(R.id.fragment_form_edit_text_health);
        sterilizedCheckBox = (CheckBox) rootView.findViewById(R.id.fragment_form_check_box_steriasable);

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

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(NAME_JSON_REQUEST_KEY, nameEditText.getText());
                    jsonObject.put(BREED_JSON_REQUEST_KEY, breedEditText.getText());
                    jsonObject.put(LOVE_JSON_REQUEST_KEY, loveEditText.getText());
                    jsonObject.put(DNLOVE_JSON_REQUEST_KEY, dnloveEditText.getText());
                    jsonObject.put(AGE_JSON_REQUEST_KEY, ageEditText.getText());
                    jsonObject.put(NOTE_JSON_REQUEST_KEY,noteEditText.getText());
                    jsonObject.put(RECOMMENDATION_JSON_REQUEST_KEY,recommendationsEditText.getText());
                    jsonObject.put(VACCINATION_JSON_REQUEST_KEY,vaccinationEditText.getText());
                    jsonObject.put(HEALTH_JSON_REQUEST_KEY,healthEditText.getText());
                    jsonObject.put(STERILIZED_JSON_REQUEST_KEY,sterilizedCheckBox.isChecked() ? 1 : 0);
                    ServerRequest.sendFormToServer(view.getContext(), jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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