package com.tecgraf.plugins.server.process;


import com.tecgraf.plugins.common.Functionality;
import com.tecgraf.plugins.common.User;
import com.tecgraf.plugins.common.UserFunctionality;
import com.tecgraf.plugins.server.dao.UserFunctionalityPermissionDAO;
import com.tecgraf.plugins.server.dao.utils.StatementBuilderFactory;
import com.tecgraf.plugins.server.dao.utils.StatementDDLBuilder;
import com.tecgraf.plugins.server.dao.utils.StatementDMLBuilder;

import java.sql.SQLException;
import java.util.List;

public class PermissionProcess {
    public List<UserFunctionality> getPermissions() throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new UserFunctionalityPermissionDAO(ddl).listUserFunctionalities();

        }finally {
            ddl.close();
        }
    }

    public Long numberOfUserFunctionalities() throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new UserFunctionalityPermissionDAO(ddl).numberOfUserFunctionalities();

        }finally {
            ddl.close();
        }
    }

    public List<UserFunctionality> listUserFunctionalities() throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new UserFunctionalityPermissionDAO(ddl).listUserFunctionalities();

        }finally {
            ddl.close();
        }
    }


    public List<UserFunctionality> findUsersFromFunctionality(Functionality functionality) throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new UserFunctionalityPermissionDAO(ddl).findUsersFromFunctionality(functionality);

        }finally {
            ddl.close();
        }
    }

    public List<UserFunctionality> findFunctionalitiesByUser(User user) throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new UserFunctionalityPermissionDAO(ddl).findFunctionalitiesByUser(user);

        }finally {
            ddl.close();
        }
    }


    public boolean addPermission(User user, Functionality functionality) throws SQLException {
        StatementDMLBuilder dml = StatementBuilderFactory.getDMLBuilderInstance();
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new UserFunctionalityPermissionDAO(ddl, dml).addPermission(user, functionality);

        }finally {
            ddl.close();
            dml.close();
        }
    }

    public boolean removePermission(User user, Functionality functionality) throws SQLException {
        StatementDMLBuilder dml = StatementBuilderFactory.getDMLBuilderInstance();
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new UserFunctionalityPermissionDAO(ddl, dml).removePermission(user, functionality);

        }finally {
            ddl.close();
            dml.close();
        }
    }

    public boolean removePermissionById(Long user, Long functionality) throws SQLException {
        StatementDMLBuilder dml = StatementBuilderFactory.getDMLBuilderInstance();
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new UserFunctionalityPermissionDAO(ddl, dml).removePermissionById(user, functionality);

        }finally {
            ddl.close();
            dml.close();
        }
    }


}
