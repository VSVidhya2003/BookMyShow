package com.example.dao;

import com.example.dto.RatingDTO;
import java.sql.*;

public class RatingDAOImpl implements RatingDAO {
    private Connection conn;

    public RatingDAOImpl() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "root", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean saveRating(RatingDTO rating) {
        try {
            String sql = "INSERT INTO ratings (user_name, rating) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rating.getUserName());
            ps.setInt(2, rating.getRating());
            return ps.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            // User already rated
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public RatingDTO getRatingByUser(String userName) {
        try {
            String sql = "SELECT * FROM ratings WHERE user_name=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                RatingDTO rating = new RatingDTO();
                rating.setId(rs.getInt("id"));
                rating.setUserName(rs.getString("user_name"));
                rating.setRating(rs.getInt("rating"));
                return rating;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
