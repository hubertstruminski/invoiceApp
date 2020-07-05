package com.hubertstruminski.invoice.app.config;

import com.hubertstruminski.invoice.app.controller.MainWindowController;
import com.hubertstruminski.invoice.app.view.ViewCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
//
//    @Bean
//    @Scope("prototype")
//    public ViewCreator createViewCreator() {
//        return new ViewCreator();
//    }

//    @Bean
//    @Scope("prototype")
//    public MainWindowController createMainWindowController() {
//        return new MainWindowController();
//    }
//    @Bean
//    public DataSource h2DataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .build();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws ClassNotFoundException {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setDataSource(h2DataSource());
//        emf.setPackagesToScan("com.hubertstruminski.invoice.app.model", "com.hubert.struminski.invoice.app.repository");
//        emf.setPersistenceXmlLocation(getClass().getResource("/META-INF/persistence.xml").toString());
////        emf.setJpaVendorAdapter(jpaAdapter());
////        Properties properties = new Properties();
////        properties.setProperty("javax.persistence.schema-generation.database.action", "create");
////        emf.setJpaProperties(jpaProterties());
//        return emf;
//    }
}
