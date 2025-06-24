package com.example.StudyHub_backend_bord.service.probe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service @RequiredArgsConstructor
public class ProbeService {
    private final JdbcTemplate jdbcTemplate;

    public void validateLiveness() {
        // 기본 통과
    }
    public void validateReadiness() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        } catch (DataAccessException ex) {
            log.error("Readiness check failed: {}", ex.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Database not ready",
                    ex
            );
        }
    }
}