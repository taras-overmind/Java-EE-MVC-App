package com.taras_overmind.epam_final_project.db.service;
import com.taras_overmind.epam_final_project.db.dao.ConnectionPool;
import com.taras_overmind.epam_final_project.db.dto.UserDTO;
import com.taras_overmind.epam_final_project.db.repository.UserRepo;
import org.apache.log4j.Logger;


public class UserService {
    private static Logger LOG = Logger.getLogger(ConnectionPool.class.getName());

    private UserRepo userRepo = new UserRepo();

    public String loginCheck(String username, String password) {
        var user = userRepo.getUserByName(username);
        if (user == null)
            return "no such user";
        if (!user.getPassword().equals(password))
            return "wrong password";
        if(user.getStateId()==0)
            return "You have no access to courses";
        return null;
    }
    public String registerCheck(String username, String password){
        if(getUserRepo().getUserByName(username)!=null)
            return "User with this login already exists";
        if(username.contains(" "))
            return "Login mustn't have spaces";
        if(password.length()<6)
            return "Password should have at least 6 characters";
        return null;
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }
}