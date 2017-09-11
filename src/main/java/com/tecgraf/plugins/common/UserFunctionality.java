package com.tecgraf.plugins.common;

import java.io.Serializable;

public interface UserFunctionality extends Serializable {
    User getUser();

    void setUser(User user);

    Functionality getFunctionality();

    void setFunctionality(Functionality functionality);
}
