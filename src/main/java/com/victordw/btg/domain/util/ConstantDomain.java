package com.victordw.btg.domain.util;

public class ConstantDomain {

	private ConstantDomain() {
		throw new IllegalArgumentException("This is a utility class and cannot be instantiated");
	}

	public enum Utils {
		CLIENT("cliente"),
		FUND("fondo");


		private final String util;

		Utils(String util) {
			this.util = util;
		}

		public String get() {
			return util;
		}
	}
	public static final String NOT_FOUND_MESSAGE = "El %s con id %s no existe";
}
