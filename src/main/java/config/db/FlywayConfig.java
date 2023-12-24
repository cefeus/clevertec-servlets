package config.db;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import util.ReadProperties;

import static util.constants.DatabaseConstants.DRIVER;
import static util.constants.DatabaseConstants.PASSWORD;
import static util.constants.DatabaseConstants.SCHEMA;
import static util.constants.DatabaseConstants.URL;
import static util.constants.DatabaseConstants.USERNAME;
import static util.constants.FlywayConstants.MIGRATION_LOCATION;

/**
 * Migration config class
 */
public class FlywayConfig {
    public Configuration config(){
        var config = new FluentConfiguration();
        config.dataSource(
                ReadProperties.getPropertyByKey(URL),
                ReadProperties.getPropertyByKey(USERNAME),
                ReadProperties.getPropertyByKey(PASSWORD)
        );
        config.driver(ReadProperties.getPropertyByKey(DRIVER));
        config.schemas(ReadProperties.getPropertyByKey(SCHEMA));
        config.locations(ReadProperties.getPropertyByKey(MIGRATION_LOCATION));

        return config;
    }

    public Flyway flywayTemplate(){
        return new Flyway(config());
    }

}
