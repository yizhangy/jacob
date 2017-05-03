/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2015 Forward Thinking Ltd
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

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.inject.Guice;
import com.jacob.web.guice.module.WebModule;

public class ResourceApplication extends ResourceConfig {

	@Inject
	public ResourceApplication(ServiceLocator serviceLocator){
		packages("com.jacob.resource");
		register(MultiPartFeature.class);
		register(JacksonJaxbJsonProvider.class);

		// Guice integration
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
		GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
		guiceBridge.bridgeGuiceInjector(Guice.createInjector(new WebModule()));
	}
}