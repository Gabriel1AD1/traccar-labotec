package com.labotec.traccar.infra.exception;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestBodyLoggingFilter extends GenericFilterBean {

    // Lista de endpoints que se deben excluir
    private static final List<String> EXCLUDED_ENDPOINTS = List.of(
            "/api/v1/integration-traccar"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpServletRequest) {
            String uri = httpServletRequest.getRequestURI();

            // Excluir los endpoints configurados
            if (isExcluded(uri)) {
                chain.doFilter(request, response);
                return;
            }

            // Capturar el cuerpo si el endpoint no está excluido
            BufferedReader reader = httpServletRequest.getReader();
            String body = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            request.setAttribute("body", body);
        }
        chain.doFilter(request, response);
    }

    // Verificar si el endpoint está en la lista de excluidos
    private boolean isExcluded(String uri) {
        return EXCLUDED_ENDPOINTS.stream().anyMatch(uri::startsWith);
    }
}
