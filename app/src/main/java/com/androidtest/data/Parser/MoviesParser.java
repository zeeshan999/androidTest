package com.androidtest.data.Parser;

import com.androidtest.data.Model.MovieItem;
import com.androidtest.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesParser extends ParentParser {


    @Override
    public void performParsing(String response) {
        try
        {
            ArrayList<MovieItem> list = new ArrayList<>();

            JSONObject object = new JSONObject(response);

            if(object.has("results"))
            {
                JSONArray array = object.getJSONArray("results");

                MovieItem item;
                for(int i = 0; i < array.length(); i++)
                {

                    item = new MovieItem();
                    object = array.getJSONObject(i);

                    item.setId(object.optString("id"));
                    item.setTitle(object.optString("title"));
                    item.setOriginalTitle(object.optString("original_title"));
                    item.setOriginalLanguage(object.optString("original_language"));
                    item.setPosterPath(Constants.POSTER_BASE_PATH + object.optString("poster_path"));
                    item.setBackDropPath(object.optString("backdrop_path"));
                    item.setReleaseDate(object.optString("release_date"));
                    item.setOverView(object.optString("overview"));
                    item.setAdult(object.optBoolean("adult"));

                    list.add(item);
                }
            }


            if(list.size() > 0)
                parsingEvent.onParsingEvent(Constants.ON_SUCCESS, list);
            else
                parsingEvent.onParsingEvent(Constants.ON_FAILURE);
        }
        catch (Exception e)
        {
            parsingEvent.onParsingEvent(Constants.ON_FAILURE);
        }

    }
}
