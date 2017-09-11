package com.tecgraf.plugins.server.dao;

import com.tecgraf.plugins.common.Functionality;
import com.tecgraf.plugins.server.dao.utils.StatementDDLBuilder;
import com.tecgraf.plugins.server.dao.utils.StatementDMLBuilder;
import com.tecgraf.plugins.server.factories.FunctionalityFactory;
import com.tecgraf.plugins.server.process.utils.Strings;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.tecgraf.plugins.server.dao.queries.FunctionalityQueries.*;


public class FunctionalityDAO {
    private StatementDDLBuilder ddl;
    private StatementDMLBuilder dml;


    public FunctionalityDAO() {    }

    public FunctionalityDAO(StatementDDLBuilder ddl) {
        this.ddl = ddl;
    }


    public FunctionalityDAO(StatementDDLBuilder ddl, StatementDMLBuilder dml) {
        this.ddl = ddl;
        this.dml = dml;
    }

    public Long numberOfFunctionalities() throws SQLException {
        Long id = null;
        ddl.addSQL(countId()).build();

        while(ddl.getResult().next()){
            id = ddl.getResult().getLong(1);
        }
        return id;
    }

    public List<Functionality> listFunctionalities() throws SQLException {
        List<Functionality> list = new ArrayList<>();
        ddl.addSQL(selectAll()).build();
        while(ddl.getResult().next()){
            list.add(FunctionalityFactory.getFunctionalityByResultSet(ddl.getResult()));
        }
        return list;
    }

    public Functionality lastFunctionality() throws SQLException {
        Functionality functionality = null;
        Long id = lastId();
        ddl.addSQL(selectById());
        ddl.preparingStatement().setLong(1, id);
        ddl.build();

        while(ddl.getResult().next()){
            functionality = FunctionalityFactory.getFunctionalityByResultSet(ddl.getResult());

        }
        return functionality;

    }

    public Long lastId() throws SQLException {
        Long lastID = null;
        ddl.addSQL(maxId()).build();
        while(ddl.getResult().next()){
            lastID = ddl.getResult().getLong(1);
        }
        return lastID;
    }

    public Functionality findById(Long id) throws SQLException {
        Functionality functionality = null;

        ddl.addSQL(selectById());
        ddl.preparingStatement().setLong(1, id);
        ddl.build();
        while(ddl.getResult().next()){
            functionality = FunctionalityFactory.getFunctionalityByResultSet(ddl.getResult());
        }
        return functionality;

    }

    public Functionality findByName(String name) throws SQLException{
        Functionality functionality = null;

        ddl.addSQL(selectByName());
        ddl.preparingStatement().setString(1, name);
        ddl.build();
        while(ddl.getResult().next()){
            functionality = FunctionalityFactory.getFunctionalityByResultSet(ddl.getResult());
        }
        return functionality;
    }


    public Long nextId() throws SQLException {
        return lastId() + 1;

    }

    public boolean create(Functionality functionality) throws SQLException {
        dml.addSQL(insert());
        dml.preparingStatement().setString(1, functionality.getName());
        dml.preparingStatement().setString(2, functionality.getDescription());
        dml.preparingStatement().setLong(3, functionality.getPluginId());

        //executeUpdate: return number of rows inserted
        return dml.build().getResultValue() > 0;
    }

    public boolean update(Functionality updatedFunctionality, Long id) throws SQLException {
        //First get the current user values
        Functionality oldFunctionality = findById(id);

        dml.addSQL(updateById());
        dml.preparingStatement().setString(1, updatedFunctionality.getName() != null ? updatedFunctionality.getName() : oldFunctionality.getName());
        dml.preparingStatement().setString(2, updatedFunctionality.getDescription() != null ? updatedFunctionality.getDescription() : oldFunctionality.getDescription());
        dml.preparingStatement().setLong(3, updatedFunctionality.getPluginId() != null ? updatedFunctionality.getPluginId() : oldFunctionality.getPluginId());
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

    public Object[][] getFullFunctionalityData(String pluginName) throws SQLException {
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
                items[count] = FunctionalityFactory.getFullDataFilteredByResultSet(ddl.getResult());
                count++;
            }

            return items;

        } else {
            return null;
        }
    }

    public Object[][] getFullFunctionalityDataByUserId(Long userId) throws SQLException {
        int count = 0;
        this.ddl.addSQL(countFullByUserId());
        this.ddl.preparingStatement().setLong(1, userId);
        this.ddl.build();

        int lines = ddl.getResult().next() ? ddl.getResult().getInt(1) : 0;


        if(lines > 0){
            count = 0;
            this.ddl.addSQL(selectFullByUserId());
            this.ddl.preparingStatement().setLong(1, userId);
            this.ddl.build();

            count = 0;
            Object[][] items = new Object[lines][4];
            while(ddl.getResult().next()){
                items[count] = FunctionalityFactory.getFullDataFilteredByResultSet(ddl.getResult());
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
