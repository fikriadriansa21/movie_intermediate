package com.example.krayz.movie_intermediate.main;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.krayz.movie_intermediate.R;
import com.example.krayz.movie_intermediate.data.ApiClient;
import com.example.krayz.movie_intermediate.data.dao.MovieResponseDao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerMain;
    private MainAdapter mAdapter;
    private List<MainDao> mData= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                            for (MovieResponseDao.MovieData data : response.body().getResults()) {
                                mData.add(new MainDao(data.getTitle(),"https://image.tmdb.org/t/p/w185/" + data.getPoster_path()));
                            }

                            mAdapter.notifyDataSetChanged();
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

        Toast.makeText(this,"Loading Data 5 detik . . .",Toast.LENGTH_SHORT).show();
    }

}
