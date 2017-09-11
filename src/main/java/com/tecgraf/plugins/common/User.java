package com.tecgraf.plugins.common;

import java.io.Serializable;

public interface User extends Serializable {
    Long getId();

    String getLogin();

    String getFullName();

    String getStatus();

    String getCurrentManagement();
}
