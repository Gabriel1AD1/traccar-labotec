package com.labotec.traccar.infra.exception;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.ErrorLogEntityRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ErrorLogCleanupService {

    private final ErrorLogEntityRepository errorLogRepository;

    public ErrorLogCleanupService(ErrorLogEntityRepository errorLogRepository) {
        this.errorLogRepository = errorLogRepository;
    }

    /**
     * Tarea programada para eliminar errores antiguos todos los días a las 3:00 AM.
     */
    @Scheduled(cron = "0 0 3 * * ?") // Cron job para las 3:00 AM todos los días
    public void cleanupOldErrors() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1); // Ejemplo: eliminar errores más antiguos de una semana
        int deletedRecords = errorLogRepository.deleteByTimestampBefore(oneWeekAgo);
        System.out.println("Deleted " + deletedRecords + " old error logs.");
    }
}
