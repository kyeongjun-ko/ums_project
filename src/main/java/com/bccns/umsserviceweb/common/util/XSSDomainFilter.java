package com.bccns.umsserviceweb.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class XSSDomainFilter<DOMAIN> {

	public DOMAIN getFilterdObject(DOMAIN d) {
		Object object = d;
		if (object != null) {

			XSSFilter xssFilter = new XSSFilter();

			try {

				Class<?> clazz = null;

				clazz = object.getClass();

				Field[] fields = clazz.getDeclaredFields();

				for (Field f : fields) {
					XSSFilterField xssFilterField = f.getAnnotation(XSSFilterField.class);
					if (xssFilterField != null && f.getType().getName().indexOf("java.lang.String") != -1) {
						//if (f.getType().getName().indexOf("java.lang.String") != -1) {
							String getMethodName = "get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
							String setMethodName = "set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);

							Method getMethod = clazz.getMethod(getMethodName, null);
							Method setMethod = clazz.getMethod(setMethodName, java.lang.String.class);
							if (getMethod != null) {
								String fieldValue = (String) getMethod.invoke(d, null);
								fieldValue = xssFilter.filter(fieldValue); // XSS필터링

								setMethod.invoke(d, fieldValue);
							}
						//}
					}
				} // END OF FOR (Field)
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return d;
	}

	public List<DOMAIN> getFilterdObject(List<DOMAIN> list) {
		try {
			if (list != null) {

				XSSFilter xssFilter = new XSSFilter();

				for (DOMAIN item : list) {
					Object object = item;

					Class<?> clazz = null;

					clazz = object.getClass();

					Field[] fields = clazz.getDeclaredFields();

					for (Field f : fields) {
						XSSFilterField xssFilterField = f.getAnnotation(XSSFilterField.class);
						if (xssFilterField != null && f.getType().getName().indexOf("java.lang.String") != -1) {
							//if (f.getType().getName().indexOf("java.lang.String") != -1) {
								String getMethodName = "get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
								String setMethodName = "set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);

								Method getMethod = clazz.getMethod(getMethodName, null);
								Method setMethod = clazz.getMethod(setMethodName, java.lang.String.class);
								if (getMethod != null) {
									String fieldValue = (String) getMethod.invoke(object, null);
									fieldValue = xssFilter.filter(fieldValue); // XSS필터링
									setMethod.invoke(object, fieldValue);
								}
							//}
						}
					} // END OF FOR (Field)
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
