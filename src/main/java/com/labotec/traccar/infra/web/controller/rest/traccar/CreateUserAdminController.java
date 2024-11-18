package com.labotec.traccar.infra.web.controller.rest.traccar;

import com.labotec.traccar.infra.db.mysql.jpa.traccar.entity.TcUser;
import com.labotec.traccar.infra.web.controller.rest.traccar.dto.CreateUserAdminCompanyDTO;
import com.labotec.traccar.infra.web.controller.rest.traccar.dto.UserCreateNotAdministratorDTO;
import com.labotec.traccar.infra.web.controller.rest.traccar.service.CompanyService;
import com.labotec.traccar.infra.web.controller.rest.traccar.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "user-admin")
@AllArgsConstructor
public class CreateUserAdminController {
    private final CompanyService companyService;
    private final UserService userService;
    @PostMapping("")
    public Map<String, Object> createCompanyAndUserAdmin(@Valid @RequestBody CreateUserAdminCompanyDTO companyDTO) {
        return  companyService.createCompanyAndUser(companyDTO);
    }

    @PostMapping("/user-not-admin")
    public TcUser createCompanyAndUserAdmin(
            @Valid @RequestBody UserCreateNotAdministratorDTO companyDTO,
            @RequestHeader Long userId) {
        return  userService.createUserForByAdministrator(companyDTO,userId);
    }

}
