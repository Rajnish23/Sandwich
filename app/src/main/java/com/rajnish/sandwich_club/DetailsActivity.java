package com.rajnish.sandwich_club;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rajnish.sandwich_club.model.Sandwich;
import com.rajnish.sandwich_club.utils.JsonUtils;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnownTv;
    private TextView mAlsoKnownLabel;
    private TextView mOriginTv;
    private TextView mOriginLabel;
    private TextView mDescriptionTv;
    private TextView mIngredientTv;
    private ImageView mSandwichIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mSandwichIv = findViewById(R.id.image_iv);
        mAlsoKnownTv = findViewById(R.id.also_known_tv);
        mAlsoKnownLabel = findViewById(R.id.alsoKnownAs_label);
        mOriginTv = findViewById(R.id.origin_tv);
        mOriginLabel = findViewById(R.id.placeOfOrigin_label);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredientTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();

        if(intent == null){
            closeOnError();
            return;
        }

        int position = getIntent().getIntExtra(EXTRA_POSITION,DEFAULT_POSITION);

        if(position == DEFAULT_POSITION){
            closeOnError();
            return;
        }

        String sandWichesDetail[] = getResources().getStringArray(R.array.sandwich_details);
        String json = sandWichesDetail[position];

        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if(sandwich == null){
            closeOnError();
            return;
        }

        populateUI(sandwich);
        setTitle(sandwich.getMainName());
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sandwich.getAlsoKnownAs().get(0));

            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                stringBuilder.append(", ");
                stringBuilder.append(sandwich.getAlsoKnownAs().get(i));
            }
            mAlsoKnownTv.setText(stringBuilder.toString());
        } else {
            mAlsoKnownTv.setVisibility(View.GONE);
            mAlsoKnownLabel.setVisibility(View.GONE);
        }

        // set Text to originTv
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mOriginTv.setVisibility(View.GONE);
            mOriginLabel.setVisibility(View.GONE);
        } else {
            mOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        // set Text to descriptionTv
        mDescriptionTv.setText(sandwich.getDescription());

        // set Text to ingredientTv
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\u2022");
            stringBuilder.append(sandwich.getIngredients().get(0));

            for (int i = 1; i < sandwich.getIngredients().size(); i++) {
                stringBuilder.append("\n");
                stringBuilder.append("\u2022");
                stringBuilder.append(sandwich.getIngredients().get(i));
            }
            mIngredientTv.setText(stringBuilder.toString());
        }

        // display the image
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mSandwichIv);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
