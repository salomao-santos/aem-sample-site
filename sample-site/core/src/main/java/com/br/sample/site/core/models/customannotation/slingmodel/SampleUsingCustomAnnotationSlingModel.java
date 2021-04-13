package com.br.sample.site.core.models.customannotation.slingmodel;



import java.util.HashMap;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.br.sample.site.core.provider.Multifield;

import lombok.Getter;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SampleUsingCustomAnnotationSlingModel {

	// Beware of the @Required annotation, it can generate errors in the application, if the component is added to the page and is not used.
	// When using the @Required annotation, it is recommended to use @Default, to avoid errors.
	
	//Cuidado com a anotação @Required, ela pode gerar problemas na aplicação, caso o componente seja adicionado na página e não seja utilizado.
	//Ao utilizar a anotação @Required recomendo utilizar o @Default, para evitar erros.
	//@Required
	//@Default(values = "title")
	@Getter
	@ValueMapValue
	private String title;

	@Getter
	@ValueMapValue
	@Default(values = "description")
	private String description;

	@Getter
	@Multifield(name = "states", values = {"jrc:name", "initials", "flag"})
    private List<HashMap> states;

}
