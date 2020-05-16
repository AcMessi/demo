package com.acn.demo.util;

import com.acn.demo.model.Trade;
import com.acn.demo.util.TradeUtil;

import junit.framework.TestCase;

/**
 * @Description: unit test for TradeUtil
 * @Author: messi.chaoqun.wang
 * @Date: 2020/5/16
 */
public class TradeUtilTest extends TestCase {

	public void testSaveTradeMapWithNoData() {
		TradeUtil.saveTradeMap(null, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 0);
		assertEquals(TradeUtil.securityMap.size(), 0);
		assertEquals(TradeUtil.tradeMap.size(), 0);

		clear();
	}

	public void testSaveTradeMapWithData() {
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

		TradeUtil.saveTradeMap(trade, 1l, "REL", 50, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeMapWithDifferentSecurity() {
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

		TradeUtil.saveTradeMap(trade, 1l, "TEL", 50, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 2);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeMapForSell() {
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

		TradeUtil.saveTradeMap(trade, 1l, "REL", 50, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_SELL);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeMapForUpdate() {
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

		TradeUtil.saveTradeMap(trade, 2l, "REL", 50, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 2);

		clear();
	}

	public void testSaveTradeMapForCancel() {
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

		TradeUtil.saveTradeMap(trade, 1l, "REL", 50, TradeUtil.ACTION_TYPE_CANCEL, TradeUtil.TRADE_TYPE_BUY);
		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testShowResult() {
		TradeUtil.securityMap.put("REL", new Integer(50));
		TradeUtil.showResult();
		clear();
	}

	public void testShowResultWithNoData() {
		TradeUtil.securityMap.put("REL", new Integer(50));
		TradeUtil.showResult();
		clear();
	}

	private static void clear() {
		TradeUtil.transactionList.clear();
		TradeUtil.tradeMap.clear();
		TradeUtil.securityMap.clear();
	}
}
