package com.acn.demo.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.acn.demo.model.Trade;

/**
 * @Description: trade工具类
 * @Author: messi.chaoqun.wang
 * @Date: 2020/5/16
 */
public class TradeUtil {

	public static final int TRADE_TYPE_BUY = 1;

	public static final int TRADE_TYPE_SELL = 2;

	public static final int ACTION_TYPE_INSERT = 1;

	public static final int ACTION_TYPE_UPDATE = 2;

	public static final int ACTION_TYPE_CANCEL = 3;

	public static List<Long> transactionList = new ArrayList<Long>();

	public static Map<Long, Trade> tradeMap = new LinkedHashMap<Long, Trade>();

	public static Map<String, Integer> securityMap = new LinkedHashMap<String, Integer>();

	public static synchronized void saveTrade(Long transactionId, Long tradeId, String securityCode, int quantity,
			int actionType, int tradeType) {

		if (!transactionList.contains(transactionId) && transactionId > 0) {
			transactionList.add(transactionId);

			Trade trade = tradeMap.get(tradeId);
			boolean flag = true;

			// 交易已cancel时 ： 任何字段都可以更新，但应该被无视
			if (trade != null && trade.getActionType() == ACTION_TYPE_CANCEL) {
				flag = false;
			}

			if (trade == null) {
				// 交易未存在时，进行insert
				if (actionType == ACTION_TYPE_INSERT) {
					trade = new Trade();
				}

				// 交易存在时才能进行update和cancel
				if (actionType > 1) {
					flag = false;
				}
			}

			if (flag) {
				trade.setVersion(trade.getVersion() + 1); // version自增长1， insert为开始， cancel为结束
				trade.setSecurityCode(securityCode);
				trade.setActionType(actionType); // 1：insert 2:update 3:cancel
				trade.setTradeType(tradeType); // 1：buy 2:sell
				trade.setQuantity(quantity);
				tradeMap.put(tradeId, trade);

				int securityCodeVal = securityMap.get(securityCode) == null ? 0
						: securityMap.get(securityCode).intValue();

				if (tradeType == TRADE_TYPE_BUY) {
					securityMap.put(securityCode, securityCodeVal + quantity);
				}

				if (tradeType == TRADE_TYPE_SELL) {
					securityMap.put(securityCode, securityCodeVal - quantity);
				}
			}
		}

		System.out.println(Thread.currentThread().getName() + " Positions : ");

		Iterator<Entry<String, Integer>> entries = securityMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, Integer> entry = entries.next();
			String key = entry.getKey();
			Integer val = entry.getValue();
			System.out.println(Thread.currentThread().getName() + " : " + key + " " + val);
		}
	}

}
