package com.kspo.voc.comn.log;


import com.kspo.voc.comn.util.Utilities;

/**
 * <pre>
 * com.kspo.base.common.log - LogDaoLocal.java
 * </pre>
 *
 * @ClassName : LogDaoLocal
 * @Description : LogDaoLocal
 * @author : 김성태
 * @date : 2021. 4. 23.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

public class LogDaoLocal {

	public static ThreadLocal<ChangeLogVo> local = new ThreadLocal<ChangeLogVo>();

	public static ChangeLogVo get() {
		if (local.get() == null) {
			ChangeLogVo vo = new ChangeLogVo();
			vo.setChngCallDt(Utilities.getDateTimeString());
			local.set(vo);
		}
		return local.get();

	}

	public static void clear() {
		local.set(null);

	}

}