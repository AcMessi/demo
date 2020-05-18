package com.acn.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.acn.demo.model.Trade;
import com.acn.demo.util.TradeUtil;

/**
 * @Description: console application
 * @Author: messi.chaoqun.wang
 * @Date: 2020/5/16
 */
public class App {

	public static void main(String[] args) {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		final App app = new App();

		// transaction1
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				app.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);
			}
		});

		// transaction2
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				app.saveTrade(2l, 2l, "ITC", 40, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_SELL);
			}
		});

		// transaction3
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				app.saveTrade(3l, 3l, "INF", 70, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);
			}
		});

		// transaction4
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				app.saveTrade(4l, 1l, "REL", 60, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);
			}
		});

		// transaction5
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				app.saveTrade(5l, 2l, "ITC", 30, TradeUtil.ACTION_TYPE_CANCEL, TradeUtil.TRADE_TYPE_BUY);
			}
		});

		// transaction6
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				app.saveTrade(6l, 4l, "INF", 20, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_SELL);
			}
		});

	}

	/**
	 * save trade data.
	 *
	 * @param transactionId primary key
	 * @param tradeId       trade identifier
	 * @param securityCode  security identifier
	 * @param quantity      quantity of security
	 * @param actionType    type of action
	 * @param tradeType     type of trade
	 * @return
	 */
	public synchronized void saveTrade(Long transactionId, Long tradeId, String securityCode, int quantity,
			int actionType, int tradeType) {
		System.out.println(Thread.currentThread().getName() + " starts");
		
		// save data when transactionId is valid and not existed
		if (!TradeUtil.transactionList.contains(transactionId) && transactionId > 0) {
			TradeUtil.transactionList.add(transactionId);

			Trade trade = null;
			while (trade == null) {
				trade = TradeUtil.tradeMap.get(tradeId);

				if (trade == null && actionType == TradeUtil.ACTION_TYPE_INSERT) {
					// Insert if the current trade type is Insert and no trade exists with the same tradeId
					trade = new Trade();
				}

				// if the trade is null, the action type of current thread is not Insert
				// the current thread should be waiting until the Insert is executed with the same tradeId
				if (trade == null) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			// save the data of trade
			TradeUtil.saveTradeMap(trade, tradeId, securityCode, quantity, actionType, tradeType);

			// show results
			TradeUtil.showResult();

			// notify the threads which represent Update or Cancel if the current thread is Insert
			if (actionType == TradeUtil.ACTION_TYPE_INSERT) {
				this.notifyAll();
			}
		}
		
		System.out.println(Thread.currentThread().getName() + " ends");
	}
}
