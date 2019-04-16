package com.es.phoneshop.model.defence.dos;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DosServiceImpl implements DosService {
    private static final int THRESHOLD = 20;
    private static final int TIME_TO_RESET = 60 * 1000;

    private static DosService instance;
    private Map<String, AtomicInteger> ipCallCount;

    public static DosService getInstance() {
        if (instance == null) {
            synchronized (DosServiceImpl.class) {
                if (instance == null) {
                    instance = new DosServiceImpl();
                }
            }
        }
        return instance;
    }

    private DosServiceImpl() {
        ipCallCount = new ConcurrentHashMap<>();
        reset();
    }

    @Override
    public boolean isAllowed(String ip) {
        AtomicInteger count = ipCallCount.get(ip);
        if (Objects.isNull(count)) {
            count = new AtomicInteger(0);
            ipCallCount.put(ip, count);
        }
        int value = count.incrementAndGet();
        return value < THRESHOLD;
    }

    private void reset() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ipCallCount.clear();
            }
        }, 0, TIME_TO_RESET);
    }
}
