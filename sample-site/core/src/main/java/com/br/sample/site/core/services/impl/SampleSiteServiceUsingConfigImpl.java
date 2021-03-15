package com.br.sample.site.core.services.impl;

import javax.net.ssl.HttpsURLConnection;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import com.br.sample.site.core.config.SampleHttpConfiguration;
import com.br.sample.site.core.models.ServiceModel;
import com.br.sample.site.core.services.ConnectionService;
import com.br.sample.site.core.services.SampleSiteService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(name = "Sample Site Service Using Config Impl", 
           service = SampleSiteService.class, 
           immediate = true)
@Designate(ocd = SampleHttpConfiguration.class)
public class SampleSiteServiceUsingConfigImpl implements SampleSiteService {

	private SampleHttpConfiguration configuration;

	@Reference
	private ConnectionService connectionService;
	
	@Activate
	protected void activate(SampleHttpConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public ServiceModel makeHttpCall() {
		
		ServiceModel response;
		
		try {

			/**
			 * Lendo os valores da configuração
			 */
			
			boolean enable = configuration.enableConfig();
			String protocol = configuration.getProtocol();
			String server = configuration.getServer();
			String endpoint = configuration.getEndpoint();

			String url = protocol + "://" + server + "/" + endpoint;
			
			if (enable) {
			
				response = connectionService.readJson(url);

				log.info("----------< JSON response from the webservice is >----------");
				log.info("StatusCode: " + response.getStatusCode());
				
				return response;
				
			} else {
				
				log.info("----------< Configuration is not enabled >----------");
				
				response = new ServiceModel(HttpsURLConnection.HTTP_INTERNAL_ERROR, "Configuration not enabled");
			}

		} catch (Exception e) {

			log.error(e.getMessage(), e);
			
			response = new ServiceModel(HttpsURLConnection.HTTP_INTERNAL_ERROR, "Configuration not enabled");
		}
		
		return response;
	}
	
}
