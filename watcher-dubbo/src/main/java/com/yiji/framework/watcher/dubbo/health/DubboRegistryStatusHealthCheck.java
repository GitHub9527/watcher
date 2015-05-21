/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2015-05-19 17:20 创建
 *
 */
package com.yiji.framework.watcher.dubbo.health;

import java.util.Collection;

import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.support.AbstractRegistryFactory;
import com.codahale.metrics.health.HealthCheck;
import com.yiji.framework.watcher.Utils;

/**
 * @author qiubo@yiji.com
 */
public class DubboRegistryStatusHealthCheck extends HealthCheck {
	public DubboRegistryStatusHealthCheck() {
        Utils.checkClassExists("com.alibaba.dubbo.registry.support.AbstractRegistryFactory", "dubbo");
	}
	
	@Override
	protected Result check() throws Exception {
		Collection<Registry> regsitries = AbstractRegistryFactory.getRegistries();
		if (regsitries.size() == 0) {
			return Result.healthy("no registry found");
		}
		boolean isOK = true;
		StringBuilder buf = new StringBuilder();
		for (Registry registry : regsitries) {
			if (buf.length() > 0) {
				buf.append(",");
			}
			buf.append(registry.getUrl().getAddress());
			if (!registry.isAvailable()) {
				isOK = false;
				buf.append("(disconnected)");
			} else {
				buf.append("(connected)");
			}
		}
		if (isOK) {
			return Result.healthy();
		} else {
			return Result.unhealthy(buf.toString());
		}
	}
}
