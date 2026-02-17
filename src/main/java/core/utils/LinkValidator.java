package core.utils;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.openqa.selenium.WebElement;

public class LinkValidator {

	private static final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

	public boolean isValidLink(String URL) {
		return URL != null && !URL.isEmpty() && !URL.toLowerCase().startsWith("javascript") && !URL.equals("#");
	}
	
	

	public int getStatusCode(String Url) {

		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(Url))
					.timeout(Duration.ofSeconds(10))
					.method("HEAD", HttpRequest.BodyPublishers.noBody())
					.build();
			HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
			return response.statusCode();
		} catch (Exception e) {
			return 500; // Treat exception as broken
		}
	}
	
	public String describeLink(WebElement link, int index) {
		return String.format(
			"[index=%d | tag=%s | text='%s' | href=%s]",
			index,
			link.getTagName(),
			link.getText().trim(),
			link.getAttribute("href")
		);
	}



}
