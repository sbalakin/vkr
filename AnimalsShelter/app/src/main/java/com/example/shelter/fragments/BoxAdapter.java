package com.example.shelter.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shelter.MainActivity;
import com.example.shelter.R;

import java.util.ArrayList;

/**
 * Created by Дмитрий on 23.03.2015.
 */
public class BoxAdapter extends BaseAdapter {
    Context ctx;
    MainActivity activity;
    LayoutInflater lInflater;
    ArrayList<Product> objects;
    public Product p;
    public BoxAdapter(Context context, ArrayList<Product> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // используем созданные, но не используемые view
        Holder holder;

        p = getProduct(position);
        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.item, parent, false);
            holder = new Holder();
            holder.nameTextView = (TextView) convertView.findViewById(R.id.tvName);
            holder.breedTextView = (TextView) convertView.findViewById(R.id.tvBreed);
            holder.loveTextView = (TextView) convertView.findViewById(R.id.tvLove);
            holder.dnloveTextView = (TextView) convertView.findViewById(R.id.tvdnLove);
            holder.ageTextView = (TextView) convertView.findViewById(R.id.tvAge);
            holder.numberTextView = (TextView) convertView.findViewById(R.id.tvNumber);
            holder.imageView = ((ImageView) convertView.findViewById(R.id.ivImage));
            holder.imageCallView = ((ImageView) convertView.findViewById(R.id.ivImageCall));

            convertView.setTag(holder);
        }
        else{
            holder = (Holder) convertView.getTag();
        }

        holder.nameTextView.setText("Вид: " + p.name);
        holder.breedTextView.setText("Порода: " + p.breed);
        holder.loveTextView.setText("Любит: " + p.love);
        holder.dnloveTextView.setText("Не любит: " + p.dnlove);
        holder.ageTextView.setText("Возраст: " + p.age);
        holder.numberTextView.setText("Телефон: " + p.numberClient);
        holder.imageView.setImageResource(p.image);
        holder.imageCallView.setImageResource(p.imageCall);

        holder.imageCallView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка


        //((TextView) view.findViewById(R.id.tvBreed)).setText("Порода: " + p.breed);
        //((TextView) view.findViewById(R.id.tvLove)).setText("Любит: " + p.love);
        //((TextView) view.findViewById(R.id.tvdnLove)).setText("Не любит: " + p.dnlove);
        //((TextView) view.findViewById(R.id.tvAge)).setText("Возраст: " + p.age);
       //((TextView) view.findViewById(R.id.tvNumber)).setText("Телефон: " + p.numberClient);
        //((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);
        //((ImageView) view.findViewById(R.id.ivImageCall)).setImageResource(p.imageCall);


        return convertView;

    }

    private static class Holder{
        public TextView nameTextView;
        public TextView breedTextView;
        public TextView loveTextView;
        public TextView dnloveTextView;
        public TextView ageTextView;
        public TextView numberTextView;
        public ImageView imageView;
        public ImageView imageCallView;

    }
    // товар по позиции
    Product getProduct(int position) {
        return ((Product) getItem(position));
    }
    public void call(){
        String url = p.numberClient;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
        ctx.startActivity(intent);
    }
}
