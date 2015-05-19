/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2015-04-25 15:54 创建
 *
 */
package com.yiji.framework.watcher.metrics;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.collect.Maps;
import com.yiji.framework.watcher.metrics.base.AbstractCachedWatcherMetrics;

/**
 * @author qiubo@yiji.com
 */
public class GCMetrics extends AbstractCachedWatcherMetrics {
	private final Pattern WHITESPACE = Pattern.compile("[\\s]+");
	
	public Object doMonitor(Map<String, Object> params) {
		Map<String, Object> map = Maps.newHashMap();
		List<GarbageCollectorMXBean> mxBeans = ManagementFactory.getGarbageCollectorMXBeans();
		if (mxBeans == null || mxBeans.isEmpty()) {
			return map;
		}
		long totalTime = 0;
		long totalCount = 0;
		for (GarbageCollectorMXBean mxBean : mxBeans) {
			final String name = WHITESPACE.matcher(mxBean.getName()).replaceAll("-");
			long time = mxBean.getCollectionTime();
			long count = mxBean.getCollectionCount();
			totalCount += count;
			totalTime += time;
			map.put(name + ".count", mxBean.getCollectionCount());
			map.put(name + ".time", mxBean.getCollectionTime());
		}
		map.put("totalTime", totalTime);
		map.put("totalCount", totalCount);
		return map;
	}
	
	@Override
	public CacheTime getCacheTime() {
		return CacheTime.THIRTY_SECOND;
	}
	
	public String name() {
		return "gc";
	}
	
	public String desc() {
		return "show gc stats";
	}
}
