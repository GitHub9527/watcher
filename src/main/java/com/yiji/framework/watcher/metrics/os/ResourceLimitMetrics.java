/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2015-04-26 15:28 创建
 *
 */
package com.yiji.framework.watcher.metrics.os;

import java.util.Map;

import org.hyperic.sigar.SigarException;

/**
 * @author qiubo@yiji.com
 */
public class ResourceLimitMetrics extends AbstractOSMonitorMetrics {
	private Map resourceLimit = null;
	
	public Object doMonitor(Map<String, Object> params) throws SigarException {
		if (resourceLimit == null) {
			resourceLimit = SigarFactory.getSigar().getResourceLimit().toMap();
		}
		return resourceLimit;
	}
	
	public String name() {
		return "resourceLimit";
	}
	
	public String desc() {
		return "resource limits by the underlying OS";
	}
}
