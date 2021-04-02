// ######################################
// #             Puzzle 15              #
// ######################################
// Authors:
// Sapir Nahum
// Moshe Tendler

package com.sapirn_moshet.ex1;

// ######################################
// #              Imports               #
// ######################################
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private boolean checked = false;
    private Button btnPlay;
    private Switch swchMusic;
    public static String PACKAGE_NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);

        swchMusic = (Switch) findViewById(R.id.swch_music);
        swchMusic.setOnCheckedChangeListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_play:
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("music",checked);
                startActivity(intent);

        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            checked=true;
        } else {
            checked=false;
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuItem aboutMenu = menu.add("About");
        MenuItem exitMenu = menu.add("Exit");
        aboutMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                showAboutDialog();
                return true;
            }
        });
        exitMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                showExitDialog();
                return true;
            }
        });


        return true;
    }
    public void showAboutDialog(){

        PACKAGE_NAME = getApplicationContext().getPackageName();
        Log.d("mylog", ">>>> showAboutDialog()");

        String aboutApp = "Puzzle 15 (" + this.getPackageName() + ")\n" +
                "By Sapir Nahum & Moshe Tendler, 24/3/2021.";
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("About App");
        dialog.setMessage(aboutApp);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Log.d("mylog", ">>>> OK");
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void showExitDialog() {
        Log.d("mylog", ">>>> showExitDialog()");
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.ic_exit1);
        dialog.setTitle("Exit App");
        dialog.setMessage("Do you really want to exit Puzzle 15 ?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("mylog", ">>>> YES");
                finish(); // close this activity
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Log.d("mylog", ">>>> NO");
            }
        });
        dialog.show();
    }

}