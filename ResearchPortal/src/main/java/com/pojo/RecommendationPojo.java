package com.pojo;


public class RecommendationPojo {
	private int rid;
	private String sessionToken, stocksymbol,rdate, rtype, rdetails;
	

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}


	public String getStocksymbol() {
		return stocksymbol;
	}

	public void setStocksymbol(String stocksymbol) {
		this.stocksymbol = stocksymbol;
	}

	public String getRtype() {
		return rtype;
	}

	public void setRtype(String rtype) {
		this.rtype = rtype;
	}

	public String getRdetails() {
		return rdetails;
	}

	public void setRdetails(String rdetails) {
		this.rdetails = rdetails;
	}	
	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public RecommendationPojo( int rid, String stocksymbol, String rdate, String rtype, String rdetails
			) {
//		this.userid = userid;
		this.rid = rid;
		this.stocksymbol = stocksymbol;
		this.rdate = rdate;
		this.rtype = rtype;
		this.rdetails = rdetails;
		
	}

	

	

	
	
	
	

}
