package com.example.dao;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.dto.UserDTO;

public class UserDAOImpl implements UserDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/userdb";
    private static final String USER = "root";   // change if needed
    private static final String PASS = "root";   // change if needed
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean registerUser(UserDTO user) {
        boolean status = false;
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            ps.setString(3, hashedPassword);

            int rows = ps.executeUpdate();
            status = rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
        
    }

    @Override
    public UserDTO loginUser(String email, String password) {
        UserDTO user = null;
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "SELECT * FROM users WHERE email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");

                System.out.println("Entered password: " + password);
                System.out.println("Stored hash: " + storedHash);
                System.out.println("Match result: " + passwordEncoder.matches(password, storedHash));

                if (passwordEncoder.matches(password, storedHash)) {
                    user = new UserDTO();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
       