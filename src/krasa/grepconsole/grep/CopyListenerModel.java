package krasa.grepconsole.grep;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

public class CopyListenerModel {
	private final boolean caseSensitive;
	private final boolean wholeLine;
	private final String expression;
	private final String unlessExpression;
	private final boolean regex;

	public CopyListenerModel(boolean caseSensitive, boolean wholeLine, boolean regex, String expression,
			String unlessExpression) {
		this.caseSensitive = caseSensitive;
		this.wholeLine = wholeLine;
		this.expression = expression;
		this.unlessExpression = unlessExpression;
		this.regex = regex;
	}

	public Matcher matcher() {
		Pattern unlessExpressionPattern = null;
		Pattern expressionPattern = null;
		if (!StringUtils.isBlank(expression)) {
			expressionPattern = Pattern.compile(expression, computeFlags());
		}
		if (!StringUtils.isBlank(unlessExpression)) {
			unlessExpressionPattern = Pattern.compile(unlessExpression, computeFlags());
		}
		return new Matcher(expressionPattern, unlessExpressionPattern, wholeLine);
	}

	public String getExpression() {
		return expression;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public boolean isWholeLine() {
		return wholeLine;
	}

	public String getUnlessExpression() {
		return unlessExpression;
	}

	public boolean isRegex() {
		return regex;
	}

	private int computeFlags() {
		int i = caseSensitive ? 0 : Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
		int j = regex ? 0 : Pattern.LITERAL;
		return i | j;
	}

	public static class Matcher {
		private final Pattern expressionPattern;
		private final Pattern unlessExpressionPattern;
		private final boolean wholeLine;

		public Matcher(Pattern expressionPattern, Pattern unlessExpressionPattern, boolean wholeLine) {
			this.expressionPattern = expressionPattern;
			this.unlessExpressionPattern = unlessExpressionPattern;
			this.wholeLine = wholeLine;
		}

		public boolean matches(CharSequence s) {
				if (matchesPattern(expressionPattern, s) && !matchesPattern(unlessExpressionPattern, s)) {
					return true;
				}
			return false;
		}

		private boolean matchesPattern(Pattern pattern, CharSequence matchedLine) {
			boolean matches = false;
			if (pattern != null) {
				if (wholeLine) {
					matches = pattern.matcher(matchedLine).matches();
				} else {
					matches = pattern.matcher(matchedLine).find();
				}
			}
			return matches;
		}

		@Override
		public String toString() {
			return "[" +
					"expressionPattern=" + expressionPattern +
					", unlessExpressionPattern=" + unlessExpressionPattern +
					", wholeLine=" + wholeLine +
					']';
		}
	}
}
