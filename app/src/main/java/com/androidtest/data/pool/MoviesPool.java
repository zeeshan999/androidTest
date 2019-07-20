package com.androidtest.data.pool;

import com.androidtest.data.Model.MovieItem;
import com.androidtest.util.Constants;

import java.util.ArrayList;

public class MoviesPool extends ParentPool{


    private int pageIndex = 1;

    @Override
    public void loadItems() {

        if(requestController != null)
        {
            requestController.requestResponse(mContext, Constants.MOVIES_API_URL + pageIndex, fragmentType);
        }
    }


    @Override
    protected void onSuccess(Object... args) {

        pageIndex++;
        itemsList = (ArrayList<MovieItem>) args[0];

        iPoolEvent.onPoolsEvent(Constants.ON_SUCCESS, itemsList);

    }

    @Override
    protected void onFailure() {

        iPoolEvent.onPoolsEvent(Constants.ON_FAILURE);
    }
}
