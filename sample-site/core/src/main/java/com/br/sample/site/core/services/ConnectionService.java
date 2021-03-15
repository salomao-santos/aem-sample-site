package com.br.sample.site.core.services;

import com.br.sample.site.core.models.ServiceModel;

public interface ConnectionService {
	
	public ServiceModel readJson(String url);
  
}