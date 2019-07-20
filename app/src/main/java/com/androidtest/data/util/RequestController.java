package com.androidtest.data.util;

import android.content.Context;

import com.androidtest.data.Dataloader.DataLoader;
import com.androidtest.data.Interface.IDataLoader;
import com.androidtest.data.Interface.IParsingEvent;
import com.androidtest.data.Interface.IRequestController;
import com.androidtest.data.Parser.MoviesParser;
import com.androidtest.data.Parser.ParentParser;
import com.androidtest.util.Constants;

public class RequestController {


    private String fragmentType;
    private IRequestController listener;
    private DataLoader dataLoader;
    private Context mContent;
    private ParentParser parser;



    public void requestResponse(Context context, String url, String fragmentType) {

        this.fragmentType = fragmentType;
        this.mContent = context;

        if(dataLoader == null)
        {
            dataLoader = new DataLoader();
            dataLoader.registerEventListener(dataLoaderEvents);
        }


        dataLoader.loadServerResponse(mContent, url);


    }

    public void registerEventListener(IRequestController eventListener) {

        this.listener = eventListener;
    }


    IDataLoader dataLoaderEvents = new IDataLoader() {
        @Override
        public void onDataLoaderEventListener(String eventType, Object... args) {

            switch (eventType)
            {
                case Constants.ON_SUCCESS:

                    initializeParser();

                    parser.performParsing((String) args[0]);

                    break;

                case Constants.ON_FAILURE:
                    listener.onRequestControllerResponse(Constants.ON_FAILURE);

                    break;
            }

        }
    };


    private void initializeParser()
    {
        switch (fragmentType)
        {
            case Constants.MOVIES_FRAGMENT:

                parser = new MoviesParser();

                break;
        }


        parser.registerEventListener(parsingListener);
    }


    private IParsingEvent parsingListener = new IParsingEvent() {
        @Override
        public void onParsingEvent(String eventType, Object... args) {

            if(listener != null)
                listener.onRequestControllerResponse(eventType, args);

        }
    };

    public void destroy() {
    }
}
