package com.example.customimagegenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    MaterialButton generateBtn;
    ProgressBar progressBar;
    ImageView imageView;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText=findViewById(R.id.input_text);
        generateBtn=findViewById(R.id.generate_btn);
        progressBar=findViewById(R.id.progress_bar);
        imageView=findViewById(R.id.image_view);


        generateBtn.setOnClickListener((v)->{
            String text= inputText.getText().toString().trim();
            if (text.isEmpty()){
                inputText.setError("Text can't be empty");
            }
            callAPI(text);
        });
    }

    void  callAPI(String text){
        //API Calling
        JSONObject jsonBody=new JSONObject();
        try {
            jsonBody.put("prompt",text);
            jsonBody.put("size","256x256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody=RequestBody.create(jsonBody.toString(),JSON);
        Request request=new Request.Builder()
                .url("https://api.openai.com/v1/images/generations")
                .header("Authorization","Bearer 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")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(getApplicationContext(),"Failed to generate image",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.i("Response : ",response.body().string());
            }
        });

    }
}