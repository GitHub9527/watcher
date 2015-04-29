/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2015-04-24 23:48 创建
 *
 */
package com.yiji.framework.watcher;

/**
 * 监控服务接口
 * @author qzhanbo@yiji.com
 */
public interface MonitorService extends MonitorMetricsRepository {
	/**
	 * 通过监控请求获取到监控结果数据
	 * @param request 监控请求
	 * @return 监控结果
	 */
	String monitor(MonitorRequest request);
}
