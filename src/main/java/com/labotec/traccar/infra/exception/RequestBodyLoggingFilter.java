package com.labotec.traccar.infra.exception;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestBodyLoggingFilter extends GenericFilterBean {

    // Lista de endpoints que se deben excluir
    private static final List<String> EXCLUDED_ENDPOINTS = List.of(
            "/api/v1/integration-traccar",
            "/ws" // Excluir el endpoint de WebSocket

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

            // Envolver la solicitud para permitir múltiples lecturas
            CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpServletRequest);

            // Leer y almacenar el cuerpo
            String body = cachedRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            cachedRequest.setAttribute("body", body);

            // Pasar la solicitud envuelta a la cadena de filtros
            chain.doFilter(cachedRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    // Verificar si el endpoint está en la lista de excluidos
    private boolean isExcluded(String uri) {
        return EXCLUDED_ENDPOINTS.stream().anyMatch(uri::startsWith);
    }
}
