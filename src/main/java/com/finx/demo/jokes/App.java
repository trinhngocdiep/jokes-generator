package com.finx.demo.jokes;

import com.finx.demo.jokes.config.AppConfig;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class App extends Application<AppConfig> {
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "I say funny jokes";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addBundle(GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .build());
    }

    @Override
    public void run(AppConfig config, Environment environment) {
    }
}
