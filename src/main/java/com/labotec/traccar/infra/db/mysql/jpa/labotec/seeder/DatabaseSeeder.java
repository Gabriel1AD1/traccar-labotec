package com.labotec.traccar.infra.db.mysql.jpa.labotec.seeder;

import com.labotec.traccar.app.ports.out.services.BusStopService;
import com.labotec.traccar.app.ports.out.services.CompanyService;
import com.labotec.traccar.app.ports.out.services.UserService;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ConfigurationRouteProcessServerEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.ConfigurationRouteProcessServerEntityRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final CompanyService companyService;
    private final BusStopService busStopService;
    private final UserService userService;
    private final ConfigurationRouteProcessServerEntityRepositoryJpa processServerEntityRepositoryJpa;
    @Override
    public void run(String... args) throws Exception {
        /*
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setCompanyId(10000L);
        Company company = companyService.create(companyDTO);
        System.out.println(company.toString());
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setCompanyId(10000L);
        userCreateDTO.setUserId(10000L);
        User user = userService.createUser(userCreateDTO);
        System.out.println(user.toString());
        BusStopCreateDTO busStop = new BusStopCreateDTO();
        busStop.setName("Paradero 01 ");
        busStop.setLatitude("11.02021");
        busStop.setLatitude("-11.02021");
        busStopService.create(busStop,10000L);

        userService.deleteUserById(10000L);
        companyService.deleteCompanyById(10000L);
        busStopService.deleteById(10000L , 10000L);*/

       if (processServerEntityRepositoryJpa.count() == 0 ){
           processServerEntityRepositoryJpa.save(ConfigurationRouteProcessServerEntity
                   .builder()
                           .radiusValidateBusStop(15.0)
                           .configurationPrimary(true)
                   .build());
       }
    }
}
