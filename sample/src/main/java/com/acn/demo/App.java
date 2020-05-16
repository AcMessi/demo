package com.acn.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.acn.demo.util.TradeUtil;

/**
 * @Description:
 * @Author: messi.chaoqun.wang
 * @Date: 2020/5/16
 */
public class App {

	public static void main(String[] args) {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

		// transaction1
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + "开始执行");
				TradeUtil.saveTrade(1l, 1l, "REL", 50, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);
			}
		});

		// transaction2
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + "开始执行");
				TradeUtil.saveTrade(2l, 2l, "ITC", 40, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_SELL);
			}
		});

		// transaction3
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + "开始执行");
				TradeUtil.saveTrade(3l, 3l, "INF", 70, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);
			}
		});

		// transaction4
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + "开始执行");
				TradeUtil.saveTrade(4l, 1l, "REL", 60, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);
			}
		});

		// transaction5
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + "开始执行");
				TradeUtil.saveTrade(5l, 2l, "ITC", 30, TradeUtil.ACTION_TYPE_CANCEL, TradeUtil.TRADE_TYPE_BUY);
			}
		});

		// transaction6
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + "开始执行");
				TradeUtil.saveTrade(6l, 4l, "INF", 20, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_SELL);
			}
		});

		// transaction3
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + "开始执行");
				TradeUtil.saveTrade(3l, 3l, "INF", 70, TradeUtil.ACTION_TYPE_INSERT, TradeUtil.TRADE_TYPE_BUY);
			}
		});

		// transaction4
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + "开始执行");
				TradeUtil.saveTrade(4l, 1l, "REL", 60, TradeUtil.ACTION_TYPE_UPDATE, TradeUtil.TRADE_TYPE_BUY);
			}
		});

	}
}
