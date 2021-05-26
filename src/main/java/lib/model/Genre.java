package lib.model;

import java.util.Objects;

public class Genre {
    private Integer genre_id;
    private String genre_name;

    public Genre() {

    }

    public Genre(Integer genre_id, String genre_name) {
        this.genre_id = genre_id;
        this.genre_name = genre_name;
    }

    public Integer getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(Integer genre_id) {
        this.genre_id = genre_id;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(genre_id, genre.genre_id) &&
                Objects.equals(genre_name, genre.genre_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genre_id, genre_name);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genre_id=" + genre_id +
                ", genre_name='" + genre_name + '\'' +
                '}';
    }
}
