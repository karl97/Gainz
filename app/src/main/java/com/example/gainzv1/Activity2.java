package com.example.gainzv1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    String url = "https://script.google.com/macros/s/AKfycbzX21wPLafJwfkD04pMrGvDvw8ts3Er-KGm5CN3Rx372MkjWvVvdbbzvxjxtU-Cr84Z/exec" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        findViewById(R.id.createXML).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createWorkoutXML("PULL");
            }
        });
        findViewById(R.id.fillXML).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExerciseXML("PULL","triceps",3,12,15);
            }
        });
    }

    public void addExerciseXML(String Workout,String exerciseName,int sets,int repMin, int repMax)
    {
        Map<String, String> a = new HashMap<>();
        a.put("action","addExerciseXML");
        a.put("workout",Workout);
        a.put("exerciseName",exerciseName);
        a.put("sets",Integer.toString(sets));
        a.put("repMin",Integer.toString(repMin));
        a.put("repMax",Integer.toString(repMax));
        SendRequest(a);
    }

    public void createWorkoutXML(String WorkoutName)
    {
        Map<String, String> a = new HashMap<>();
        a.put("action","createWorkoutXML");
        a.put("workoutName",WorkoutName);
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