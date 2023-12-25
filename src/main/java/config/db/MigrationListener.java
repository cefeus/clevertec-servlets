package config.db;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.val;
import util.ReadProperties;

@WebListener
public class MigrationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (ReadProperties.getPropertyByKey("USE_MIGRATION").equalsIgnoreCase("true")) {
            val flywayConfig = new FlywayConfig();
            flywayConfig.flywayTemplate().migrate();
        }
    }
}
