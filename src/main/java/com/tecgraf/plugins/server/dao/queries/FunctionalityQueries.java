package com.tecgraf.plugins.server.dao.queries;

public class FunctionalityQueries {
    public enum FIELDS { ID, NAME, DESCRIPTION, START_DATE, PLUGIN_ID}
    /**
     * String query with one parameter
     * 1 - ID
     * @return
     */
    public static String deleteById(){
        return "DELETE FROM FUNCTIONALITIES WHERE ID=?";
    }

    public static String maxId(){
        return "SELECT MAX(ID) FROM FUNCTIONALITIES";
    }

    /**
     * String query with one parameter
     * 1 - ID
     * @return
     */
    public static String selectById(){
        return "SELECT * FROM FUNCTIONALITIES WHERE ID = ?";
    }

    /**
     * String query with one parameter
     * 1 - NAME
     * @return
     */
    public static String countFullByName(){
        return "SELECT COUNT(*) FROM FUNCTIONALITIES WHERE 1 = 1 ";
    }

    /**
     * String query with one parameter
     * 1 - ID
     * @return
     */
    public static String countFullByUserId(){
        return "SELECT COUNT(*)\n" +
                "\n" +
                "FROM \n" +
                "    USERS US, FUNCTIONALITIES FU, USERS_FUNC_PERMISSIONS UF\n" +
                "\n" +
                "WHERE \n" +
                "        US.ID = UF.USER_ID\n" +
                "    AND UF.FUNCTIONALITY_ID = FU.ID\n" +
                "    AND US.ID = ?";
    }

    /**
     * String query with one parameter
     * 1 - ID
     * @return
     */
    public static String selectFullByUserId(){
        return "SELECT FU.*\n" +
                "\n" +
                "FROM \n" +
                "    USERS US, FUNCTIONALITIES FU, USERS_FUNC_PERMISSIONS UF\n" +
                "\n" +
                "WHERE \n" +
                "        US.ID = UF.USER_ID\n" +
                "    AND UF.FUNCTIONALITY_ID = FU.ID\n" +
                "    AND US.ID = ?";
    }

    /**
     * String query with one parameter
     * 1 - NAME
     * @return
     */
    public static String selectFullByName(){
        return "SELECT * FROM FUNCTIONALITIES WHERE 1 = 1 ";
    }


    /**
     * String query with one parameter
     * 1 - NAME
     * @return
     */
    public static String selectByName(){
        return "SELECT * FROM FUNCTIONALITIES WHERE NAME = ?";
    }

    public static String selectAll(){
        return "SELECT * FROM FUNCTIONALITIES";
    }

    public static String countId(){
        return "SELECT COUNT(ID) FROM FUNCTIONALITIES";
    }

    /**
     * String query with 4 parameters
     * 1 - ID
     * 2 - NAME
     * 3 - DESCRIPTION
     * 4 - PLUGIN_ID
     *
     * @return
     */
    public static String insert(){
        return "INSERT INTO FUNCTIONALITIES (NAME, DESCRIPTION, PLUGIN_ID) VALUES (?, ?, ?)";
    }

    /**
     * String query with 4 parameters
     * 1 - NAME
     * 2 - DESCRIPTION
     * 3 - PLUGIN_ID
     *
     * 4 - ID: Functionality in the where
     * @return
     */
    public static String updateById(){
        return "UPDATE FUNCTIONALITIES SET NAME=?, DESCRIPTION=?, PLUGIN_ID=? WHERE ID=?";
    }

    public static String deleteAll(){
        return "DELETE FROM FUNCTIONALITIES";
    }

}
