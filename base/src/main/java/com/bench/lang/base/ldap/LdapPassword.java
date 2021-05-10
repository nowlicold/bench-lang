package com.bench.lang.base.ldap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LdapPassword密码
 * 
 * @author cold
 *
 * @version $Id: LdapPassword.java, v 0.1 2016年3月28日 下午7:52:43 cold Exp $
 */
public class LdapPassword {

	/** patron de las claves guardadas en un ldap */
	private static final Pattern PATT = Pattern.compile("[{](.+)[}](.*)");
	/** algoritmo */
	private final String algorithm;
	/** password */
	private final String data;

	/**
	 * Creates the LdapPassword.
	 *
	 * @param passwordString
	 */
	public LdapPassword(final String passwordString) {
		final Matcher m = PATT.matcher(passwordString);

		if (m.lookingAt()) {
			algorithm = m.group(1);
			data = m.group(2);
		} else {
			algorithm = null;
			data = passwordString;
		}
	}

	/**
	 * Returns the algorithm.
	 * 
	 * @return <code>String</code> with the algorithm.
	 */
	public final String getAlgorithm() {
		return algorithm;
	}

	/**
	 * Returns the data.
	 * 
	 * @return <code>String</code> with the data.
	 */
	public final String getData() {
		return data;
	}

	/** @see java.lang.Object#toString() */
	@Override
	public final String toString() {
		return "{" + algorithm + "}" + data;
	}
}
