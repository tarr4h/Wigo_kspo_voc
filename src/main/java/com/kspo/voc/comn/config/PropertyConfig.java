package com.kspo.voc.comn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPropertyService;
import com.kspo.base.common.model.EzPropertyServiceImpl;

/**
 * 
 * <pre>
 * com.kspo.voc.comn.config - TilesConfig.java
 * </pre>
 *
 * @ClassName : TilesConfig
 * @Description : Tiles 설정
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

@Configuration
public class PropertyConfig {
//	@Autowired
//	private Environment environment;

	@Bean(name = "propertiesService")
	EzPropertyService tilesViewResolver() {

		final EzPropertyServiceImpl propertyService = new EzPropertyServiceImpl();
		EzMap map = new EzMap();
		map.setString("storage.root", "c:/nas/files/");
		map.setString("storage.temp", "c:/nas/files/temp/");

		map.setString("currentPageNo", "1");
		map.setString("recordCountPerPage", "30");
		map.setString("pageSize", "10");
		// map.setString("urlSuffix", ".do");
		map.setString("urlSuffix", "");

		map.setString("context.manage", "");

		map.setString("tiles.voc", "voc/");
		map.setString("tiles.voc.blank", "voc/blank/");
		map.setString("tiles.voc.vblank", "voc/vblank/");

		propertyService.setProperties(map);

		return propertyService;
	}
}
