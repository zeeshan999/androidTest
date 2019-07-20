package com.androidtest.data.Parser;

import com.androidtest.data.Interface.IParsingEvent;

public class ParentParser {

    protected IParsingEvent parsingEvent;

    public void registerEventListener(IParsingEvent parsingListener) {

        this.parsingEvent = parsingListener;
    }

    public void performParsing(String response) {
    }
}
