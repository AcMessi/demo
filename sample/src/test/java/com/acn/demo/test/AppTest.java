package com.acn.demo.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.acn.demo.App;
import com.acn.demo.model.Trade;
import com.acn.demo.util.TradeUtil;

import junit.framework.TestCase;

/**
 * @Description: unit test for App
 * @Author: messi.chaoqun.wang
 * @Date: 2020/5/16
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class AppTest extends TestCase {

	public void testSaveTrade() {
		App app = new App();
		app.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);

		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeWithExistedTrade() {
		Trade trade = new Trade();
		trade.setTransactionId(1l);
		trade.setTradeId(1l);
		trade.setSecurityCode("REL");
		trade.setQuantity(50);
		trade.setActionType(TradeUtil.ACTION_TYPE_INSERT);
		trade.setTradeType(TradeUtil.TRADE_TYPE_BUY);
		TradeUtil.transactionList.add(1l);
		TradeUtil.tradeMap.put(1l, trade);
		TradeUtil.securityMap.put("REL", new Integer(50));

		App app = new App();
		app.saveTrade(2l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);

		assertEquals(TradeUtil.transactionList.size(), 2);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeWithDifferentTrade() {
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

		App app = new App();
		app.saveTrade(2l, 2l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);

		assertEquals(TradeUtil.transactionList.size(), 2);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 2);

		clear();
	}

	public void testSaveTradeWithInvalidData() {
		App app = new App();
		app.saveTrade(-1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);

		assertEquals(TradeUtil.transactionList.size(), 0);
		assertEquals(TradeUtil.securityMap.size(), 0);
		assertEquals(TradeUtil.tradeMap.size(), 0);

		clear();
	}

	public void testSaveTradeWithInvalidSecurity() {
		TradeUtil.transactionList.add(0l);

		App app = new App();
		app.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);

		assertEquals(TradeUtil.transactionList.size(), 2);
		assertEquals(TradeUtil.securityMap.size(), 1);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	public void testSaveTradeWithExistedSecurity() {
		TradeUtil.transactionList.add(1l);

		App app = new App();
		app.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);

		assertEquals(TradeUtil.transactionList.size(), 1);
		assertEquals(TradeUtil.securityMap.size(), 0);
		assertEquals(TradeUtil.tradeMap.size(), 0);

		clear();
	}

	public void testZSaveTradeForWait() {
		final CountDownLatch latch = new CountDownLatch(2);
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(2);
		final App app = new App();

		// transaction1
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + "开始执行");
					app.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);
				} finally {
					latch.countDown();
				}
			}
		});

		// transaction2
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + "开始执行");
					app.saveTrade(2l, 1l, "ITC", 40, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_SELL);
				} finally {
					latch.countDown();
				}
			}
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals(TradeUtil.transactionList.size(), 2);
		assertEquals(TradeUtil.securityMap.size(), 2);
		assertEquals(TradeUtil.tradeMap.size(), 1);

		clear();
	}

	private static void clear() {
		TradeUtil.transactionList.clear();
		TradeUtil.tradeMap.clear();
		TradeUtil.securityMap.clear();
	}
}
