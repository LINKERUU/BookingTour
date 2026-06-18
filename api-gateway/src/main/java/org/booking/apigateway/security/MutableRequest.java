package org.booking.apigateway.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.*;

public class MutableRequest extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders = new LinkedCaseInsensitiveMap<>();

    public MutableRequest(HttpServletRequest request) {
        super(request);
    }

    public void addHeader(String name, String value) {
        customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String value = customHeaders.get(name);
        return value != null ? value : super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        names.addAll(customHeaders.keySet());
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        String value = customHeaders.get(name);

        if (value != null) {
            return Collections.enumeration(List.of(value));
        }

        return super.getHeaders(name);
    }
}