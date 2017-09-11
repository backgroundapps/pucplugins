package com.tecgraf.plugins.common;

import java.io.Serializable;
import java.util.Date;

public interface Functionality extends Serializable {

    Long getId();

    String getName();

    String getDescription();

    Date getStartDate();

    Long getPluginId();

}
