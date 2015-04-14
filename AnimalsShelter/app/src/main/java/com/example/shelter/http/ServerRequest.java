package com.example.shelter.http;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Ultra on 31.03.2015.
 */
public class ServerRequest {

    public static void sendFormToServer(final Context context, JSONObject objectRequest){
        try {
            String url = "http://10.0.3.2:3333/json/";
            JSONObject jsonObjectForSending = new JSONObject();
            jsonObjectForSending.put("json", /*objectRequest.toString()*/ "HI");
            //Toast.makeText(context, jsonObjectForSending.toString(), Toast.LENGTH_LONG).show();
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObjectForSending, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof ServerError){
                                ServerError serverError = (ServerError) error;
                                Toast.makeText(context, serverError.toString(), Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            RequestQueueSingleton.getInstance(context).addToRequestQueue(jsObjRequest);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

// Access the RequestQueue through your singleton class.

}
