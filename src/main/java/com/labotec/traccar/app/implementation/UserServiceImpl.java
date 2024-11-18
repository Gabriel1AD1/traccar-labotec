package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.usecase.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.UserRepository;
import com.labotec.traccar.app.usecase.ports.out.UserService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.web.dto.entel.create.UserDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    @Override
    public User createUser(UserDTO user) {
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
