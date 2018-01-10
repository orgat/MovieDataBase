package com.revenant.moviedatabase.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.revenant.moviedatabase.Model.Movie;
import com.revenant.moviedatabase.R;
import com.revenant.moviedatabase.Utility.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie movie;
    private TextView movieTitle;
    private TextView releaseDate;
    private TextView category;
    private TextView rating;
    private TextView runTime;
    private TextView directors;
    private TextView actors;
    private TextView writers;
    private TextView plot;
    private TextView boxOffice;
    private String movieId;
    private ImageView movieImage;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        queue= Volley.newRequestQueue(this);
        movie = (Movie) getIntent().getSerializableExtra("movie");
        movieId = movie.getImdbId();
        setUpUi();
        getMovieDetails(movieId);
    }

    private void getMovieDetails(String id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("Ratings")) {
                        JSONArray ratings = response.getJSONArray("Ratings");
                        String source = null;
                        String value = null;
                        if (ratings.length() > 0) {
                            JSONObject mRating = ratings.getJSONObject(ratings.length() - 1);
                            source = mRating.getString("Source");
                            value = mRating.getString("Value");
                            rating.setText(source + " : " + value);
                        } else {
                            rating.setText("Ratings: N/A");
                        }

                        movieTitle.setText(response.getString("Title"));
                        releaseDate.setText("Released: " + response.getString("Released"));
                        directors.setText("Director: " + response.getString("Director"));
                        writers.setText("Writers: "+response.getString("Writer"));
                        plot.setText("Plot: "+response.getString("Plot"));
                        runTime.setText("Runtime: "+ response.getString("Runtime"));
                        actors.setText("Actors: "+response.getString("Actors"));
                        boxOffice.setText("Box Office: "+response.getString("BoxOffice"));

                        Picasso.with(getApplicationContext()).load(response.getString("Poster"))
                                .into(movieImage);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error:",error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void setUpUi() {

        movieTitle = findViewById(R.id.movieTitleDetId);
        releaseDate = findViewById(R.id.movieReleaseDetId);
        category = findViewById(R.id.movieCategoryDetId);
        rating = findViewById(R.id.movieRatingDetId);
        runTime = findViewById(R.id.movieRunTimeDetId);
        directors = findViewById(R.id.directedByDetId);
        actors = findViewById(R.id.actorsID);
        writers = findViewById(R.id.writersID);
        plot = findViewById(R.id.plotID);
        boxOffice = findViewById(R.id.boxOfficeID);
        movieImage = findViewById(R.id.movieImageDetId);
    }

}
