package com.androidtest.data;

import android.content.Context;

import com.androidtest.data.pool.MoviesPool;
import com.androidtest.data.pool.ParentPool;
import com.androidtest.data.Interface.IDataManager;
import com.androidtest.data.Interface.IPoolEvent;
import com.androidtest.ui.Fragment.MoviesFragment;
import com.androidtest.util.Constants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataManager {

    private Context mContent;

    private ParentPool pool;
    private HashMap<String, ParentPool> poolsMap;
    private IDataManager dataManagerEventListener;


    public void init(Context context) {

        this.mContent = context;

        poolsMap = new HashMap<>();
    }

    public void requestItems(String fragmentType) {

        if(fragmentType != null && fragmentType.equalsIgnoreCase(Constants.MOVIES_FRAGMENT))
        {
            pool = new MoviesPool();

            pool.registerEventReciever(event);

            pool.initialize(mContent, fragmentType);

            poolsMap.put(fragmentType, pool);

            pool.loadItems();
        }
    }




    public void loadItems(String fragmentType)
    {
        if(poolsMap.get(fragmentType) != null)
        {
            poolsMap.get(fragmentType).loadItems();
        }
    }

    public void registerEventListener(MoviesFragment eventListener) {

        this.dataManagerEventListener = eventListener;
    }

    private IPoolEvent event = new IPoolEvent() {
        @Override
        public void onPoolsEvent(String eventType, Object... args) {

            dataManagerEventListener.onDataManagerEvent(eventType, args);

        }
    };

    public void destroy() {

        if(poolsMap != null && !poolsMap.isEmpty())
        {
            for (Map.Entry<String, ParentPool> entry : poolsMap.entrySet())
            {
                entry.getValue().destroy();
            }

            poolsMap.clear();
        }
    }
}
