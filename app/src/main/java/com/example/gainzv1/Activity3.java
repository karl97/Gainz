package com.example.gainzv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Activity3 extends AppCompatActivity {

    Intent intent;
    ArrayList<ImageButton> bottomnavbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        bottomnavbar = new ArrayList<ImageButton>();
        bottomnavbar.add((ImageButton) findViewById(R.id.bottom_toolbar_btn1));
        bottomnavbar.add( (ImageButton) findViewById(R.id.bottom_toolbar_btn2));
        bottomnavbar.add( (ImageButton) findViewById(R.id.bottom_toolbar_btn3));



        bottomnavbar.forEach((btn)-> btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId())
                {

                    case R.id.bottom_toolbar_btn1:
                        intent = new Intent(Activity3.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.bottom_toolbar_btn2:
                        intent = new Intent(Activity3.this, Activity2.class);
                        startActivity(intent);
                        break;
                    case R.id.bottom_toolbar_btn3:

                        break;
                    default:
                }

            }
        }));
    }
}