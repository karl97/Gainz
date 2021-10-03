package com.example.gainzv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    Intent intent;
    ArrayList<ImageButton> bottomnavbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        WindowInsetsController controller = getWindow().getInsetsController();
        if (controller != null) {
            controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }
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
        }));
    }
}