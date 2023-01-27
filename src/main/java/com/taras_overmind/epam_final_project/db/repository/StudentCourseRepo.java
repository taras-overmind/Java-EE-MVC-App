package com.taras_overmind.epam_final_project.db.repository;

import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dto.StudentCourseDTO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentCourseRepo {

    private static final Logger LOG = Logger.getLogger(StudentCourseRepo.class.getName());
    public List<StudentCourseDTO> findStudentsByUserId(int id, boolean withMarks) {
        StudentCourseDTO studentCourseDTO;
        List<StudentCourseDTO> list = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                PreparedStatement statement;
                if (!withMarks)
                    statement = connection.prepareStatement(Query.SELECT_STUDENTS_WITHOUT_MARK);
                else
                    statement = connection.prepareStatement(Query.SELECT_STUDENTS_WITH_MARK);
                statement.setInt(1, id);
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    studentCourseDTO = new StudentCourseDTO();
                    studentCourseDTO.setStudentName(resultSet.getString("surname") + " " + resultSet.getString("name")
                            + " " + resultSet.getString("patronymic"));
                    studentCourseDTO.setCourseName(resultSet.getString("name_course"));
                    studentCourseDTO.setId(resultSet.getInt("id_student_course"));
                    if(withMarks)
                        studentCourseDTO.setMark(resultSet.getInt("mark"));
                    list.add(studentCourseDTO);
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        return list;
    }
}
