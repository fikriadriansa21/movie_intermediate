package com.example.krayz.movie_intermediate.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.krayz.movie_intermediate.R;
import com.example.krayz.movie_intermediate.main.MainDao;
import com.squareup.picasso.Picasso;

/**
 * Created by Krayz on 10/12/2017.
 */

public class DetailActivity extends AppCompatActivity{
    private MainDao mData;

    private TextView textTitle, textRelease, textDesc;
    private ImageView imgBack, imgPoster;
    private Toolbar mToolbar;
    private RatingBar rating;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mData = getIntent().getParcelableExtra("dataMovie");

        mToolbar = (Toolbar)findViewById(R.id.toolbarDetail);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(mData.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        textTitle = findViewById(R.id.textTitle);
        textDesc = findViewById(R.id.textDesc);
        textRelease = findViewById(R.id.textRelease);

        imgBack = findViewById(R.id.imageToolbar);
        imgPoster = findViewById(R.id.imagePoster);

        textTitle.setText(mData.getTitle());
        textDesc.setText(mData.getDescription());
        textRelease.setText("Release Date : " + mData.getReleaseDate());

 
        Picasso.with(this)
                .load(mData.getImageUrl())
                .into(imgPoster);

        Picasso.with(this)
                .load(mData.getImageBackground())
                .into(imgBack);
    }
}
