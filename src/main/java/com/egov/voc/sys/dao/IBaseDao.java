package com.egov.voc.sys.dao;

import java.util.List;

public interface IBaseDao {
	<T> T select(Object param);
	<T> List<T> selectList(Object param);
	int selectListCount(Object param);
	int insert(Object param);
	int update(Object param);
	int delete(Object param);
}
