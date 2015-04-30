package com.yiji.framework.watcher.adaptor.web.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * @author qiubo@yiji.com
 */
public class IPRangeTest {
	@Test
	public void testRange_equal() throws Exception {
		IPRange ipRange = new IPRange("127.0.0.1");
		boolean allow = ipRange.isIPAddressInRange(new IPAddress("127.0.0.1"));
		assertThat(allow).isTrue();
	}
	
	@Test
	public void testRange_notequal() throws Exception {
		IPRange ipRange = new IPRange("127.0.0.1");
		boolean allow = ipRange.isIPAddressInRange(new IPAddress("127.0.0.2"));
		assertThat(allow).isFalse();
	}
	
	@Test
	public void testRange_in() throws Exception {
		IPRange ipRange = new IPRange("192.168.0.0/255.255.255.0");
		boolean allow = ipRange.isIPAddressInRange(new IPAddress("192.168.0.0"));
		assertThat(allow).isTrue();
	}
	
	@Test
	public void testRange_in1() throws Exception {
		IPRange ipRange = new IPRange("192.168.0.0/255.255.255.0");
		boolean allow = ipRange.isIPAddressInRange(new IPAddress("192.168.0.255"));
		assertThat(allow).isTrue();
	}
	
	@Test
	public void testRange_in2() throws Exception {
		IPRange ipRange = new IPRange("192.168.0.0/255.255.255.0");
		boolean allow = ipRange.isIPAddressInRange(new IPAddress("192.168.1.0"));
		assertThat(allow).isFalse();
	}
	
	@Test
	public void testRange_in3() throws Exception {
		IPRange ipRange = new IPRange("192.168.0.0/255.255.0.0");
		boolean allow = ipRange.isIPAddressInRange(new IPAddress("192.168.1.0"));
		assertThat(allow).isTrue();
	}
	
}