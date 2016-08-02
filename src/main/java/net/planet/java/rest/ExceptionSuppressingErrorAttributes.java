package net.planet.java.rest;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */


@Component
public class ExceptionSuppressingErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes,
                                                  boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        errorAttributes.remove("exception");
        Object message = requestAttributes.getAttribute("javax.servlet.error.message", RequestAttributes.SCOPE_REQUEST);
        if (message != null) {
            errorAttributes.put("message", message);
        }
        return errorAttributes;
    }
}

