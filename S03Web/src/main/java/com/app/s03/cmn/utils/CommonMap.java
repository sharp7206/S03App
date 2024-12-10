package com.app.s03.cmn.utils;

import org.apache.commons.collections4.map.ListOrderedMap;

@SuppressWarnings("rawtypes")
public class CommonMap extends ListOrderedMap {

	private static final long serialVersionUID = 4921720821082091680L;

	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {		
		return super.put(CommonUtils.convert2CamelCase((String) key), value);//oracle
		//postergre return super.put(((String) key).toUpperCase(), value);
	}
}
