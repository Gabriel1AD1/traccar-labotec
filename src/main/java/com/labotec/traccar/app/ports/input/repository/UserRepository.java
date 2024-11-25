package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.domain.database.models.User;

public interface UserRepository {
    User findByUserId(Long userId);
    void deleById(Long userId);
    User create(User user);
}
