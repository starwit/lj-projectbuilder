package de.spring.persistence.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import de.spring.persistence.entity.AbstractEntity;
import de.spring.persistence.response.ResponseCode;
import de.spring.persistence.response.ResponseMetadata;

public class EntityValidator {
	
	public static ResourceBundle RES = ResourceBundle.getBundle("ValidationMessages", Locale.getDefault());
	
	public static ResponseMetadata validate(AbstractEntity<Long> entity) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<AbstractEntity<Long>>> constraintViolations = validator.validate(entity);
		
		String entityname = entity.getClass().getSimpleName();

		if(!constraintViolations.isEmpty()) {
			List<ValidationError> validationErrors = new ArrayList<ValidationError>();
			String interpolatedMessage = "";
			for (ConstraintViolation<AbstractEntity<Long>> constraintViolation : constraintViolations) {
				String attributeName = RES.getString(constraintViolation.getPropertyPath().toString());
				ValidationError ve = new ValidationError(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage().replace("{fieldname}", attributeName));
				validationErrors.add(ve);
				interpolatedMessage = interpolatedMessage + "\r\n" + ve.getMessage();
			}
			
			return new ResponseMetadata(ResponseCode.NOT_VALID, "validation.error." + entityname, validationErrors);
		} 
		else return new ResponseMetadata(ResponseCode.OK, "Die Validierung war erfolgreich.");
	}
	
	public static ResponseMetadata savedResultExists(AbstractEntity result) {
		ResponseMetadata state = new ResponseMetadata();
		
		if (result == null || result.getId() == null) {
			state.setResponseCode(ResponseCode.ERROR);
			state.setMessage("Der Eintrag konnte nicht gespeichert werden.");
		} else {
			state.setResponseCode(ResponseCode.OK);
			state.setMessage("Der Eintrag mit ID " + result.getId() + " wurde erfolgreich gespeichert.");
		}
		return state;
	}
	
	public static ResponseMetadata found(AbstractEntity result) {
		
		ResponseMetadata state = new ResponseMetadata();
		
		if (result == null || result.getId() == null) {
			state.setResponseCode(ResponseCode.NOT_FOUND);
			state.setMessage("Der Eintrag wurde nicht gefunden.");
		} else {
			state = setStateOk(state);
		}
		return state;
	}

	public static ResponseMetadata isNotEmpty(Collection<? extends AbstractEntity> result) {
		ResponseMetadata wrapper = new ResponseMetadata();
		wrapper = checkNotEmpty(result, wrapper);
		return wrapper;
	}

	public static ResponseMetadata isNotEmpty(AbstractEntity result, Collection<? extends AbstractEntity> children) {
		ResponseMetadata wrapper = found(result);
		if (ResponseCode.OK.equals(wrapper.getResponseCode())) {
			wrapper = checkNotEmpty(children, wrapper);
		}
		
		return wrapper;
	}
	
	private static ResponseMetadata checkNotEmpty(Collection<? extends AbstractEntity> result,
			ResponseMetadata wrapper) {
		if (result == null || result.size() == 0) {
			wrapper.setResponseCode(ResponseCode.EMPTY);
			wrapper.setMessage("Es wurden keine Einträge gefunden.");
		} else {
			wrapper = setStateOk(wrapper);
		}
		return wrapper;
	}
	
	private static ResponseMetadata setStateOk(ResponseMetadata state) {
		state.setResponseCode(ResponseCode.OK);
		state.setMessage("Es sind keine Fehler aufgetreten.");
		return state;
	}

}
