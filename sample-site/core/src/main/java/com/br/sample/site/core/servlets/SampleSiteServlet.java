package com.br.sample.site.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.br.sample.site.core.models.ServiceModel;
import com.br.sample.site.core.services.SampleSiteService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(
        immediate = true,
        service = Servlet.class,
        property = {
        		Constants.SERVICE_DESCRIPTION + "=JSON Sample Servlet to read the data from the external webservice",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.resourceTypes=" + "sample-site/components/page",
                "sling.servlet.selectors=" + SampleSiteServlet.DEFAULT_SELECTOR,
                "sling.servlet.extensions=json"
        })

public class SampleSiteServlet extends SlingSafeMethodsServlet {
	
	
	private static final long serialVersionUID = 1L;


	protected static final String DEFAULT_SELECTOR = "states-and-cities";
	
	
	@Reference
	private SampleSiteService sampleSiteService; 
	
	private ServiceModel serviceModel;
	
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        
    	PrintWriter out = response.getWriter();
        
        serviceModel = sampleSiteService.makeHttpCall();
        
        response.setHeader("Content-Type", "application/json; charset=utf-8");
        response.setStatus(serviceModel.getStatusCode());

        out.print(serviceModel.getJson());
        out.close();
    }
	

}
