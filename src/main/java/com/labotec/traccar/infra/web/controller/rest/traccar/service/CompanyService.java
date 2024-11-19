package com.labotec.traccar.infra.web.controller.rest.traccar.service;

import com.labotec.traccar.app.usecase.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.UserRepository;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.entity.TcCompany;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.entity.TcUser;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.repository.TcCompanyRepository;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.repository.TcUserRepository;
import com.labotec.traccar.infra.web.controller.rest.traccar.dto.CreateUserAdminCompanyDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("companyServiceTraccar")
@AllArgsConstructor
@Transactional
public class CompanyService {

    private final TcUserRepository tcUserRepository;
    private final TcCompanyRepository tcCompanyRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    @Transactional
    public Map<String , Object> createCompanyAndUser(CreateUserAdminCompanyDTO userAdminCompanyDTO){
        TcCompany tcCompany = new TcCompany();
        tcCompany.setName(userAdminCompanyDTO.getCompanyName());
        tcCompany.setAddress(userAdminCompanyDTO.getCompanyAddress());
        tcCompany.setEmail(userAdminCompanyDTO.getCompanyEmail());
        tcCompany.setDomain(userAdminCompanyDTO.getCompanyDomain());
        tcCompany.setPhone(userAdminCompanyDTO.getCompanyPhone());
        tcCompany.setCodeSecret(userAdminCompanyDTO.getSecretCode());
        tcCompany.setState("ACTIVE");
        TcCompany tcCompanySaved = tcCompanyRepository.save(tcCompany);
        TcUser tcUser = new TcUser();
        tcUser.setEmail(userAdminCompanyDTO.getAdminEmail());
        tcUser.setName(userAdminCompanyDTO.getAdminUserName());
        tcUser.setPassword(userAdminCompanyDTO.getAdminPassword());
        tcUser.setZoom(0);
        tcUser.setLongitude(0.0);
        tcUser.setLatitude(0.0);
        tcUser.setDevicereadonly(false);
        tcUser.setLimitcommands(false);
        tcUser.setIsAdministratorCompany(true);
        tcUser.setTcCompany(tcCompanySaved);
        tcUser.setAdministrator(false);
        tcUser.setReadonly(false);
        tcUser.setFixedemail(false);
        tcUser.setTemporary(false);
        tcUser.setDisabled(false);
        tcUser.setAttributes("{}");
        TcUser tcUserSaved = tcUserRepository.save(tcUser);

        Company company = new Company();
        company.setCompanyId(Long.valueOf(tcCompanySaved.getId()));
        Company companySaved = companyRepository.create(company);
        User user = new User();
        user.setUserId(Long.valueOf(tcUser.getId()));
        user.setCompanyId(companySaved);
        User userSaved = userRepository.create(user);
        return Map.of("User" , userSaved);
    }

}
