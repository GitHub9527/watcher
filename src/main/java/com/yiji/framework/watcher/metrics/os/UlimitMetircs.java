/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2015-05-04 18:33 创建
 *
 */
package com.yiji.framework.watcher.metrics.os;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qiubo@yiji.com
 */
public class UlimitMetircs extends AbstractOSMonitorMetrics {
	private static final Logger logger = LoggerFactory.getLogger(UlimitMetircs.class);
	
	@Override
	public Object doMonitor(Map<String, Object> params) throws Exception {
		WatcherUlimit watcherUlimit = new WatcherUlimit();
		if (params.containsKey("help") || params.containsKey("h")) {
			return watcherUlimit.getComment();
		}
		return watcherUlimit.getUlimitInfo();
	}
    @Override
    public CacheTime getCacheTime() {
        return CacheTime.THIRTY_SECOND;
    }

    @Override
    protected List<String> getParamsBuildKey() {
        List<String> keys=Lists.newArrayList(super.getParamsBuildKey());
        keys.add("h");
        keys.add("help");
        return keys;
    }

    @Override
	public String name() {
		return "ulimit";
	}
	
	@Override
	public String desc() {
		return "system resource limits,h/help show help";
	}
}
