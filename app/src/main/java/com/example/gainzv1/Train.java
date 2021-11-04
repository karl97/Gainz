package com.example.gainzv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Size;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Space;
import android.widget.TableRow;
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

import java.util.HashMap;
import java.util.Map;

class WorkoutIds
{
    View weight;
    View[] reps;
    View note;
    public WorkoutIds(View w, View[] r,View n)
    {
        this.weight=w;
        this.reps=r;
        this.note=n;
    }
}
class WorkoutData
{
    int weight;
    int[] reps;
    String note;
    public WorkoutData(int w, int[] r,String n)
    {
        this.weight=w;
        this.reps=r;
        this.note=n;
    }
}

public class Train extends AppCompatActivity {

    static String [] repValues= new String [21];;
    static
    {
        for(int i= 0;i<21;i++)
        {
        repValues[i] = Integer.toString(i);
        }
    }

    String url;
    HashMap<String,WorkoutData> data;
    HashMap<String,WorkoutIds> Ids;
    String workout;
    ProgressDialog loading;
    boolean firstReq=true;
    int numEx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);


        Intent intent = getIntent();
        workout = intent.getStringExtra("workout");
        url = intent.getStringExtra("url");
        TextView t = findViewById(R.id.top_toolbar_text);
        t.setText(workout);
        HashMap<String, Exercise> exercises = (HashMap<String, Exercise>)intent.getSerializableExtra("exercises");
        Ids = new HashMap<String,WorkoutIds>();
        numEx=exercises.size();
        LinearLayout root = findViewById(R.id.linearLayoutExercises);
        int counter = 1;
        exercises.forEach((k, v) -> {
            LinearLayout row1 = new LinearLayout(this);
            row1.setOrientation(LinearLayout.HORIZONTAL);
            row1.setBackgroundColor(getResources().getColor(R.color.orange));


            TextView ex = new TextView(this);
            ex.setText(" "+v.name);
            ex.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
            TextView repRange = new TextView(this);
            repRange.setText("   "+Integer.toString(v.sets)+"X"+v.repRange);
            row1.addView(ex);
            row1.addView(repRange);
            root.addView(row1);


            LinearLayout row2 = new LinearLayout(this);
            row2.setOrientation(LinearLayout.HORIZONTAL);
            row2.setGravity(Gravity.CENTER_VERTICAL);

            EditText weight = new EditText(this);
            weight.setInputType(InputType.TYPE_CLASS_NUMBER);
            weight.setHint("Weight");
            weight.setEms(3);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30,0,30,0);
            weight.setLayoutParams(lp);
            row2.addView(weight);

            View[] pickers = new View[v.sets];
            for(int i =0;i<v.sets;i++)
            {
                NumberPicker picker = new NumberPicker(this);
                picker.setMinValue(0);
                picker.setMaxValue(repValues.length-1);
                picker.setDisplayedValues(repValues);
                picker.setScaleY(0.75F);
                picker.setScaleX(0.75F);
                LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                l.setMargins(-10,-10,-10,-10);
                picker.setLayoutParams(l);
                row2.addView(picker);

                pickers[i]=picker;
            }
            LinearLayout row3 = new LinearLayout(this);
            row3.setOrientation(LinearLayout.HORIZONTAL);
            EditText note = new EditText(this);
            note.setInputType(InputType.TYPE_CLASS_TEXT);
            note.setHint("Note");
            note.setLayoutParams(lp);
            row3.addView(note);
            root.addView(row2);
            root.addView(row3);

            WorkoutIds IDS = new WorkoutIds(weight,pickers,note);
            Ids.put(v.name,IDS);
        });

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    public void onSaveClicked(View view)
    {

        Ids.forEach((k,v) -> {
            Log.d("Ex",k);
            String w = ((EditText)v.weight).getText().toString();
            if(w.matches(""))
                w="-";
            Log.d("weight",w);
            String[] num=new String[v.reps.length];
            for(int i =0;i<v.reps.length;i++)
            {
                num[i] = Integer.toString(((NumberPicker)v.reps[i]).getValue());
                Log.d("Set"+Integer.toString(i),num[i]);
            }
            String n = ((EditText)v.note).getText().toString();
            if(n.matches(""))
                n="...";
            Log.d("Note",n);

            createNewRow(workout,k,w,num,n);
        });
    }
    public void createNewRow(String Workout, String exercise, String weight, String[] reps, String note)
    {
        Map<String, String> a = new HashMap<>();
        a.put("action","createNewRow");
        a.put("workout",Workout);
        a.put("exercise",exercise);
        a.put("weight",weight);
        a.put("sets",Integer.toString(reps.length));
        for(int i = 1; i<=reps.length; i=i+1)
        {
            a.put(Integer.toString(i), reps[i - 1]);
        }
        a.put("note",note);

        SendRequest(a);
    }

    private void SendRequest(Map<String, String> params)
    {
        if(firstReq) {
            loading = ProgressDialog.show(this, "Sending Training Data", "Please wait");
            firstReq=false;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        numEx = numEx - 1;
                        if(numEx<1)
                            loading.dismiss();
                        Toast.makeText(Train.this,response,Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(Train.this,MainActivity.class);
                        //startActivity(intent);
                        //finish();
                        onBackPressed();

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
}