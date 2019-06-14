
public class Info { // To antikeimeno pou apothikeoume id,status,datetime

	private String id, status, datetime;

	public Info(String id, String status, String datetime) {
		this.id = id;
		this.status = status;
		this.datetime = datetime;
	}

	public String getId() {
		return id;
	}

	public String MaketoString() {
		String text = id + " " + status + " " + datetime;
		return text;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

}
