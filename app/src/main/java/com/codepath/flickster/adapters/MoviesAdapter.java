package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.flickster.DetailActivity;
import com.codepath.flickster.R;
import com.codepath.flickster.model.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>  {

   /* ArrayList<Movie> movies;
    Config config;
    Context context;

    public MoviesAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context=viewGroup.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);

        View movieView=inflater.inflate(R.layout.item_movie,viewGroup,false);

        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder viewHolder, int i) {
        Movie movie=movies.get(i);

        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        //Orientation du vue
        boolean isPotrait=context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT;

        String imgURL;

        //Test sur le vue
        if (isPotrait){
            //Build image for the poster URl
            imgURL=config.getImageURL(config.getPosterSize(),movie.getPosterPath());
        }
        else {
            imgURL=config.getImageURL(config.getBackdropSize(),movie.getBackdropPath());
        }

        //Choisir son placeholder
        int placeholderId=isPotrait?R.drawable.flicks_movie_placeholder:R.drawable.flicks_backdrop_placeholder;
        ImageView imageView=isPotrait?viewHolder.ivPosterImage:viewHolder.ivBackdropImage;

        //Load image
        Glide.with(context).load(imgURL).apply(new RequestOptions().bitmapTransform(new RoundedCornersTransformation(30,0))).apply(new RequestOptions().placeholder(placeholderId).error(R.drawable.flicks_movie_placeholder)).into(imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public Config getConfig(Config config){
        return config;
    }
    public void setConfig(){
        this.config=config;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Lier les items par leur id
            ivPosterImage=(ImageView)itemView.findViewById(R.id.ivPoster);
            ivBackdropImage=(ImageView)itemView.findViewById(R.id.ivBackdropImage);
            tvTitle=(TextView)itemView.findViewById(R.id.tvTitle);
            tvOverview=(TextView)itemView.findViewById(R.id.tvOverview);
        }
    }

    */
    Context context;
    List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    TextView tvTitle;
    TextView tvOverview;
    ImageView ivPoster;
    ConstraintLayout container;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("smile","onCreateViewHolder");
        View view=LayoutInflater.from(context).inflate(R.layout.item_movie,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("smile","onBindViewHolder"+i);
        Movie movie=movies.get(i);

        viewHolder.Bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder( View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvOverview=itemView.findViewById(R.id.tvOverview);
            ivPoster=itemView.findViewById(R.id.ivPoster);
            container=itemView.findViewById(R.id.container);
        }

        public void Bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL=movie.getPosterPath();
            //Reference the backdrop phat if phone is in landscape
            if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                imageURL=movie.getBackdropPath();
            }

            int radius=30, margin=10;
            Glide.with(context).load(imageURL).into(ivPoster);
            //Glide.with(context).load(imageURL).into(ivPoster);
            //Glide.with(context).load(imageURL).transform(new BlurTransformation()).into(imageURL);
            // Add click listener on the whole row
            // Navigate to detail activity on tap
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Navigate to detail ctivity on tap
                    Intent i=new Intent(context, DetailActivity.class);
                   // i.putExtra("title",movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }

}
