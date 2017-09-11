package com.tecgraf.plugins.common;

import java.util.Date;

public class PluginImpl implements Plugin {
    private Long id;
    private String name;
    private String description;
    private Date startDate;

    public PluginImpl(String name, String description){
        this(null, name, description, null);
    }

    public PluginImpl(Long id, String name, String description){
        this(id, name, description, null);
    }

    public PluginImpl(String name, String description, Date startDate){
        this(null, name, description, startDate);
    }

    public PluginImpl(Long id, String name, String description, Date startDate){
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
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
    public Date getStartDate() {
        return startDate;
    }
}
