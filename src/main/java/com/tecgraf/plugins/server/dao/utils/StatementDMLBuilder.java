package com.tecgraf.plugins.server.dao.utils;


import com.tecgraf.plugins.server.dao.conf.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatementDMLBuilder implements Statementable{
    private int resultValue;

    protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected String sql;


    public StatementDMLBuilder(){
        System.out.println("int StatementDMLBuilder");
        this.connection = new ConnectionFactory().getConnection();
        System.out.println("Conn created: " + connection);
    }

    @Override
    public Statementable addSQL(String sql) {
        this.preparedStatement = null;

        this.sql = sql;
        return this;
    }

    @Override
    public void close() throws SQLException{

        if(this.preparedStatement != null ){
            this.preparedStatement.close();
        }

        if(this.connection != null && !this.connection.isClosed()){
            this.connection.close();
        }
    }

    @Override
    public Statementable build() throws SQLException {
        if(this.preparedStatement == null){
            System.out.println("Conn: " + this.connection);
            System.out.println("SQL: " + this.sql);

            this.preparedStatement = this.connection.prepareStatement(this.sql);

        }
        this.resultValue = this.preparedStatement.executeUpdate();
        return this;
    }

    @Override
    /**
     * TODO: Just because of the Interface. This method can not be called. Refactor!
     *
     */
    public ResultSet getResult(){
        new RuntimeException("This method can not be called. Try call for getResult");
        return null;
    }

    @Override
    public int getResultValue() {
        return this.resultValue;
    }

    @Override
    public PreparedStatement preparingStatement() throws SQLException{
        if(this.preparedStatement == null){
            this.preparedStatement = this.connection.prepareStatement(this.sql);

        }
        return preparedStatement;
    }
}
