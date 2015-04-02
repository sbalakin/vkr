package com.example.interface3.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.interface3.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Дмитрий on 17.03.2015.
 */
public class FragmentLenta extends Fragment {

    private Spinner spinnerType;
    private Spinner spinnerGender;
    private Spinner spinnerSterilise;
    ArrayList<Product> products = new ArrayList<Product>();
    //ImageView imageView;
    BoxAdapter boxAdapter;
    Random random = new Random();
    ListView lvMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lenta, container,
                false);

        spinnerType = (Spinner) rootView.findViewById(R.id.fragment_list_spinner_animal_type);
        spinnerGender = (Spinner) rootView.findViewById(R.id.fragment_list_spinner_animal_gender);
        spinnerSterilise = (Spinner) rootView.findViewById(R.id.fragment_list_spinner_animal_sterialise);
        /*imageView = (ImageView) rootView.findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });*/

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,new String[]{"Все","Кошки", "Собаки", "Прочие"});
        spinnerType.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,new String[]{"Все","Мальчик", "Девочка"});
        spinnerGender.setAdapter(arrayAdapter1);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,new String[]{"Все","Стериализован", "Нестериализован"});
        spinnerSterilise.setAdapter(arrayAdapter2);


        // создаем адаптер
        fillData();
        boxAdapter = new BoxAdapter(getActivity(), products);
        lvMain = (ListView) rootView.findViewById(R.id.lvMain);
        lvMain.setAdapter(boxAdapter);

        return rootView;
    }

    private void call(){
        String url = "tel:3334444";
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
    startActivity(intent);
}
    // генерируем данные для адаптера
    void fillData() {
        for (int i = 1; i <= 5; i++) {
            int variant = random.nextInt(2);
            switch (variant){
                case 0: products.add(new Product("Кошка","Персидская","Спать","Не спать","1 год","89213312123",R.mipmap.cat,R.mipmap.ic_action_call));
                    break;
                case 1: products.add(new Product("Собака","Акита","Спать","Не спать","2 год","89213312123",R.mipmap.akita,R.mipmap.ic_action_call));
                    break;
            }
        }
    }
}