package com.taras_overmind.epam_final_project.command;

import com.taras_overmind.epam_final_project.command.commandResult.CommandResult;
import com.taras_overmind.epam_final_project.command.commandResult.ForwardResult;
import com.taras_overmind.epam_final_project.db.ConnectionPool;
import com.taras_overmind.epam_final_project.db.Query;
import com.taras_overmind.epam_final_project.db.dto.UserInfoDTO;
import com.taras_overmind.epam_final_project.db.dto.UserInfoDTO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetUsersPageCommand extends Command{
    public static final Logger LOG = Logger.getLogger(GetStudentPageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response, String forward)
            throws IOException, ServletException {
        LOG.trace("Start tracing GetUsersPageCommand");

        HttpSession session = request.getSession();
        UserInfoDTO userInfoDTO;
        List<UserInfoDTO> list1 = new ArrayList<>();
        List<UserInfoDTO> list2 = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection()) {
            if (connection != null) {
                PreparedStatement statement1 = connection.prepareStatement(Query.SELECT_STUDENTS_INFO);
                PreparedStatement statement2 = connection.prepareStatement(Query.SELECT_LECTURERS_INFO);
                connection.setAutoCommit(false);
                statement1.execute();
                statement2.execute();
                ResultSet resultSet = statement1.getResultSet();
                while (resultSet.next()) {
                    userInfoDTO = new UserInfoDTO();
                    userInfoDTO.setId_user(resultSet.getInt("id_user"));
                    userInfoDTO.setName(resultSet.getString("surname")+" "+resultSet.getString("name")
                            + " "+resultSet.getString("patronymic"));
                    userInfoDTO.setName_state(resultSet.getString("name_state"));
                    list1.add(userInfoDTO);
                }
                resultSet=statement2.getResultSet();
                while (resultSet.next()) {
                    userInfoDTO = new UserInfoDTO();
                    userInfoDTO.setId_user(resultSet.getInt("id_user"));
                    userInfoDTO.setName(resultSet.getString("surname")+" "+resultSet.getString("name")
                            + " "+resultSet.getString("patronymic"));
                    userInfoDTO.setName_state(resultSet.getString("name_state"));
                    list2.add(userInfoDTO);
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        session.setAttribute("result1", list1);
        session.setAttribute("result2", list2);


        return new ForwardResult("/WEB-INF/jsp/users.jsp");
    }
}
