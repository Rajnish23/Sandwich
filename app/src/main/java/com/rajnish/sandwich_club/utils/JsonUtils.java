package com.rajnish.sandwich_club.utils;

import com.rajnish.sandwich_club.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String TAG = JsonUtils.class.getSimpleName();

    private final static String NAME_CODE = "name";
    private final static String MAIN_NAME_CODE = "mainName";
    private final static String ALSO_KNOWN_AS_CODE = "alsoKnownAs";
    private final static String PLACE_OF_ORIGIN_CODE = "placeOfOrigin";
    private final static String DESCRIPTION_CODE = "description";
    private final static String IMAGE_CODE = "image";
    private final static String INGREDIENTS_CODE = "ingredients";

    public static Sandwich parseSandwichJson(String json){
        try{
            JSONObject mainJsonObject = new JSONObject(json);

            JSONObject name = mainJsonObject.getJSONObject(NAME_CODE);

            String mainName = name.getString(MAIN_NAME_CODE);

            JSONArray alsoKnownArray = name.getJSONArray(ALSO_KNOWN_AS_CODE);

            List<String> alsoKnownList = convertToListFromJsonArray(alsoKnownArray);

            String placeOfOrigin = mainJsonObject.getString(PLACE_OF_ORIGIN_CODE);

            String description = mainJsonObject.getString(DESCRIPTION_CODE);

            String image = mainJsonObject.getString(IMAGE_CODE);

            JSONArray incredientsArray = mainJsonObject.getJSONArray(INGREDIENTS_CODE);

            List<String> ingredients = convertToListFromJsonArray(incredientsArray);

            return new Sandwich(mainName,alsoKnownList,placeOfOrigin,description,image,ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    private static List<String> convertToListFromJsonArray(JSONArray alsoKnownArray) throws JSONException {
        List<String> list = new ArrayList<>(alsoKnownArray.length());

        for (int i=0; i< alsoKnownArray.length();i++) {
            list.add(alsoKnownArray.getString(i));
        }

        return list;
    }
}
