package com.labotec.traccar.app.usecase.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.web.dto.entel.create.UserDTO;

public interface UserRepository {
    User findByUserId(Long userId);
    void deleById(Long userId);
    User create(User user);
}
