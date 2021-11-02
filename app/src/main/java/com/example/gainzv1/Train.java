package com.example.gainzv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

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

import java.util.HashMap;

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
    HashMap<String,WorkoutData> data;
    HashMap<String,WorkoutIds> Ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);


        Intent intent = getIntent();
        String workout = intent.getStringExtra("workout");
        TextView t = findViewById(R.id.top_toolbar_text);
        t.setText(workout);
        HashMap<String, Exercise> exercises = (HashMap<String, Exercise>)intent.getSerializableExtra("exercises");
        Ids = new HashMap<String,WorkoutIds>();

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
            Log.d("weight",w);
            for(int i =0;i<v.reps.length;i++)
            {
                int num = ((NumberPicker)v.reps[i]).getValue();
                Log.d("Set"+Integer.toString(i),Integer.toString(num));
            }
            String n = ((EditText)v.note).getText().toString();
            Log.d("Note",n);
        });
    }
}