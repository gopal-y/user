package gitUtility;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import Helper.Result;


public class GitHub {
	public Result push(String path, String repo,String user,String password) throws IOException, NoFilepatternException, GitAPIException {
		Result r = new Result();
		try {
			Git git = Git.init().setDirectory(new File(path)).call();
			// git.cloneRepository().setURI(repo).call();

			AddCommand add = git.add();
			add.addFilepattern("users.ini").call();
			CommitCommand commit = git.commit();
			try {
				commit.setMessage("updated" + new Date().toString()).call();
			} catch (NoClassDefFoundError e) {
				e.printStackTrace();
			}
			git = Git.open(new File(path + "/.git"));
			RefSpec spec = new RefSpec("refs/heads/master:refs/heads/child");
			PushCommand push = git.push();

			Iterable<PushResult> i = push.setRemote(repo).setRefSpecs(spec)
					.setCredentialsProvider(new UsernamePasswordCredentialsProvider(user,password)).call();

			List<Ref> remoteBranches = git.branchList().setListMode(ListMode.REMOTE).call();
			for (PushResult pushResult : i) {
				Collection<RemoteRefUpdate> resultsCollection = pushResult.getRemoteUpdates();
				Map<PushResult, RemoteRefUpdate> resultsMap = new HashMap<>();
				for (RemoteRefUpdate remoteRefUpdate : resultsCollection) {
					resultsMap.put(pushResult, remoteRefUpdate);
				}

				RemoteRefUpdate remoteUpdate = pushResult.getRemoteUpdate("refs/heads/child");

				if (remoteUpdate != null) {
					org.eclipse.jgit.transport.RemoteRefUpdate.Status status = remoteUpdate.getStatus();
					r.setUpdated(status.equals(org.eclipse.jgit.transport.RemoteRefUpdate.Status.OK));
					r.setUploaded(status.equals(org.eclipse.jgit.transport.RemoteRefUpdate.Status.OK)
							|| status.equals(org.eclipse.jgit.transport.RemoteRefUpdate.Status.UP_TO_DATE));
				}

				if (remoteUpdate == null && !remoteBranches.toString().contains("refs/heads/child")) {

					for (RemoteRefUpdate resultValue : resultsMap.values()) {
						if (resultValue.toString().contains("REJECTED_OTHER_REASON")) {
							r.setUploaded(false);
						}
					}
					System.out.println(remoteUpdate.getStatus().toString());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println(r.getUploaded() + " " + r.getUpdated());
			return r;
		}
	}

}
