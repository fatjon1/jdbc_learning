import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDAOImpl implements MovieDAO {
    private final Connection connection;

    public MovieDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void createTable() throws DatabaseActionException {
        try (Statement createTableStmnt = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS MOVIES (id INTEGER AUTO_INCREMENT, " +
                    "title VARCHAR(255), " +
                    "genre VARCHAR(255), " +
                    "yearOfRelease INTEGER, " +
                    "PRIMARY KEY (id))";

            createTableStmnt.execute(createTableQuery);
        } catch (SQLException e) {
            throw new DatabaseActionException("Error ne krijimin e tabeles!");
        }
    }

    @Override
    public void deleteTable() throws DatabaseActionException {
        try {
            Statement deleteTable = connection.createStatement();
            String deleteTableQuery = "DROP TABLE IF EXISTS MOVIES";
            deleteTable.execute(deleteTableQuery);
            System.out.println("tabela u fshi");
        } catch (SQLException e) {
            throw new DatabaseActionException("Error ne fshirjen e tabeles!");
        }
    }

    @Override
    public void createMovie(Movie movie) throws DatabaseActionException {
        try (PreparedStatement insertStmnt = connection.prepareStatement("INSERT INTO MOVIES (title, genre, yearOfRelease) VALUES (?, ?, ?)")) {
            insertStmnt.setString(1, movie.getTitle());
            insertStmnt.setString(2, movie.getGenre());
            insertStmnt.setInt(3, movie.getYearOfRelease());
            insertStmnt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseActionException("Error ne krijimin e filmit!");
        }
    }

    @Override
    public void deleteMovie(int id) throws DatabaseActionException {
        try (PreparedStatement deleteStmnt = connection.prepareStatement("DELETE FROM movies WHERE id = ?")) {
            deleteStmnt.setInt(1, id);
            deleteStmnt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseActionException("Error ne fshirjen e filmit!");
        }
    }

    @Override
    public void updateMoviesTitle(int id, String newTitle) throws DatabaseActionException {
        try (PreparedStatement updateStmnt = connection.prepareStatement("UPDATE movies SET title = ? WHERE id = ?")) {
            updateStmnt.setString(1, newTitle);
            updateStmnt.setInt(2, id);
            updateStmnt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseActionException("Error ne update te filmit");
        }
    }

    @Override
    public Optional<Movie> findMovieById(int id) throws DatabaseActionException {
        try (PreparedStatement searchStmnt = connection.prepareStatement("SELECT * FROM movies WHERE id = ?")) {
            searchStmnt.setInt(1, id); //SELECT * FROM movies WHERE id = id
            boolean searchResult = searchStmnt.execute();
            if(searchResult) {
                ResultSet foundMovie = searchStmnt.getResultSet();
                if(foundMovie.next()) {
                    String title = foundMovie.getString("title");
                    String genre = foundMovie.getString(3);
                    Integer yearOfRelease = foundMovie.getInt(4);
                    Movie movie = new Movie((long) id, title, genre, yearOfRelease);
                    return Optional.of(movie);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseActionException("Error ne gjetjen e filmit!");
        }
    }
    @Override
    public List<Movie> findAll() throws DatabaseActionException {
        try (Statement findAllStatement = connection.createStatement()) {
            String findAllQuery = "SELECT * FROM movies";
            ResultSet moviesResultSet = findAllStatement.executeQuery(findAllQuery);
            List<Movie> movies = new ArrayList<>();
            while(moviesResultSet.next()) {
                Integer id = moviesResultSet.getInt(1);
                String title = moviesResultSet.getString(2);
                String genre = moviesResultSet.getString(3);
                Integer yearOfRelease = moviesResultSet.getInt(4);
                Movie movie = new Movie(id.longValue(), title, genre, yearOfRelease);
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            throw new DatabaseActionException("Error ne marrjen e filmave!");
        }
    }

}
