package jsonUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gitUtility.GitHub;

public class JsonRead {

	private static String readAll(Reader rd) throws IOException {
		// function to read the json
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}

	public JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		JSONArray arr = null;
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			String json = "";
			arr = new JSONArray(jsonText);
			// System.out.println(arr.length());
		} finally {
			is.close();
			return arr;

		}
	}

	public void writeIni(String path, JSONArray arr) throws IOException {

		File file = new File("C:/Users/Prasad/Desktop/upload/users.ini");
		if (path != "") {
			file = new File(path);
		}
		FileWriter writer;
		try {
			file.createNewFile();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		writer = new FileWriter(file, false);

		for (int i = 0; i < arr.length(); i++) {
			try {
				writer.write("[" + ((JSONObject) arr.get(i)).get("id") + "]\n");
			} catch (JSONException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				writer.write("title=" + ((JSONObject) arr.get(i)).get("title").toString() + "\n");
			} catch (JSONException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				writer.write("completed=" + ((JSONObject) arr.get(i)).get("completed").toString() + "\n");
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		writer.close();

	}
}
