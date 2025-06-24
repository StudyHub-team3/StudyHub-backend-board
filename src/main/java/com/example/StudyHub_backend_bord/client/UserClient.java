package com.example.StudyHub_backend_bord.client;

import com.example.StudyHub_backend_bord.dto.UserDto;

public interface UserClient {
    UserDto getUserById(Long userId);
}