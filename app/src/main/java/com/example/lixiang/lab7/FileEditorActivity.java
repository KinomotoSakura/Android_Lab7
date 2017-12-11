package com.example.lixiang.lab7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileEditorActivity extends AppCompatActivity {
    private String FILE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_editor);

        final EditText file_name = (EditText)findViewById(R.id.file_name);
        final EditText file_edit = (EditText)findViewById(R.id.file_edit);
        Button save = (Button)findViewById(R.id.save_button);
        Button load = (Button)findViewById(R.id.load_button);
        Button clear = (Button)findViewById(R.id.clear_file);
        Button delete = (Button)findViewById(R.id.delete_file);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FILE_NAME = file_name.getText().toString() + ".txt";
                try(FileOutputStream fileOutputStream = openFileOutput(FILE_NAME,MODE_PRIVATE)){
                    String text = file_edit.getText().toString();
                    fileOutputStream.write(text.getBytes());
                    fileOutputStream.close();
                    Log.i("TAG", "Successfully saved file");
                    Toast.makeText(FileEditorActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("TAG", "Fail to save file");
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FILE_NAME = file_name.getText().toString() + ".txt";
                try(FileInputStream fileInputStream = openFileInput(FILE_NAME)){
                    byte[] contents = new byte[fileInputStream.available()];
                    int read_result = fileInputStream.read(contents);
                    file_edit.setText(new String(contents));
                    Toast.makeText(FileEditorActivity.this, "Load successfully", Toast.LENGTH_SHORT).show();
                    Log.i("TAG", "Successfully load");
                } catch (FileNotFoundException e) {
                    Toast.makeText(FileEditorActivity.this, "Fail to load file", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Fail to load file");
                } catch (IOException e) {
                    Log.e("TAG", "Fail to read");
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_edit.setText("");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FILE_NAME = file_name.getText().toString() + ".txt";
                File dir = getFilesDir();
                File file = new File(dir, FILE_NAME);
                boolean deleted = file.delete();
                if (deleted) {
                    Toast.makeText(FileEditorActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FileEditorActivity.this, "Fail to delete file", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
