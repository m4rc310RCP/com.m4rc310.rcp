package reports.utils;

public class Ref {
	private String name;
	private String md5;
	
	private String fileJrxml;
	private String fileJasper;

	private String pluginId;
	
	public String getPluginId() {
		return pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	public Ref() {
	}
	
	public Ref(String name, String md5, String fileJrxml, String fileJasper) {
		super();
		this.name = name;
		this.md5 = md5;
		this.fileJrxml = fileJrxml;
		this.fileJasper = fileJasper;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getFileJrxml() {
		return fileJrxml;
	}
	public void setFileJrxml(String fileJrxml) {
		this.fileJrxml = fileJrxml;
	}
	public String getFileJasper() {
		return fileJasper;
	}
	public void setFileJasper(String fileJasper) {
		this.fileJasper = fileJasper;
	}
	
	
}
