package file.util;

public class StringHelper {

	public static boolean isEmpty(String value) {
		int length;
		if (value != null && (length = value.length()) != 0) {
			for(int index = 0; index < length; ++index) {
				char ch = value.charAt(index);
				if (ch != ' ' && ch != 160 && !Character.isISOControl(ch)) {
					return false;
				}
			}

			return true;
		} else {
			return true;
		}
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
}
