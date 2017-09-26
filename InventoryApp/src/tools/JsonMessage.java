package tools;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "JsonMessage")
public class JsonMessage {
	
	@JsonProperty
	Map<String, String> message;
	@JsonProperty
	Map<String, String> errors;
	@JsonProperty
	String status;
	@JsonProperty
	String path;
	@JsonProperty
	String date;
	@JsonProperty
	Map<String, Object> data;
	@JsonProperty
	String expirationDate;
	
	public JsonMessage() {
		this.message = new HashMap<String,String>(0);
		this.errors = new HashMap<String,String>(0);
		this.status = "";
		this.path = "";
		this.date = "";
		this.data = new HashMap<String,Object>(0);
		this.expirationDate = "";
	}
	
	public JsonMessage(Map<String, String> message, Map<String, String> errors, String status, String path,
			String date,Map<String, Object> data,String expirationDate) {
		super();
		this.message = message;
		this.errors = errors;
		this.status = status;
		this.path = path;
		this.date = date;
		this.data = data;
	}
	public Map<String, String> getMessage() {
		return message;
	}
	public void setMessage(Map<String, String> message) {
		this.message = message;
	}
	public Map<String, String> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
}
