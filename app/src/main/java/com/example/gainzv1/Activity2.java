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
    String url = "https://script.google.com/macros/s/AKfycbzGmcYId368Sz9HR3bD-OT2AOfvCAgTSwYNeuLV7_ldR_8IfVuLrHp4mKI95DzwtbTp/exec" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        findViewById(R.id.createWorkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createWorkoutXML("PULL");
            }
        });

        findViewById(R.id.createExercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExerciseXML("PULL","triceps",3,"12-15");
            }
        });

        findViewById(R.id.createRow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] e = new int[3];
                e[0]=12;
                e[1]=11;
                e[2]=10;
                createNewRow("PULL","triceps",200,e,"COOOL");
            }
        });


        findViewById(R.id.deleteRow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteLastRow("PULL","triceps");
            }
        });

    }


    public void deleteLastRow(String Workout, String exercise)
    {
        Map<String, String> a = new HashMap<>();
        a.put("action","deleteLastRow");
        a.put("workout",Workout);
        a.put("exercise",exercise);
        SendRequest(a);
    }

    public void createNewRow(String Workout, String exercise, int weight, int[] reps, String note)
    {
        Map<String, String> a = new HashMap<>();
        a.put("action","createNewRow");
        a.put("workout",Workout);
        a.put("exercise",exercise);
        a.put("weight",Integer.toString(weight));
        a.put("sets",Integer.toString(reps.length));
        for(int i = 1; i<=reps.length; i=i+1)
        {
            a.put(Integer.toString(i), Integer.toString(reps[i - 1]));
        }
        a.put("note",note);
        SendRequest(a);
    }

    public void addExerciseXML(String Workout,String exerciseName,int sets,String reps)
    {
        Map<String, String> a = new HashMap<>();
        a.put("action","addExerciseXML");
        a.put("workout",Workout);
        a.put("exerciseName",exerciseName);
        a.put("sets",Integer.toString(sets));
        a.put("reps",reps);
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