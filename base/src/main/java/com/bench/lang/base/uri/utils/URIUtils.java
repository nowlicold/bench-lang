package com.bench.lang.base.uri.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * URI工具类
 * 
 * @author cold
 * 
 * @version $Id: URIUtils.java, v 0.1 2011-7-27 下午06:05:49 cold Exp $
 */
public class URIUtils {

	public static final URIUtils INSTANCE = new URIUtils();

	/**
	 * 域名后缀
	 */
	public static final String[] DOMAIN_NAME_SUFFIX = new String[] { ".com", ".cn", ".net", ".org", ".so", ".biz", ".com.cn", ".net.cn", ".org.cn", ".gov.cn", ".name",
			".info", ".cc", ".tv", ".中国", ".mobi", ".me", ".asia", ".co", ".tel" };

	/**
	 * 是否子域名
	 * 
	 * @param rootDomain
	 * @param subDomain
	 * @return
	 */
	public static boolean isSubDomain(String url, String rootDomain) {
		String subDomain = getDomain(url);
		return StringUtils.equalsIgnoreCase(rootDomain, subDomain)
				|| StringUtils.endsWithIgnoreCase(subDomain, StringUtils.startsWith(rootDomain, StringUtils.DOT_SIGN) ? rootDomain : StringUtils.DOT_SIGN + rootDomain);
	}

	/**
	 * 获取根域名
	 * 
	 * @param url
	 * @return
	 */
	public static String getRootDomain(String url) {
		String domain = getDomain(url);
		if (StringUtils.isEmpty(domain)) {
			return domain;
		}
		for (String nameSuffix : DOMAIN_NAME_SUFFIX) {
			if (StringUtils.countMatches(nameSuffix, StringUtils.DOT_SIGN) > 1) {
				if (StringUtils.equals(nameSuffix, domain)) {
					return domain;
				}
				if (domain.endsWith(nameSuffix)) {
					domain = domain.substring(0, domain.length() - nameSuffix.length());
					int index = StringUtils.lastIndexOf(domain, StringUtils.DOT_SIGN);
					if (index < 0) {
						return domain + nameSuffix;
					} else {
						return domain.substring(index + 1, domain.length()) + nameSuffix;
					}
				}
			}
		}
		for (String nameSuffix : DOMAIN_NAME_SUFFIX) {
			if (StringUtils.countMatches(nameSuffix, StringUtils.DOT_SIGN) == 1) {
				if (StringUtils.equals(nameSuffix, domain)) {
					return domain;
				}
				if (domain.endsWith(nameSuffix)) {
					domain = domain.substring(0, domain.length() - nameSuffix.length());
					int index = StringUtils.lastIndexOf(domain, StringUtils.DOT_SIGN);
					if (index < 0) {
						return domain + nameSuffix;
					} else {
						return domain.substring(index + 1, domain.length()) + nameSuffix;
					}
				}
			}
		}
		return domain;
	}

	/**
	 * 是否任意一个根域名
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isSubDomainAny(String url, String[] rooDomains) {
		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(rooDomains)) {
			return false;
		}
		String subDomain = getDomain(url);
		for (String rooDomain : rooDomains) {
			if (isSubDomain(subDomain, rooDomain)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否同一根域名
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isSameRootDomain(String url1, String url2) {
		if (StringUtils.isEmpty(url1) || StringUtils.isEmpty(url2)) {
			return false;
		}
		return StringUtils.equals(getRootDomain(url1), getRootDomain(url2));
	}

	/**
	 * 是否同一根域名
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isSameRootDomain(String url1, String[] url2) {
		if (StringUtils.isEmpty(url1) || StringUtils.isEmpty(url2)) {
			return false;
		}
		String domain1 = getDomain(url1);
		for (String url22 : url2) {
			String domain2 = getDomain(url22);
			if (StringUtils.endsWith(domain1, domain2) || StringUtils.endsWith(domain2, domain1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据一个链接返回所有的域名<br>
	 * 
	 * @param url
	 * @return
	 */
	public static String[] getDomains(String url) {
		String domainName = getDomain(url);
		if (StringUtils.isEmpty(domainName)) {
			return new String[0];
		}
		String[] domainNames = StringUtils.split(domainName, StringUtils.DOT_SIGN);
		if (ArrayUtils.getLength(domainNames) <= 1) {
			return new String[0];
		}
		List<String> domainNameList = new ArrayList<String>();
		for (int i = 0; i < domainNames.length - 1; i++) {
			String currentDomainName = StringUtils.join(ArrayUtils.subarray(domainNames, i, domainNames.length), StringUtils.DOT_SIGN);
			if (!ArrayUtils.contains(DOMAIN_NAME_SUFFIX, "." + currentDomainName)) {
				domainNameList.add(currentDomainName);
			}
		}
		return domainNameList.toArray(new String[domainNameList.size()]);
	}

	/**
	 * 是否同一域名
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isSameDomain(String url1, String[] url2) {
		if (StringUtils.isEmpty(url1) || StringUtils.isEmpty(url2)) {
			return false;
		}

		String domain1 = getDomain(url1);
		for (String url22 : url2) {
			String domain2 = getDomain(url22);
			if (StringUtils.equals(domain1, domain2)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 是否同一域名
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isSameDomain(String url1, String url2) {
		if (StringUtils.isEmpty(url1) || StringUtils.isEmpty(url2)) {
			return false;
		}

		String domain1 = getDomain(url1);

		String domain2 = getDomain(url2);

		return StringUtils.equals(domain1, domain2);

	}

	public static String getDomain(String url1) {
		String domain1 = url1;
		try {
			domain1 = new URL(url1).getHost();
		} catch (Exception e) {

		}
		domain1 = StringUtils.trim(domain1);
		if (StringUtils.startsWith(domain1, "www.")) {
			domain1 = domain1.substring(4);
		}
		return domain1;
	}

	public static String getPath(String url1) {
		try {
			return new URL(url1).getPath();
		} catch (Exception e) {
			return null;
		}
	}

	public static String getOriginalDomain(String url1) {
		try {
			return new URL(url1).getHost();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取url后缀,如htm,html,jsp,asp等
	 * 
	 * @param url
	 * @return
	 */
	public static String getUrlSuffix(String url) {
		if (StringUtils.isEmpty(url)) {
			return url;
		}
		url = StringUtils.substringBefore(url, "?");
		int index = StringUtils.lastIndexOf(url, ".");
		if (index >= 0) {
			return StringUtils.substring(url, index + 1);
		}
		return null;
	}
}
