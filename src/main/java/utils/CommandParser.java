package utils;
import commands.*;
import menu.Menu;
import report.ReportWriter;
import reservation.ReservationList;
import stock.Stock;

import java.io.IOException;

public class CommandParser {
    public static void parseCommand(String command,Menu menu, Stock stock, ReservationList reservations) {
        if (command.equals("bye")){
            try {
                new ReportWriter(stock,reservations,menu).writeToFile();
            } catch (IOException e) {
                System.out.println("Error writing to file");
            }
            new QuitCommand().execute();
        }
        String[] commands = command.split(";",2);
        String[] split_commands = commands[0].split(" ",2);
        if (split_commands[0].equals("add")) {
            if (split_commands[1].equals("dish")) {
                //Add dish
                menu.addDish(commands[1]);
                successfulCommand();
            } else if (split_commands[1].equals("stock")){
                //Add stock
               new AddStockCommand(commands[1]).execute(stock);
                successfulCommand();
            } else if (split_commands[1].equals("reservation")){
                //Add reservation
                new AddReservationCommand(commands[1]).execute(reservations);
                successfulCommand();
            } else {
                errorCommand();
            }
        } else if (split_commands[0].equals("delete")){
            if (split_commands[1].equals("dish")) {
                //delete dish
                menu.deleteDish(commands[1]);
                successfulCommand();
            } else if (split_commands[1].equals("stock")){
                //delete stock
                new DeleteStockCommand(commands[1]).execute(stock);
                successfulCommand();
            } else if (split_commands[1].equals("reservation")){
                //delete reservation
                new VoidReservationCommand(commands[1]).execute(reservations);
                successfulCommand();
            } else {
                errorCommand();
            }
        } else if (split_commands[0].equals("list")) {
            if (split_commands[1].equals("dish")) {
                //list dish
                menu.printDishes();
                successfulCommand();
            } else if (split_commands[1].equals("stock")){
                //list stock
                new ListStockCommand().execute(stock);
                successfulCommand();
            } else if (split_commands[1].equals("reservation")){
                //list reservation
                new ListReservationCommand().execute(reservations);
                successfulCommand();
            } else {
                errorCommand();
            }
        } else {
            errorCommand();
        }
    }
    public static void errorCommand() {
        System.out.println("Incorrect command");
    }
    public static void successfulCommand() { System.out.println("The command has been successfully executed.");}
}