package com.br.sample.site.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.br.sample.site.core.provider.Multifield;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ExampleComponentMultifieldUsingCustomAnnotationSlingModel {

	protected static final String RESOURCE_TYPE = "/app/wknd/components/samplemultifieldmodel";
    
	@ValueMapValue
	@Required
	private String title;

	@ValueMapValue
	@Default(values = "description")
	private String description;

	// Multifield Child Resource for States
//	@ChildResource(name = "states")
//	private List<Resource> resourceStates;
//	private List<StateModel> states;
	
	@Multifield(name = "states", values = {"jrc:name", "initials", "flag"})
    private List<HashMap> listItems;

    
	

//	@PostConstruct
//	protected void init() {
//		states = new ArrayList<StateModel>();
//
//		if (resourceStates != null) {
//			states = populatedModelState(states, resourceStates);
//		}
//
//	}

	private List<StateModel> populatedModelState(List<StateModel> states, List<Resource> resources) {

		for (Resource r : resources) {
			StateModel stateModel = r.adaptTo(StateModel.class);
			if (stateModel != null)
				states.add(stateModel);
		}

		return states;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

//	public List<StateModel> getStates() {
//		return states;
//	}
	
	public List<HashMap> getListItems() {
        return listItems;
    }
}
