package reports.utils;

import java.util.List;
import java.util.Map;

public class ReportReferences {
	private String name;
	private Map<String, Object> params;
	private List<?> values;
	
	
	public ReportReferences(String name, Map<String, Object> params, List<?> values) {
		super();
		this.name = name;
		this.params = params;
		this.values = values;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public List<?> getValues() {
		return values;
	}
	public void setValues(List<?> values) {
		this.values = values;
	}
	
	
}
