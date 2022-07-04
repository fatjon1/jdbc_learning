import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, DatabaseActionException {
        Connection con = ConnectionToDB.connectToDB();

        MovieDAO movieDAOImpl = new MovieDAOImpl(con);
       movieDAOImpl.createTable();
        // movieDAOImpl.deleteTable();
        // movieDAOImpl.createMovie(new Movie(null,"Padrino", "Classic", 1989));
        // movieDAOImpl.deleteMovie(3);
        // movieDAOImpl.updateMoviesTitle(1,"testTitle");
        // System.out.println(movieDAOImpl.findMovieById(1).get());
        movieDAOImpl.findAll().forEach(System.out::println);


    }
}
