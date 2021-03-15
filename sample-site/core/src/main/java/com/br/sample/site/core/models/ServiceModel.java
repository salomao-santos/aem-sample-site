package com.br.sample.site.core.models;

import lombok.Getter;
import lombok.Setter;

public class ServiceModel {

	@Getter
    @Setter
    int statusCode;

    @Getter
    @Setter
    String json;

    public ServiceModel(int statusCode, String json) {
        this.statusCode = statusCode;
        this.json = json;
    }

    @Override
    public String toString() {
        return "ServiceModel {" +
                "statusCode = " + statusCode +
                ", json = '" + json + '\'' +
                '}';
    }
	
}
