package com.minhabaterausada.MinhaBateraUsadaAPI.service;

import com.minhabaterausada.MinhaBateraUsadaAPI.connection.DatabaseConnection;
import com.minhabaterausada.MinhaBateraUsadaAPI.domain.LoginRequest;
import com.minhabaterausada.MinhaBateraUsadaAPI.domain.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    DatabaseConnection databaseConnection;

    @Value("${API_PASSWORD}")
    private String apiPassword;

    public List<User> listAllUsers(String password){

        if(!password.equals(apiPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API password");
        }

        List<User> users = new ArrayList<>();
        String sql = "SELECT id, email, password, name FROM tb_user";
        try(Connection conn = databaseConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                User u = new User(resultSet.getLong("id"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("name"));
                users.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public User getUser(long id, String password){
        if(!password.equals(apiPassword)){
            return null;
        }
        String sql = "SELECT email, password, name FROM tb_user WHERE id = ?";
        try(Connection conn = databaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return new User(id, resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean addUser(User user, String password){
        if(!password.equals(apiPassword)){
            return false;
        }
        if(!emailAlreadyExists(user.getEmail())){
            String sql = "INSERT INTO tb_user (email, password, name) VALUES (?,?,?)";
            try(Connection conn = databaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)){
                    statement.setString(1, user.getEmail());
                    statement.setString(2, encryptPassword(user.getPassword()));
                    statement.setString(3, user.getName());
                    statement.executeUpdate();
                    return true;
                } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else{
            return false;
        }
    }

    private boolean emailAlreadyExists(String email){
        String sql = "SELECT name FROM tb_user WHERE email = ?";
        try(Connection conn = databaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();

                return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifyLogin(LoginRequest loginRequest, String password){
        if(!password.equals(apiPassword)){
            return false;
        }
        String sql = "SELECT password FROM tb_user WHERE email = ?";
        try(Connection conn = databaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, loginRequest.getEmail());
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()){
                    if(verifyPassword(loginRequest.getPassword(), resultSet.getString("password"))){
                        return true;
                    }
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password, String hash){
        return BCrypt.checkpw(password,hash);
    }
}
