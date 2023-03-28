package optifoodmanagement.ui.model.response;

public class TenantResponseModel {
	
	private long tenantId;
	private String name;
	private String url;
	private String licenseKey;
	private Integer totalPage;
	
	public long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getLicenseKey() {
		return licenseKey;
	}
	
	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	
}
