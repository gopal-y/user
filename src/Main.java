import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.json.JSONArray;
import org.json.JSONException;

import Helper.Result;
import gitUtility.GitHub;
import jsonUtility.JsonRead;
import mailerUtility.Mailer;

public class Main {
	public static void main(String[] args)
			throws IOException, JSONException, InvalidRemoteException, TransportException, GitAPIException {
		JsonRead jsonRead = new JsonRead();
		JSONArray jsonArray = jsonRead.readJsonFromUrl("https://jsonplaceholder.typicode.com/todos/");
		String s = "C:/Users/Prasad/Desktop/upload";
		jsonRead.writeIni("", jsonArray);

		// System.out.println(s);
		GitHub hook = new GitHub();
		Result r = hook.push(s, "http://github.com/gopal-y/user.git", "gopal-y", "dob06121997*Y");
		if (r.getUploaded() && r.getUpdated()) {
			Mailer m = new Mailer();
			m.sendMEssage(m.configure(), "Users List Updated",
					"Please open the below link to access the updated content http://github.com/gopal-y/user/blob/child/users.ini");
			} else if (r.getUploaded() && !r.getUpdated()) {
			System.out.println("No new data found ignoring commit");

		} else if (!r.getUploaded()) {
			System.out.println("Some Error Occured");
		}
	}
}
