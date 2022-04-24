package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.databinding.ActivityDashBoardBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class DashBoardActivity extends AppCompatActivity {

    String city = "";


    ActivityDashBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dash_board);

        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();

        binding.searchIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = binding.searchBar.getText().toString();
                Log.d("cname","city is:- "+city);

                String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=4013cdca0ff67e4797239670504cb757";

                RequestQueue requestQueue = Volley.newRequestQueue(DashBoardActivity.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, BASE_URL, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
//                        textView.setText("Response: " + response.toString());
                                try {

                                    JSONObject jsonObject = response.getJSONObject("coord");
                                    binding.longi.setText(jsonObject.getString("lon"));
                                    binding.lati.setText(jsonObject.getString("lat"));

                                    JSONObject jsonObjectMain = response.getJSONObject("main");
                                    binding.temp.setText(jsonObjectMain.getString("temp"));
                                    binding.feelsLike.setText(jsonObjectMain.getString("feels_like"));
                                    binding.tempMin.setText(jsonObjectMain.getString("temp_min"));
                                    binding.tempMax.setText(jsonObjectMain.getString("temp_max"));
                                    binding.pressure.setText(jsonObjectMain.getString("pressure"));
                                    binding.humidty.setText(jsonObjectMain.getString("humidity"));

                                    binding.visibility.setText(response.getString("visibility"));




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(DashBoardActivity.this, "Turn on Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                requestQueue.add(jsonObjectRequest);
            }
        });


    }
}