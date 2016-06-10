package de.hsadmin.common.error;

public class UserError {

	public static final String MSG_REQUIRED_FIELD = "MSG_REQUIRED_FIELD";
	public static final String MSG_NO_FIELD_WRITEACCESS = "MSG_NO_FIELD_WRITEACCESS";
	public static final String MSG_FIELD_NOT_SEARCHABLE = "MSG_FIELD_NOT_SEARCHABLE";
	public static final String MSG_NOT_IMPLEMENTED = "MSG_NOT_IMPLEMENTED";
	public static final String MSG_FIELD_DOESNOT_EXIST = "MSG_FIELD_DOESNOT_EXIST";
	public static final String MSG_FORBIDDEN_RUNAS = "MSG_FORBIDDEN_RUNAS";
	public static final String MSG_INVALID_TICKET = "MSG_INVALID_TICKET";
	public static final String MSG_MISSING_AUTHORIZATION = "MSG_MISSING_AUTHORIZATION";
	public static final String MSG_INVALID_DATEFORMAT = "MSG_INVALID_DATEFORMAT";
	public static final String MSG_FIELD_REQUIRES_MINLENGTH = "MSG_FIELD_REQUIRES_MINLENGTH";
	public static final String MSG_FIELD_EXCEEDS_MAXLENGTH = "MSG_FIELD_EXCEEDS_MAXLENGTH";
	public static final String MSG_FIELD_DOESNOT_MATCH_REGEXP = "MSG_FIELD_DOESNOT_MATCH_REGEXP";
	public static final String MSG_UNKNOWN_KEY = "MSG_UNKNOWN_KEY";
	public static final String MSG_INT_VALUE_EXPECTED = "MSG_INT_VALUE_EXPECTED";
	public static final String MSG_PAC_NOT_CANCELLED = "MSG_PAC_NOT_CANCELLED";
	public static final String MSG_PAC_CANCEL_DATE_IN_FUTURE = "MSG_PAC_CANCEL_DATE_IN_FUTURE";
	public static final String MSG_CUSTOMER_HAS_PACS = "MSG_CUSTOMER_HAS_PACS";
	public static final String MSG_CUSTOMER_IS_MEMBER = "MSG_CUSTOMER_IS_MEMBER";
	public static final String MSG_CUSTOMER_DELETE_DEADLINE_NOT_EXPIRED = "MSG_CUSTOMER_DELETE_DEADLINE_NOT_EXPIRED";
	public static final String MSG_ENTITY_EXISTS = "MSG_ENTITY_EXISTS";
	public static final String MSG_BOOLEAN_VALUE_EXPECTED = "MSG_BOOLEAN_VALUE_EXPECTED";
	public static final String MSG_DATE_VALUE_EXPECTED = "MSG_DATE_VALUE_EXPECTED";
	public static final String MSG_STRING_VALUE_EXPECTED = "MSG_STRING_VALUE_EXPECTED";
	public static final String MSG_HASHMAP_VALUE_EXPECTED = "MSG_HASHMAP_VALUE_EXPECTED";
	public static final String MSG_FIELD_DOESNOT_VALIDATE = "MSG_FIELD_DOESNOT_VALIDATE";
	
	private final String message;
	private final String[] parameters;

	public UserError(String msgKey, String... params) {
		this.message = msgKey;
		this.parameters = params;
	}

	public String getLocalizedMessage() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(message);
		stringBuilder.append(": ");
		for (String p : parameters) {
			stringBuilder.append(p);
			stringBuilder.append(", ");
		}
		return stringBuilder.substring(0, stringBuilder.length() - 2);
	}

	public String getMessageKey() {
		return message;
	}
	
}
