package com.tecgraf.plugins.server.factories;


import com.tecgraf.plugins.common.Functionality;
import com.tecgraf.plugins.common.FunctionalityImpl;
import com.tecgraf.plugins.server.dao.queries.FunctionalityQueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class FunctionalityFactory {

    public static Functionality getFunctionalityByResultSet(ResultSet rst) throws SQLException {
        return new FunctionalityImpl(
                rst.getLong(FunctionalityQueries.FIELDS.ID.name()),
                rst.getString(FunctionalityQueries.FIELDS.NAME.name()),
                rst.getString(FunctionalityQueries.FIELDS.DESCRIPTION.name()),
                rst.getDate(FunctionalityQueries.FIELDS.START_DATE.name()),
                rst.getLong(FunctionalityQueries.FIELDS.PLUGIN_ID.name())

        );
    }

    public static Object[] getFullDataFilteredByResultSet(ResultSet rst) throws SQLException {
        return new Object[] {
                rst.getLong(FunctionalityQueries.FIELDS.ID.name()),
                rst.getString(FunctionalityQueries.FIELDS.NAME.name()),
                rst.getString(FunctionalityQueries.FIELDS.DESCRIPTION.name()),
                new SimpleDateFormat("dd/MM/yyyy").format(rst.getDate(FunctionalityQueries.FIELDS.START_DATE.name())),
        };
    }
}
