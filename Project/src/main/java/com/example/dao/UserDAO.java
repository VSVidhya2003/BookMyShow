package com.example.dao;

import com.example.dto.UserDTO;

public interface UserDAO {
    boolean registerUser(UserDTO user);
    UserDTO loginUser(String email, String password);
}
