package net.planet.java.rest;

import net.planet.java.dto.ValidationErrorDto;
import net.planet.java.exceptions.ResourceDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
@ControllerAdvice
public class RestControllerAdvice {

    private MessageSource messageSource;

    @Autowired
    public RestControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDto processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    private ValidationErrorDto processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDto dto = new ValidationErrorDto();

        for (FieldError fieldError: fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }

        return dto;
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }

        return localizedErrorMessage;
    }
}
