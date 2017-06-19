package ay3524.com.flags;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ay3524.com.flags.model.WorldData;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements WorldFlagAdapter.ClickListener {

    GridLayoutManager layoutManager;
    WorldFlagAdapter adapter;
    RecyclerView recyclerView;
    RelativeLayout emptyView;
    Button retryButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        emptyView = (RelativeLayout) findViewById(R.id.empty_view);
        retryButton = (Button) findViewById(R.id.retry);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        recyclerView.setHasFixedSize(true);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getApplicationContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            layoutManager = new GridLayoutManager(getApplicationContext(), 3);
            recyclerView.setLayoutManager(layoutManager);
        }

        checkInternetAndRequest();

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!checkInternetAndRequest()){
                   Toast.makeText(MainActivity.this, "Still No Internet Connection!", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    private boolean checkInternetAndRequest() {
        if(Constants.isConnected(getApplicationContext())){
            progressBar.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            performNetworkRequest();
            return true;
        }else{
            emptyView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return false;
        }
    }

    private void performNetworkRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URI)
                .build();

        WorldService weatherService = retrofit.create(WorldService.class);
        Observable<WorldData> data = weatherService.getWorldData();

        data.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WorldData>() {
                    @Override
                    public final void onCompleted() {
                        // Disable the progressbar
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e("Error World Data App", e.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public final void onNext(WorldData response) {
                        Toast.makeText(MainActivity.this,response.getWorldpopulation().get(0).getCountry() , Toast.LENGTH_SHORT).show();
                        //mCardAdapter.addData(response);
                        adapter = new WorldFlagAdapter(response.getWorldpopulation(), getApplicationContext());
                        adapter.setClickListener(MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View v, int pos) {


    }
}
