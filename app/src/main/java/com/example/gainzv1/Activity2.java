package com.example.gainzv1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Workout
{
    String name;
    HashMap<String, Exercise> exercises;
     Workout(String n, HashMap<String, Exercise> map)
    {
        this.name = n;
        exercises = map;
    }
}

class Exercise implements Serializable
{
    String name;
    int sets;
    String repRange;
    Exercise(String n, int s, String r)
    {
        this.name = n;
        this.sets = s;
        this.repRange = r;
    }
}

public class Activity2 extends AppCompatActivity {

     HashMap<String, Workout> workouts;
    ProgressDialog loading;
    Intent intent;
    String url = "https://script.google.com/macros/s/AKfycbyD_g70vSKN7u8lTmzm6TjClyv5oiHSSZreX96Q3tKFuMoaJEI4PyygUb1Upvl1uXjR/exec" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        getItems();


/*
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
        });*/

    }


    public void getExercise(String workout,String exercise)
    {

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
        loading = new ProgressDialog(this);
        loading.show(this,"Sending Request","Please wait");
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

    private void getItems() {

        loading =  ProgressDialog.show(this,"Loading Data","please wait...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+"?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void parseItems(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(response);
            JSONArray jarray = jobj.getJSONArray("items");


            workouts = new HashMap<String, Workout>();
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                String workoutName = jo.getString("w");
                int num_ex = Integer.parseInt(jo.getString("num_ex"));
                HashMap<String, Exercise> exercises = new HashMap<String, Exercise>();
                int j;
                for (j = 1; j <= num_ex; j++) // comes at format ex(workoutNum)_(exNum)
                {
                    String exName = jo.getString("ex" + Integer.toString(j));
                    int exSets = Integer.parseInt(jo.getString("sets" + Integer.toString(j)));
                    String exRepRange = jo.getString("repRange" + Integer.toString(j));
                    Exercise e = new Exercise(exName, exSets, exRepRange);
                    exercises.put(exName, e);
                }

                workouts.put(workoutName, new Workout(workoutName, exercises));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //adapter = new SimpleAdapter(this,list,R.layout.list_item_row,
        //      new String[]{"itemName","brand","price"},new int[]{R.id.tv_item_name,R.id.tv_brand,R.id.tv_price});
        LinearLayout root = findViewById(R.id.linearLayoutWorkouts);
        workouts.forEach((k, v) ->
                {
                    Log.d("WORKOUT---------------", v.name);
                    Button b = new Button(this);
                    b.setText(v.name);
                    b.setHeight(150);
                    int color = getResources().getColor(R.color.orange);
                    b.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(Activity2.this, Train.class);
                            i.putExtra("workout",v.name);
                            i.putExtra("exercises",v.exercises);
                            i.putExtra("url",url);
                            startActivity(i);
                        }
                    });
                    root.addView(b);
                    v.exercises.forEach((kk, vv) -> {
                        Log.d("Exercise", vv.name);
                        Log.d("sets", Integer.toString(vv.sets));
                        Log.d("RepRange", vv.repRange);
                    });
                }
        );




        //listView.setAdapter(adapter);
        loading.dismiss();
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