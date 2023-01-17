package com.taras_overmind.epam_final_project.db.service;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.repository.UserRepo;
import org.apache.log4j.Logger;


public class UserService {
    private static Logger LOG = Logger.getLogger(ConnectionPool.class.getName());

    private UserRepo userRepo = new UserRepo();

    public String checkUser(String username, String password) {
        var user = userRepo.getUserByName(username);
        if (user == null)
            return "no such user";
        if (!user.getPassword().equals(password))
            return "wrong password";
        if(user.getStateId()==0)
            return "you're banned";
        return null;
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }
}