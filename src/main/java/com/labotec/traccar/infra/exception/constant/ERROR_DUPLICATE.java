package com.labotec.traccar.infra.exception.constant;

import java.util.Map;

public class ERROR_DUPLICATE {
    public static final Map<String, String> UNIQUE_CONSTRAINT_MESSAGES = Map.of(
            "tc_users.email", "The email address is already in use.",
            "tc_users.login", "The username is already in use."
    );
}
