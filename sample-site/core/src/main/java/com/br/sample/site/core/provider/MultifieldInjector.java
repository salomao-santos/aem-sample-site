package com.br.sample.site.core.provider;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;
import org.apache.sling.models.spi.injectorspecific.InjectAnnotationProcessor2;
import org.apache.sling.models.spi.injectorspecific.StaticInjectAnnotationProcessorFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component(
	    immediate = true, service = {Injector.class, StaticInjectAnnotationProcessorFactory.class},
	    property = {Constants.SERVICE_RANKING + ":Integer=7000"})
	public class MultifieldInjector implements Injector, StaticInjectAnnotationProcessorFactory {

	    @Override
	    public Object getValue(Object adaptable, String fieldName, Type type, AnnotatedElement annotatedElement, DisposalCallbackRegistry disposalCallbackRegistry) {
	        if (adaptable instanceof SlingHttpServletRequest && annotatedElement.isAnnotationPresent(Multifield.class)) {

	            Multifield annotation = annotatedElement.getAnnotation(Multifield.class);

	            try {
	                Resource resource = ((SlingHttpServletRequest) adaptable).getResource();

	                Node multifieldNode = resource.adaptTo(Node.class);

	                if (multifieldNode == null) {
	                    return null;
	                }

	                if (!multifieldNode.hasNode(annotation.name())) {
	                    return new ArrayList<HashMap>();
	                }

	                return getMultifield(resource.getChild(annotation.name()), annotation.values());
	            } catch (Exception ex) {
	            	 log.error("Error on access repository and get field value.", ex);
	            }
	        }
	        return null;
	    }
	    
	    public List<HashMap> getMultifield(Resource resource, String[] fields) {
	        Node resourceNode = resource.adaptTo(Node.class);
	        NodeIterator ni;
	        List<HashMap> multifield = new ArrayList<HashMap>();
			try {
				ni = resourceNode.getNodes();
				
				

		        while (ni.hasNext()) {
		            HashMap<String, String> values = new HashMap<>();

		            Node currentNode = (Node) ni.nextNode();

		            for (String field : fields) {
		                if (currentNode.hasProperty(field)) {
		                    values.put(field, currentNode.getProperty(field).getString());
		                }
		            }

		            multifield.add(values);
		        }
				
			} catch (RepositoryException ex) {
				log.error("Error", ex);
			}

	        

	        return multifield;
	    }
	    
	    @Override
	    public String getName() {
	        return "page-multifield-provider-injector";
	    }

	    @Override
	    public InjectAnnotationProcessor2 createAnnotationProcessor(AnnotatedElement element) {

	        if (element == null) {
	            return null;
	        }

	        Multifield annotation = element.getAnnotation(Multifield.class);

	        if (annotation == null) {
	            return null;
	        }

	        return new RequestedPageMetadataProviderAnnotationProcessor();

	    }
	}
