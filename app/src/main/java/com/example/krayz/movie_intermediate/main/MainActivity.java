package com.example.krayz.movie_intermediate.main;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.krayz.movie_intermediate.R;
import com.example.krayz.movie_intermediate.data.ApiClient;
import com.example.krayz.movie_intermediate.data.dao.MovieResponseDao;
import com.example.krayz.movie_intermediate.data.offline.MovieContract;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mRecyclerMain;
    private MainAdapter mAdapter;
    private List<MainDao> mData= new ArrayList<>();

    private String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0,null,this);


        //MainAdapter mainAdapter = new MainAdapter(mData);
        mAdapter = new MainAdapter(mData);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);

        mRecyclerMain = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerMain.setAdapter(mAdapter);
        mRecyclerMain.setLayoutManager(gridLayoutManager);

        ApiClient.service().getMovieList("e53249564abad5cf3b4c348de0c26aee")
                .enqueue(new Callback<MovieResponseDao>() {
                    @Override
                    public void onResponse(Call<MovieResponseDao> call, Response<MovieResponseDao> response) {
                        if (response.isSuccessful()) {
                            Uri deleteUri = MovieContract.MovieEntry.CONTENT_URI;
                            getContentResolver().delete(deleteUri,null,null);

                            for (MovieResponseDao.MovieData data : response.body().getResults()) {
//                                mData.add(new MainDao(data.getTitle(),"https://image.tmdb.org/t/p/w185/" + data.getPoster_path()));
                                ContentValues contentValues = new ContentValues();

                                contentValues.put(MovieContract.MovieEntry.COLUMN_FAVORITE_IDS,data.getId());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE,data.getTitle());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_ORI_TITLE,data.getOriginal_title());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT,data.getVote_count());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_VIDEO,data.isVideo() ? 1 : 0);
                                contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVG,data.getVote_average());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY,data.getPopularity());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH,data.getPoster_path());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANG,data.getOriginal_language());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_GENRE,"");
                                contentValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,data.getBackdrop_path());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_ADULT,data.isAdult() ? 1:0);
                                contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,data.getOverview());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,data.getRelease_date());

                                Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,contentValues);

                                if(uri != null){
                                    Log.d("onResponse","INSERT DATA SUCCESS!");
                                }
                            }
                            getSupportLoaderManager().restartLoader(0,null,MainActivity.this);
//                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponseDao> call, Throwable t) {
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });



//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mData.add(new MainDao("Satu","https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Maby_multif_F_050222_061_kng.jpg/180px-Maby_multif_F_050222_061_kng.jpg"));
//                mData.add(new MainDao("Dua","http://cdn2.tstatic.net/aceh/foto/bank/images/buaya_20151031_114802.jpg"));
//                mData.add(new MainDao("Tiga","https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Maby_multif_F_050222_061_kng.jpg/180px-Maby_multif_F_050222_061_kng.jpg"));
//                mData.add(new MainDao("Empat","https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Maby_multif_F_050222_061_kng.jpg/180px-Maby_multif_F_050222_061_kng.jpg"));
//                mData.add(new MainDao("Lima","https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Maby_multif_F_050222_061_kng.jpg/180px-Maby_multif_F_050222_061_kng.jpg"));
//                mData.add(new MainDao("Enam","https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Maby_multif_F_050222_061_kng.jpg/180px-Maby_multif_F_050222_061_kng.jpg"));
//                mData.add(new MainDao("Tujuh","https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Maby_multif_F_050222_061_kng.jpg/180px-Maby_multif_F_050222_061_kng.jpg"));
//                mData.add(new MainDao("Delapan","https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Maby_multif_F_050222_061_kng.jpg/180px-Maby_multif_F_050222_061_kng.jpg"));
//                mData.add(new MainDao("Sembilan","https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Maby_multif_F_050222_061_kng.jpg/180px-Maby_multif_F_050222_061_kng.jpg"));
//                mData.add(new MainDao("Sepuluh","https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Maby_multif_F_050222_061_kng.jpg/180px-Maby_multif_F_050222_061_kng.jpg"));
//
//                mAdapter.notifyDataSetChanged();
//            }
//        },5000);

//        Toast.makeText(this,"Loading Data 5 detik . . .",Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(MainActivity.this){
        Cursor mMovieData = null;

            @Override
            protected void onStartLoading() {
                if(mMovieData != null){
                    deliverResult(mMovieData);
                }else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieContract.MovieEntry._ID);
                }catch (Exception e){
                    Log.e(TAG,"Failed to asynchronously load data. \n"+e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                mMovieData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.wtf("onLoadFinished",String.valueOf(data.getCount()));
        mData.clear();

        for(int i = 0; i < data.getCount();i++){
            data.moveToPosition(i);

            mData.add(new MainDao(
               data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)),
                    "https://image.tmdb.org/t/p/w185/" + data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH))
            ));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
