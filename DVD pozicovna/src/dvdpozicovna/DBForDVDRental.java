package dvdpozicovna;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBForDVDRental {
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/DVDpozicovna";
	private static final String JDBC_DRIVER = "org.postgresql.Driver";
	private static final String USER = "postgres";
	private static final String PWD = "imro";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PWD);
			stmt = conn.createStatement();

			String sql = "CREATE TYPE dvd.MovieCategory AS ENUM" + " ('Comedy', 'Action', 'Horror','Sci-Fi',"
					+ " 'Drama', 'Musical','Sport', 'Documentary', 'Adult')";
			stmt.executeUpdate(sql);

			sql = "CREATE TYPE dvd.PrizeCategory AS ENUM ('A', 'B', 'C')";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE dvd.User (id numeric(10) not null, " + "first_name varchar, " + "last_name varchar,"
					+ "address_street varchar, " + "address_city varchar, " + "address_zip varchar," + "email varchar,"
					+ "tel_number varchar, " + "primary key (id))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE dvd.Rental (id numeric(10) not null, " + "user_id numeric(10) REFERENCES dvd.User(id),"
					+ "date_from Date, " + "date_to Date," + "primary key (id))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE dvd.DVD (id numeric(10) not null, "
					+ "rental_id numeric(10) REFERENCES dvd.Rental(id), " + "name varchar," + "genre dvd.MovieCategory,"
					+ "prize_category dvd.PrizeCategory," + "count numeric(3)," + "rented_count numeric(3),"
					+ "primary key (id))";
			stmt.executeUpdate(sql);

		} catch (ClassNotFoundException e) {
			System.err.println("Trieda PostgreSQL Drivera nebola najdena.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Nastala chyba pri pripajani k databaze.");
			e.printStackTrace();
		}

		// uzatvorenie spojenia s DB
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("Pri zatvarani spojenia s DB prislo k chybe.");
			e.printStackTrace();
		}

		System.out.println("Program prebehol v poriadku.");
	}

}
