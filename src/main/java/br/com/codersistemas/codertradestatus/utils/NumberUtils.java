package br.com.codersistemas.codertradestatus.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {

	public static String formatBR(BigDecimal valor) {
		NumberFormat instance = NumberFormat.getInstance(new Locale("pt","BR"));
		instance.setMinimumFractionDigits(2);
		return instance.format(valor.floatValue());
	}

	public static String formatBR(Float valor) {
		return valor != null ? formatBR(new BigDecimal(valor.floatValue())) : null;
	}

}
