package cn.bluethink.ecssparkClient.model;

import java.util.List;

/**
 * @author flw
 * @date 2018年9月18日 
 */
public class Data {
    private String label;
    private String sn;
    private List<String> values;
    private String url;
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
}
