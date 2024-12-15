package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.ports.input.repository.UserRepository;
import com.labotec.traccar.app.ports.out.services.UserService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.web.labotec.request.create.UserCreateDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserServiceI implements UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    @Override
    public User createUser(UserCreateDTO user) {
        Company company = companyRepository.findById(user.getCompanyId());
        User newUser = new User();
        newUser.setUserId(user.getUserId());
        newUser.setCompanyId(company);
        return userRepository.create(newUser);
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleById(userId);
    }

}
