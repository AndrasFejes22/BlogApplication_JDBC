package main;

import connection.ConnectionFactory;
import handler.BlogHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("Hello JDBC");
        ConnectionFactory connectionFactory = createConnectionFactory();
        //ITT vannak a menűpontok, ezt kell menűsíteni
        try(Scanner scanner = new Scanner(System.in)){
            new Main().run(scanner);
        }
    }

    private void run(Scanner scanner) throws SQLException, IOException {
        int menuItem;
        try (scanner) {
            ConnectionFactory connectionFactory = createConnectionFactory();
            do {
                printingMenu();
                System.out.println();
                menuItem = readInt("Please give a whole number!", scanner);
                switch (menuItem) {
                    case 1:
                        try(Connection connection = connectionFactory.getConnection()){

                            BlogHandler.queryAll(connection);

                        }catch(SQLException e) {
                            System.err.println("Error code: " + e.getErrorCode());
                            System.err.println("Message: " + e.getMessage());
                            System.err.println("State: " + e.getSQLState());
                            e.printStackTrace();
                        }
                        System.out.println();
                        break;

                }

            } while (menuItem != 0);
        }

    }

    private static ConnectionFactory createConnectionFactory() throws IOException {
        Properties dbProperties = new Properties ();
        dbProperties.load(Main.class.getResourceAsStream("/database.properties"));

        ConnectionFactory connectionFactory = new ConnectionFactory(dbProperties);
        return connectionFactory;
    }

    static int readInt(String askMessage, Scanner scanner) {
        boolean inputCorrect;
        int number = 0;
        do {
            inputCorrect = true;
            System.out.println(askMessage);
            try {
                number = scanner.nextInt();
                if(number > 6 || number < 0){
                    System.out.println("You can only enter a number between 0 and 5!");
                    inputCorrect = false;
                }

            } catch (InputMismatchException e) {
                System.out.println("This is not a valid integer!");
                inputCorrect = false;
            } finally {
                scanner.nextLine();
            }
        } while (!inputCorrect);
        return number;
    }

    public static void printingMenu() {
        System.out.println("Please choose from the following menu items:");
        System.out.println("--------------------------------------------");
        System.out.println("1. QueryAll");
        System.out.println("2. Insert");
        System.out.println("3. Update");
        System.out.println("4. Delete");
        System.out.println( "\u001b[1;32m" + "NEW!" + "\u001b[0m");
        System.out.println("0. Exit");
    }
}
