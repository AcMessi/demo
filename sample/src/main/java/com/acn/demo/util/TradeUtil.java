package com.acn.demo.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

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

	public static Map<Long, Trade> tradeMap = new ConcurrentHashMap<Long, Trade>();

	public static Map<String, Integer> securityMap = new LinkedHashMap<String, Integer>();

	public static void saveTradeMap(Trade trade, Long tradeId, String securityCode, int quantity, int actionType,
			int tradeType) {
		if (trade != null) {
			trade.setVersion(trade.getVersion() + 1); // version自增长1， insert为开始， cancel为结束

			// 若为cancel操作，则无视其他字段更新
			if (actionType != ACTION_TYPE_CANCEL) {
				trade.setSecurityCode(securityCode);
				trade.setActionType(actionType); // 1：insert 2:update 3:cancel
				trade.setTradeType(tradeType); // 1：buy 2:sell
				trade.setQuantity(quantity);

				int securityCodeVal = securityMap.get(securityCode) == null ? 0
						: securityMap.get(securityCode).intValue();

				// 交易类型为buy时，相关security code的quantity进行加法计算
				if (tradeType == TRADE_TYPE_BUY) {
					securityMap.put(securityCode, securityCodeVal + quantity);
				}

				// 交易类型为sell时，相关security code的quantity进行减法计算
				if (tradeType == TRADE_TYPE_SELL) {
					securityMap.put(securityCode, securityCodeVal - quantity);
				}
			}

			tradeMap.put(tradeId, trade);
		}
	}

	public static void showResult() {
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
