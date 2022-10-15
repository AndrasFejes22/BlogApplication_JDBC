package handler;

import post.Person;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.Scanner;

public class BlogHandler {


    //INSERT
    public static void insertPerson (Connection c, Person person) {
        String insertPerson = "INSERT INTO blogs.phonebook  (id, name, number, created, slug)"
                + "VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement statement = c.prepareStatement(insertPerson)){

            statement.setLong(1, person.getId());
            statement.setString(2, person.getName());
            statement.setString(3, person.getNumber());
            statement.setTimestamp(4, Timestamp.from(person.getCreated().toInstant()));
            statement.setString(5, person.getSlug());

            int changedRows = statement.executeUpdate();
            System.out.println("Modified rows: " + changedRows);

            System.out.println("Posts table recreated");
        }catch(SQLException e) {
            System.err.println("Error occurred when executing SQL statement");
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            System.err.println("State: " + e.getSQLState());
            //System.err.println("State: " + e.getLocalizedMessage());
        }
    }

    //UPDATE
    public static void updatePerson (Connection c, Person person) {
        String insertPerson = "UPDATE blogs.phonebook SET name =?, number =?, slug =? WHERE id =?)"
                + "VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement statement = c.prepareStatement(insertPerson)){

            statement.setString(1, person.getName());
            statement.setString(2, person.getNumber());
            statement.setString(3, person.getSlug());
            statement.setLong(4, person.getId());

            int changedRows = statement.executeUpdate();
            System.out.println("Changed rows: " + changedRows);

            System.out.println("Posts table recreated");
        }catch(SQLException e) {
            System.err.println("Error occurred when executing SQL statement");
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            System.err.println("State: " + e.getSQLState());
            //System.err.println("State: " + e.getLocalizedMessage());
        }
    }

    //SELECT
    //Sql injection?
    public static void select (Connection c, String text) {
        //try(Statement st = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
        try{
            String query = "select * \r\n"
                    + "from blogs.phonebook\r\n"
                    + "where number like" + "'%" + text + "%'" + "or name like" + "'%" + text + "%'";
            //String query = "SELECT id, name, number, created, slug FROM blogs.phonebook WHERE id = 1";//
            //String query = "select * from blogs.phonebook where "number" like '%jakab%' or "name" like '%jakab%';";//működik
            String query2 = "select * \r\n"
                    + "from blogs.phonebook\r\n"
                    + "where number LIKE ? or name LIKE ?";

            PreparedStatement preparedStatement = c.prepareStatement(query2);
            preparedStatement.setString(1, "%" + text + "%");
            preparedStatement.setString(2, "%" + text + "%");
            //ResultSet rs = st.executeQuery(query);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){

                System.out.println("The name or number you are looking for is based on the entry found:");
                rs.beforeFirst();

                while (rs.next()) {
                    Person p = new Person(); //ezeket pl be lehet pakolni egy listába és akkot list-et adunk vissza, és azon lehet dolgozni
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

    //QUERY ALL
    public static void queryAll (Connection c) {
        try(Statement st = c.createStatement()){
            String query = "SELECT id, name, number, created, slug FROM blogs.phonebook ";//a végére teljes elérési útvonal kell!
            //String query = "SELECT id, name, number, created, slug FROM blogs.phonebook WHERE id = 1";//a végére teljes elérési útvonal kell!
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                Person p = new Person(); //ezeket pl be lehet pakolni egy listába és akkot list-et adunk vissza, és azon lehet dolgozni
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

    public static void update(Connection c, Person person){
        //String updatePost = "UPDATE post SET "
    }



    public static Person readPersonData(Scanner scanner2) {
        //try(Scanner scanner2 = new Scanner(System.in)){
        Person person = new Person();
        try{

            System.out.print("name: ");
            String name = scanner2.nextLine();

            System.out.print("number: ");
            String number = scanner2.nextLine();

            System.out.print("slug: ");
            String slug = scanner2.nextLine();

            System.out.print("id: ");
            Long id = scanner2.nextLong();

             person = new Person(id, name, number, slug, ZonedDateTime.now());


        }catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return person;
    }
}
