package com.example.gainzv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    ArrayList<ImageButton> bottomnavbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setDecorFitsSystemWindows(false);
        /*WindowInsetsController controller = getWindow().getInsetsController();
        if (controller != null) {
            controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }*/

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
                                break;
                            case R.id.bottom_toolbar_btn2:
                                intent = new Intent(MainActivity.this, Activity2.class);
                                startActivity(intent);
                                break;
                            case R.id.bottom_toolbar_btn3:
                                intent = new Intent(MainActivity.this, Activity3.class);
                                startActivity(intent);
                                break;
                            default:
                        }

                    }
                }));
    }
}