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
package com.jacob.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jacob.AppConfig;
import com.jacob.reddit.service.RedditUpdatingSchedulerService;
import com.jacob.youtube.service.YoutubeSourceScheduleService;

@Path("/task")
public class AdminResource{
	private RedditUpdatingSchedulerService redditUpdatingSchedulerService;
	private YoutubeSourceScheduleService youtubeSourceScheduleService;
	private static final String PARAM_TOKEN = "token";
	private static final boolean ENABLE_REDDIT = false;
	private static final boolean ENABLE_YOUTUBE = true;
	private static boolean SCHEDULED = false;
	
	@Inject
	public AdminResource(RedditUpdatingSchedulerService redditUpdatingSchedulerService, YoutubeSourceScheduleService youtubeSourceScheduleService) {
		this.redditUpdatingSchedulerService = redditUpdatingSchedulerService;
		this.youtubeSourceScheduleService = youtubeSourceScheduleService;
	}
	
	@GET
	@Path("/schedule")
	@Produces({ MediaType.TEXT_PLAIN })
	public String source(@QueryParam(PARAM_TOKEN)String token) {
		StringBuilder builder = new StringBuilder("Following Service Are Enabled: ");
		if (SCHEDULED) {
			throw new WebApplicationException("The task is already scheduled.Please restart environment to rescheduled it.", Response.Status.NOT_MODIFIED);
		}
		if (token.equals(AppConfig.getInstance().getAppClientKey())) {
			if (ENABLE_REDDIT) {
				this.redditUpdatingSchedulerService.scheduleJob();
				builder.append("Reddit spider service is enabled; ");
			}
			if (ENABLE_YOUTUBE){
				this.youtubeSourceScheduleService.scheduleJob();
				builder.append("Youtube spider service is enabled; ");
			}
			SCHEDULED = true;
			return builder.toString();
		}else {
			throw new WebApplicationException("NOT ALLOWED ACCESS! Please Contact Admin.", Response.Status.FORBIDDEN);
		}
	}

}