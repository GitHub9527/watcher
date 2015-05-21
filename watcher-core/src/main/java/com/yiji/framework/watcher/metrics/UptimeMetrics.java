/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2015-04-25 15:43 创建
 *
 */
package com.yiji.framework.watcher.metrics;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;
import com.yiji.framework.watcher.metrics.base.AbstractWatcherMetrics;

/**
 * @author qiubo@yiji.com
 */
public class UptimeMetrics extends AbstractWatcherMetrics {
	public static final String simple = "yyyy-MM-dd HH:mm:ss";
	private  String startTime = null;
	
	public Object watch(Map<String, Object> params) {
		long uptime = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
		long s = uptime % 60;//秒
		long m = uptime / 60 % 60;//分
		long h = uptime / 60 / 60 % 24;//时
		long d = uptime / 60 / 60 / 24;//天
		StringBuilder sb = new StringBuilder();
		if (d != 0) {
			sb.append(d).append("day");
		}
		if (h != 0) {
			sb.append(h).append("hour");
		}
		if (m != 0) {
			sb.append(m).append("min");
		}
		if (s != 0) {
			sb.append(s).append("sec");
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("uptimeStr", sb.toString());
		map.put("uptime", uptime);
		if (startTime == null) {
			startTime = new SimpleDateFormat(simple).format(new Date(ManagementFactory.getRuntimeMXBean().getStartTime()));
		}
		map.put("startTime", startTime);
		return map;
	}
	
	public String name() {
		return "uptime";
	}
	
	public String desc() {
		return "show process up time";
	}
}
