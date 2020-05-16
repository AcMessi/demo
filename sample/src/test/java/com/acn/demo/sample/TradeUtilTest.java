package com.acn.demo.sample;

import com.acn.demo.model.Trade;
import com.acn.demo.util.TradeUtil;

import junit.framework.TestCase;

/**
 * @Description: unit test for TradeUtil
 * @Author: messi.chaoqun.wang
 * @Date: 2020/5/16
 */
public class TradeUtilTest extends TestCase {

	public void testSaveTradeWhenTransactionIdIsZero() {
		TradeUtil.saveTrade(0l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 0);
		assertEquals(TradeUtil.securityMap.size(), 0);
		assertEquals(TradeUtil.tradeMap.size(), 0);

		clear();
	}

	public void testSaveTradeWhenTransactionIdIsNotZero() {
		TradeUtil.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeWhenTransactionIdAndTradeIdIsZero() {
		TradeUtil.saveTrade(0l, 0l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_SELL);
		assertEquals(TradeUtil.transactionList.size(), 0);
		assertEquals(TradeUtil.securityMap.size(), 0);
		assertEquals(TradeUtil.tradeMap.size(), 0);

		clear();
	}

	public void testSaveTradeWhenTransactionIdIsZeroForSell() {
		TradeUtil.saveTrade(0l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_SELL);
		assertEquals(TradeUtil.transactionList.size(), 0);
		assertEquals(TradeUtil.securityMap.size(), 0);
		assertEquals(TradeUtil.tradeMap.size(), 0);

		clear();
	}

	public void testSaveTradeWhenTransactionIdIsNotZeroForSell() {
		TradeUtil.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_SELL);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeWithExistedTransactionId() {
		TradeUtil.transactionList.add(1l);

		TradeUtil.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 0);
		assertEquals(TradeUtil.tradeMap.size(), 0);

		clear();
	}

	public void testSaveTradeInsertWithoutData() {
		TradeUtil.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeUpdateWithoutData() {
		TradeUtil.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 0);
		assertEquals(TradeUtil.tradeMap.size(), 0);

		clear();
	}

	public void testSaveTradeUpdateWithData() {
		Trade trade = new Trade();
		trade.setTransactionId(1l);
		trade.setTradeId(1l);
		trade.setSecurityCode("REL");
		trade.setQuantity(50);
		trade.setActionType(TradeUtil.ACTION_TYPE_CANCEL);
		trade.setTradeType(TradeUtil.TRADE_TYPE_BUY);
		TradeUtil.transactionList.add(1l);
		TradeUtil.tradeMap.put(1l, trade);
		TradeUtil.securityMap.put("REL", new Integer(50));

		TradeUtil.saveTrade(2l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 2);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeCancelWithoutData() {
		TradeUtil.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_CANCEL, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 0);
		assertEquals(TradeUtil.tradeMap.size(), 0);

		clear();
	}

	public void testSaveTradeCancelWithData() {
		Trade trade = new Trade();
		trade.setTransactionId(1l);
		trade.setTradeId(1l);
		trade.setSecurityCode("REL");
		trade.setQuantity(50);
		trade.setActionType(TradeUtil.ACTION_TYPE_UPDATE);
		trade.setTradeType(TradeUtil.TRADE_TYPE_BUY);
		TradeUtil.transactionList.add(1l);
		TradeUtil.tradeMap.put(1l, trade);
		TradeUtil.securityMap.put("REL", new Integer(50));

		TradeUtil.saveTrade(2l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_CANCEL, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 2);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeWithoutSecurityData() {
		Trade trade = new Trade();
		trade.setTransactionId(1l);
		trade.setTradeId(1l);
		trade.setSecurityCode("REL");
		trade.setQuantity(50);
		trade.setActionType(TradeUtil.ACTION_TYPE_INSERT);
		trade.setTradeType(TradeUtil.TRADE_TYPE_BUY);
		TradeUtil.transactionList.add(1l);
		TradeUtil.tradeMap.put(1l, trade);

		TradeUtil.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 0);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeWithDataWhenTransactionIdIsNotZero() {
		Trade trade = new Trade();
		trade.setTransactionId(1l);
		trade.setTradeId(1l);
		trade.setSecurityCode("REL");
		trade.setQuantity(50);
		trade.setActionType(TradeUtil.ACTION_TYPE_INSERT);
		trade.setTradeType(TradeUtil.TRADE_TYPE_BUY);
		TradeUtil.transactionList.add(1l);
		TradeUtil.tradeMap.put(1l, trade);

		TradeUtil.saveTrade(0l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 0);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeWithDataWhenTransactionIdIsZero() {
		Trade trade = new Trade();
		trade.setTransactionId(0l);
		trade.setTradeId(1l);
		trade.setSecurityCode("REL");
		trade.setQuantity(50);
		trade.setActionType(TradeUtil.ACTION_TYPE_INSERT);
		trade.setTradeType(TradeUtil.TRADE_TYPE_BUY);
		TradeUtil.transactionList.add(1l);
		TradeUtil.tradeMap.put(1l, trade);

		TradeUtil.saveTrade(0l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 0);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	private static void clear() {
		TradeUtil.transactionList.clear();
		TradeUtil.tradeMap.clear();
		TradeUtil.securityMap.clear();
	}
}
