package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.web.labotec.request.create.UserCreateDTO;

public interface UserService
{
    User createUser(UserCreateDTO user);
    void deleteUserById(Long userId);
}
