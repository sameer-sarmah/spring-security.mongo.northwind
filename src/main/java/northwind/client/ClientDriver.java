package northwind.client;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import northwind.entity.User;
import northwind.exception.CoreException;

public class ClientDriver {
	private static final String userService = "https://localhost:8080/api/user";
	private static final String permissionService = "https://localhost:8080/api/user/%s/%s";
	private static final String READ = "READ";
	private static final String WRITE = "WRITE";

	public static void main(String[] args) {
		SpringHttpClient springHttpClient = new SpringHttpClient();
		Map<String, String> queryParams = new HashMap<>();
		Map<String, String> headers = new HashMap<>();

		String userCredentials = "admin:admin";
		String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", basicAuth);
		User sameer = new User("zypher", "elnino_09", "sameer", "sarmah");
		User mayuri = new User("sumpy", "sumpy@14", "mayuri", "sarmah");
		User alexandra = new User("alexandra", "beautiful", "alexandra", "didario");
		List<User> persons = Arrays.asList(sameer, mayuri, alexandra);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		for (User user : persons) {
			String prettyJsonString = gson.toJson(user);
			executeHttpClient(springHttpClient, userService, HttpMethod.POST, headers, queryParams, prettyJsonString);
			if (user.getUserName().equals("zypher")) {
				String url = String.format(permissionService, user.getUserName(), WRITE);
				executeHttpClient(springHttpClient, url, HttpMethod.POST, headers, queryParams, "");
			} else if (user.getUserName().equals("sumpy")) {
				String url = String.format(permissionService, user.getUserName(), READ);
				executeHttpClient(springHttpClient, url, HttpMethod.POST, headers, queryParams, "");
			}
		}

	}

	private static void executeHttpClient(SpringHttpClient httpClient, String service, HttpMethod method,
			Map<String, String> headers, Map<String, String> queryParams, String payload) {
		try {
			String jsonResponse = httpClient.request(service, method, headers, queryParams, payload);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(jsonResponse);
			String prettyJsonString = gson.toJson(je);
			System.out.println(prettyJsonString);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
