package zad1;

import java.sql.*;

class Database {

    private final String dbUrl;
    private final TravelData travelData;

    Database(String dbUrl, TravelData travelData) {
        this.travelData = travelData;
        this.dbUrl = dbUrl;
    }

    public void insertRecordIntoDB(Record record) {
        String username = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading JDBC driver: " + e.getMessage());
            return;
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return;
        }

        String sql = "INSERT INTO records (country_code, country_name, start_date, end_date, location, price, currency) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, record.getCountryCode().toLanguageTag());
            statement.setString(2, record.getCountryName());
            statement.setDate(3, new java.sql.Date(record.getStartDate().getTime()));
            statement.setDate(4, new java.sql.Date(record.getEndDate().getTime()));
            statement.setString(5, record.getLocation());
            statement.setDouble(6, record.getPrice());
            statement.setString(7, record.getCurrency());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting record into database: " + e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }


    public void createInMemoryDB() {
        String username = "inmemory_user";
        String password = "inmemory_password";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading JDBC driver: " + e.getMessage());
            return;
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return;
        }

        try {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE records (country_code VARCHAR(5), country_name VARCHAR(255), start_date DATE, end_date DATE, location VARCHAR(255), price DOUBLE, currency VARCHAR(3))";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    void create() {
        createInMemoryDB();
        for (Record r : travelData.getData()) {
            insertRecordIntoDB(r);
        }
    }

    void showGui() {
        new MainWindow(travelData.getData(), travelData.getDictionary());
    }
}
