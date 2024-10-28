package com.labotec.traccar.infra.db.mysql.jpa.labotec.seeder;

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

import java.util.Arrays;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final VehicleTypeRepositoryJpa vehicleTypeRepository; // Repositorio de la entidad Vehicle
    private final CompanyRepositoryJpa companyRepository; // Repositorio de la entidad Company
    private final BusStopRepositoryJpa busStopRepository; // Repositorio de la entidad BusStop
    private final RouteRepositoryJpa routeRepository; // Repositorio de la entidad Route

    @Override
    public void run(String... args) throws Exception {
        // Insertar tipos de vehículos si no existen
        if (vehicleTypeRepository.count() == 0) {
            vehicleTypeRepository.saveAll(Arrays.asList(
                    new VehicleTypeEntity("Triciclo"),
                    new VehicleTypeEntity("Furgoneta"),
                    new VehicleTypeEntity("Motos"),
                    new VehicleTypeEntity("Camión"),
                    new VehicleTypeEntity("Automóvil")
            ));

            System.out.println("Base de datos inicializada con tipos de vehículos.");
        }
        if (false) {


            // Insertar compañías si no existen
            if (companyRepository.count() == 0) {
                companyRepository.saveAll(Arrays.asList(
                        new CompanyEntity("Compañía A", "123456789", (byte) 1),
                        new CompanyEntity("Compañía B", "987654321", (byte) 1)
                ));

                System.out.println("Base de datos inicializada con compañías.");
            }

            // Insertar paraderos (Bus Stops) si no existen
            if (busStopRepository.count() == 0) {
                busStopRepository.saveAll(Arrays.asList(
                        new BusStopEntity("Paradero 1", "12.3456", "-76.5678", (byte) 1, null),
                        new BusStopEntity("Paradero 2", "12.1234", "-76.1234", (byte) 1, null)
                ));

                System.out.println("Base de datos inicializada con paraderos.");
            }

            // Insertar rutas si no existen
            if (routeRepository.count() == 0) {
                routeRepository.saveAll(Arrays.asList(
                        new RouteEntity("Ruta 1", (byte) 1, null, null, companyRepository.findAll().iterator().next()), // Asignando a una compañía
                        new RouteEntity("Ruta 2", (byte) 1, null, null, companyRepository.findAll().iterator().next())  // Asignando a una compañía
                ));

                System.out.println("Base de datos inicializada con rutas.");
            }
        }

    }
}
