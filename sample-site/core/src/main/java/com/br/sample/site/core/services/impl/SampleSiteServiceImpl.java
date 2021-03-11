package com.br.sample.site.core.services.impl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.br.sample.site.core.models.ServiceModel;
import com.br.sample.site.core.services.ConnectionService;
import com.br.sample.site.core.services.SampleSiteService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(name = "Sample Site Service Impl", service = SampleSiteService.class, immediate = true)
public class SampleSiteServiceImpl implements SampleSiteService {
	
	@Reference
	ConnectionService connectionService;
	
	@Override
	public ServiceModel makeHttpCall() {
		
		String protocol = "";
		String server = "";
		String endpoint = "";
		
		String url = protocol + "://" + server + "/" + endpoint;
		
		ServiceModel response = connectionService.readJson(url);
		
		return response;
	}

}
