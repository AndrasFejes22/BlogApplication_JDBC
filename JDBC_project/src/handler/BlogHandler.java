package handler;

import post.Post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.Scanner;

public class BlogHandler {

    public static void insert (Connection c) {
        String query = "insert into blogs.phonebook values (15, 'John Doe', '06701453477', current_timestamp, 'jo_oreg_john_doe')";
        try(Statement statement = c.createStatement()){

            statement.execute(query);

            System.out.println("Posts table recreated");
        }catch(SQLException e) {
            System.err.println("Error occured when executing SQL statement");
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            System.err.println("State: " + e.getSQLState());
            //System.err.println("State: " + e.getLocalizedMessage());
        }
    }

    public static void select (Connection c, String text) {
        try(Statement st = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            String query = "select * \r\n"
                    + "from blogs.phonebook\r\n"
                    + "where number like" + "'%" + text + "%'" + "or name like" + "'%" + text + "%'";
            //String query = "SELECT id, name, number, created, slug FROM blogs.phonebook WHERE id = 1";//
            //String query = "select * from blogs.phonebook where "number" like '%jakab%' or "name" like '%jakab%';";//működik
            ResultSet rs = st.executeQuery(query);


            if(rs.next()){

                System.out.println("The name or number you are looking for is based on the entry found:");
                rs.beforeFirst();

                while (rs.next()) {
                    Post p = new Post(); //ezeket pl be lehet pakolni egy listába és akkot list-et adunk vissza, és azon lehet dolgozni
                    //pl stream()-okkal (filter, stb)
                    p.setId(rs.getLong("id"));
                    p.setName(rs.getString("name"));
                    p.setNumber(rs.getString("number"));
                    p.setSlug(rs.getString("slug"));
                    //p.setCreated(ZonedDateTime.ofInstant(rs.getTimestamp("created").toInstant(), ZoneId.systemDefault()));
                    //ehhez a Post class toString-ben a "skipp nulls" be van most állítva, mert szar a formátuma

                    System.out.println(p);

                }
            } else {
                System.out.println("No such name or number in the list!");
            }

        }catch(SQLException e) {
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            System.err.println("State: " + e.getSQLState());
            e.printStackTrace();
        }
    }

    //LEKÉRDEZÉS, teljes lista
    public static void queryAll (Connection c) {
        try(Statement st = c.createStatement()){
            String query = "SELECT id, name, number, created, slug FROM blogs.phonebook ";//a végére teljes elérési útvonal kell!
            //String query = "SELECT id, name, number, created, slug FROM blogs.phonebook WHERE id = 1";//a végére teljes elérési útvonal kell!
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                Post p = new Post(); //ezeket pl be lehet pakolni egy listába és akkot list-et adunk vissza, és azon lehet dolgozni
                //pl stream()-okkal (filter, stb)
                p.setId(rs.getLong("id"));
                p.setName(rs.getString("name"));
                p.setNumber(rs.getString("number"));
                p.setSlug(rs.getString("slug"));
                //p.setCreated(ZonedDateTime.ofInstant(rs.getTimestamp("created").toInstant(), ZoneId.systemDefault()));
                //ehhez a Post class toRtringjében a "skipp nulls" be van most állítva, mert szar a formátuma

                System.out.println(p);
            }

        }catch(SQLException e) {
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            System.err.println("State: " + e.getSQLState());
            e.printStackTrace();
        }
    }

    private static Post readPostData(Scanner scanner) {
        try(scanner){
            System.out.println("id: ");
            Long id = scanner.nextLong();

            System.out.println("name: ");
            String name = scanner.nextLine();

            System.out.println("number: ");
            String number = scanner.nextLine();

            System.out.println("slug: ");
            String slug = scanner.nextLine();

            Post post = new Post(id, name, number, slug, ZonedDateTime.now());

            return post;
        }

    }
}
