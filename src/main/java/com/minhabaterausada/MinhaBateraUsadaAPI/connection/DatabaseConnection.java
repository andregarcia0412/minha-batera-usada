package com.minhabaterausada.MinhaBateraUsadaAPI.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DatabaseConnection {
    @Value("${DB_PUBLIC_URL}")
    private String URL;
    @Value("${DB_USER}")
    private String USER;
    @Value("${DB_PASSWORD}")
    private String PASSWORD;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
