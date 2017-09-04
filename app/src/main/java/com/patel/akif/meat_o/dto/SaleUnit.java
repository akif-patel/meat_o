package com.patel.akif.meat_o.dto;

/**
 * Created by akif_p on 28/06/2017.
 */

public enum SaleUnit {
    KG  ("KG"),
    GM  ("GM"),
    DZ  ("DZ"),
    PK  ("PK"),
    PC  ("PC");

    private final String unit;

    SaleUnit (String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        switch(this) {
            case KG: return "Kilogram";
            case GM: return "Gram";
            case DZ: return "Dozen";
            case PK: return "Pack";
            case PC: return "Piece";
            default: throw new IllegalArgumentException();
        }
    }
}

