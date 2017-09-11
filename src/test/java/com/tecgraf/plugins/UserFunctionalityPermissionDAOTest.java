package com.tecgraf.plugins;


import com.tecgraf.plugins.common.*;
import com.tecgraf.plugins.server.dao.FunctionalityDAO;
import com.tecgraf.plugins.server.dao.PluginDAO;
import com.tecgraf.plugins.server.dao.UserDAO;
import com.tecgraf.plugins.server.dao.UserFunctionalityPermissionDAO;
import com.tecgraf.plugins.server.dao.conf.DB;
import com.tecgraf.plugins.server.dao.utils.StatementBuilderFactory;
import com.tecgraf.plugins.server.dao.utils.StatementDDLBuilder;
import com.tecgraf.plugins.server.dao.utils.StatementDMLBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;


public class UserFunctionalityPermissionDAOTest {
    private UserDAO userDAO;
    private FunctionalityDAO functionalityDAO;
    private UserFunctionalityPermissionDAO dao;

    private PluginImpl plugin;
    private User user;

    private StatementDMLBuilder dml;
    private StatementDDLBuilder ddl;

    private void persistPlugin() throws SQLException {
        plugin = new PluginImpl("PLUGIN", "PLUGINBUILDER");
        PluginDAO pdao = new PluginDAO(ddl, dml);
        pdao.create(plugin );
        plugin = (PluginImpl) pdao.lastPlugin();
    }
    
    private void addFuncinalities() throws SQLException {
        String[] functionalitiesName = {"CREATE", "READ", "UPDATE", "REMOVE"};
        for (int i = 0; i < functionalitiesName.length; i++) {
            functionalityDAO.create(new FunctionalityImpl(functionalitiesName[i], functionalitiesName[i], plugin.getId()));
        }
    }

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        ddl = StatementBuilderFactory.getDDLBuilderInstance();
        dml = StatementBuilderFactory.getDMLBuilderInstance();

        userDAO = new UserDAO(ddl, dml);
        functionalityDAO = new FunctionalityDAO(ddl, dml);
        dao = new UserFunctionalityPermissionDAO(ddl, dml);

        user = new UserImpl("ARIOSVALDO", "ARIOSVALDO LENNON", "ACTIVE", "Y");
        userDAO.create(user);
        user = userDAO.lastUser();

        persistPlugin();
        addFuncinalities();

        Functionality functionality = functionalityDAO.findByName("CREATE");
        assertTrue(dao.addPermission(user, functionality));


    }


    @Test
    public void shouldReturnNumberOfUserFunctionalities() throws SQLException {
        assertNotNull(dao.numberOfUserFunctionalities());
    }

    @Test
    public void shouldReturnSomeUserFunctionality() throws SQLException {
        assertFalse(dao.listUserFunctionalities().isEmpty());
        assertNotNull(dao.listUserFunctionalities().get(0).getUser());
        assertNotNull(dao.listUserFunctionalities().get(0).getFunctionality());
    }

    @Test
    public void shouldReturnSomeUserFromFunctionality() throws SQLException {
        Functionality functionality = functionalityDAO.findByName("CREATE");
        assertEquals(user.getId(), dao.findUsersFromFunctionality(functionality).get(0).getUser().getId());
    }


    @Test
    public void shouldReturnSomeFunctionalitiesByUser() throws SQLException {
        assertNotNull(dao.findFunctionalitiesByUser(user).get(0).getFunctionality().getId());
    }

    @Test
    public void shouldAddCREATEPermission() throws SQLException {
        Functionality functionality = functionalityDAO.findByName("READ");
        assertTrue(dao.addPermission(user, functionality));
    }

    @Test
    public void shouldRemovePermission() throws SQLException {
        Functionality functionality = functionalityDAO.findByName("CREATE");
        assertTrue(dao.removePermission(user, functionality));
    }

    @After
    public void deleteAllElements() throws SQLException {
        DB.restart(ddl, dml);

    }


}
