package server;

import java.util.Date;

public class Auction {
	private int id;						
	private int product_id;			
	private int seller_id;					
	private int buyer_id;				
	private Date end_date;		
	private String auction_state;
	
	
	public Auction(int id, int product_id, int seller_id, int buyer_id, Date end_date, String auction_state) {
		this.id = id;
		this.product_id = product_id;
		this.seller_id = seller_id;
		this.buyer_id = buyer_id;
		this.end_date = end_date;
		this.auction_state = auction_state;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getProductId() {
		return product_id;
	}


	public void setProductId(int product_id) {
		this.product_id = product_id;
	}


	public int getSellerId() {
		return seller_id;
	}


	public void setSellerId(int seller_id) {
		this.seller_id = seller_id;
	}


	public int getBuyerId() {
		return buyer_id;
	}


	public void setBuyerId(int buyer_id) {
		this.buyer_id = buyer_id;
	}


	public Date getEndDate() {
		return end_date;
	}


	public void setEndDate(Date end_date) {
		this.end_date = end_date;
	}


	public String getAuctionState() {
		return auction_state;
	}


	public void setAuctionState(String auction_state) {
		this.auction_state = auction_state;
	}	
	
	
	
	
}
