package com.ayyayo.g.common;

import com.ayyayo.g.model.KeyValuePair;

import java.util.Arrays;
import java.util.List;

public class CurrencyHelper {
	private final List<KeyValuePair<String, String>> currencyList;

	public CurrencyHelper() {
		currencyList = Arrays.asList(
				new KeyValuePair<>("INR", "INR India Rupees"),
				new KeyValuePair<>("USD", "USD United States Dollars"),
				new KeyValuePair<>("EUR", "EUR Euro"),
				new KeyValuePair<>("CAD", "CAD Canada Dollars"),
				new KeyValuePair<>("GBP", "GBP United Kingdom Pounds"),
				new KeyValuePair<>("DEM", "DEM Germany Deutsche Marks"),
				new KeyValuePair<>("FRF", "FRF France Francs"),
				new KeyValuePair<>("JPY", "JPY Japan Yen"),
				new KeyValuePair<>("NLG", "NLG Netherlands Guilders"),
				new KeyValuePair<>("ITL", "ITL Italy Lira"),
				new KeyValuePair<>("CHF", "CHF Switzerland Francs"),
				new KeyValuePair<>("DZD", "DZD Algeria Dinars"),
				new KeyValuePair<>("ARP", "ARP Argentina Pesos"),
				new KeyValuePair<>("BBD", "BBD Barbados Dollars"),
				new KeyValuePair<>("BBD", "BBD Barbados Dollars"),
				new KeyValuePair<>("AUD", "AUD Australia Dollars"),
				new KeyValuePair<>("ATS", "ATS Austria Schillings"),
				new KeyValuePair<>("BSD", "BSD Bahamas Dollars"),
				new KeyValuePair<>("BBD", "BBD Barbados Dollars"),
				new KeyValuePair<>("BEF", "BEF Belgium Francs"),
				new KeyValuePair<>("BMD", "BMD Bermuda Dollars"),
				new KeyValuePair<>("BRR", "BRR Brazil Real"),
				new KeyValuePair<>("BGL", "BGL Bulgaria Lev"),
				new KeyValuePair<>("CLP", "CLP Chile Pesos"),
				new KeyValuePair<>("CNY", "CNY China Yuan Renmimbi"),
				new KeyValuePair<>("CYP", "CYP Cyprus Pounds"),
				new KeyValuePair<>("CSK", "CSK Czech Republic Koruna"),
				new KeyValuePair<>("DKK", "DKK Denmark Kroner"),
				new KeyValuePair<>("XCD", "XCD Eastern Caribbean Dollars"),
				new KeyValuePair<>("EGP", "EGP Egypt Pounds"),
				new KeyValuePair<>("FJD", "FJD Fiji Dollars"),
				new KeyValuePair<>("FIM", "FIM Finland Markka"),
				new KeyValuePair<>("XAU", "XAU Gold Ounces"),
				new KeyValuePair<>("GRD", "GRD Greece Drachmas"),
				new KeyValuePair<>("HKD", "HKD Hong Kong Dollars"),
				new KeyValuePair<>("HUF", "HUF Hungary Forint"),
				new KeyValuePair<>("ISK", "ISK Iceland Krona"),
				new KeyValuePair<>("IDR", "IDR Indonesia Rupiah"),
				new KeyValuePair<>("IEP", "IEP Ireland Punt"),
				new KeyValuePair<>("ILS", "ILS Israel New Shekels"),
				new KeyValuePair<>("JMD", "JMD Jamaica Dollars"),
				new KeyValuePair<>("JOD", "JOD Jordan Dinar"),
				new KeyValuePair<>("LBP", "LBP Lebanon Pounds"),
				new KeyValuePair<>("LUF", "LUF Luxembourg Francs"),
				new KeyValuePair<>("MYR", "MYR Malaysia Ringgit"),
				new KeyValuePair<>("MXP", "MXP Mexico Pesos"),
				new KeyValuePair<>("NZD", "NZD New Zealand Dollars"),
				new KeyValuePair<>("NOK", "NOK Norway Kroner"),
				new KeyValuePair<>("PKR", "PKR Pakistan Rupees"),
				new KeyValuePair<>("XPD", "XPD Palladium Ounces"),
				new KeyValuePair<>("PHP", "Philippines Pesos"),
				new KeyValuePair<>("XPT", "XPT Platinum Ounces"),
				new KeyValuePair<>("PLZ", "PLZ Poland Zloty"),
				new KeyValuePair<>("PTE", "PTE Portugal Escudo"),
				new KeyValuePair<>("ROL", "ROL Romania Leu"),
				new KeyValuePair<>("RUR", "RUR Russia Rubles"),
				new KeyValuePair<>("SAR", "SAR Saudi Arabia Riyal"),
				new KeyValuePair<>("XAG", "XAG Silver Ounces"),
				new KeyValuePair<>("SGD", "SGD Singapore Dollars"),
				new KeyValuePair<>("SKK", "SKK Slovakia Koruna"),
				new KeyValuePair<>("ZAR", "ZAR South Africa Rand"),
				new KeyValuePair<>("KRW", "KRW South Korea Won"),
				new KeyValuePair<>("ESP", "ESP Spain Pesetas"),
				new KeyValuePair<>("XDR", "XDR Special Drawing Right (IMF)"),
				new KeyValuePair<>("SDD", "SDD Sudan Dinar"),
				new KeyValuePair<>("SEK", "SEK Sweden Krona"),
				new KeyValuePair<>("TWD", "TWD Taiwan Dollars"),
				new KeyValuePair<>("THB", "THB Thailand Baht"),
				new KeyValuePair<>("TTD", "TTD Trinidad and Tobago Dollars"),
				new KeyValuePair<>("TRL", "TRL Turkey Lira"),
				new KeyValuePair<>("VEB", "VEB Venezuela Bolivar"),
				new KeyValuePair<>("ZMK", "ZMK Zambia Kwacha")
		);
	}

	public List<KeyValuePair<String, String>> getCurrencyList() {
		return currencyList;
	}

	public int getCurrencyIndex(String key) {
		for (int i = currencyList.size() - 1; i >= 0; i--) {
			if (currencyList.get(i).getKey().contentEquals(key)) return i;
		}
		return 0;
	}
}
