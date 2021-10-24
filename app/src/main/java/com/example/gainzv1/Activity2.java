package com.example.gainzv1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity2 extends AppCompatActivity {

    Intent intent;
    ArrayList<ImageButton> bottomnavbar;
    String url = "https://script.google.com/macros/s/AKfycbwpSkjhhPsgziYD2GdZ5eHDp3ZJ7E2z9M1szZixJWq4bPUfMVH5TuMi357Z_q7GgLsf/exec";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        findViewById(R.id.createXML).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFieldsXML("test");
            }
        });
    }

    public void addFieldsXML(String filename)
    {
        Map<String, String> a = new HashMap<>();
        a.put("action","addFieldsXML");
        a.put("fileName",filename);
        a.put("length","5");
        a.put("1","ex1");
        a.put("2","3");
        a.put("3","8");
        a.put("4","12");
        a.put("5","...");
        SendRequest(a);
    }

    public void createXML(String filename)
    {
        Map<String, String> a = new HashMap<>();
        a.put("action","createXML");
        a.put("fileName",filename);
        SendRequest(a);
    }

    private void SendRequest(Map<String, String> params)
    {
        final ProgressDialog loading = ProgressDialog.show(this,"Sending Request","Please wait");
        final String name = "newXML";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(Activity2.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),Activity2.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }

    public void bottomNavbarClick(View view) {
        switch (view.getId())
        {

            case R.id.bottom_toolbar_btn1:
                intent = new Intent(Activity2.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_toolbar_btn2:

                break;
            case R.id.bottom_toolbar_btn3:
                intent = new Intent(Activity2.this, Activity3.class);
                startActivity(intent);
                break;
            default:
        }

    }

}