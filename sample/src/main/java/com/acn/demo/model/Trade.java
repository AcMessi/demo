package com.acn.demo.model;

/**
 * @Description: the model of trade
 * @Author: messi.chaoqun.wang
 * @Date: 2020/5/16
 */
public class Trade {

	/**
	 * primary key
	 */
	private long transactionId;

	/**
	 * trade identifier
	 */
	private long tradeId;

	/**
	 * version : start with 1s
	 */
	private int version;

	/**
	 * security identifier
	 */
	private String securityCode;

	/**
	 * quantity of security
	 */
	private int quantity;

	/**
	 * type of action
	 */
	private int actionType;

	/**
	 * type of trade
	 */
	private int tradeType;

	public long getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getSecurityCode() {
		return this.securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getActionType() {
		return this.actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public int getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

}
