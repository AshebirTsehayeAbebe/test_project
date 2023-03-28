package optifoodmanagement.ui.model.response;

public class ChainResponseModel {
	
	private Integer chainId;
	
	private String chainName;
	
	private Integer totalPage;
	
	public Integer getChainId() {
		return chainId;
	}
	
	public void setChainId(Integer chainId) {
		this.chainId = chainId;
	}
	
	public String getChainName() {
		return chainName;
	}
	
	public void setChainName(String chainName) {
		this.chainName = chainName;
	}
	
	public Integer getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
}
