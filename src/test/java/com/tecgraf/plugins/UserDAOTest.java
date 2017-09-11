package com.tecgraf.plugins;


import com.tecgraf.plugins.common.FunctionalityImpl;
import com.tecgraf.plugins.common.PluginImpl;
import com.tecgraf.plugins.common.User;
import com.tecgraf.plugins.common.UserImpl;
import com.tecgraf.plugins.server.dao.FunctionalityDAO;
import com.tecgraf.plugins.server.dao.PluginDAO;
import com.tecgraf.plugins.server.dao.UserDAO;
import com.tecgraf.plugins.server.dao.UserFunctionalityPermissionDAO;
import com.tecgraf.plugins.server.dao.conf.DB;
import com.tecgraf.plugins.server.dao.utils.StatementBuilderFactory;
import com.tecgraf.plugins.server.dao.utils.StatementDDLBuilder;
import com.tecgraf.plugins.server.dao.utils.StatementDMLBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;


public class UserDAOTest {
    private UserDAO userDAO;
    private PluginDAO pluginDAO;
    private FunctionalityDAO functionalityDAO;
    private StatementDMLBuilder dml;
    private StatementDDLBuilder ddl;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        ddl = StatementBuilderFactory.getDDLBuilderInstance();
        dml = StatementBuilderFactory.getDMLBuilderInstance();

        userDAO = new UserDAO(ddl, dml);
        pluginDAO = new PluginDAO(ddl, dml);
        functionalityDAO = new FunctionalityDAO(ddl, dml);
        userDAO.create(new UserImpl("ARIOSVALDO", "ARIOSVALDO LENNON", "ACTIVE", "Y"));
    }

    @Test
    public void shouldReturnNumberOfUsers() throws SQLException {
        Assert.assertNotNull(userDAO.numberOfUsers());
    }

    @Test
    public void shouldReturnSomeOne() throws SQLException {
        Assert.assertFalse(userDAO.listUsers().isEmpty());
        Assert.assertNotNull(userDAO.listUsers().get(0).getLogin());

    }

    @Test
    public void shouldReturnLastUserID() throws SQLException {
        userDAO.create(new UserImpl("DE_MENO", "DE MENO", "ACTIVE", "Y"));
        userDAO.create(new UserImpl("ZE_PEQUENO", "ZE PEQUENO", "ACTIVE", "Y"));

        User user = userDAO.lastUser();
        Assert.assertEquals(user.getId(), userDAO.lastId());

    }

    @Test
    public void shouldReturnLastUser() throws SQLException {
        userDAO.create(new UserImpl("ABADIAS", "ABADIAS ZATTA", "ACTIVE", "Y"));
        userDAO.create(new UserImpl("COTEFILL", "COTEFILL ZATTA", "ACTIVE", "Y"));

        Assert.assertEquals("COTEFILL", userDAO.lastUser().getLogin());

    }

    @Test
    public void shouldReturnUserById_1() throws SQLException {
        userDAO.create(new UserImpl("PILDAS", "PILDAS ZATTA", "ACTIVE", "Y"));
        Long id = userDAO.lastId();
        Assert.assertEquals("PILDAS", userDAO.findById(id).getLogin());

    }

    @Test
    public void shouldReturnNextId() throws SQLException {
        Assert.assertEquals(new Long(userDAO.lastId() + 1), userDAO.nextId());
    }

    @Test
    public void shouldAddOneUser() throws SQLException {
        userDAO.create(new UserImpl("BIL", "BILLY MENDY", "ACTIVE", "Y"));
        Assert.assertEquals("BIL", userDAO.lastUser().getLogin());

    }

    @Test
    public void shouldUpdateOneUser() throws SQLException {
        userDAO.create(new UserImpl("BOB", "BOB ZATTA", "ACTIVE", "Y"));
        Assert.assertEquals("BOB", userDAO.lastUser().getLogin());

        userDAO.update(new UserImpl(null, null, "INACTIVE", "N"), userDAO.lastId());

        User user = userDAO.findById(userDAO.lastId());

        Assert.assertEquals("BOB", user.getLogin());
        Assert.assertEquals("BOB ZATTA", user.getFullName());
        Assert.assertEquals("INACTIVE", user.getStatus());
        Assert.assertEquals("N", user.getCurrentManagement());

    }

    @Test
    public void shouldCancelUser() throws SQLException {
        userDAO.create(new UserImpl("ROMERO", "ROMERO ZATTA", "ACTIVE", "Y"));
        Assert.assertEquals("ROMERO", userDAO.lastUser().getLogin());

        userDAO.cancelUser(userDAO.lastId());
        Assert.assertEquals("INACTIVE", userDAO.lastUser().getStatus());
    }


    @Test
    public void shouldRemoveOneUser() throws SQLException {
        User user = userDAO.lastUser();

        userDAO.create(new UserImpl("RODOVILSON", "RODOVILSON ZATTA", "ACTIVE", "Y"));
        Assert.assertEquals("RODOVILSON", userDAO.lastUser().getLogin());

        userDAO.delete(userDAO.lastId());
        Assert.assertEquals(user.getLogin(), userDAO.lastUser().getLogin());
    }

    @Test
    public void shouldFilter() throws SQLException {
        new PluginDAO(ddl, dml).create(new PluginImpl("PLUGIN", "PLUGIN"));
        new FunctionalityDAO(ddl, dml).create(new FunctionalityImpl("FUNCTIONALITY", "FUNCTIONALITY", pluginDAO.lastId()));
        new UserFunctionalityPermissionDAO(ddl, dml).addPermission(new UserImpl(userDAO.lastId()), new FunctionalityImpl(functionalityDAO.lastId()));


        Object[][] data = userDAO.getFullUserData(null, null, null, null, null, null);
        Assert.assertNotNull(data);

    }

    @Test
    public void shouldFilterADMIN() throws SQLException {
        new PluginDAO(ddl, dml).create(new PluginImpl("PLUGIN", "PLUGIN"));
        new FunctionalityDAO(ddl, dml).create(new FunctionalityImpl("FUNCTIONALITY", "FUNCTIONALITY", pluginDAO.lastId()));
        new UserFunctionalityPermissionDAO(ddl, dml).addPermission(new UserImpl(userDAO.lastId()), new FunctionalityImpl(functionalityDAO.lastId()));


        Object[][] data = userDAO.getFullUserData("ADMIN", null, null, null, null, null);
        Assert.assertNotNull(data);
        Assert.assertEquals("ADMIN", data[0][1]);

    }

    @Test
    public void shouldFilterFullName() throws SQLException {
        new PluginDAO(ddl, dml).create(new PluginImpl("PLUGIN", "PLUGIN"));
        new FunctionalityDAO(ddl, dml).create(new FunctionalityImpl("FUNCTIONALITY", "FUNCTIONALITY", pluginDAO.lastId()));
        new UserFunctionalityPermissionDAO(ddl, dml).addPermission(new UserImpl(userDAO.lastId()), new FunctionalityImpl(functionalityDAO.lastId()));


        Object[][] data = userDAO.getFullUserData(null, "JOSE ADMIN", null, null, null, null);
        Assert.assertNotNull(data);
        Assert.assertEquals("ADMIN", data[0][1]);

    }

    @Test
    public void shouldFilterStatus() throws SQLException {
        new PluginDAO(ddl, dml).create(new PluginImpl("PLUGIN", "PLUGIN"));
        new FunctionalityDAO(ddl, dml).create(new FunctionalityImpl("FUNCTIONALITY", "FUNCTIONALITY", pluginDAO.lastId()));
        new UserFunctionalityPermissionDAO(ddl, dml).addPermission(new UserImpl(userDAO.lastId()), new FunctionalityImpl(functionalityDAO.lastId()));


        Object[][] data = userDAO.getFullUserData(null, null,  "ACTIVE", null, null, null);
        Assert.assertNotNull(data);

    }


    @Test
    public void shouldFilterCurrentManagement() throws SQLException {
        new PluginDAO(ddl, dml).create(new PluginImpl("PLUGIN", "PLUGIN"));
        new FunctionalityDAO(ddl, dml).create(new FunctionalityImpl("FUNCTIONALITY", "FUNCTIONALITY", pluginDAO.lastId()));
        new UserFunctionalityPermissionDAO(ddl, dml).addPermission(new UserImpl(userDAO.lastId()), new FunctionalityImpl(functionalityDAO.lastId()));


        Object[][] data = userDAO.getFullUserData(null, null, null, "Y", null, null);
        Assert.assertNotNull(data);

    }

    @Test
    public void shouldFilterPlugin() throws SQLException {
        new PluginDAO(ddl, dml).create(new PluginImpl("PLUGIN", "PLUGIN"));
        new FunctionalityDAO(ddl, dml).create(new FunctionalityImpl("FUNCTIONALITY", "FUNCTIONALITY", pluginDAO.lastId()));
        new UserFunctionalityPermissionDAO(ddl, dml).addPermission(new UserImpl(userDAO.lastId()), new FunctionalityImpl(functionalityDAO.lastId()));


        Object[][] data = userDAO.getFullUserData(null, null, null, null, "PLUGIN", null);
        Assert.assertNotNull(data);
        Assert.assertNotNull(data[0][1]);

    }

    @Test
    public void shouldFilterFunctionality() throws SQLException {
        new PluginDAO(ddl, dml).create(new PluginImpl("PLUGIN", "PLUGIN"));
        new FunctionalityDAO(ddl, dml).create(new FunctionalityImpl("FUNCTIONALITY", "FUNCTIONALITY", pluginDAO.lastId()));
        new UserFunctionalityPermissionDAO(ddl, dml).addPermission(new UserImpl(userDAO.lastId()), new FunctionalityImpl(functionalityDAO.lastId()));


        Object[][] data = userDAO.getFullUserData(null, null , null, null, null, "FUNCTIONALITY");
        Assert.assertNotNull(data[0][1]);

    }



    @Test
    public void shouldFilterAdminManager() throws SQLException {
        new PluginDAO(ddl, dml).create(new PluginImpl("PLUGIN", "PLUGIN"));
        new FunctionalityDAO(ddl, dml).create(new FunctionalityImpl("FUNCTIONALITY", "FUNCTIONALITY", new PluginDAO(ddl, dml).lastId()));
        new UserFunctionalityPermissionDAO(ddl, dml).addPermission(new UserImpl(userDAO.lastId()), new FunctionalityImpl(new FunctionalityDAO(ddl, dml).lastId()));


        Object[][] data = userDAO.getFullUserData("ADMIN", null, null, "Y", null, null);
        Assert.assertNotNull(data);
        Assert.assertEquals("ADMIN", data[0][1]);

    }


    @Test
    public void shouldFilterNoInfo() throws SQLException {
        new PluginDAO(ddl, dml).create(new PluginImpl("PLUGIN", "PLUGIN"));
        new FunctionalityDAO(ddl, dml).create(new FunctionalityImpl("FUNCTIONALITY", "FUNCTIONALITY", pluginDAO.lastId()));
        new UserFunctionalityPermissionDAO(ddl, dml).addPermission(new UserImpl(userDAO.lastId()), new FunctionalityImpl(functionalityDAO.lastId()));

        Object[][] data = userDAO.getFullUserData("LOGIN NOT FOUND", null, null, null, null, null);
        Assert.assertNull(data);

    }


    @After
    public void deleteAllElements() throws SQLException {
        DB.restart(ddl, dml);

    }


}
