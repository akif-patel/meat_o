package com.patel.akif.meat_o.dto;

/**
 * Created by akif_p on 18/06/2017.
 */

public enum Status {
    INACTIVE                (0),
    ACTIVE                  (1),
    PENDING_VERIFICATION    (2);

    private final int status;

    Status (int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }
}
