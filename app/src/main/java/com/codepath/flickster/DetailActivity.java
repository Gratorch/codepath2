package com.codepath.flickster;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.model.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends YouTubeBaseActivity {

    private static  final String YOUTUBE_API_KEY="AIzaSyDF-VFiYk2GBO7lBOSaVI1WyQzpscG7yqc";
    private  static  final  String TRAILLER_API="https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle=findViewById(R.id.tvTitle);
        tvOverview=findViewById(R.id.tvOverview);
        ratingBar=findViewById(R.id.ratingBar);
        youTubePlayerView=findViewById(R.id.player);

        movie=(Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float)movie.getVoteAverage());

        AsyncHttpClient client =new AsyncHttpClient();
        client.get(String.format(TRAILLER_API,movie.getMovieId()), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray results=response.getJSONArray("results");
                    if (results.length()==0){
                        return;
                    }
                    JSONObject movieTrailler=results.getJSONObject(0);
                    String youtubeKey=movieTrailler.getString("key");
                    initializeYoutube(youtubeKey);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    private void initializeYoutube(final String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("smile","On init succes");
                if (movie.getVoteAverage()>5.0) {
                    youTubePlayer.loadVideo(youtubeKey);
                }else {
                    youTubePlayer.cueVideo(youtubeKey);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}
