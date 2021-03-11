package com.br.sample.site.core.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.osgi.service.component.annotations.Component;

import com.br.sample.site.core.models.ServiceModel;
import com.br.sample.site.core.services.ConnectionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(name = "Connection Service Impl", service = ConnectionService.class, immediate = true)
public class ConnectionServiceImpl implements ConnectionService {

	static final String USER_AGENT = "Mozilla/5.0";
	
	@Override
	public ServiceModel readJson(String url) {
		
		ServiceModel serviceModel;
		
		String json = "";
		
		int statusCode = HttpsURLConnection.HTTP_INTERNAL_ERROR;
		
		
		try {
			/**
			 * Get the URL object from the passed url string
			 */
			URL requestURL = new URL(url);

			/**
			 * Creating an object of HttpURLConnection
			 */
			HttpsURLConnection connection = (HttpsURLConnection) requestURL.openConnection();

			/**
			 * Setting the request method
			 */
			connection.setRequestMethod("GET");

			/**
			 * Setting the request property
			 */
			connection.setRequestProperty("User-Agent", USER_AGENT);

			/**
			 * Get the response code
			 */
			statusCode = connection.getResponseCode();

			if (statusCode == HttpsURLConnection.HTTP_OK) {

				/**
				 * Getting an instance of BufferedReader to read the response returned
				 */
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				/**
				 * String which will read the response line by line
				 */
				String inputLine;

				/**
				 * StringBuffer object to append the string as a whole
				 */
				StringBuffer response = new StringBuffer();

				/**
				 * Read until empty line is encountered
				 */
				while ((inputLine = in.readLine()) != null) {

					/**
					 * Append each line to make the response as a whole
					 */
					response.append(inputLine);
				}

				/**
				 * Closing the BufferedReader to avoid memory leaks
				 */
				in.close();

				/**
				 * Return the response
				 */
				
				
				json = response.toString();
				
			}

		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e.getCause());
			
		} catch (IOException e) {
			log.error(e.getMessage(), e.getCause());
		}
        
		serviceModel = new ServiceModel(statusCode, json);		
				
				
		return serviceModel;
	}

}
