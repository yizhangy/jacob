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

import java.util.List;
import java.util.TimerTask;

import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.parser.entity.Submission;

public class RedditUpdatingScheduler extends TimerTask{
	private RedditClientService redditClientService;
	
	public RedditUpdatingScheduler(RedditClientService redditClientService) {
		this.redditClientService = redditClientService;
	}

	@Override
	public void run() {
		RedditToken token =  redditClientService.createToken();
		List<Submission> submissions = this.redditClientService.getSubredditSubmissions(RedditSubDir.FUNNY.getSubDirName(), token);
		System.out.println(submissions);
	}

}