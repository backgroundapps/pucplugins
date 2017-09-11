package com.tecgraf.plugins.server.factories;


import com.tecgraf.plugins.common.User;
import com.tecgraf.plugins.common.UserImpl;
import com.tecgraf.plugins.server.dao.queries.UserQueries;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFactory {

    public static User getUserByResultSet(ResultSet rst) throws SQLException {
        return new UserImpl(
                rst.getLong(UserQueries.FIELDS.ID.name()),
                rst.getString(UserQueries.FIELDS.LOGIN.name()),
                rst.getString(UserQueries.FIELDS.FULL_NAME.name()),
                rst.getString(UserQueries.FIELDS.STATUS.name()),
                rst.getString(UserQueries.FIELDS.CURRENT_MANAGEMENT.name())
        );
    }

    public static Object[] getFullDataFilteredByResultSet(ResultSet rst) throws SQLException {
        return new Object[] {
            rst.getLong(UserQueries.FIELDS_DATA.USER_ID.name()),
            rst.getString(UserQueries.FIELDS_DATA.LOGIN.name()),
            rst.getString(UserQueries.FIELDS_DATA.FULL_NAME.name()),
            rst.getString(UserQueries.FIELDS_DATA.STATUS.name()),
            rst.getString(UserQueries.FIELDS_DATA.CURRENT_MANAGEMENT.name()),
            rst.getString(UserQueries.FIELDS_DATA.FUNCITONALITY_NAME.name()),
            rst.getString(UserQueries.FIELDS_DATA.PLUGIN_NAME.name())
        };
    }

}
