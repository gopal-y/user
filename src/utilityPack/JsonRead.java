package utilityPack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonRead {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	public static String readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			String json = "";
			JSONArray arr = new JSONArray(jsonText);
			System.out.println(arr.length());
			File file = new File("C:/Users/Prasad/Desktop/users.ini");
			FileWriter writer;
			if (file.createNewFile()) {
				writer = new FileWriter(file, false);

			} else {
				writer = new FileWriter(file, true);
			}

			for (int i = 0; i < arr.length(); i++) {
				writer.write("[" + ((JSONObject) arr.get(i)).get("id") + "]\n");
				writer.write("title=" + ((JSONObject) arr.get(i)).get("title").toString() + "\n");
				writer.write("completed=" + ((JSONObject) arr.get(i)).get("completed").toString() + "\n");

			}
			writer.close();
			return json;
		} finally {

			is.close();
		}
	}

	public static void main(String[] args) throws IOException, JSONException {
		String json = readJsonFromUrl("https://jsonplaceholder.typicode.com/todos/");
		System.out.println(json);
	

	}
}
