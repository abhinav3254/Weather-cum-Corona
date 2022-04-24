package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.weather.databinding.ActivityCoronaBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class CoronaActivity extends AppCompatActivity {

    ActivityCoronaBinding binding;
    String country = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_corona);

        binding = ActivityCoronaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();

        binding.searchCorona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = binding.tvSearchBox.getText().toString();
                String url = "https://corona.lmao.ninja/v2/countries/"+country;
                RequestQueue requestQueue = Volley.newRequestQueue(CoronaActivity.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
//                        textView.setText("Response: " + response.toString());
                                try {

                                    binding.cases.setText(response.getString("cases"));
                                    binding.todayCases.setText(response.getString("todayCases"));
                                    binding.deaths.setText(response.getString("deaths"));
                                    binding.todayDeaths.setText(response.getString("todayDeaths"));
                                    binding.recovered.setText(response.getString("recovered"));
                                    binding.todayRecovered.setText(response.getString("todayRecovered"));
                                    binding.active.setText(response.getString("active"));
                                    binding.critical.setText(response.getString("critical"));
                                    binding.casesPerOneMillion.setText(response.getString("casesPerOneMillion"));
                                    binding.deathsPerOneMillion.setText(response.getString("deathsPerOneMillion"));
                                    binding.testsPerOneMillion.setText(response.getString("testsPerOneMillion"));
                                    binding.activePerOneMillion.setText(response.getString("activePerOneMillion"));

                                    JSONObject jsonObject = response.getJSONObject("countryInfo");
                                    String countryImageURL = jsonObject.getString("flag");
                                    Glide.with(CoronaActivity.this).load(countryImageURL).into(binding.countryImage);

                                    binding.countryName.setText(response.getString("country"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(CoronaActivity.this, "Turn on Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}