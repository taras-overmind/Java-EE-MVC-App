package com.taras_overmind.epam_final_project.db;



/**
 * Contains all queries
 */
public class Query {

    //Select_as queries

    //Simple select queries
    public static final String SELECT_ALL_USERS =           "SELECT * FROM USERS";
    public static final String SELECT_USER_BY_LOGIN =       "SELECT * FROM USERS WHERE LOGIN = ?";

    //Complex select queries
    public static final String SELECT_LAST_USER_ID =                                    "SELECT MAX(ID_USER) FROM USERS";

    //Create queries
    public static final String CREATE_USER =                "INSERT INTO USERS          VALUES (DEFAULT, ?, ?, null, 2, 1)";
    public static final String REGISTER_USER_ON_COURSE=     "INSERT INTO STUDENT_COURSE VALUES (DEFAULT, ?, ?)";

    //Update queries
        public static final String UPDATE_PASSWORD =    "UPDATE USERS       SET PASSWORD = ? WHERE ID_USER = ?";
    public static final String CHANGE_STATE_USER =  "UPDATE USERS       SET ID_STATE = ? WHERE ID_USER = ?";


}
