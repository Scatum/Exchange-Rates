package com.example.engine.engine;

import com.example.engine.service.RatesService;
import com.example.engine.serviceImpl.RatesServiceImpl;

public class Engine {
    private static volatile Engine instance = null;

    RatesService ratesService = null;


    private Engine() {
    }

    public static Engine get() {
        if (instance == null) {
            synchronized (Engine.class) {
                if (instance == null) {
                    instance = new Engine();
                }
            }
        }
        return instance;
    }

    public synchronized RatesService getRatesService(){
        if (ratesService == null) {
            ratesService = new RatesServiceImpl();
        }
        return ratesService;
    }


}
