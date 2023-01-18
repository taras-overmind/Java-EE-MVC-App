package com.taras_overmind.epam_final_project.db;


/**
 * Contains all queries
 */
public class Query {

    //Select_as queries

    //Simple select queries
    public static final String SELECT_ALL_USERS = "SELECT * FROM USERS";
    public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM USERS WHERE LOGIN = ?";

    //Complex select queries
    public static final String SELECT_LAST_USER_ID = "SELECT MAX(ID_USER) FROM USERS";
    public static final String SELECT_LAST_STUDENT_ID = "SELECT MAX(ID) FROM STUDENTS";
    public static final String SELECT_LAST_LECTURER_ID = "SELECT MAX(ID) FROM LECTURERS";

    //Create queries
    public static final String CREATE_USER = "INSERT INTO USERS          VALUES (DEFAULT, ?, ?, null, ?, ?)";
    public static final String CREATE_STUDENT = "INSERT INTO STUDENTS    VALUES (DEFAULT, ?, ?, ?, ?)";
    public static final String CREATE_LECTURER = "INSERT INTO LECTURERS    VALUES (DEFAULT, ?, ?, ?, ?)";
    public static final String REGISTER_USER_ON_COURSE = "INSERT INTO STUDENT_COURSE VALUES (DEFAULT, ?, ?)";



    public static final String SELECT_ALL_THEMES =          "SELECT * FROM THEMES";
    public static final String SELECT_ALL_LECTURERS =       "SELECT * FROM LECTURERS";
    public static final String SELECT_ALL_STATUSES =        "SELECT * FROM STATUSES";
    public static final String SELECT_ALL_COURSES =         "SELECT * FROM COURSES";
    public static final String SELECT_ALL_STUDENTS =        "SELECT * FROM STUDENTS";
    public static final String SELECT_ALL_STUDENT_ON_COURSE="SELECT * FROM STUDENT_COURSE";
    public static final String SELECT_ALL_DEFINITE_COURSE = "SELECT * FROM COURSES WHERE ID_COURSE=?";

    //Update queries
    public static final String UPDATE_PASSWORD = "UPDATE USERS       SET PASSWORD = ? WHERE ID_USER = ?";
    public static final String CHANGE_STATE_USER = "UPDATE USERS       SET ID_STATE = ? WHERE ID_USER = ?";


}
