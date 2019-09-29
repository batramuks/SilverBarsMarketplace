package sliverbars.model;

public class OrderRequest {

	private String userId;
	private Double orderQuantity;
	private Long price;
	private String orderType;
	private Long orderId;
	
	public OrderRequest(){}
	
	public OrderRequest(String userId, Double orderQuantity, Long price, String orderType, Long orderId) {
		super();
		this.userId = userId;
		this.orderQuantity = orderQuantity;
		this.price = price;
		this.orderType = orderType;
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Double orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderRequest [userId=" + userId + ", orderQuantity=" + orderQuantity + ", price=" + price
				+ ", orderType=" + orderType + ", orderId=" + orderId + "]\n";
	}
	

}
