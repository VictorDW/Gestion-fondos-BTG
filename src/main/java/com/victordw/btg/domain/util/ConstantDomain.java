package com.victordw.btg.domain.util;

public class ConstantDomain {

	private ConstantDomain() {
		throw new IllegalArgumentException("This is a utility class and cannot be instantiated");
	}

	public static final String NOT_FOUND_MESSAGE = "El cliente con id %s no existe";
}
