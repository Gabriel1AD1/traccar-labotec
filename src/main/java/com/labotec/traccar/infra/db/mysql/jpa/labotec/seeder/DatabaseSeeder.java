package com.labotec.traccar.infra.db.mysql.jpa.labotec.seeder;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleTypeEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.VehicleTypeRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private VehicleTypeRepositoryJpa vehicleRepository;  // Repositorio de la entidad Vehicle

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya hay datos en la base de datos
        if (vehicleRepository.count() == 0) {
            // Insertar algunos tipos de vehículos
            vehicleRepository.save(new VehicleTypeEntity("Triciclo"));
            vehicleRepository.save(new VehicleTypeEntity("Furgoneta"));
            vehicleRepository.save(new VehicleTypeEntity("Motos"));
            vehicleRepository.save(new VehicleTypeEntity("Camión"));
            vehicleRepository.save(new VehicleTypeEntity("Automóvil"));
            vehicleRepository.save(new VehicleTypeEntity("SUV"));
            vehicleRepository.save(new VehicleTypeEntity("Autobús"));
            vehicleRepository.save(new VehicleTypeEntity("Minivan"));
            vehicleRepository.save(new VehicleTypeEntity("Bicicleta"));
            vehicleRepository.save(new VehicleTypeEntity("Motocicleta"));
            vehicleRepository.save(new VehicleTypeEntity("Camioneta"));
            vehicleRepository.save(new VehicleTypeEntity("Coche Eléctrico"));
            vehicleRepository.save(new VehicleTypeEntity("Carro de Golf"));
            vehicleRepository.save(new VehicleTypeEntity("Remolque"));
            vehicleRepository.save(new VehicleTypeEntity("Caravana"));
            vehicleRepository.save(new VehicleTypeEntity("Tractor"));
            vehicleRepository.save(new VehicleTypeEntity("Vehículo Utilitario Deportivo (UTV)"));
            vehicleRepository.save(new VehicleTypeEntity("Vehículo Todo Terreno (ATV)"));

            System.out.println("Base de datos inicializada con datos de prueba.");
        }
    }

}