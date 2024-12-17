package com.labotec.traccar.infra.sp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StoredProcedureCreator implements ApplicationRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Verificando si el procedimiento almacenado existe...");

        // Verificar si el SP ya existe en la base de datos
        String checkProcedureQuery = """
            SELECT COUNT(1)
            FROM information_schema.ROUTINES
            WHERE ROUTINE_SCHEMA = DATABASE() AND ROUTINE_NAME = 'UpdateSensorStateAndReturn';
        """;

        int procedureExists = jdbcTemplate.queryForObject(checkProcedureQuery, Integer.class);

        if (procedureExists == 0) {
            System.out.println("El procedimiento no existe. Creando el procedimiento almacenado...");

            // Crear el SP si no existe
            String createProcedure = """
                CREATE PROCEDURE UpdateSensorStateAndReturn(
                    IN p_dispositivo_id BIGINT,
                    IN p_nombre_sensor VARCHAR(255),
                    IN p_estado_nuevo VARCHAR(255)
                )
                BEGIN
                    DECLARE v_tiempo_diferencia BIGINT;

                    IF EXISTS (
                        SELECT 1
                        FROM tbl_sensor_dispositivo
                        WHERE dispositivo_id = p_dispositivo_id AND nombre_sensor = p_nombre_sensor
                    ) THEN
                        SELECT TIMESTAMPDIFF(MINUTE, inicio_estado_actual, UTC_TIMESTAMP()) 
                        INTO v_tiempo_diferencia
                        FROM tbl_sensor_dispositivo
                        WHERE dispositivo_id = p_dispositivo_id AND nombre_sensor = p_nombre_sensor;

                        IF EXISTS (
                            SELECT 1
                            FROM tbl_sensor_dispositivo
                            WHERE dispositivo_id = p_dispositivo_id 
                              AND nombre_sensor = p_nombre_sensor 
                              AND estado_actual != p_estado_nuevo
                        ) THEN
                            UPDATE tbl_sensor_dispositivo
                            SET tiempo_acumulado = 0,
                                estado_actual = p_estado_nuevo,
                                inicio_estado_actual = UTC_TIMESTAMP()
                            WHERE dispositivo_id = p_dispositivo_id AND nombre_sensor = p_nombre_sensor;
                        ELSE
                            UPDATE tbl_sensor_dispositivo
                            SET tiempo_acumulado = tiempo_acumulado + v_tiempo_diferencia
                            WHERE dispositivo_id = p_dispositivo_id AND nombre_sensor = p_nombre_sensor;
                        END IF;
                    END IF;

                    SELECT id, dispositivo_id, nombre_sensor, tipo_sensor, tipo_dato, 
                           estado_actual, inicio_estado_actual, tiempo_acumulado
                    FROM tbl_sensor_dispositivo
                    WHERE dispositivo_id = p_dispositivo_id AND nombre_sensor = p_nombre_sensor;
                END;
            """;

            jdbcTemplate.execute(createProcedure);
            System.out.println("Procedimiento almacenado creado exitosamente.");
        } else {
            System.out.println("El procedimiento ya existe. No se necesita crear.");
        }
    }
}
