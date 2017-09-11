package com.tecgraf.plugins.server.dao.queries;

public class UserQueries {
    public enum FIELDS { ID, LOGIN, FULL_NAME, STATUS, CURRENT_MANAGEMENT }
    public enum FIELDS_DATA {
        USER_ID, LOGIN, FULL_NAME, STATUS, CURRENT_MANAGEMENT,
        FUNCITONALITY_NAME,
        PLUGIN_NAME
    }
    /**
     * String query with one parameter
     * 1 - ID
     * @return
     */
    public static String deleteById(){
        return "DELETE FROM USERS WHERE ID=?";
    }

    public static String maxId(){
        return "SELECT MAX(ID) FROM USERS";
    }

    /**
     * String query with one parameter
     * 1 - ID
     * @return
     */
    public static String selectById(){
        return "SELECT * FROM USERS WHERE ID = ?";
    }

    /**
     * String query with one parameter
     * 1 - ID
     * @return
     */
    public static String selectByLogin(){
        return "SELECT * FROM USERS WHERE LOGIN = ?";
    }


    public static String selectAll(){
        return "SELECT * FROM USERS";
    }

    public static String countId(){
        return "SELECT COUNT(ID) FROM USERS";
    }

    /**
     * String query with 5 parameters
     * 1 - ID
     * 2 - LOGIN
     * 3 - FULL_NAME
     * 4 - STATUS
     * 5 - CURRENT_MANAGEMENT
     *
     * @return
     */
    public static String insert(){
        return "INSERT INTO USERS (LOGIN, FULL_NAME, STATUS, CURRENT_MANAGEMENT) VALUES (?, ?, ?, ?)";
    }

    /**
     * String query with 5 parameters
     * 1 - LOGIN
     * 2 - FULL_NAME
     * 3 - STATUS
     * 4 - CURRENT_MANAGEMENT
     *
     * 5 - ID: Used in the where
     * @return
     */
    public static String updateById(){
        return "UPDATE USERS SET LOGIN=?, FULL_NAME=?, STATUS=? ,CURRENT_MANAGEMENT=? WHERE ID=?";
    }

    /**
     * String query with 2 parameter
     * 1 - STATUS
     * 2 - ID
     * @return
     */
    public static String updateStatusById(){
        return "UPDATE USERS SET STATUS=? WHERE ID=?";
    }

    public static String deleteAll(){
        return "DELETE FROM USERS";
    }

    public static String countDataFromUserFunctionalityAndPlugin() {
        return "SELECT \n" +
                "    COUNT(*)\n" +
                "\n" +
                "FROM \n" +
                "    USERS US, FUNCTIONALITIES FU, PLUGINS PL, USERS_FUNC_PERMISSIONS UF\n" +
                "\n" +
                "WHERE \n" +
                "        US.ID = UF.USER_ID\n" +
                "    AND UF.FUNCTIONALITY_ID = FU.ID\n" +
                "    AND FU.PLUGIN_ID = PL.ID\n" +
                "    AND 1 = 1";
    }

    public static String countDataFromUser() {
        return "SELECT \n" +
                "    COUNT(*)\n" +
                "\n" +
                "FROM \n" +
                "    USERS US\n" +
                "\n" +
                "WHERE \n" +
                "    1 = 1";
    }

    public static String selectDataFromUserFunctionalityAndPlugin(){
        return "SELECT \n" +
                "    US.ID USER_ID, US.LOGIN LOGIN, US.FULL_NAME FULL_NAME, US.STATUS STATUS, US.CURRENT_MANAGEMENT CURRENT_MANAGEMENT,\n" +
                "    FU.NAME FUNCITONALITY_NAME, \n" +
                "    FU.NAME PLUGIN_NAME \n" +
                "\n" +
                "FROM \n" +
                "    USERS US, FUNCTIONALITIES FU, PLUGINS PL, USERS_FUNC_PERMISSIONS UF\n" +
                "\n" +
                "WHERE \n" +
                "        US.ID = UF.USER_ID\n" +
                "    AND UF.FUNCTIONALITY_ID = FU.ID\n" +
                "    AND FU.PLUGIN_ID = PL.ID\n" +
                "    AND 1 = 1";
    }

    public static String selectDataFromUser(){
        return "SELECT \n" +
                "    US.ID USER_ID, US.LOGIN LOGIN, US.FULL_NAME FULL_NAME, US.STATUS STATUS, US.CURRENT_MANAGEMENT CURRENT_MANAGEMENT,\n" +
                "    '' FUNCITONALITY_NAME, \n" +
                "    '' PLUGIN_NAME \n" +
                "\n" +
                "FROM \n" +
                "    USERS US\n" +
                "\n" +
                "WHERE \n" +
                "    1 = 1";
    }
}
