package com.tecgraf.plugins.common;

import java.util.Date;

public class FunctionalityImpl implements Functionality {
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Long pluginID;

    public FunctionalityImpl(Long id){
        this(id, null, null, null, null);
    }

    public FunctionalityImpl(String name, String description, Long pluginID ){
        this(null, name, description, null, pluginID);
    }

    public FunctionalityImpl(String name, String description, Date startDate, Long pluginID ){
        this(null, name, description, startDate, pluginID);
    }

    public FunctionalityImpl(Long id, String name, String description, Date startDate, Long pluginID){
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.pluginID = pluginID;

    }

    @Override
    public Long getId() { return id; }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public Date getStartDate() { return startDate; }
    @Override
    public Long getPluginId() { return pluginID; }
}
