package net.planet.java.rest;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/2/16.
 */
public class NestedContentResource<T> extends ResourceSupport {
    private final Resources<T> nested;

    public NestedContentResource(Iterable<T> toNest) {
        this.nested = new Resources<T>(toNest);
    }

    @JsonUnwrapped
    public Resources<T> getNested() {
        return this.nested;
    }
}
