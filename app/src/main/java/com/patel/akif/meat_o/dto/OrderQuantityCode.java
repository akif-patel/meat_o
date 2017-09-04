package com.patel.akif.meat_o.dto;

/**
 * Created by akif_p on 28/06/2017.
 */

public enum OrderQuantityCode {
    KG_1  ("KG_1"),
    KG_2  ("KG_2"),
    GM_1  ("GM_1"),
    NUM   ("NUM");

    private final String unit;

    OrderQuantityCode(String unit) {
        this.unit = unit;
    }
}

