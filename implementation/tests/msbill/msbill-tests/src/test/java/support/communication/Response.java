package support.communication;

public class Response {

	private Integer statusCode;
	private String responseObjectString;

	public Response(Integer statusCode, String responseBodyAsString) {
		this.statusCode = statusCode;
		this.responseObjectString = responseBodyAsString;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getResponseObjectString() {
		return responseObjectString;
	}

	public void setResponseObjectString(String responseBodyAsString) {
		this.responseObjectString = responseBodyAsString;
	}

}
