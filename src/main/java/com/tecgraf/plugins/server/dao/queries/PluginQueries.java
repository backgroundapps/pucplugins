package com.tecgraf.plugins.server.dao.queries;

public class PluginQueries {
    public enum FIELDS { ID, NAME, DESCRIPTION, START_DATE}
    /**
     * String query with one parameter
     * 1 - ID
     * @return
     */
    public static String deleteById(){
        return "DELETE FROM PLUGINS WHERE ID=?";
    }

    public static String maxId(){
        return "SELECT MAX(ID) FROM PLUGINS";
    }

    /**
     * String query with one parameter
     * 1 - ID
     * @return
     */
    public static String selectById(){
        return "SELECT * FROM PLUGINS WHERE ID = ?";
    }

    /**
     * String query with one parameter
     * 1 - NAME
     * @return
     */
    public static String selectByName(){
        return "SELECT * FROM PLUGINS WHERE NAME = ?";
    }

    /**
     * String query with one parameter
     * 1 - NAME
     * @return
     */
    public static String selectFullByName(){
        return "SELECT * FROM PLUGINS WHERE 1 = 1 ";
    }


    /**
     * String query with one parameter
     * 1 - NAME
     * @return
     */
    public static String countByName(){
        return "SELECT COUNT(*) FROM PLUGINS WHERE NAME = ?";
    }

    /**
     * String query with one parameter
     * 1 - NAME
     * @return
     */
    public static String countFullByName(){
        return "SELECT COUNT(*) FROM PLUGINS WHERE 1 = 1 ";
    }

    public static String selectAll(){
        return "SELECT * FROM PLUGINS";
    }

    public static String countId(){
        return "SELECT COUNT(ID) FROM PLUGINS";
    }

    /**
     * String query with 3 parameters
     * 1 - ID
     * 2 - NAME
     * 3 - DESCRIPTION
     *
     * @return
     */
    public static String insert(){
        return "INSERT INTO PLUGINS ( NAME, DESCRIPTION) VALUES (?, ?)";
    }

    /**
     * String query with 4 parameters
     * 1 - NAME
     * 2 - DESCRIPTION
     * 3 - START_DATE
     *
     * 4 - ID: Plugin in the where
     * @return
     */
    public static String updateById(){
        return "UPDATE PLUGINS SET NAME=?, DESCRIPTION=?, START_DATE=? WHERE ID=?";
    }

    public static String deleteAll(){
        return "DELETE FROM PLUGINS";
    }

}
