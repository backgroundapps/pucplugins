package com.tecgraf.plugins;


import com.tecgraf.plugins.common.Plugin;
import com.tecgraf.plugins.common.PluginImpl;
import com.tecgraf.plugins.server.dao.PluginDAO;
import com.tecgraf.plugins.server.dao.conf.DB;
import com.tecgraf.plugins.server.dao.utils.StatementBuilderFactory;
import com.tecgraf.plugins.server.dao.utils.StatementDDLBuilder;
import com.tecgraf.plugins.server.dao.utils.StatementDMLBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;


public class PluginDAOTest {
    private PluginDAO pluginDAO;
    private StatementDDLBuilder ddl;
    private StatementDMLBuilder dml;


    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        ddl = StatementBuilderFactory.getDDLBuilderInstance();
        dml = StatementBuilderFactory.getDMLBuilderInstance();

        pluginDAO = new PluginDAO(ddl, dml);
        pluginDAO.create(new PluginImpl("FRAME 1", "FRAME BUILDER", new Date()));
    }

    @Test
    public void shouldReturnNumberOfPlugins() throws SQLException {
        Assert.assertNotNull(pluginDAO.numberOfPlugins());
    }

    @Test
    public void shouldReturnSomePlugin() throws SQLException {
        Assert.assertFalse(pluginDAO.listPlugins().isEmpty());
        Assert.assertNotNull(pluginDAO.listPlugins().get(0).getName());
    }

    @Test
    public void shouldReturnLastPluginID() throws SQLException {
        pluginDAO.create(new PluginImpl("FRAME 2", "FRAME BUILDER", new Date()));
        pluginDAO.create(new PluginImpl("STATUS BAR", "STATUS BAR BUILDER", new Date()));

        Plugin plugin = pluginDAO.lastPlugin();
        Assert.assertEquals(plugin.getId(), pluginDAO.lastId());
    }

    @Test
    public void shouldReturnLastPlugin() throws SQLException {
        pluginDAO.create(new PluginImpl("BUTTON", "BUTTON BUILDER", new Date()));
        pluginDAO.create(new PluginImpl("FRAME 3", "FRAME BUILDER", new Date()));

        Assert.assertEquals("FRAME 3", pluginDAO.lastPlugin().getName());
    }

    @Test
    public void shouldReturnPluginById_1() throws SQLException {
        pluginDAO.create(new PluginImpl("FRAME 4", "FRAME BUILDER", new Date()));
        Long id = pluginDAO.lastId();
        Assert.assertEquals("FRAME 4", pluginDAO.findById(id).getName());
    }

    @Test
    public void shouldReturnNextId() throws SQLException {
        Assert.assertEquals(new Long(pluginDAO.lastId() + 1), pluginDAO.nextId());
    }

    @Test
    public void shouldAddOnePlugin() throws SQLException {
        pluginDAO.create(new PluginImpl("FRAME 5", "FRAME BUILDER", new Date()));
        Assert.assertEquals("FRAME 5", pluginDAO.lastPlugin().getName());
    }

    @Test
    public void shouldUpdateOnePlugin() throws SQLException {
        pluginDAO.create(new PluginImpl("FRAME 6", "FRAME BUILDER", new Date()));
        Assert.assertEquals("FRAME 6", pluginDAO.lastPlugins().getName());

        pluginDAO.update(new PluginImpl(null, "FRAME BUILDER FLEX", new Date()), pluginDAO.lastId());

        Plugin plugin = pluginDAO.findById(pluginDAO.lastId());

        Assert.assertEquals("FRAME 6", plugin.getName());
        Assert.assertEquals("FRAME BUILDER FLEX", plugin.getDescription());
    }



    @Test
    public void shouldRemoveOnePlugin() throws SQLException {
        Plugin plugin = pluginDAO.lastPlugin();

        pluginDAO.create(new PluginImpl("FRAME 7", "FRAME BUILDER", new Date()));
        Assert.assertEquals("FRAME 7", pluginDAO.lastPlugin().getName());

        pluginDAO.delete(pluginDAO.lastId());
        Assert.assertEquals(plugin.getName(), pluginDAO.lastPlugin().getName());
    }

    @After
    public void removePlugins() throws  SQLException{

        DB.restart(ddl, dml);
    }


}
