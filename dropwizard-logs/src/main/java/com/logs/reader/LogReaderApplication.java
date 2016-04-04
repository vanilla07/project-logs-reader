package com.logs.reader;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.logs.reader.core.LogData;
import com.logs.reader.db.LogDataDAO;
import com.logs.reader.resources.GenerateResource;
import com.logs.reader.resources.LogsResource;
import com.logs.reader.resources.LogDataResource;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class LogReaderApplication extends Application<LogReaderConfiguration> {
    
	public static void main(String[] args) throws Exception {
        new LogReaderApplication().run(args);
    }

    private final HibernateBundle<LogReaderConfiguration> hibernateBundle =
            new HibernateBundle<LogReaderConfiguration>(LogData.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(LogReaderConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "Logs Reader";
    }

    @Override
    public void initialize(Bootstrap<LogReaderConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addBundle(new MigrationsBundle<LogReaderConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(LogReaderConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(LogReaderConfiguration configuration, Environment environment) {
        final LogDataDAO dao = new LogDataDAO(hibernateBundle.getSessionFactory());

        environment.jersey().register(new LogsResource(dao));
        environment.jersey().register(new LogDataResource(dao));
        environment.jersey().register(new GenerateResource(dao));
        
        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
            environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
