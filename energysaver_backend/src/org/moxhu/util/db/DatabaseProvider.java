package org.moxhu.util.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.moxhu.MoxhuObject;
import org.moxhu.web.app.ContextApplication;
import org.moxhu.exception.GeneralException;

/**
 * Encapsulates database configuration via 
 * JNDI. JDBC is not supported.
 * 
 * 
 * <pre>
 * # Specify database configuration type of 'jndi' or 'jdbc' or 'connection_manager'
 * alias.db.configurationType=jndi
 * 
 * # For database configuration type 'jndi',this will be used as default.
 * </pre>
 * 
 * For JNDI connections the WebContainer must be defined: example: WEBLOGIC
 * @see ContextApplication#getWebAppContainer()
 * 
 */
public class DatabaseProvider implements MoxhuObject {

	/**
	 * holds the logger object for the general log file
	 */
	

	private static DatabaseProvider DBP = null;

	public enum ConfigurationType {
		JNDI, JDBC;
	}

	private DatabaseProvider() {

	}

	public static  DatabaseProvider getInstance() {
		if (DBP == null)
			synchronized(DatabaseProvider.class){
				if(DBP ==null)
					DBP = new DatabaseProvider();
			}
		return DBP;
	}

	public Connection getConnection(String dbAlias, ConfigurationType type) throws SQLException, GeneralException {
		return openConnection(dbAlias, type);
	}

	/**
	 * Get database connection from data-source or driver manager, depending on
	 * which is configured.
	 */
	private Connection openConnection(String dbAlias, ConfigurationType type)
	        throws SQLException, GeneralException {

		if (type == null) {
			type = getDefaultConfigurationType();
		}

		if (type == ConfigurationType.JDBC) {
			throw new GeneralException("JDBC Cofiguration Type is NOT supported");
		} else if (type == ConfigurationType.JNDI) {

			InitialContext cxt;
			Connection conn = null;
			try {
				cxt = new InitialContext();

				if (cxt == null) {

					LOGGER.error("[] ERROR DB: No context ");
					LOGGER.error("[] Error while trying to conenct to Database");

					throw new GeneralException("No context");
				}
				if (ContextApplication.getInstance().getWebAppContainer() == ContextApplication.WebAppContainer.TOMCAT) {

					javax.naming.Context envcxt = null;

					envcxt = (javax.naming.Context) cxt
					        .lookup("java:/comp/env");

					if (envcxt == null) {

						LOGGER.error("[] ERROR DB: No context ");
						LOGGER.error("[] Error while trying to conenct to Database");
						throw new GeneralException("No context");

					} else {

						DataSource ds = null;

						ds = (DataSource) envcxt.lookup(dbAlias);

						if (ds == null) {

							LOGGER
							        .error("[] ERROR DB: Data source not found!");
							LOGGER.error("[] Error while trying to conenct to Database");
							throw new GeneralException("Data source not found!");

						}

						conn = ds.getConnection();

					}

				} else if (ContextApplication.getInstance().getWebAppContainer() == ContextApplication.WebAppContainer.WEBLOGIC) {

					DataSource ds = (DataSource) cxt.lookup(dbAlias);

					if (ds == null) {

						LOGGER.error("[] ERROR DB: Data source not found!");
						LOGGER.error("[] Error while trying to conenct to Database");
						throw new GeneralException("Data source not found!");

					}

					conn = ds.getConnection();

				} else {
					
					throw new GeneralException(
					        "JNDI Cofiguration Type is only supported for TOMCAT and WEBLOGIC");
				}

			} catch (NamingException e) {
				LOGGER.error("DB ERROR", e);
				LOGGER.error("[] Error while trying to conenct to Database");
				throw new GeneralException(
				        "cannot locate JNDI DataSource ["
				                + dbAlias
				                + "]. "
				                + "Likely problem: no DataSource or datasource is misconfigured.");
			}

			return conn;
		} else {
			throw new GeneralException("No ConfigurationType");
		}

	}

	/**
	 * Closes a connection to a database using the Speedy ConnectionManager
	 * 
	 * @param The
	 *            connection to be closed
	 * @throws SQLException
	 * @throws SPEException
	 */
	public void returnConnection(Connection conn, ConfigurationType type)
	        throws SQLException, GeneralException {
		closeConnection(conn, type);
	}

	private void closeConnection(Connection conn, ConfigurationType type)
	        throws SQLException, GeneralException {
		if (type == ConfigurationType.JNDI) {
			conn.close();
		} else if (type == ConfigurationType.JDBC) {
			throw new GeneralException("JDBC Cofiguration Type is NOT supported");
		} else {
			throw new GeneralException("NO ConfigurationType");
		}
	}

	public ConfigurationType getConfigurationType(String confString) {
		if (confString == null) {
			return getDefaultConfigurationType();
		} else if (confString.equalsIgnoreCase(ConfigurationType.JDBC
		        .toString())) {
			return ConfigurationType.JDBC;
		} else if (confString.equalsIgnoreCase(ConfigurationType.JNDI
		        .toString())) {
			return ConfigurationType.JNDI;
		} else {
			return ConfigurationType.JNDI;
		}
	}

	public ConfigurationType getDefaultConfigurationType() {
		return ConfigurationType.JNDI;
	}

}