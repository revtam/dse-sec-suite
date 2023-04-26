package support.communication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpCommunicator {

	public static final Logger logger = Logger.getLogger(HttpCommunicator.class.getName());

	public static <T> Response postRequest(String url, Map<String, String> body, Object payload)
			throws IOException, InterruptedException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("Fetch-Mode", "SINGLE");

			ObjectMapper objectMapper = new ObjectMapper();
			if (payload != null) {
				String payloadString = objectMapper.writeValueAsString(payload);
				body.put("payload", payloadString);
			}
			String requestBody = objectMapper.writeValueAsString(body);
			StringEntity entity = new StringEntity(requestBody);
			httpPost.setEntity(entity);

			Response responseOutput = new Response(0, null);

			try (CloseableHttpResponse response = client.execute(httpPost)) {
				responseOutput.setStatusCode(response.getStatusLine().getStatusCode());
				logger.info("Response received for request, status " + response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					try (InputStream input = response.getEntity().getContent()) {
						String respObjectAsString = objectMapper.readTree(input).toString();
						responseOutput.setResponseObjectString(respObjectAsString);
					}
				}
			}

			return responseOutput;
		}
	}

	public static <T> void postRequestWithCallback(String url, Map<String, String> body, Consumer<Object> callback,
			Class<T> callbackObjectType) throws IOException, InterruptedException {
		Response response = postRequest(url, body, null);
		Object respObject = null;
		if (response.getStatusCode() == HttpStatus.SC_OK) {
			ObjectMapper objectMapper = new ObjectMapper();
			String respObjectAsString = response.getResponseObjectString();
			respObjectAsString = respObjectAsString.replace("\\", "");
			respObjectAsString = respObjectAsString.substring(2, respObjectAsString.length() - 2);
			respObject = objectMapper.readValue(respObjectAsString, callbackObjectType);
		}
		callback.accept(respObject);
	}

	public static <T> void postRequestWithStatusCallback(String url, Map<String, String> body,
			Consumer<Integer> callback) throws IOException, InterruptedException {
		Response response = postRequest(url, body, null);
		callback.accept(response.getStatusCode());
	}

	public static void postRequestWithPayload(String url, Map<String, String> body, Object payload)
			throws IOException, InterruptedException {
		postRequest(url, body, payload);
	}

	public static void postRequestBasic(String url, Map<String, String> body) throws IOException, InterruptedException {
		postRequest(url, body, null);
	}

}
