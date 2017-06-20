package ay3524.com.flags.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import ay3524.com.flags.utils.ApiInterface;
import ay3524.com.flags.utils.Constants;
import ay3524.com.flags.R;
import ay3524.com.flags.adapter.WorldFlagAdapter;
import ay3524.com.flags.model.WorldData;
import ay3524.com.flags.model.Worldpopulation;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements WorldFlagAdapter.ClickListener, View.OnClickListener {

    GridLayoutManager layoutManager;
    WorldFlagAdapter adapter;
    RecyclerView recyclerView;
    RelativeLayout emptyView;
    Button retryButton;
    ProgressBar progressBar;
    ArrayList<Worldpopulation> worldata, savedWorldData;
    private static final String STATE_SAVE = "state_save";
    private static final String LOG_ERROR_TAG = "Error World Data App";
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        emptyView = (RelativeLayout) findViewById(R.id.empty_view);
        retryButton = (Button) findViewById(R.id.retry);
        retryButton.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        // Setting up the RecyclerView for LANDSCAPE AND PORTRAIT.
        // LANDSCAPE VERSION SHOWS TWO FLAGS IN A ROW AND PORTRAIT VERSION HAS THREE.
        recyclerView.setHasFixedSize(true);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getApplicationContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            layoutManager = new GridLayoutManager(getApplicationContext(), 3);
            recyclerView.setLayoutManager(layoutManager);
        }

        // Using savedInstanceState to handle configuration change
        if (savedInstanceState != null && Constants.isConnected(getApplicationContext())) {
            savedWorldData = savedInstanceState.getParcelableArrayList(STATE_SAVE);
            adapter = new WorldFlagAdapter(savedWorldData, getApplicationContext());
            adapter.setClickListener(MainActivity.this);
            recyclerView.setAdapter(adapter);
            count = 1;
        } else {
            checkInternetAndRequest();
        }
    }

    /**
     * This method is used for checking Internet Connection and performing Network request
     * Shows a proper error message if Internet Connection not available.
     */
    private boolean checkInternetAndRequest() {
        if (Constants.isConnected(getApplicationContext())) {
            progressBar.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            performNetworkRequest();
            return true;
        } else {
            emptyView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return false;
        }
    }

    /**
     * This method is used for the network request using Retrofit Library.
     * After calling Retrofit this function calls Observer to fetch images of flags.
     */
    private void performNetworkRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URI)
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        performObserverRequest(apiInterface);

    }

    /**
     * This method is used for performing Observer Request using RxJava.
     *
     * @param apiInterface - using the retrofit fetching interface for Observer.
     */
    private void performObserverRequest(ApiInterface apiInterface) {

        Observable<WorldData> data = apiInterface.getWorldData();

        data.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WorldData>() {
                    @Override
                    public final void onCompleted() {
                        // Disabling the progressbar after complete network request.
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e(LOG_ERROR_TAG, e.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public final void onNext(WorldData response) {
                        worldata = response.getWorldpopulation();
                        adapter = new WorldFlagAdapter(response.getWorldpopulation(), getApplicationContext());
                        adapter.setClickListener(MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (count == 1) {
            outState.putParcelableArrayList(STATE_SAVE, savedWorldData);
        } else {
            outState.putParcelableArrayList(STATE_SAVE, worldata);
        }
    }

    @Override
    public void onItemClick(View v, int pos) {
        Worldpopulation worldpopulation = worldata.get(pos);
        String flagImageUrl = worldpopulation.getFlag();
        String rank = String.valueOf(worldpopulation.getRank());
        String country = worldpopulation.getCountry();
        String population = worldpopulation.getPopulation();

        Intent i = new Intent(getApplicationContext(), FlagDetail.class);
        i.putExtra(Constants.FLAG_URL_INTENT_STRING, flagImageUrl);
        i.putExtra(Constants.RANK_INTENT_STRING, rank);
        i.putExtra(Constants.COUNTRY_INTENT_STRING, country);
        i.putExtra(Constants.POPULATION_INTENT_STRING, population);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if (!checkInternetAndRequest()) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_error_network), Toast.LENGTH_SHORT).show();
        }
    }
}