package ke;

public class CRFmodel{
	private String crfModelPath;
	private String crfModelName;
	public String getCrfModelPath() {
		return crfModelPath;
	}
	public void setCrfModelPath(String crfModelPath) {
		this.crfModelPath = crfModelPath;
	}
	public String getCrfModelName() {
		return crfModelName;
	}
	public void setCrfModelName(String crfModelName) {
		this.crfModelName = crfModelName;
	}
	public CRFmodel() {}
	public CRFmodel(String crfModelPath, String crfModelName) {
		super();
		this.crfModelPath = crfModelPath;
		this.crfModelName = crfModelName;
	}
	
}