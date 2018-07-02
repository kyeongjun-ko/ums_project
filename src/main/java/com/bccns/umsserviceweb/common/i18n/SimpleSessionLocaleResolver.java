package com.bccns.umsserviceweb.common.i18n;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

public class SimpleSessionLocaleResolver extends SessionLocaleResolver {
	public static final String SESSION_LOCALE_KEY_NAME = "locale";
	private String supportedLocale;

	public void setSupportedLocale(String supportedLocale) {
		this.supportedLocale = supportedLocale;
	}

	protected String getSupportedLocale() {
		return this.supportedLocale;
	}

	public void setLocale(HttpServletRequest request,
			HttpServletResponse response, Locale locale) {
		if (locale != null) {
			locale = checkSupportedLocale(locale);
			request.getSession().setAttribute("locale",
					locale.getLanguage() + "_" + locale.getCountry());
		}

		super.setLocale(request, response, locale);
	}

	protected Locale determineDefaultLocale(HttpServletRequest request) {
		Locale defaultLocale = super.determineDefaultLocale(request);

		defaultLocale = checkSupportedLocale(defaultLocale);
		request.getSession().setAttribute("locale",
				defaultLocale.getLanguage() + "_" + defaultLocale.getCountry());

		return defaultLocale;
	}

	private Locale checkSupportedLocale(Locale locale) {
		String language = locale.getLanguage();
		String country = locale.getCountry();

		if ((country == null) || ("".equals(country)) || (country.length() < 2)) {
			return new Locale("en", "US");
		}

		Locale supportedLocale = null;

		String[] supportLocaleString = this.supportedLocale.split(",");

		for (int i = 0; i < supportLocaleString.length; ++i) {
			if (supportLocaleString[i].equals(language + "_" + country)) {
				supportedLocale = locale;
				break;
			}

		}

		if (supportedLocale == null) {
			supportedLocale = new Locale("en", "US");
		}

		return supportedLocale;
	}
}
