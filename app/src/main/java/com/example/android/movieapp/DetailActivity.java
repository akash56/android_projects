package com.example.android.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        }

@Override
    public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater inflater=getMenuInflater();
    inflater.inflate(R.menu.menu_detail, menu);
    return true;
}
@Override
    public boolean onOptionsItemSelected(MenuItem item){
    int id=item.getItemId();
    if(id==R.id.action_settings){
        startActivity(new Intent(this,SettingActivity.class));
    return true;
    }
return super.onOptionsItemSelected(item);
}
}