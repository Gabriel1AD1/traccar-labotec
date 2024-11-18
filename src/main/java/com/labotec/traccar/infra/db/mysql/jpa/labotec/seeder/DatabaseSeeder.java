package com.labotec.traccar.infra.db.mysql.jpa.labotec.seeder;

import com.labotec.traccar.app.usecase.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.out.BusStopService;
import com.labotec.traccar.app.usecase.ports.out.CompanyService;
import com.labotec.traccar.app.usecase.ports.out.UserService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.web.dto.entel.create.BusStopDTO;
import com.labotec.traccar.domain.web.dto.entel.create.CompanyDTO;
import com.labotec.traccar.domain.web.dto.entel.create.UserDTO;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.CompanyEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleTypeEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.BusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.VehicleTypeRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.CompanyRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.BusStopRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteRepositoryJpa; // Suponiendo que tienes este repositorio
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final CompanyService companyService;
    private final BusStopService busStopService;
    private final UserService userService;
    @Override
    public void run(String... args) throws Exception {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setCompanyId(10000L);
        Company company = companyService.create(companyDTO);
        System.out.println(company.toString());
        UserDTO userDTO = new UserDTO();
        userDTO.setCompanyId(10000L);
        userDTO.setUserId(10000L);
        User user = userService.createUser(userDTO);
        System.out.println(user.toString());
        BusStopDTO busStop = new BusStopDTO();
        busStop.setName("Paradero 01 ");
        busStop.setLatitude("11.02021");
        busStop.setLatitude("-11.02021");
        busStopService.create(busStop,10000L);

        userService.deleteUserById(10000L);
        companyService.deleteCompanyById(10000L);
        busStopService.deleteById(10000L , 10000L);

    }
}
