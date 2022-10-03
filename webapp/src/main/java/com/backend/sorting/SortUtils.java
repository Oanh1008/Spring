package com.backend.sorting;

import java.util.HashMap;
import java.util.Map;

import com.backend.entity.Customer;

public class SortUtils {
    private SortUtils() {
    }

    /**
     * Key:   URL Sort Parameter
     * Value: Table Column
     */
    public static Map<String, String> CUSTOMER_ORDER_MAP = new HashMap<String, String>() {
        private static final long serialVersionUID = 4293312786696007788L;

        {
            put("firstName", Customer.FIRST_NAME);
            put("lastName", Customer.LAST_NAME);
            put("email", Customer.EMAIL);
        }
    };
}