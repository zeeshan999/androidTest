package com.androidtest.data.pool;

import android.content.Context;

import com.androidtest.data.util.RequestController;
import com.androidtest.data.Interface.IPoolEvent;
import com.androidtest.data.Interface.IRequestController;
import com.androidtest.data.Model.MovieItem;
import com.androidtest.util.Constants;

import java.util.ArrayList;

public class ParentPool {

    protected Context mContext;
    protected String fragmentType;
    protected RequestController requestController;
    protected ArrayList<MovieItem> itemsList;
    protected IPoolEvent iPoolEvent;


    public void initialize(Context context, String fragmentType)
    {
        this.mContext = context;
        this.fragmentType = fragmentType;
        itemsList = new ArrayList<>();

        requestController = new RequestController();
        requestController.registerEventListener(requestControllerEventListener);

    }

    public void loadItems() {
    }


    protected IRequestController requestControllerEventListener = new IRequestController() {
        @Override
        public void onRequestControllerResponse(String eventType, Object... args) {

            switch (eventType)
            {
                case Constants.ON_SUCCESS:

                    onSuccess(args);

                    break;

                case Constants.ON_FAILURE:

                    onFailure();

                    break;
            }

        }
    };

    protected void onSuccess(Object...args)
    {

    }

    protected void onFailure()
    {

    }

    public void registerEventReciever(IPoolEvent event) {

        this.iPoolEvent = event;
    }

    public void destroy() {

        if(requestController != null)
            requestController.destroy();
        requestController = null;

        if(itemsList != null)
            itemsList.clear();
        itemsList = null;
    }
}
