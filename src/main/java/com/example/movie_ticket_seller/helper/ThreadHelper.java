package com.example.movie_ticket_seller.helper;

import java.util.List;

public class ThreadHelper {
    public static void synchronizedMultiple(List<Object> locks, Runnable criticalSection) {
        synchronized (locks.get(0)) {
            if (locks.size() == 1) {
                criticalSection.run();
            } else {
                synchronizedMultiple(locks.subList(1, locks.size()), criticalSection);
            }
        }
    }

}
