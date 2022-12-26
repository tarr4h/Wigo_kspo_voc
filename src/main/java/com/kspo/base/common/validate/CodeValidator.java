package com.kspo.base.common.validate;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.ComnCdBaseVo;
import com.kspo.voc.sys.service.ComnCdService;

public class CodeValidator implements ConstraintValidator<CodeValue, CharSequence> {
	String codeId;
	String[] codes;
	ComnCdService codeService;

	@Override
	public void initialize(CodeValue constraintAnnotation) {
		codeService = Utilities.getBean(ComnCdService.class);
		codeId = constraintAnnotation.codeId();
		codes = constraintAnnotation.codes();
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (Utilities.isEmpty(value)) {
			return true;
		}
		if (Utilities.isNotEmpty(codeId)) {
			ComnCdBaseVo vo = new ComnCdBaseVo();
			vo.setTopComnCd(codeId);
			vo.setComnCd(value.toString());
			try {
				return codeService.get(vo) != null;
			} catch (Exception e) {
				return false;
			}
		}
		if (Utilities.isNotEmpty(codes))
			return Arrays.asList(codes).contains(value);

		return true;

	}
}
