package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.web.dto.entel.create.UserDTO;

public interface UserService
{
    User createUser(UserDTO user);
    void deleteUserById(Long userId);
}
