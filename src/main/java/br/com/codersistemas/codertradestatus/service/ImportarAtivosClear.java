package br.com.codersistemas.codertradestatus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.codersistemas.codertradestatus.model.ItemCarteira;

public class ImportarAtivosClear {

	public static void main(String[] args) {
		
		String txtClear = "SWING TRADE\r\n"
				+ "BBDC411BRADESCO PN EJ N1\r\n"
				+ "Quantidade\r\n"
				+ "100\r\n"
				+ "Valor\r\n"
				+ "R$ 2.100,00\r\n"
				+ "Cotação\r\n"
				+ "21,000,00%COGN3COGNA ON ON NM\r\n"
				+ "Quantidade\r\n"
				+ "100\r\n"
				+ "Valor\r\n"
				+ "R$ 439,00\r\n"
				+ "Cotação\r\n"
				+ "4,390,00%ITUB4ITAUUNIBANCOPN ED N1\r\n"
				+ "Quantidade\r\n"
				+ "200\r\n"
				+ "Valor\r\n"
				+ "R$ 5.118,00\r\n"
				+ "Cotação\r\n"
				+ "25,590,00%OIBR4OI PN N1\r\n"
				+ "Quantidade\r\n"
				+ "180\r\n"
				+ "Valor\r\n"
				+ "R$ 399,60\r\n"
				+ "Cotação\r\n"
				+ "2,220,00%PETR4PETROBRAS PN N2\r\n"
				+ "Quantidade\r\n"
				+ "300\r\n"
				+ "Valor\r\n"
				+ "R$ 5.967,00\r\n"
				+ "Cotação\r\n"
				+ "19,890,00%";
		
		ImportarAtivosClear iac = new ImportarAtivosClear();
		List<ItemCarteira> itens = iac.importar("");
		System.out.println(itens);
		
	}

	public List<ItemCarteira> importar(String txtClear) {
		
		String[] split = txtClear.replace("%", "%\n").split("\n");
		
		List<ItemCarteira> itens = new ArrayList<>();
		
		ItemCarteira item = null;
		
		int i = 0;
		for (String linha : split) {
			switch (i++) {
			case 0:
				continue;
			case 1:
				item = new ItemCarteira();
				itens.add(item);
				Pattern p = Pattern.compile("\\d+");
				Matcher matcher = p.matcher(linha);
				while (matcher.find()) {
					int end = matcher.end();
					String substring = linha.substring(0, end);
					item.setNomeAtivo(substring);
					break;
				}
				continue;
			case 2:
				continue;
			case 3:
				item.setQuantidade(new Integer(linha.trim()));
				continue;
			case 4:
				continue;
			case 5:
				continue;
			case 6:
				continue;
			case 7:
				Pattern p2 = Pattern.compile(",");
				Matcher matcher2 = p2.matcher(linha);
				while (matcher2.find()) {
					int end = matcher2.end();
					String substring = linha.substring(0, end+2);
					item.setValorAquisicao(new Float(substring.replace("R$", "").replace(".", "").replace(",", ".").trim()));
					break;
				}
				i = 1;
				continue;
			}
		}

		return itens;
	}

}



