package com.tecgraf.plugins.server.dao.queries;

public class UserFunctionalityPermissionQueries {
    public enum FIELDS { USER_ID, FUNCTIONALITY_ID}
    /**
     * String query with 2 parameters
     * 1 - USER_ID
     * 2 - FUNCTIONALITY_ID
     *
     * @return
     */
    public static String insert(){
        return "INSERT INTO USERS_FUNC_PERMISSIONS (USER_ID, FUNCTIONALITY_ID) VALUES (?, ?)";
    }

    public static String selectAll(){
        return "SELECT * FROM USERS_FUNC_PERMISSIONS";
    }

    public static String countId(){
        return "SELECT COUNT(USER_ID) FROM USERS_FUNC_PERMISSIONS";
    }

    /**
     * String query with 1 parameter
     * 2 - FUNCTIONALITY_ID
     *
     * @return
     */
    public static String selectByFunctionalityId(){
        return "SELECT * FROM USERS_FUNC_PERMISSIONS WHERE FUNCTIONALITY_ID = ?";
    }

    /**
     * String query with 1 parameter
     * 1 - USER_ID
     *
     * @return
     */
    public static String selectByUserId(){
        return "SELECT * FROM USERS_FUNC_PERMISSIONS WHERE USER_ID = ?";
    }

    public static String deleteByUserIdAndFunctionalityId(){
        return "DELETE FROM USERS_FUNC_PERMISSIONS WHERE USER_ID = ? AND FUNCTIONALITY_ID = ?";
    }

    public static String deleteAll(){
        return "DELETE FROM USERS_FUNC_PERMISSIONS";
    }


}

