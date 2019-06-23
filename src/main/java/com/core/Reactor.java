package com.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Reactor {
    private static final Logger logger = LoggerFactory.getLogger(Reactor.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(6);

    public void submitTask() {

    }
}
