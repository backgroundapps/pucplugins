package com.tecgraf.plugins.server.dao;


import com.tecgraf.plugins.common.Plugin;
import com.tecgraf.plugins.server.dao.utils.StatementDDLBuilder;
import com.tecgraf.plugins.server.dao.utils.StatementDMLBuilder;
import com.tecgraf.plugins.server.factories.PluginFactory;
import com.tecgraf.plugins.server.process.utils.Strings;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.tecgraf.plugins.server.dao.queries.PluginQueries.*;


public class PluginDAO {
    private StatementDDLBuilder ddl;
    private StatementDMLBuilder dml;

    public PluginDAO() {
    }

    public PluginDAO(StatementDDLBuilder ddl) {
        this.ddl = ddl;
    }



    public PluginDAO(StatementDDLBuilder ddl, StatementDMLBuilder dml) {
        this.ddl = ddl;
        this.dml = dml;
    }

    public Long numberOfPlugins() throws SQLException {
        Long id = null;

        ddl.addSQL(countId()).build();

        while(ddl.getResult().next()){
            id = ddl.getResult().getLong(1);
        }
        return id;
    }

    public List<Plugin> listPlugins() throws SQLException {

        List<Plugin> list = new ArrayList<>();
        ddl.addSQL(selectAll()).build();
        while(ddl.getResult().next()){
            list.add(PluginFactory.getPluginByResultSet(ddl.getResult()));
        }
        return list;
    }

    public Plugin lastPlugin() throws SQLException {
        Plugin plugin = null;

        Long id = lastId();
        ddl.addSQL(selectById());
        ddl.preparingStatement().setLong(1, id);
        ddl.build();

        while(ddl.getResult().next()){
            plugin = PluginFactory.getPluginByResultSet(ddl.getResult());

        }
        return plugin;
    }

    public Long lastId() throws SQLException {
        Long lastID = null;
        ddl.addSQL(maxId()).build();
        while(ddl.getResult().next()){
            lastID = ddl.getResult().getLong(1);
        }
        return lastID;
    }

    public Plugin findById(Long id) throws SQLException {
        Plugin plugin = null;

        ddl.addSQL(selectById());
        ddl.preparingStatement().setLong(1, id);
        ddl.build();
        while(ddl.getResult().next()){
            plugin = PluginFactory.getPluginByResultSet(ddl.getResult());
        }
        return plugin;
    }

    public Plugin findByName(String name) throws SQLException {
        Plugin plugin = null;

        ddl.addSQL(selectByName());
        ddl.preparingStatement().setString(1, name);
        ddl.build();
        while(ddl.getResult().next()){
            plugin = PluginFactory.getPluginByResultSet(ddl.getResult());
        }
        return plugin;
    }

    public Long nextId() throws SQLException {
        return lastId() + 1;
    }

    public Plugin lastPlugins() throws SQLException {
        return findById(lastId());
    }

    public boolean create(Plugin plugin) throws SQLException {
        dml.addSQL(insert());
        dml.preparingStatement().setString(1, plugin.getName());
        dml.preparingStatement().setString(2, plugin.getDescription());

        //executeUpdate: return number of rows inserted
        return dml.build().getResultValue() > 0;
    }

    public boolean update(Plugin updatedPlugin, Long id) throws SQLException {
        //First get the current user values
        Plugin oldPlugin = findById(id);

        dml.addSQL(updateById());
        dml.preparingStatement().setString(1, updatedPlugin.getName() != null ? updatedPlugin.getName() : oldPlugin.getName());
        dml.preparingStatement().setString(2, updatedPlugin.getDescription() != null ? updatedPlugin.getDescription() : oldPlugin.getDescription());
        dml.preparingStatement().setDate(3, updatedPlugin.getStartDate() != null ? new Date(updatedPlugin.getStartDate().getTime()) : new Date( oldPlugin.getStartDate().getTime()));
        dml.preparingStatement().setLong(4, id);

        //executeUpdate: return number of rows inserted
        return dml.build().getResultValue() > 0;
    }

    public boolean delete(Long id) throws SQLException {
        dml.addSQL(deleteById());
        dml.preparingStatement().setLong(1, id);
        return dml.build().getResultValue() > 0;
    }

    public boolean deleteAllElements() throws  SQLException{
        dml.addSQL(deleteAll());
        return dml.build().getResultValue() > 0;
    }


    public Object[][] getFullPluginData(String pluginName) throws SQLException {
        int count = 0;
        StringBuilder countSQL = new StringBuilder(countFullByName());

        buildComplexWhere(pluginName, count, countSQL);
        this.ddl.build();

        int lines = ddl.getResult().next() ? ddl.getResult().getInt(1) : 0;


        if(lines > 0){
            count = 0;
            StringBuilder sql = new StringBuilder(selectFullByName());
            buildComplexWhere(pluginName, count, sql);
            this.ddl.build();



            count = 0;
            Object[][] items = new Object[lines][4];
            while(ddl.getResult().next()){
                items[count] = PluginFactory.getFullDataFilteredByResultSet(ddl.getResult());
                count++;
            }

            return items;

        } else {
            return null;
        }
    }

    private void buildComplexWhere(String pluginName, int count, StringBuilder sql) throws SQLException {
        sql.append(Strings.notEmpty(pluginName)           ? " AND NAME              = ?" : "");

        this.ddl.addSQL(sql.toString());

        if(Strings.notEmpty(pluginName)){
            this.ddl.preparingStatement().setString(++count, pluginName );
        }

    }

}
