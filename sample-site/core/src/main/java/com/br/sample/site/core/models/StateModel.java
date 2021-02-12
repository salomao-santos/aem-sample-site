package com.br.sample.site.core.models;

import org.apache.sling.api.resource.Resource;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class StateModel {

	@ValueMapValue(name = "jcr:name")
	private String name;

	@ValueMapValue(name = "initials")
	private String initials;

	@ValueMapValue(name = "icon")
	private String icon;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
