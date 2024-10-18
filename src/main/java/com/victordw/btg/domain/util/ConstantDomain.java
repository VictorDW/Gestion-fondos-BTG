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
	public static final Integer NUMBER_ZERO = 0;
	public static final String TYPE_OPENING = "Apertura";
	public static final String TYPE_CANCELLATION = "Cancelación";
	public static final String SMS = "sms";
	public static final String SUBJECT = "Suscrición exitosa";
	public static final String MESSAGE_NOTIFICATION = "Felicidades, te acabas de suscribir al fondo %s";

	public static final String NOT_FOUND_MESSAGE = "El %s con id %s no existe";
	public static final String AMOUNT_TO_INVEST_IS_NOT_VALID = "El monto a invertir no cumple con el mínimo necesario para vincularse al fondo %s";
	public static final String INSUFFICIENT_BALANCE = "No tiene saldo disponible para vincularse al fondo %s";
	public static final String FUND_ALREADY_EXISTS = "Ya cuentas con una suscrición al fondo %s";
}
