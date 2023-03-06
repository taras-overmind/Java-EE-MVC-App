package com.taras_overmind.epam_final_project.db.service;
import com.taras_overmind.epam_final_project.db.dto.UserDTO;
import com.taras_overmind.epam_final_project.db.entity.UserEntity;
import com.taras_overmind.epam_final_project.db.repository.UserRepo;
import org.apache.log4j.Logger;

import java.util.List;


public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class.getName());

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String loginCheck(String username, String password) {
        LOG.trace("Start tracing UserService#loginCheck");

        var user = userRepo.getUserByName(username);
        if (user == null)
            return "no such user";
        if(username.contains(" "))
            return "username shouldn't have spaces";
        if (!user.getPassword().equals(password))
            return "wrong password";
        if(user.getStateId()==0)
            return "You have no access to courses";
        return null;
    }
    public String registerCheck(String username, String password){
        LOG.trace("Start tracing UserService#registerCheck");
        if(userRepo.getUserByName(username)!=null)
            return "User with this login already exists";
        if (username.length() < 5 )
            return "username should have at least 5 characters";
        String pattern = "^[a-zA-Z0-9_]*$";
        if(!username.matches(pattern)||!password.matches(pattern))
            return "Use only letters and numbers";
        if(password.length()<6)
            return "Password should have at least 6 characters";
        return null;
    }

    public UserEntity getUserByName(String username) {
        return userRepo.getUserByName(username);
    }

    public UserEntity createUser(String login, String password, int role) {
        return userRepo.createUser(login, password, role);
    }

    public void changeUserState(int id, String state) {
        userRepo.changeUserState(id, state);
    }

    public void registerUserOnCourse(int id, int idCourse) {
        userRepo.registerUserOnCourse(id, idCourse);
    }

    public List<UserDTO> findUsers(boolean isStudent) {
        return userRepo.findUsers(isStudent);
    }
}