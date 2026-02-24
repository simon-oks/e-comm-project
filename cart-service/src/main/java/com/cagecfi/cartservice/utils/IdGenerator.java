package com.cagecfi.cartservice.utils;

import java.util.UUID;

public class IdGenerator {

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "") +
                UUID.randomUUID().toString().replace("-", "");
    }
}
