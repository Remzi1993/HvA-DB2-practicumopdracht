package nl.hva.ict.data.MySQL;

import nl.hva.ict.MainApplication;
import java.sql.*;
import java.util.Properties;

public class MySQL {
    protected Connection connection;
    private Properties properties;

    public MySQL() {
        connect();
    }

    // Connect database
    private void connect() {
        // Check of er al een verbinding is of als er al credentials aanwezig zijn in MainApplication
        if (connection != null || MainApplication.getMysqlHost().equals("") ||
                MainApplication.getMysqlUsername().equals("") || MainApplication.getMysqlPassword().equals("")) {
            return;
        }

        try {
            // Check of de MySQL driver is geïnstalleerd
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(MainApplication.getMysqlHost(), getProperties());
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Kan geen verbinding maken : " + e);
        }
    }

    /**
     * Met deze class worden de DB credentials opgehaald en in een properties object gezet.
     * @return properties object
     */
    private Properties getProperties() {
        // Als er geen properties object is gemaakt, maak hem dan aan
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", MainApplication.getMysqlUsername());
            properties.setProperty("password", MainApplication.getMysqlPassword());
        }
        return properties;
    }

    /**
     * Sluit MySQL verbinding
     */
    public void disconnect() {
        // Alleen als er een verbinding is. Anders sluit je een reeds gesloten verbinding en dat geeft gedoe.
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Methode om een PreparedStatement (met de ? in de query) te maken.
     * @param sql Je SQL code
     * @return Een resultset met je data
     * @throws SQLException Een error als het niet gaat.
     */
    public PreparedStatement getStatement(String sql) throws SQLException {
        if (this.connection == null) {
            connect();
        }
        return connection.prepareStatement(sql);
    }

    /**
     * Methode om een update PreparedStatement (met de ? in de query) te maken.
     * @param ps je SQL code
     * @throws SQLException Een error als het niet gaat.
     */
    public void executeUpdatePreparedStatement(PreparedStatement ps) throws SQLException {
        if (this.connection == null) {
            connect();
        }
        ps.executeUpdate();
    }

    /**
     * Methode om een select PreparedStatement (met de ? in de query) te maken.
     * @param ps je SQL code
     * @throws SQLException Een error als het niet gaat.
     */
    public ResultSet executeSelectPreparedStatement(PreparedStatement ps) throws SQLException {
        if (this.connection == null) {
            connect();
        }
        return ps.executeQuery();
    }
}