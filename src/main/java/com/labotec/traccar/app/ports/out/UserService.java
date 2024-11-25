package com.labotec.traccar.app.ports.out;

import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.web.dto.labotec.request.create.UserCreateDTO;

public interface UserService
{
    User createUser(UserCreateDTO user);
    void deleteUserById(Long userId);
}
