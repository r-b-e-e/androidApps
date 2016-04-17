package com.example.group10b_hw05;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Ganesh Ramamoorthy
 * Rakesh Balan
 * Priyanka
 */
public class RecyclerAdapterGrid extends RecyclerView.Adapter<RecyclerAdapterGrid.PodcastGridViewHolder> {
    final String GOTOPLAYACTIVITY = "com.example.group10b_hw05.intent.action.PLAYACTIVITY";
    final String PODCASTREF = "PodcastRef";

    ArrayList<TedShow> tedShows;
    Context context;
    public static MediaPlayer mediaPlayer = null;

    public RecyclerAdapterGrid(ArrayList<TedShow> tedShowsDisplay, Context aContext) {
        this.tedShows = tedShowsDisplay;
        this.context = aContext;
    }

    public static class PodcastGridViewHolder extends RecyclerView.ViewHolder{
        LinearLayout podcastItems;
        TextView imgTitle;
        ImageView imageIcon,imgPlay;

        public PodcastGridViewHolder(View itemView) {
            super(itemView);

            podcastItems = (LinearLayout) itemView.findViewById(R.id.linearLayoutPodcastGrid);
            imageIcon = (ImageView) itemView.findViewById(R.id.imageViewTedview);
            imgTitle = (TextView) itemView.findViewById(R.id.textViewPodcastTitle);
            imgPlay = (ImageView) itemView.findViewById(R.id.imageViewPlayButton);
        }
    }

    @Override
    public PodcastGridViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View lView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.podcast_grid,viewGroup,false);
        PodcastGridViewHolder tedShowView = new PodcastGridViewHolder(lView);
        return tedShowView;
    }

    @Override
    public void onBindViewHolder(PodcastGridViewHolder tedViewHolder, final int i) {
        String lImage = tedShows.get(i).getImage();
        if (lImage!=null) {
            Picasso.with(context).load(lImage)
                    .resize(40, 40).into(tedViewHolder.imageIcon);
        }
        else Picasso.with(context).load(R.drawable.no_image)
                .resize(40, 40).into(tedViewHolder.imageIcon);
        tedViewHolder.imgTitle.setText(tedShows.get(i).getTitle());

        tedViewHolder.imgTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlayActivity(tedShows.get(i));
            }
        });

        tedViewHolder.imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio(tedShows.get(i).getAudio());
            }
        });

    }

    @Override
    public int getItemCount() {
        return tedShows.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void startPlayActivity(TedShow apodcastItem){
        Intent intent = new Intent(GOTOPLAYACTIVITY);
        intent.putExtra(PODCASTREF,apodcastItem);
        context.startActivity(intent);
    }


    public void playAudio(String aAudioStreamLink){
        if(mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(aAudioStreamLink);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((MainActivity) context).playing();
    }

    public static MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }
}
