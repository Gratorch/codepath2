package com.codepath.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.flickster.adapters.MoviesAdapter;
import com.codepath.flickster.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    private static final String MOVIE_URL="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    List<Movie> movies;

    //Add RecyclerView support library to the Gradle build file-DONE
    //Define a model class to use as the data source-DONE
    //Add a RecyclerView to your activity to display the items
    //Create a custom row layout XML file to visualize the item
    //Create a RecyclerView.Adapter and ViewHolder to render the item
    //Bind the adapter to the data source to populate the RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


        //String title=getIntent().getStringExtra("MOVIE_TITLE");
        //String overview=getIntent().getStringExtra("MOVIE_OVERVIEW");

        //TextView tvTitle=(TextView)findViewById(R.id.tvTitle);
        //tvTitle.setText(title);

        //TextView tvOverview=(TextView)findViewById(R.id.tvOverview);
       // tvOverview.setText(overview);
      //  RecyclerView rvMovie=findViewById(R.id.rvMovies);
       // movies=new ArrayList<>();
       // final MoviesAdapter moviesAdapter=new MoviesAdapter(movies);
       // rvMovie.setLayoutManager(new RecyclerView.LayoutManager() {
         /*
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return null;
            }
        });
        rvMovie.setAdapter(moviesAdapter);
        */

        RecyclerView rvMovie=findViewById(R.id.rvMovies);
        movies=new  ArrayList<>();
        final MoviesAdapter adapter=new MoviesAdapter(this,movies);
        rvMovie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rvMovie.setAdapter(adapter);

        AsyncHttpClient client =new AsyncHttpClient();
        client.get(MOVIE_URL,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonArray = null;
                try {
                    movieJsonArray = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonArray));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
