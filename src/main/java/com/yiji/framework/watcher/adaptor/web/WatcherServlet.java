/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2015-04-27 12:57 创建
 *
 */
package com.yiji.framework.watcher.adaptor.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.yiji.framework.watcher.DefaultMonitorService;
import com.yiji.framework.watcher.MonitorRequest;
import com.yiji.framework.watcher.ResponseType;

/**
 * @author qzhanbo@yiji.com
 */
public class WatcherServlet extends HttpServlet {
	private static String velocityPath = "com/yiji/framework/watcher/adaptor/web/index.vm";
	private static String vmContent = null;
	private String index = null;
	private String appName = null;
	
	public WatcherServlet() {
	}
	
	public WatcherServlet(String appName) {
		this.appName = appName;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String uri = req.getPathInfo();
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
		if (uri == null || uri.equals("/") || uri.equals("/index.html") || uri.equals("/index.htm")) {
			handleIndex(req, resp);
			return;
		} else if (uri.indexOf("q.do") != -1) {
			Map<String, Object> paramMap = getRequestParamMap(req);
			MonitorRequest request = new MonitorRequest();
			request.setParams(paramMap);
			String action = (String) paramMap.get("action");
			if (action == null) {
				resp.getWriter().write("请指定action参数值");
			}
			request.setAction(paramMap.get("action").toString());
			setPrettyFormat(paramMap, request);
			setResType(paramMap, request);
			resp.getWriter().write(DefaultMonitorService.INSTANCE.monitor(request));
		} else {
			resp.getWriter().write("不支持的请求");
		}
	}
	
	private void setPrettyFormat(Map<String, Object> paramMap, MonitorRequest request) {
		Object prettyFormat = paramMap.get("prettyFormat");
		if (prettyFormat != null) {
			request.setPrettyFormat(Boolean.parseBoolean(prettyFormat.toString()));
		}
	}
	
	private void setResType(Map<String, Object> paramMap, MonitorRequest request) {
		try {
			Object resType = paramMap.get("resType");
			if (resType == null || ResponseType.valueOf(resType.toString()) == null) {
				request.setResponseType(ResponseType.JSON);
			} else {
				request.setResponseType(ResponseType.valueOf(resType.toString()));
			}
		} catch (IllegalArgumentException e) {
			request.setResponseType(ResponseType.JSON);
		}
	}
	
	private void handleIndex(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Map<String, Object> params = Maps.newHashMap();
			params.put("appName", appName);
			params.put("metricses", DefaultMonitorService.INSTANCE.monitorMetricses());
			if (vmContent == null) {
				vmContent=readFile(velocityPath);
			}
			index = parseVelocity(vmContent, params);
		} catch (Exception e) {
			index = Throwables.getStackTraceAsString(e);
		}
		try {
			resp.getWriter().write(index);
		} catch (IOException e) {
			//do nothing
		}
	}
	
	private static String readFile(String resourceLocation) {
		InputStream inputStream = getDefaultClassLoader().getResourceAsStream(resourceLocation);
		if (inputStream == null) {
			return resourceLocation + "不存在";
		} else {
			try {
				return CharStreams.toString(new InputStreamReader(inputStream));
			} catch (IOException e) {
				return Throwables.getStackTraceAsString(e);
			} finally {
				Closeables.closeQuietly(inputStream);
			}
		}
	}
	
	private static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			//ignore
		}
		if (cl == null) {
			cl = WatcherServlet.class.getClassLoader();
		}
		return cl;
	}
	
	private static String parseVelocity(String templateContent, Map<String, Object> param) {
		VelocityContext context = new VelocityContext(param);
		StringWriter w = new StringWriter();
		VelocityEngine velocity = new VelocityEngine();
		//模板内引用解析失败时不抛出异常
		velocity.setProperty("runtime.references.strict", "false");
		velocity.init();
		velocity.evaluate(context, w, velocityPath, templateContent);
		return w.toString();
	}
	
	private Map<String, Object> getRequestParamMap(HttpServletRequest req) {
		Map<String, Object> paramMap = Maps.newHashMap();
		Map<String, String[]> stringMap = req.getParameterMap();
		stringMap.entrySet().stream().filter(stringEntry -> stringEntry.getValue() != null).forEach(stringEntry -> {
			paramMap.put(stringEntry.getKey(), stringEntry.getValue()[0]);
		});
		return paramMap;
	}
	
}
