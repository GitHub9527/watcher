package com.yiji.framework.watcher;

/**
 * 
 * @author Bohr.Qiu <qiubo@yiji.com>
 * 
 */
public class WatherTomcatBootStrap {
	public static void main(final String[] args) {
		new BootstrapHelper(11111, false, "local").start();
	}
	
}
