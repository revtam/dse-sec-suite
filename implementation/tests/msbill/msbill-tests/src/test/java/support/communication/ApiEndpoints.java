package support.communication;

import java.util.HashMap;
import java.util.Map;

public class ApiEndpoints {

	public static final String apiPrefix = "http://localhost:8081/api/v1";

	public static String SUBSCRIBE() {
		return apiPrefix + "/subscribe";
	}

	public static Map<String, String> subscribeBody(String eventName, String identifier, String publishTarget) {
		return createLongBody(eventName, identifier, publishTarget);
	}

	public static String SUBSCRIPTIONS() {
		return apiPrefix + "/subscriptions";
	}

	public static Map<String, String> subscriptionsBody(String eventName, String identifier, String publishTarget) {
		return createLongBody(eventName, identifier, publishTarget);
	}

	public static String PUBLISH() {
		return apiPrefix + "/publish";
	}

	public static Map<String, String> publishBody(String eventName, String publishTarget) {
		return createShortBody(eventName, publishTarget);
	}

	static Map<String, String> createLongBody(String eventName, String identifier, String publishTarget) {
		Map<String, String> body = createShortBody(eventName, publishTarget);
		body.put("identifier", identifier);
		return body;
	}

	static Map<String, String> createShortBody(String eventName, String publishTarget) {
		return new HashMap<>() {
			{
				put("eventName", eventName);
				put("publishTarget", publishTarget);
			}
		};
	}

}
