package com.example.lixiang.lab7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText password = (EditText)findViewById(R.id.password);
        final EditText confirm_password = (EditText)findViewById(R.id.confirm_password);
        Button ok = (Button)findViewById(R.id.ok_button);
        Button clear = (Button)findViewById(R.id.clear_button);

        final SharedPreferences sharedPref = getSharedPreferences("MY_KEY", MODE_PRIVATE);
        final String key = sharedPref.getString("KEY_SCORE", "n");

        if(key.equals("n")){
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //password为空
                    if(TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(confirm_password.getText().toString())){
                        Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    //password不一致
                    else if(!confirm_password.getText().toString().equals(password.getText().toString())){
                        Toast.makeText(MainActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                    }
                    //password一致
                    else{
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("KEY_SCORE", password.getText().toString());
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, FileEditorActivity.class);
                        startActivity(intent);
                    }
                }
            });
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    password.setText("");
                    confirm_password.setText("");
                }
            });
        } else{
            confirm_password.setVisibility(GONE);
            password.setHint("Password");
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(password.getText().toString().equals(key)){
                        Intent intent = new Intent(MainActivity.this, FileEditorActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    password.setText("");
                }
            });
        }
    }
}
