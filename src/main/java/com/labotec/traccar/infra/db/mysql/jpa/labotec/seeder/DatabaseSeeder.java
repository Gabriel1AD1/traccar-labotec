package com.labotec.traccar.infra.db.mysql.jpa.labotec.seeder;

import com.labotec.traccar.app.ports.out.BusStopService;
import com.labotec.traccar.app.ports.out.CompanyService;
import com.labotec.traccar.app.ports.out.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final CompanyService companyService;
    private final BusStopService busStopService;
    private final UserService userService;
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
    }
}
