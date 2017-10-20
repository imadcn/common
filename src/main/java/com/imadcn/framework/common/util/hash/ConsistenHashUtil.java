package com.imadcn.framework.common.util.hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性HASH工具
 */
public class ConsistenHashUtil<T> {
	/**
	 * 复制节点也叫虚拟节点个数
	 */
	private int numberOfReplicas = 160;

	/**
	 * 一致性Hash环
	 */
	private final SortedMap<Long, T> circle;

	/**
	 * 构造，使用MD5作为默认hash算法
	 * 
	 * @param numberOfReplicas 复制的节点个数也叫虚拟节点数，增加每个节点的复制节点有利于负载均衡
	 * @param nodes 节点对象
	 */
	public ConsistenHashUtil(Collection<T> nodes) {
		this.circle = new TreeMap<Long, T>();

		for (T node : nodes) {
			add(node);
		}
	}
	
	/**
	 * 构造，使用MD5作为默认hash算法
	 * 
	 * @param numberOfReplicas 复制的节点个数也叫虚拟节点数，增加每个节点的复制节点有利于负载均衡
	 * @param nodes 节点对象
	 */
	public ConsistenHashUtil(Collection<T> nodes, int nodeCopies) {
		this.circle = new TreeMap<Long, T>();
		this.numberOfReplicas = 8 * nodeCopies;

		for (T node : nodes) {
			add(node);
		}
	}

	/**
	 * 增加节点
	 * 
	 * 每增加一个节点，就会在闭环上增加给定复制节点数
	 * 
	 * 例如复制节点数是2，则每调用此方法一次，增加两个虚拟节点，这两个节点指向同一Node
	 * 由于hash算法会调用node的toString方法，故按照toString去重
	 * 
	 * @param node 节点对象
	 */
	public void add(T node) {
		for (int i = 0; i < numberOfReplicas / 4; i++) {
			byte[] digest = computeMd5(node.toString() + "-" + i);
			for (int h = 0; h < 4; h++) {
				circle.put(defaultHash(digest, h), node);
			}
		}
	}

	/**
	 * 移除节点的同时移除相应的虚拟节点
	 * 
	 * @param node 节点对象
	 */
	public void remove(T node) {
		for (int i = 0; i < numberOfReplicas / 4; i++) {
			byte[] digest = computeMd5(node.toString() + i);
			for (int h = 0; h < 4; h++) {
				circle.remove(defaultHash(digest, h));
			}
		}
	}

	/**
	 * 获得一个最近的顺时针节点
	 * 
	 * @param key 为给定键取Hash，取得顺时针方向上最近的一个虚拟节点对应的实际节点
	 * @return 节点对象
	 */
	public T get(Object key) {
		if (circle.isEmpty()) {
			return null;
		}

		byte[] digest = computeMd5(key.toString());
		long hash = defaultHash(digest, 0);

		if (!circle.containsKey(hash)) {
			SortedMap<Long, T> tailMap = circle.tailMap(hash);
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}

		return circle.get(hash);
	}

	/**
	 * 
	 * @param digest
	 * @param nTime
	 * @return
	 */
	public long defaultHash(byte[] digest, int nTime) {
		long rv = ((long) (digest[3 + nTime * 4] & 0xFF) << 24) | ((long) (digest[2 + nTime * 4] & 0xFF) << 16) | ((long) (digest[1 + nTime * 4] & 0xFF) << 8) | (digest[0 + nTime * 4] & 0xFF);

		return rv & 0xffffffffL;
	}

	/**
	 * 
	 * @param k
	 * @return
	 */
	public byte[] computeMd5(String k) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 not supported", e);
		}
		md5.reset();
		byte[] keyBytes = null;
		try {
			keyBytes = k.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unknown string :" + k, e);
		}

		md5.update(keyBytes);
		return md5.digest();
	}

	private void printNodes() {
		System.out.println("size=" + this.circle.size());
		System.out.println(this.circle);
	}
	
	public static void main(String[] args) {
		List<String> nodes = new ArrayList<String>();
		nodes.add("1");
		nodes.add("2");
		nodes.add("3");
		nodes.add("4");
		
//		for (int i=0; i <2; i ++) {
//			nodes.add("memcache" + i);
//		}
		
		ConsistenHashUtil<String> consistenHashUtil = new ConsistenHashUtil<String>(nodes);
		consistenHashUtil.printNodes();
//		Set<Entry<Long, String>>  set = consistenHashUtil.circle.entrySet();
//		Iterator<Entry<Long, String>> iterator = set.iterator();
//		while (iterator.hasNext()) {
//			Entry<Long, String> e = iterator.next();
//			
//			if (e.getKey() == 1742964L) {
//				System.out.println(e.getValue());
//			}
//		}
		String a = "TTT";
		String B = consistenHashUtil.get(a);
		System.out.println(B);
		
//		System.out.println("put begin");
//		for (int i=0; i <50; i ++) {
//			System.out.println("haha" + i + "=" + consistenHashUtil.get("haha" + i));
//		}
//		
//		System.out.println("get begin");
//		for (int i=0; i <50; i ++) {
//			System.out.println("haha" + i + "=" + consistenHashUtil.get("haha" + i));
//		}
	}
}
