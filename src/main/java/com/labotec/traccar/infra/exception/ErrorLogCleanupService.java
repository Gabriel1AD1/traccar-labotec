package com.labotec.traccar.infra.exception;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.ErrorLogEntityRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class ErrorLogCleanupService {

    private final ErrorLogEntityRepository errorLogRepository;
    private final Logger logger = LoggerFactory.getLogger(ErrorLogCleanupService.class);


    /**
     * Tarea programada para eliminar errores antiguos todos los días a las 3:00 AM.
     */
    @Scheduled(cron = "0 0 3 * * ?") // Cron job para las 3:00 AM todos los días
    public void cleanupOldErrors() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1); // Ejemplo: eliminar errores más antiguos de una semana
        int deletedRecords = errorLogRepository.deleteByTimestampBefore(oneWeekAgo);
        logger.info("Deleted {} old error logs.", deletedRecords);
    }
}
