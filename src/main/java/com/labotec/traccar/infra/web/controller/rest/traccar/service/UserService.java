package com.labotec.traccar.infra.web.controller.rest.traccar.service;

import com.labotec.traccar.infra.db.mysql.jpa.traccar.entity.TcUser;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.repository.TcUserRepository;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import com.labotec.traccar.infra.web.controller.rest.traccar.dto.UserCreateNotAdministratorDTO;
import com.labotec.traccar.infra.web.controller.rest.traccar.exception.Unauthorised;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userServiceTraccar")
@AllArgsConstructor
@Transactional
public class UserService {

    private final TcUserRepository tcUserRepository;
    public TcUser createUserForByAdministrator(UserCreateNotAdministratorDTO createNotAdministratorDTO , Long idUser) {
        TcUser tcUser = tcUserRepository.findById(Math.toIntExact(idUser)).orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado")
        );
        if (tcUser != null && !tcUser.getIsAdministratorCompany()) {
            throw new Unauthorised("No autorizado");
        }
        TcUser tcUserNotAdmin = new TcUser();
        tcUserNotAdmin.setName(createNotAdministratorDTO.getUsername());
        tcUserNotAdmin.setEmail(createNotAdministratorDTO.getEmail());
        tcUserNotAdmin.setPassword(createNotAdministratorDTO.getPassword());
        tcUserNotAdmin.setZoom(0);
        tcUserNotAdmin.setLongitude(0.0);
        tcUserNotAdmin.setLatitude(0.0);
        tcUserNotAdmin.setDevicereadonly(false);
        tcUserNotAdmin.setLimitcommands(false);
        tcUserNotAdmin.setIsAdministratorCompany(false);
        tcUserNotAdmin.setTcCompany(tcUser.getTcCompany());
        tcUserNotAdmin.setAdministrator(false);
        tcUserNotAdmin.setDisabled(false);
        tcUserNotAdmin.setReadonly(false);
        tcUserNotAdmin.setFixedemail(false);
        tcUserNotAdmin.setTemporary(false);
        tcUserNotAdmin.setAttributes("{}");

        return tcUserRepository.save(tcUserNotAdmin);
        }
}
