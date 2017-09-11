package com.tecgraf.plugins;

import com.tecgraf.plugins.server.dao.conf.ConnectionFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectionFactoryTest {
    @Test
    public void shouldReturnTheConnection(){
        Assert.assertNotNull(new ConnectionFactory().getConnection());
    }

    @Test
    public void shouldReturnThePreparedStatemant() throws SQLException {
        Connection con = new ConnectionFactory().getConnection();
        Assert.assertNotNull(con.prepareStatement("SELECT * from DUAL"));
    }

    @Test
    public void shouldReturnTheResultSet() throws SQLException {
        Connection con = new ConnectionFactory().getConnection();
        PreparedStatement ps =con.prepareStatement("SELECT * from DUAL");
        Assert.assertNotNull(ps.executeQuery());
    }

    @Test
    public void shouldReturnCurrentTime() throws SQLException, IOException {
        String result = null;
        Connection con = new ConnectionFactory().getConnection();
        PreparedStatement ps =null;
        ResultSet rst = null;

        try{
            ps = con.prepareStatement("SELECT sysdate from DUAL");
            rst = ps.executeQuery();

            while (rst.next()) {
                result = rst.getString(1);
            }

            String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            Assert.assertEquals(now, result.substring(0,10));

        }finally {
            rst.close();
            ps.close();
            con.close();
        }
    }

}
