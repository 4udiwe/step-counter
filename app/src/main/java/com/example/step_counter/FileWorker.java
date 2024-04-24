package com.example.step_counter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Locale;

public class FileWorker {
    private static final String FILE_NAME = "users-stat.json";

    public FileWorker() {
    }

    public void exportToStatFile(Context context, Root root) {

        Gson gson = new Gson();

        String jsonString = gson.toJson(root);

        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(jsonString.getBytes(StandardCharsets.UTF_8));
            Log.d("RRR", "filesaved");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Root importFromStatFile(Context context){
        try(FileInputStream fin = context.openFileInput(FILE_NAME);
            InputStreamReader streamReader = new InputStreamReader(fin)){

            Gson gson = new Gson();
            Root root = gson.fromJson(streamReader, Root.class);
            return root;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
