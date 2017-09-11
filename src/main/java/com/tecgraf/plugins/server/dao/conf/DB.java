package com.tecgraf.plugins.server.dao.conf;


import com.tecgraf.plugins.common.UserImpl;
import com.tecgraf.plugins.server.dao.UserDAO;
import com.tecgraf.plugins.server.dao.queries.FunctionalityQueries;
import com.tecgraf.plugins.server.dao.queries.PluginQueries;
import com.tecgraf.plugins.server.dao.queries.UserFunctionalityPermissionQueries;
import com.tecgraf.plugins.server.dao.queries.UserQueries;
import com.tecgraf.plugins.server.dao.utils.StatementBuilderFactory;
import com.tecgraf.plugins.server.dao.utils.StatementDDLBuilder;
import com.tecgraf.plugins.server.dao.utils.StatementDMLBuilder;

import java.sql.SQLException;

public class DB {

    public static void restart() throws SQLException {
        StatementDMLBuilder dml = StatementBuilderFactory.getDMLBuilderInstance();
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();

        restart(ddl, dml);
    }

    public static void restart(StatementDDLBuilder ddl, StatementDMLBuilder dml) throws SQLException {
        dml = StatementBuilderFactory.getDMLBuilderInstance();
        ddl = StatementBuilderFactory.getDDLBuilderInstance();

        try {
            runDeletes(dml);
            createAdmins(ddl, dml);

        }finally {
            dml.close();
            ddl.close();
        }
    }

    private static void runDeletes(StatementDMLBuilder dml) throws SQLException {
        dml.addSQL(UserFunctionalityPermissionQueries.deleteAll()).build();
        dml.addSQL(FunctionalityQueries.deleteAll()).build();
        dml.addSQL(PluginQueries.deleteAll()).build();
        dml.addSQL(UserQueries.deleteAll()).build();
    }

    private static void createAdmins(StatementDDLBuilder ddl, StatementDMLBuilder dml) throws SQLException {
        new UserDAO(ddl, dml).create(new UserImpl("ADMIN", "JOSE ADMIN", "ACTIVE", "Y"));
        new UserDAO(ddl, dml).create(new UserImpl("ALMOST_ADMIN", "ADMIN JR", "ACTIVE", "Y"));
    }
}
