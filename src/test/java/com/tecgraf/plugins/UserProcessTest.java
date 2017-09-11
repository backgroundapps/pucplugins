package com.tecgraf.plugins;


import com.tecgraf.plugins.common.User;
import com.tecgraf.plugins.common.UserImpl;
import com.tecgraf.plugins.server.dao.conf.DB;
import com.tecgraf.plugins.server.process.UserProcess;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;


public class UserProcessTest {
    private UserProcess userProcess;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        userProcess = new UserProcess();
        userProcess.create(new UserImpl("ARIOSVALDO", "ARIOSVALDO LENNON", "ACTIVE", "Y"));
    }

    @Test
    public void shouldReturnNumberOfUsers() throws SQLException {
        Assert.assertNotNull(userProcess.getNumberOfUsers());
    }

    @Test
    public void shouldReturnSomeOne() throws SQLException {
        Assert.assertFalse(userProcess.getUsers().isEmpty());
        Assert.assertNotNull(userProcess.getUsers().get(0).getLogin());

    }

    @Test
    public void shouldReturnLastUserID() throws SQLException {
        userProcess.create(new UserImpl("DE_MENO", "DE MENO", "ACTIVE", "Y"));
        userProcess.create(new UserImpl("ZE_PEQUENO", "ZE PEQUENO", "ACTIVE", "Y"));

        User user = userProcess.lastUser();
        Assert.assertEquals(user.getId(), userProcess.lastId());

    }

    @Test
    public void shouldReturnLastUser() throws SQLException {
        userProcess.create(new UserImpl("ABADIAS", "ABADIAS ZATTA", "ACTIVE", "Y"));
        userProcess.create(new UserImpl("COTEFILL", "COTEFILL ZATTA", "ACTIVE", "Y"));

        Assert.assertEquals("COTEFILL", userProcess.lastUser().getLogin());

    }

    @Test
    public void shouldReturnUserById_1() throws SQLException {
        userProcess.create(new UserImpl("PILDAS", "PILDAS ZATTA", "ACTIVE", "Y"));
        Long id = userProcess.lastId();
        Assert.assertEquals("PILDAS", userProcess.findById(id).getLogin());

    }

    @Test
    public void shouldReturnNextId() throws SQLException {
        Assert.assertEquals(new Long(userProcess.lastId() + 1), userProcess.nextId());
    }

    @Test
    public void shouldAddOneUser() throws SQLException {
        userProcess.create(new UserImpl("BIL", "BILLY MENDY", "ACTIVE", "Y"));
        Assert.assertEquals("BIL", userProcess.lastUser().getLogin());

    }

    @Test
    public void shouldUpdateOneUser() throws SQLException {
        userProcess.create(new UserImpl("BOB", "BOB ZATTA", "ACTIVE", "Y"));
        Assert.assertEquals("BOB", userProcess.lastUser().getLogin());

        userProcess.update(new UserImpl(null, null, "INACTIVE", "N"), userProcess.lastId());

        User user = userProcess.findById(userProcess.lastId());

        Assert.assertEquals("BOB", user.getLogin());
        Assert.assertEquals("BOB ZATTA", user.getFullName());
        Assert.assertEquals("INACTIVE", user.getStatus());
        Assert.assertEquals("N", user.getCurrentManagement());

    }

    @Test
    public void shouldCancelUser() throws SQLException {
        userProcess.create(new UserImpl("ROMERO", "ROMERO ZATTA", "ACTIVE", "Y"));
        Assert.assertEquals("ROMERO", userProcess.lastUser().getLogin());

        userProcess.cancelUser(userProcess.lastId());
        Assert.assertEquals("INACTIVE", userProcess.lastUser().getStatus());
    }


    @Test
    public void shouldRemoveOneUser() throws SQLException {
        User user = userProcess.lastUser();

        userProcess.create(new UserImpl("RODOVILSON", "RODOVILSON ZATTA", "ACTIVE", "Y"));
        Assert.assertEquals("RODOVILSON", userProcess.lastUser().getLogin());

        userProcess.delete(userProcess.lastId());
        Assert.assertEquals(user.getLogin(), userProcess.lastUser().getLogin());
    }

    @Test
    public void shouldLogin() throws SQLException {
        Assert.assertTrue(userProcess.isValidLogin("ADMIN"));
    }


    @After
    public void deleteAllElements() throws SQLException {
        DB.restart();
    }


}
