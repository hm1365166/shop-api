package file.util;/*
package com.file.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Rpc {
	private volatile static Rpc instance = null;
	private static Logger logger = LoggerFactory.getLogger(Rpc.class);
	private static ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

	private Rpc() {
	}

	public static Rpc getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}

	private static synchronized void syncInit() {
		if (instance == null) {
			instance = new Rpc();
		}
	}

	public String rpc(String url, Object params) {
		try {
			//String ip = this.configureProvider.getProperty(YYFaxSettleVariavle.SETTLE_URL_PREFIX);

			//String ip = "http://localhost:8088/nsettle";
			String ip = "http://localhost:8079/store";
			String reqJson = JSON.toJSONString(params);
			logger.info("请求：" + ip + url + " " + reqJson);
			String url1 = ip + url;
			String json = HttpHelper.doPostSSL(url1, JSON.toJSONString(params));
			logger.info("响应：" + StringUtils.substring(json, 0, 300));

			return json;
		} catch (Exception e) {
			return JSON.toJSONString(new RpcRsp<EmptyContent>(RspCodeEnum.COMMON_ERROR_UNKNOWN_EXCEPTION, e.getMessage()));
		}
	}

	public String async(String url, Object params) {
		try {
			//String ip = this.configureProvider.getProperty(YYFaxSettleVariavle.SETTLE_URL_PREFIX);
			String ip = "http://localhost:8099/settle";
			final String api = ip + url;
			final String reqJson = JSON.toJSONString(params);
			pool.submit(new Runnable() {
				private int counter = 0;

				@Override
				public void run() {
					try {
						counter++;
						logger.info("请求：" + api + " " + reqJson);
						String json = HttpHelper.doPostSSL(api, reqJson);
						logger.info("响应：" + json);
					} catch (IOException e) {
						if (counter < 3) { // 重试3次
							try {
								Thread.sleep(counter * 1000); // 间隔counter秒重试
							} catch (InterruptedException e1) {
							}
							pool.submit(this);
						} else {
							logger.error("请求重试" + counter + "次失败:" + api + " " + reqJson, e);
						}
					}
				}
			});
			return JSON.toJSONString(new RpcRsp<EmptyContent>());
		} catch (Exception e) {
			return JSON.toJSONString(new RpcRsp<EmptyContent>(RspCodeEnum.COMMON_ERROR_UNKNOWN_EXCEPTION, e.getMessage()));
		}
	}

	public String doGrab(String url, Object params) {
		try {
			//String ip = this.configureProvider.getProperty(YYFaxSettleVariavle.SETTLE_URL_PREFIX);
			String ip = "http://localhost:8088/nsettle";
			final String api = ip + url;
			final String reqJson = JSON.toJSONString(params);
			pool.submit(new Runnable() {
				private int counter = 0;

				@Override
				public void run() {
					try {
						counter++;
						logger.info("请求：" + api + " " + reqJson);
						String json = HttpHelper.doPostSSL(api, reqJson);
						logger.info("响应：" + json);
						if (counter < 3) { // 重试3次
							try {
								Thread.sleep(counter * 500); // 间隔counter秒重试
							} catch (InterruptedException e1) {
							}
							pool.submit(this);
						} else {
							logger.error("请求重试" + counter + "次失败:" + api + " " + reqJson);
						}
					} catch (IOException e) {
					}
				}
			});
			return JSON.toJSONString(new RpcRsp<EmptyContent>());
		} catch (Exception e) {
			return JSON.toJSONString(new RpcRsp<EmptyContent>(RspCodeEnum.COMMON_ERROR_UNKNOWN_EXCEPTION, e.getMessage()));
		}
	}

	*/
/**
	 * 查询季度交易额
	 *
	 * @return
	 * @throws IOException
	 * @author:HM
	 * @date: 2017/11/1 20:21:39
	 *//*

	public OpResponse<List<TotalInvest>> queryQuarterTotalInvest() throws IOException {
		String url = "/api/home/statics/query_quarter_total_invest";
		String rspJson = rpc(url, null);
		logger.info("rpc-queryQuarterTotalInvest:"+rspJson);
		return JSON.parseObject(rspJson, new TypeReference<OpResponse<List<TotalInvest>>>() {
		});
	}


	*/
/**
	 * 测试刷接口
	 *
	 * @return
	 * @throws IOException
	 * @author:HM
	 * @date: 2017/11/1 20:21:39
	 *//*

	public OpResponse<List<TotalInvest>> queryQuarterTotalInvestNumbers() throws IOException {
		String url = "/api/home/statics/query_quarter_total_invest";
		String rspJson = doGrab(url, null);
		logger.info("rpc-queryQuarterTotalInvest:"+rspJson);
		return JSON.parseObject(rspJson, new TypeReference<OpResponse<List<TotalInvest>>>() {
		});
	}


	*/
/**
	 *
	 *
	 * @return
	 * @throws IOException
	 * @author:HM
	 * @date: 17-11-03 09:39:07
	 * @since v1.0.0
	 *//*

	public String queryShopStore() throws IOException {
		String url = "/api/home/statics/query_quarter_total_invest";
		String rspJson = rpc(url, null);
		logger.info("rpc-queryQuarterTotalInvest:"+rspJson);
		return rspJson;
	}

}
*/
