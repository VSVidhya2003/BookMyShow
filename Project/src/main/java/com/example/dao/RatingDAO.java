package com.example.dao;

import com.example.dto.RatingDTO;

public interface RatingDAO {
    boolean saveRating(RatingDTO rating);
    RatingDTO getRatingByUser(String userName);
}
