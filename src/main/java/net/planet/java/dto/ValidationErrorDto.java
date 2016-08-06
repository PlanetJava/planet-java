package net.planet.java.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/5/16.
 */
public class ValidationErrorDto {
	@Getter
	@Setter
	private List<FieldErrorDto> fieldErrors = new ArrayList<>();

	public void addFieldError(String path, String message) {
		FieldErrorDto error = new FieldErrorDto(path, message);
		fieldErrors.add(error);
	}

}
