package com.example.group10b_hw05;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Ganesh Ramamoorthy
 * Rakesh Balan
 * Priyanka
 */
public class RecyclerAdapterList extends RecyclerView.Adapter<RecyclerAdapterList.PodcastLinearViewHolder> {
    final String GOTOPLAYACTIVITY = "com.example.group10b_hw05.intent.action.PLAYACTIVITY";
    final String PODCASTREF = "PodcastRef";

    ArrayList<TedShow> tedShows;
    static Context context;
    public static MediaPlayer mediaPlayer = null;

    public RecyclerAdapterList(ArrayList<TedShow> tedShows, Context aContext) {
        this.tedShows = tedShows;
        this.context = aContext;
    }

    public static class PodcastLinearViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout podcastItems;
        TextView txtTitle, playDate,playNow;
        ImageView imgIcon,imgPlay;

        public PodcastLinearViewHolder(View aItemView) {
            super(aItemView);

            podcastItems = (RelativeLayout) aItemView.findViewById(R.id.relativeLayoutPodcastItems);
            txtTitle = (TextView) aItemView.findViewById(R.id.textViewTitle);
            playDate = (TextView) aItemView.findViewById(R.id.textViewDate);
            imgIcon = (ImageView) aItemView.findViewById(R.id.imageViewIcon);
            imgPlay = (ImageView) aItemView.findViewById(R.id.imageViewPlayButton);
            playNow = (TextView) aItemView.findViewById(R.id.textViewPlayNow);
        }
    }

    @Override
    public PodcastLinearViewHolder onCreateViewHolder(ViewGroup aViewGroup, int i) {
        View lView = LayoutInflater.from(aViewGroup.getContext()).inflate(R.layout.podcast_row, aViewGroup, false);
        PodcastLinearViewHolder lPodcastView = new PodcastLinearViewHolder(lView);
        return lPodcastView;
    }

    @Override
    public void onBindViewHolder(PodcastLinearViewHolder tedViewHolder, final int i)  {
        String lImage = tedShows.get(i).getImage();
        if (lImage != null) {
            Picasso.with(context).load(lImage)
                    .resize(40, 40).into(tedViewHolder.imgIcon);
        }
        else Picasso.with(context).load(R.drawable.no_image)
                .resize(40, 40).into(tedViewHolder.imgIcon);
        tedViewHolder.txtTitle.setText(tedShows.get(i).getTitle());
        tedViewHolder.playDate.setText(tedShows.get(i).getPublicationDate());

        tedViewHolder.podcastItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlayActivity(tedShows.get(i));
            }
        });

        tedViewHolder.playNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio(tedShows.get(i).getAudio());
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
    public void onAttachedToRecyclerView(RecyclerView aRecyclerView) {
        super.onAttachedToRecyclerView(aRecyclerView);
    }

    public void startPlayActivity(TedShow aPodcastItem){
        Intent intent = new Intent(GOTOPLAYACTIVITY);
        intent.putExtra(PODCASTREF,aPodcastItem);
        context.startActivity(intent);
    }

    public void playAudio(String aAudioStreamLink ){

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
