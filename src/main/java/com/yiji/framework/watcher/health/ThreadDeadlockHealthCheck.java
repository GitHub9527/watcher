/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2015-04-28 15:15 创建
 *
 */
package com.yiji.framework.watcher.health;

import java.util.Set;

import com.codahale.metrics.health.HealthCheck;

/**
 * @author qiubo@yiji.com
 */
public class ThreadDeadlockHealthCheck extends HealthCheck {
	private final ThreadDeadlockDetector detector;
	
	/**
	 * Creates a new health check.
	 */
	public ThreadDeadlockHealthCheck() {
		this(new ThreadDeadlockDetector());
	}
	
	/**
	 * Creates a new health check with the given detector.
	 *
	 * @param detector a thread deadlock detector
	 */
	public ThreadDeadlockHealthCheck(ThreadDeadlockDetector detector) {
		this.detector = detector;
	}
	
	@Override
	protected Result check() throws Exception {
		final Set<String> threads = detector.getDeadlockedThreads();
		if (threads.isEmpty()) {
			return Result.healthy();
		}
		return Result.unhealthy(threads.toString());
	}
	
}