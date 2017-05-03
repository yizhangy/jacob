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
package com.jacob.reddit.service;

import com.github.jreddit.oauth.app.RedditInstalledApp;
import com.jacob.AppConfig;

public class RedditApplication extends RedditInstalledApp {

	public static final String CLIENT_SECRET = AppConfig.getInstance().getRedditClientSecret();
	public RedditApplication(String clientID, String redirectURI) {
		super(clientID, redirectURI);
	}
	
	@Override
	public String getClientSecret() {
		return CLIENT_SECRET;
	}

}