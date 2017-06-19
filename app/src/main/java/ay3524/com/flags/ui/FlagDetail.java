package ay3524.com.flags.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ay3524.com.flags.R;
import ay3524.com.flags.utils.Constants;

public class FlagDetail extends AppCompatActivity {

    ImageView flagImage;
    TextView rank, country, population;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        flagImage = (ImageView) findViewById(R.id.flag_image);
        rank = (TextView) findViewById(R.id.rank);
        country = (TextView) findViewById(R.id.country);
        population = (TextView) findViewById(R.id.population);

        //Receiving the Data from previous activity
        if (getIntent().getExtras() != null) {
            String flagUrl = getIntent().getStringExtra(Constants.FLAG_URL_INTENT_STRING);
            String rankString = getIntent().getStringExtra(Constants.RANK_INTENT_STRING);
            String countryString = getIntent().getStringExtra(Constants.COUNTRY_INTENT_STRING);
            String populationString = getIntent().getStringExtra(Constants.POPULATION_INTENT_STRING);

            //Now use them to set the image and texts
            setImageAndTextViews(flagUrl,rankString,countryString,populationString);
        }
    }

    private void setImageAndTextViews(String flagUrl, String rankString,
                                      String countryString, String populationString) {
        Glide.with(FlagDetail.this)
                .load(flagUrl)
                .into(flagImage);

        //Defining placeholders for best practises
        Resources res = getResources();
        String rankText = String.format(res.getString(R.string.rank_placeholder), rankString);
        String countryText = String.format(res.getString(R.string.country_placeholder), countryString);
        String populationText = String.format(res.getString(R.string.population_placeholder), populationString);

        rank.setText(rankText);
        country.setText(countryText);
        population.setText(populationText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            navigateUpTo(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
