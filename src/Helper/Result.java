package Helper;

public class Result {
	private Boolean uploaded = false;
	private Boolean updated = false;

	public Boolean getUploaded() {
		return uploaded;
	}

	public void setUploaded(Boolean uploaded) {
		this.uploaded = uploaded;
	}

	public Boolean getUpdated() {
		return updated;
	}

	public void setUpdated(Boolean updated) {
		this.updated = updated;
	}

	public Result() {
	}

	public Result(Boolean uploaded, Boolean updated) {
		this.updated = updated;
		this.uploaded = uploaded;
	}
}
