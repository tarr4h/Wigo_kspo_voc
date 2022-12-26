package com.kspo.base.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.kspo.voc.comn.util.Utilities;

public class YnValidator implements ConstraintValidator<YnValue, CharSequence> {

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (Utilities.isEmpty(value)) {
			return true;
		}

		return "N".equals(value) || "Y".equals(value);
	}
}
