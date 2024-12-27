package com.example.firebaseapp;

import static androidx.navigation.ActivityNavigatorDestinationBuilderKt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Locale;

public class EnvironmentFragment extends Fragment {

    private TextView insightsText;
    private ProgressBar progressBar;
    private Button updateInsightsButton, nextButton;

    private static final String API_KEY = "26ae1a723cd60f2f0cba49e87cae62cb";
    private static final String CITY = "Chittagong";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API_KEY;

    public EnvironmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_environment, container, false);

        // Initialize Views
        insightsText = view.findViewById(R.id.insightsText);
        progressBar = view.findViewById(R.id.progressBar);
        updateInsightsButton = view.findViewById(R.id.updateInsightsButton);
        nextButton = view.findViewById(R.id.next);

        // Set button listeners
        updateInsightsButton.setOnClickListener(v -> fetchWeatherData());
        nextButton.setOnClickListener(v -> navigateToNextActivity());

        return view;
    }

    private void fetchWeatherData() {
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API_URL,
                null,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    parseWeatherResponse(response);
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Failed to fetch weather data.", Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(jsonObjectRequest);
    }

    private void parseWeatherResponse(JSONObject response) {
        try {
            JSONObject main = response.getJSONObject("main");
            double temperature = main.getDouble("temp");

            String insights = String.format(Locale.getDefault(), "Temperature: %.2f °C", temperature);
            insightsText.setText(insights);
        } catch (Exception e) {
            insightsText.setText("Failed to parse weather data.");
        }
    }
    private void navigateToNextActivity() {
        Intent intent = new Intent(getActivity(), NextActivity.class);
        startActivity(intent);
    }

}
