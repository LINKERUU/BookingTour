package org.booking.bookingui.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAccessException.class)
    public String handleResourceAccess(
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        redirectAttributes.addFlashAttribute(
                "error",
                "Внешний сервис недоступен"
        );

        return redirectBack(request);
    }

    @ExceptionHandler(RestClientResponseException.class)
    public String handleRestClient(
            RestClientResponseException ex,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        String errorMessage = "Ошибка обращения к внешнему сервису";

        String responseBody = ex.getResponseBodyAsString();
        if (!responseBody.isBlank()) {

            ObjectMapper mapper = new ObjectMapper();
            Map<?, ?> errorMap = mapper.readValue(responseBody, Map.class);

            if (errorMap.containsKey("message")) {
                errorMessage = (String) errorMap.get("message");
            }
        }

        redirectAttributes.addFlashAttribute("error", errorMessage);

        return redirectBack(request);
    }

    @ExceptionHandler(Exception.class)
    public String handleUnexpected(
            Exception ex,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        log.error("Unexpected error", ex);

        redirectAttributes.addFlashAttribute(
                "error",
                "Внутренняя ошибка сервиса"
        );

        return redirectBack(request);
    }

    private String redirectBack(HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        return referer != null ? "redirect:" + referer : "redirect:/index";
    }
}