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

import org.apache.http.impl.client.HttpClientBuilder;

import com.github.jreddit.oauth.RedditOAuthAgent;
import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.oauth.app.RedditApp;
import com.github.jreddit.oauth.client.RedditClient;
import com.github.jreddit.oauth.client.RedditHttpClient;
import com.github.jreddit.oauth.client.RedditPoliteClient;
import com.github.jreddit.oauth.exception.RedditOAuthException;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.parser.entity.imaginary.CommentTreeElement;
import com.github.jreddit.parser.entity.imaginary.FullSubmission;
import com.github.jreddit.parser.exception.RedditParseException;
import com.github.jreddit.parser.listing.SubmissionsListingParser;
import com.github.jreddit.parser.single.FullSubmissionParser;
import com.github.jreddit.request.retrieval.mixed.FullSubmissionRequest;
import com.github.jreddit.request.retrieval.param.SubmissionSort;
import com.github.jreddit.request.retrieval.submissions.SubmissionsOfSubredditRequest;
import com.jacob.AppConfig;

public class RedditClientServiceImpl implements RedditClientService{
	public static final String USER_AGENT = "jReddit: Reddit API Wrapper for Java";
	public static final String CLIENT_ID = AppConfig.getInstance().getRedditClientId();
	public static final String REDIRECT_URI = AppConfig.getInstance().getRedditRedirectUrl();
	
	private RedditApp redditApp;
	private RedditOAuthAgent agent;
	private RedditClient client;
	
	public RedditClientServiceImpl() {
		// Reddit application
		redditApp = new RedditApplication(CLIENT_ID, REDIRECT_URI);
		
		// Create OAuth agent
		agent = new RedditOAuthAgent(USER_AGENT, redditApp);    
		
		// Create client
		client = new RedditPoliteClient(new RedditHttpClient(USER_AGENT, HttpClientBuilder.create().build()));
	}

	@Override
	public RedditToken createToken() {
		try {
			return agent.tokenAppOnly(true);
		} catch (RedditOAuthException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Submission> getSubredditSubmissions(String subredditName, RedditToken token) {
		// Create parser for request
        SubmissionsListingParser parser = new SubmissionsListingParser();

        // Create the request
        SubmissionsOfSubredditRequest request = (SubmissionsOfSubredditRequest) new SubmissionsOfSubredditRequest(subredditName, SubmissionSort.HOT).setLimit(100);

        // Perform and parse request, and store parsed result
        try {
			return  parser.parse(client.get(token, request));
		} catch (RedditParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<CommentTreeElement> getComments(String submitId, RedditToken token) {
		// Create parser for request
		FullSubmissionParser parser = new FullSubmissionParser();

		// Create the request
		FullSubmissionRequest request = new FullSubmissionRequest(submitId).setDepth(1);

		// Perform and parse request, and store parsed result
		try {
			FullSubmission fullSubmission = parser.parse(client.get(token, request));
			return fullSubmission.getCommentTree();
		} catch (RedditParseException e) {
			throw new RuntimeException(e);
		}
	}

}