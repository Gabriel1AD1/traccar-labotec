package com.labotec.traccar.infra.web.controller.ws;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.Executors;

@Component
public class LogWebSocketHandler extends TextWebSocketHandler {

    private static final String LOG_FILE_PATH = "logs/application.log";

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Path path = Paths.get(LOG_FILE_PATH);
                WatchService watchService = FileSystems.getDefault().newWatchService();
                path.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.context().toString().equals(path.getFileName().toString())) {
                            List<String> logs = Files.readAllLines(path);
                            String latestLogs = String.join("\n", logs);
                            session.sendMessage(new TextMessage(latestLogs));
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
