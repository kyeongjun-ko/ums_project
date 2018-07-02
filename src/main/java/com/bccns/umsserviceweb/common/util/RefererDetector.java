package com.bccns.umsserviceweb.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class RefererDetector {
	
	private HashMap<String, String> domainMap;

	public void setDomainMap(HashMap<String, String> domainMap) {
		this.domainMap = domainMap;
	}
	
	public String getRefererSiteName(String refererUrl) {

		String refererSiteName = null;
		
		String temp = StringUtils.substringAfter(refererUrl, "://");
		temp = StringUtils.substringBefore(temp, "/");

		Set<String> set = domainMap.keySet();
		Iterator<String> iter = set.iterator();

		//정확히 매치하는것 찾기
		while (iter.hasNext()) {
			String key = iter.next();
			if (StringUtils.equals(temp, key)) {
				refererSiteName = domainMap.get(key);
				break;
			}
		}

		if (StringUtils.isEmpty(refererSiteName)) {
			Iterator<String> iter2 = set.iterator();
			//비슷하게 매치하는것 찾기
			while (iter2.hasNext()) {
				String key = iter2.next();
				if (StringUtils.indexOf(temp, key) != -1) {
					refererSiteName = domainMap.get(key);
					break;
				}
			}
		}
		
		if (StringUtils.isEmpty(refererSiteName) && StringUtils.isNotEmpty(refererUrl)) {
			refererSiteName = "unkown";
		}
		
		return refererSiteName;
	}
	
	public static void main(String[] args) {
		
		HashMap<String, String> domainMap = new HashMap<String, String>();
		domainMap.put("naver.com", "naver");
		domainMap.put("naver", "naver");
		domainMap.put("google.com", "google");
		domainMap.put("google.co.kr", "google");
		domainMap.put("google", "google");
		domainMap.put("daum.net", "daum");
		domainMap.put("daum", "daum");
		domainMap.put("nate.com", "nate");
		domainMap.put("nate", "nate");
		domainMap.put("zum.com", "zum");
		domainMap.put("zum", "zum");
		domainMap.put("yahoo.com", "yahoo");
		domainMap.put("yahoo.co.kr", "yahoo");
		domainMap.put("yahoo", "yahoo");
		domainMap.put("bing.com", "bing");
		domainMap.put("bing", "bing");
		domainMap.put("joinsmsn.com", "msn");
		domainMap.put("msn.com", "msn");
		domainMap.put("msn", "msn");
		domainMap.put("cyworld.com", "cyworld");
		domainMap.put("cyworld", "cyworld");
		domainMap.put("paran.com", "paran");
		domainMap.put("paran", "paran");
		domainMap.put("dreamwiz.com", "dreamwiz");
		domainMap.put("dreamwiz", "dreamwiz");
		domainMap.put("facebook.com", "facebook");
		domainMap.put("facebook", "facebook");
		domainMap.put("twitter.com", "twitter");
		domainMap.put("twitter", "twitter");
		domainMap.put("yozm.daum.net", "yozm");
		domainMap.put("yozm", "yozm");
		domainMap.put("me2day.net", "me2day");
		domainMap.put("me2day", "me2day");
		
		RefererDetector refererDetector = new RefererDetector();
		refererDetector.setDomainMap(domainMap);
		
		String refererUrl = "http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=105";
		
		String refereSiteName = refererDetector.getRefererSiteName(refererUrl);
		System.out.println(refereSiteName);
		
		
	}
}
