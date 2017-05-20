package config;

public class XMLConfig {

	private String url;
	private int timer;
	private String unit;
	
	public String getUnit() {
		return unit;
	}
	
	public String getUrl() {
		return url;
	}
	
	public int getTimer() {
		return timer;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
}
