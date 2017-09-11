package com.tecgraf.plugins.server.factories;

import com.tecgraf.plugins.common.FunctionalityImpl;
import com.tecgraf.plugins.common.UserFunctionality;
import com.tecgraf.plugins.common.UserFunctionalityImpl;
import com.tecgraf.plugins.common.UserImpl;
import com.tecgraf.plugins.server.dao.queries.UserFunctionalityPermissionQueries;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFunctionalityFactory {
    public static UserFunctionality getUserFunctionalityByResultSet(ResultSet result) throws SQLException {
        return new UserFunctionalityImpl(
                new UserImpl(result.getLong(UserFunctionalityPermissionQueries.FIELDS.USER_ID.name())),
                new FunctionalityImpl(result.getLong(UserFunctionalityPermissionQueries.FIELDS.FUNCTIONALITY_ID.name()))

        );
    }
}
