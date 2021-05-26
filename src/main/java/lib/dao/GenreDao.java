package lib.dao;

import lib.model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GenreDao extends AbstractDao<Genre> {
    Scanner scanner = new Scanner(System.in);

    public Genre addManually() throws SQLException {
        Genre genre = new Genre();
        System.out.println("Please, enter genre: ");
        genre.setGenre_name(scanner.next());
        String SELECT = "SELECT genre_name FROM my_library.genre WHERE genre_name = '" + genre.getGenre_name() + "'";

        try (PreparedStatement statement = getConnection().prepareStatement(SELECT)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("genre exists.");
            } else {
                String INSERT = "INSERT INTO my_library.genre (genre_name) VALUES(?)";
                PreparedStatement nextStatement = getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                nextStatement.setString(1, genre.getGenre_name());
                nextStatement.executeUpdate();
                ResultSet generatedKeys = nextStatement.getGeneratedKeys();
                while (generatedKeys.next()) {
                    genre.setGenre_id(generatedKeys.getInt(1));
                }
                System.out.println("genre added.");
            }
        }
        return genre;
    }


    @Override
    public Genre create(Genre genre) throws SQLException {
        if (checkUniqueGenre(genre)) {
            String INSERT = "INSERT INTO my_library.genre (genre_name) VALUES(?)";
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT)) {
                statement.setString(1, genre.getGenre_name());
                statement.executeUpdate();
                System.out.println("New genre added.");
            }
        } else {
            System.out.println("This genre has been already added before.");
        }
        return genre;
    }

    @Override
    public Genre readOne(Integer value) throws SQLException {
        String INSERT = "SELECT * FROM my_library.genre WHERE id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT)) {
            statement.setInt(1, value);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return fetchGenre(resultSet);
            } else {
                System.out.println("Have no record.");
                return null;
            }
        }

    }

    @Override
    public List<Genre> readAll() throws SQLException {
        String INSERT = "SELECT id, genre_name FROM my_library.genre";
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT)) {
            List<Genre> genreList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Genre genre = fetchGenre(resultSet);
                genreList.add(genre);
            }
            return genreList;
        }

    }

    @Override
    public boolean update(Integer genre) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Integer genre) throws SQLException {
        String DELETE = "DELETE FROM my_library.genre WHERE id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE)) {
            statement.setInt(1, genre);
            int row = statement.executeUpdate();
            if (row > 0) {
                System.out.println("Genre deleted");
                return true;

            } else {
                System.out.println("Operation failed, no such genre in library");
                return false;

            }
        }
    }

    private Genre fetchGenre(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setGenre_id(resultSet.getInt("id"));
        genre.setGenre_name(resultSet.getString("genre_name"));
        return genre;
    }

    private boolean checkUniqueGenre(Genre genre) throws SQLException {

        String INSERT = "SELECT genre_name FROM my_library.genre WHERE genre_name = '" + genre.getGenre_name() + "'";
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT);
        ) {
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next();
        }

    }
}
