package com.tecgraf.plugins.server.dao;


import com.tecgraf.plugins.common.User;
import com.tecgraf.plugins.server.dao.utils.StatementDDLBuilder;
import com.tecgraf.plugins.server.dao.utils.StatementDMLBuilder;
import com.tecgraf.plugins.server.factories.UserFactory;
import com.tecgraf.plugins.server.process.utils.Strings;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.tecgraf.plugins.server.dao.queries.UserQueries.*;


public class UserDAO {
    private StatementDDLBuilder ddl;
    private StatementDMLBuilder dml;

    public UserDAO(StatementDDLBuilder ddl) {
        this.ddl = ddl;
    }

    public UserDAO(StatementDDLBuilder ddl, StatementDMLBuilder dml) {
        this.ddl = ddl;
        this.dml = dml;
    }

    private boolean isJustForUserInfo(String plugin, String functionality){
        return Strings.isEmpty(plugin) && Strings.isEmpty(functionality);

    }

    public Object[][] getFullUserData(String userLogin, String userFullName, String userStatus, String userCurrentManager, String pluginName, String functionalityName) throws SQLException {
        int count = 0;
        StringBuilder countSQL = new StringBuilder(isJustForUserInfo(pluginName, functionalityName) ? countDataFromUser() : countDataFromUserFunctionalityAndPlugin());
        buildComplexWhere(userLogin, userFullName, userStatus, userCurrentManager, pluginName, functionalityName, count, countSQL);
        this.ddl.build();

        int lines = ddl.getResult().next() ? ddl.getResult().getInt(1) : 0;


        if(lines > 0){
            count = 0;
            StringBuilder sql = new StringBuilder(isJustForUserInfo(pluginName, functionalityName) ? selectDataFromUser() : selectDataFromUserFunctionalityAndPlugin());
            buildComplexWhere(userLogin, userFullName, userStatus, userCurrentManager, pluginName, functionalityName, count, sql);
            this.ddl.build();



            count = 0;
            Object[][] items = new Object[lines][7];
            while(ddl.getResult().next()){
                items[count] = UserFactory.getFullDataFilteredByResultSet(ddl.getResult());
                count++;
            }

            return items;

        } else {
            return null;
        }
    }

    private void buildComplexWhere(String userLogin, String userFullName, String userStatus, String userCurrentManager, String pluginName, String functionalityName, int count, StringBuilder sql) throws SQLException {
        sql.append(Strings.notEmpty(userLogin)           ? " AND US.LOGIN              = ?" : "");
        sql.append(Strings.notEmpty(userFullName)        ? " AND US.FULL_NAME          = ?" : "");
        sql.append(Strings.notEmpty(userStatus)          ? " AND US.STATUS             = ?" : "");
        sql.append(Strings.notEmpty(userCurrentManager)  ? " AND US.CURRENT_MANAGEMENT = ?" : "");
        sql.append(Strings.notEmpty(pluginName)          ? " AND PL.NAME               = ?" : "");
        sql.append(Strings.notEmpty(functionalityName)   ? " AND FU.NAME               = ?" : "");


        this.ddl.addSQL(sql.toString());

        if(Strings.notEmpty(userLogin)){
            this.ddl.preparingStatement().setString(++count, userLogin );
        }

        if( Strings.notEmpty(userFullName)){
            this.ddl.preparingStatement().setString(++count, userFullName );
        }

        if( Strings.notEmpty(userStatus)){
            this.ddl.preparingStatement().setString(++count, userStatus );
        }

        if( Strings.notEmpty(userCurrentManager)){
            this.ddl.preparingStatement().setString(++count, userCurrentManager );
        }

        if( Strings.notEmpty(pluginName)){
            this.ddl.preparingStatement().setString(++count, pluginName );
        }

        if( Strings.notEmpty(functionalityName)){
            this.ddl.preparingStatement().setString(++count, functionalityName );
        }
    }

    public List<User> listUsers() throws SQLException {
        ddl.addSQL(selectAll()).build();
        List<User> list = new ArrayList<>();
        while(ddl.getResult().next()){
            list.add(UserFactory.getUserByResultSet(ddl.getResult()));
        }
        return list;
    }

    public Long lastId() throws SQLException {
        Long lastID = null;

        ddl.addSQL(maxId()).build();
        while(ddl.getResult().next()){
            lastID = ddl.getResult().getLong(1);
        }
        return lastID;
    }

    public Long numberOfUsers() throws SQLException {
        Long id = null;
        ddl.addSQL(countId()).build();

        while(ddl.getResult().next()){
            id = ddl.getResult().getLong(1);
        }
        return id;
    }


    public Long nextId() throws SQLException {
        return lastId() + 1;
    }

    public User findById(long id) throws SQLException {
        User user = null;

        ddl.addSQL(selectById());
        ddl.preparingStatement().setLong(1, id);
        ddl.build();
        while(ddl.getResult().next()){
            user = UserFactory.getUserByResultSet(ddl.getResult());
        }
        return user;
    }

    public boolean isValidLogin(String login) throws SQLException {
        ddl.addSQL(selectByLogin());
        ddl.preparingStatement().setString(1, login);
        ddl.build();
        return ddl.getResult().next();
    }

    public boolean create(User user) throws SQLException {

        dml.addSQL(insert());
        dml.preparingStatement().setString(1, user.getLogin());
        dml.preparingStatement().setString(2, user.getFullName());
        dml.preparingStatement().setString(3, user.getStatus());
        dml.preparingStatement().setString(4, user.getCurrentManagement());

        //executeUpdate: return number of rows inserted
        return dml.build().getResultValue() > 0;
    }

    public boolean update(User updatedUser, Long id) throws SQLException {
        //First get the current user values
        User oldUser = findById(id);

        dml.addSQL(updateById());
        dml.preparingStatement().setString(1, updatedUser.getLogin() != null ? updatedUser.getLogin() : oldUser.getLogin());
        dml.preparingStatement().setString(2, updatedUser.getFullName() != null ? updatedUser.getFullName() : oldUser.getFullName());
        dml.preparingStatement().setString(3, updatedUser.getStatus() != null ? updatedUser.getStatus() : oldUser.getStatus());
        dml.preparingStatement().setString(4, updatedUser.getCurrentManagement() != null ? updatedUser.getCurrentManagement() : oldUser.getCurrentManagement());
        dml.preparingStatement().setLong(5, id);

        //executeUpdate: return number of rows inserted
        return dml.build().getResultValue() > 0;
    }


    public User lastUser() throws SQLException {
        return findById(lastId());
    }

    public boolean cancelUser(Long id) throws SQLException {
        dml.addSQL(updateStatusById());
        dml.preparingStatement().setString(1, "INACTIVE");
        dml.preparingStatement().setLong(2, id);

        //executeUpdate: return number of rows updated
        return dml.build().getResultValue() > 0;
    }

    public boolean delete(Long id) throws SQLException {
        dml.addSQL(deleteById());
        dml.preparingStatement().setLong(1, id);
        return dml.build().getResultValue() > 0;
    }

    //Just for run all tests
    public boolean deleteAllElements() throws  SQLException{
        dml.addSQL(deleteAll());
        return dml.build().getResultValue() > 0;
    }

}
