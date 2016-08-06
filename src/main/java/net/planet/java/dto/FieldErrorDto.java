package net.planet.java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/5/16.
 */
@Data
@AllArgsConstructor
public class FieldErrorDto {
	private String field;
	private String message;
}
