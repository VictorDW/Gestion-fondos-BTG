package com.victordw.btg.domain.util;

public final class OrderUtil {

	private static final String DEFAULT_ORDER_BY = "name";

	private enum Direction {
		ASC, DESC
	}

	public static OrderData definiOrderData(String direction) {

		Direction temporaryDirection = Direction.DESC.toString().equalsIgnoreCase(direction) ?
				Direction.DESC :
				Direction.ASC;

		return new OrderData(
				temporaryDirection.toString(),
				DEFAULT_ORDER_BY
		);
	}
}
