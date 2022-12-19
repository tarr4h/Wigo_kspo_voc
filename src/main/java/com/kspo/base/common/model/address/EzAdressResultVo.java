package com.kspo.base.common.model.address;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EzAdressResultVo {
	private EzAddressCodeVo common;
	private List<EzAddressVo> juso;
}
