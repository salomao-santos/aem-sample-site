package com.br.sample.site.core.models.slingmodel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SampleUsingSlingModel {

	@ValueMapValue
	//Cuidado com a anotação @Required ela pode gerar problemas na aplicação, caso o componente seja adicionado na página e não seja utilizado.
	//Ao utilizar a anotação @Required recomendo utilizar o @Default, para evitar problemas.
	//@Required
	//@Default(values = "title")
	private String title;

	@ValueMapValue
	//@Default(values = "description")
	private String description;

	//Multifield Child Resource for States
	@ChildResource(name = "states")
	private List<Resource> resourceStates;

	private List<StateModel> states;

	@PostConstruct
	protected void init() {
		states = new ArrayList<StateModel>();

		if (resourceStates != null) {
			states = populatedModelState(states, resourceStates);
		}

	}

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

	public List<StateModel> getStates() {
		return states;
	}
	
}
