package net.planet.java.rest;

import net.planet.java.exceptions.ResourceDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@ControllerAdvice
public class RestControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(IllegalArgumentException ex,
                                               HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(ResourceDoesNotExistException.class)
    public void handleResourceDoesNotExistException(ResourceDoesNotExistException ex,
                                                    HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(),
                "The resource '" + request.getRequestURI() + "' does not exist");
    }
}
