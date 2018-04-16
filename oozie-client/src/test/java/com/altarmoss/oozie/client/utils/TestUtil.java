package com.altarmoss.oozie.client.utils;

import java.lang.reflect.Method;

public class TestUtil {
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
		Method result = clazz.getDeclaredMethod(methodName, parameterTypes);
		result.setAccessible(true);
		return result;
	}
}
