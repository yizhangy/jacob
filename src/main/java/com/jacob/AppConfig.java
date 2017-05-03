/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2017 Forward Thinking Ltd
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Forward Thinking Ltd and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Forward Thinking Ltd
 * and its suppliers and may be covered by New Zealand and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Forward Thinking Ltd.
 */
package com.jacob;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class AppConfig {
	private static final String CONFIG_FILE = "app.properties";

	private static Configuration config;

	private static AppConfig instance = new AppConfig();

	static {
		try {
			config = new PropertiesConfiguration(CONFIG_FILE);
		} catch (ConfigurationException e) {
			throw new IllegalStateException(e);
		}
	}

	public static AppConfig getInstance() {
		return instance;
	}
	
	public String getAppClientKey() {
		return config.getString("app_client_key");
	}
	
	public String getRedditClientSecret() {
		return config.getString("reddit_client_secret");
	}
	
	public String getRedditClientId() {
		return config.getString("reddit_client_id");
	}
	
	public String getRedditRedirectUrl() {
		return config.getString("reddit_redirect_url");
	}
	
	public String getYoutubeApiKey() {
		return config.getString("youtube_api_key");
	}
	
	public int getSourceUpdatePeriod() {
		return  Integer.valueOf(config.getString("source_update_period"));
	}
	
	public int getSourceUpdateWaitingPeriod() {
		return  Integer.valueOf(config.getString("source_update_waiting_period"));
	}

}

