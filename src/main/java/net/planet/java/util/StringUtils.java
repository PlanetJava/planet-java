package net.planet.java.util;

/**
 * @author Bazlur Rahman Rokon
 * @since 8/6/16.
 */
public class StringUtils {
	public static boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}

	//These appear to be unicode surrogate characters.
	// Since they are not actual characters,
	// and it seems MySQL doesn't support them, it is safe to trim them:
	public static String trim(String text) {
		if (org.springframework.util.StringUtils.isEmpty(text)) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (!Character.isHighSurrogate(ch) && !Character.isLowSurrogate(ch)) {
				sb.append(ch);
			}
		}

		return sb.toString();
	}
}
