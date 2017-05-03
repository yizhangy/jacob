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
package com.jacob.youtube.service;

import java.util.Timer;

import javax.inject.Inject;

import com.jacob.AppConfig;

public class YoutubeSourceScheduleServiceImpl implements YoutubeSourceScheduleService {
	private YoutubeSourceService clientService;
	
	@Inject
	public YoutubeSourceScheduleServiceImpl(YoutubeSourceService clientService) {
		this.clientService = clientService;
	}

	@Override
	public void scheduleJob() {
		Timer time = new Timer(); 
		YoutubeSourceScheduler scheduler = new YoutubeSourceScheduler(clientService);
		AppConfig appConfig = AppConfig.getInstance();
		time.schedule(scheduler, appConfig.getSourceUpdateWaitingPeriod(), appConfig.getSourceUpdatePeriod());
	}

}

