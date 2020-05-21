package com.incra.ratpack.modules;

import com.google.inject.Provider;
import com.incra.ratpack.config.DatabaseConfig;
import com.incra.ratpack.database.DBConfig;
import com.incra.ratpack.database.DBException;
import com.incra.ratpack.database.DBService;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jeff Risberg
 * @since 05/04/17
 */
public class DBServiceProvider implements Provider<DBService> {
  private static final Logger LOGGER = LoggerFactory.getLogger(DBServiceProvider.class);

  private DatabaseConfig databaseConfig;

  public DBServiceProvider(DatabaseConfig databaseConfig) {
    this.databaseConfig = databaseConfig;
  }

  public DBService get() {
    String persistanceUnitName = databaseConfig.getPersistanceUnitName();

    DBConfig config = new DBConfig();

    String server = databaseConfig.getServer();
    Integer portNumber = databaseConfig.getPortNumber();
    String databaseName = databaseConfig.getDatabaseName();
    String url =
        String.format(
            "jdbc:mysql://%s:%d/%s?allowMultiQueries=true&characterEncoding=utf8",
            server, portNumber, databaseName);

    LOGGER.debug("Setting up database at " + url);
    config.setDriverClassName("com.mysql.jdbc.Driver");
    config.setJdbcUrl(url);
    config.setUsername(databaseConfig.getUsername());
    config.setPassword(databaseConfig.getPassword());

    HikariDataSource dataSource = new HikariDataSource(config);

    try {
      DBService dbService = new DBService(dataSource, persistanceUnitName);
      return dbService;
    } catch (DBException e) {
      e.printStackTrace();
      return null;
    }
  }
}
