package com.hubertstruminski.invoice.app;

import moe.tristan.easyfxml.FxApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class InvoiceAppApplication extends FxApplication {

    public static void main(String[] args) {
//        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        launch(args);
    }

    @Override
    protected SpringApplicationBuilder getSab() {
        return new SpringApplicationBuilder(getClass())
                .main(getClass())
                .headless(false)
                .web(WebApplicationType.SERVLET);
    }
}
