package com.androidtest.data.Dataloader.Volley;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import java.io.File;

public class RequestQueue
{
    public static final String VOLLEY_DIRECTORY = "volley";
	private static RequestQueue mInstance;
   
	private com.android.volley.RequestQueue mRequestQueue;
    
    private static Context mCtx;
    
    private static Cache cache = null;


    private RequestQueue(Context context)
    {
        mCtx = context;

        File volleyCacheDir = new File(mCtx.getCacheDir(), VOLLEY_DIRECTORY);
        
        cache = new DiskBasedCache(volleyCacheDir, 1000 * 1024 *1024);
    	
        mRequestQueue = getRequestQueue(cache);
    }

    public static synchronized RequestQueue getInstance(Context context)
    {
        if (mInstance == null) 
        {
            mInstance = new RequestQueue(context);
        }
        
        return mInstance;
    }

    public com.android.volley.RequestQueue getRequestQueue(Cache cache)
    {
        if (mRequestQueue == null)
        {
        	Network network = new BasicNetwork(new HurlStack());
        	
            mRequestQueue = new com.android.volley.RequestQueue(cache, network);
            
            mRequestQueue.start();
        }
        
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue(cache).add(req);
    }
    

}

