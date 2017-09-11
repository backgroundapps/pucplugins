package com.tecgraf.plugins.server.factories;


import com.tecgraf.plugins.common.Plugin;
import com.tecgraf.plugins.common.PluginImpl;
import com.tecgraf.plugins.server.dao.queries.PluginQueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class PluginFactory {

    public static Plugin getPluginByResultSet(ResultSet rst) throws SQLException {
        return new PluginImpl(
                rst.getLong(PluginQueries.FIELDS.ID.name()),
                rst.getString(PluginQueries.FIELDS.NAME.name()),
                rst.getString(PluginQueries.FIELDS.DESCRIPTION.name()),
                rst.getDate(PluginQueries.FIELDS.START_DATE.name())
        );
    }

    public static Object[] getFullDataFilteredByResultSet(ResultSet rst) throws SQLException {
        return new Object[] {
                rst.getLong(PluginQueries.FIELDS.ID.name()),
                rst.getString(PluginQueries.FIELDS.NAME.name()),
                rst.getString(PluginQueries.FIELDS.DESCRIPTION.name()),
                new SimpleDateFormat("dd/MM/yyyy").format(rst.getDate(PluginQueries.FIELDS.START_DATE.name())),
        };
    }
}
