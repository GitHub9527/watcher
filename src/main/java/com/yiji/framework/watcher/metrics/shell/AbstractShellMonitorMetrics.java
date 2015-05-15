/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2015-05-15 15:10 创建
 *
 */
package com.yiji.framework.watcher.metrics.shell;

import com.yiji.framework.watcher.metrics.AbstractMonitorMetrics;

/**
 * @author qiubo@yiji.com
 */
public abstract class AbstractShellMonitorMetrics extends AbstractMonitorMetrics {
	protected static ShellExecutor shellExecutor = new ShellExecutor();
}
