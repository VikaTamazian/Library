package lib.service;

import lib.dao.GenreDao;
import lib.model.Genre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreService {
    GenreDao genreDao = new GenreDao();
    List<Genre> genreList = new ArrayList<>();

    public boolean delete(Integer id) {
        try {
            Genre genre = new Genre();
            // genre.setGenre_id(id);
            return genreDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Genre> readAll() {
        try {
            genreList.addAll(genreDao.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genreList;
    }


    public Genre create(Genre genre) throws SQLException {

        return genreDao.create(genre);

    }
}
