package com.cagecfi.productservice.utils;

import java.util.UUID;

public class IdGenerator {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "") +
                UUID.randomUUID().toString().replace("-", "");
    }
}
