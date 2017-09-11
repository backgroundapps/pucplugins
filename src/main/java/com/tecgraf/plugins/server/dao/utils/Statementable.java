package com.tecgraf.plugins.server.dao.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface responsable for organize the process between open to Connection to close Connection
 */
public interface Statementable {
    Statementable addSQL(String sql);
    void close()throws SQLException;
    Statementable build() throws SQLException;
    ResultSet getResult(); //DDL
    int getResultValue(); //DML
    PreparedStatement preparingStatement() throws SQLException;
}
