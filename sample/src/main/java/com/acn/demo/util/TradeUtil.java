package com.acn.demo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.acn.demo.model.Trade;

/**
 * @Description: trade utility class
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

	public static Map<Long, Trade> tradeMap = new HashMap<Long, Trade>();

	public static Map<String, Integer> securityMap = new LinkedHashMap<String, Integer>();

	/**
	 * save trade data.
	 *
	 * @param trade        Trade instance
	 * @param tradeId      trade identifier
	 * @param securityCode security identifier
	 * @param quantity     quantity of security
	 * @param actionType   type of action
	 * @param tradeType    type of trade
	 * @return
	 */
	public static void saveTradeMap(Trade trade, Long tradeId, String securityCode, int quantity, int actionType,
			int tradeType) {
		if (trade != null) {
			// version starts with 1 and increases by 1
			// Insert will always be 1st version of a Trade and Cancel will always be last
			// version of Trade
			trade.setVersion(trade.getVersion() + 1);

			// for Cancel, any columns can be changed and should be ignored
			if (actionType != ACTION_TYPE_CANCEL) {
				trade.setSecurityCode(securityCode);
				trade.setActionType(actionType); // 1：insert 2:update 3:cancel
				trade.setTradeType(tradeType); // 1：buy 2:sell
				trade.setQuantity(quantity);

				int securityCodeVal = securityMap.get(securityCode) == null ? 0
						: securityMap.get(securityCode).intValue();

				// when trade type is Buy, the quantity of security code should be added
				if (tradeType == TRADE_TYPE_BUY) {
					securityMap.put(securityCode, securityCodeVal + quantity);
				}

				// when trade type is Sell, the quantity of security code should be subtracted
				if (tradeType == TRADE_TYPE_SELL) {
					securityMap.put(securityCode, securityCodeVal - quantity);
				}
			}

			tradeMap.put(tradeId, trade);
		}
	}

	/**
	 * show results of trade.
	 *
	 * @return
	 */
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
