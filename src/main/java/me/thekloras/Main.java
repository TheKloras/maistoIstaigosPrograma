package me.thekloras;

import me.thekloras.DAO.MenuDAO;
import me.thekloras.DAO.UserDAO;
import me.thekloras.Entity.Menu;
import me.thekloras.Utils.HibernateUtil;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String loginOrReg;
        String logUsername;
        String logPassword;
        String regUsername;
        String regPassword;
        UserDAO userDAO = new UserDAO();
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("What you would like to do?:\n" +
                    "1. For login.\n" +
                    "2. For register.\n" +
                    "Please select a number for operation:");
            loginOrReg = scan.next();
            if (loginOrReg.contains("1")) {
                System.out.println("Enter your username:");
                logUsername = scan.next();
                System.out.println("Enter your password:");
                logPassword = scan.next();
                if (userDAO.login(logUsername, logPassword)) {
                    if (userDAO.getUserRole(logUsername).equals("user")){
                        userFunc();
                    }
                    else {
                        adminFunc();
                    }
                }
            } else if (loginOrReg.contains("2")) {
                System.out.println("Create your username:");
                regUsername = scan.next();
                System.out.println("Create your password:");
                regPassword = scan.next();
                userDAO.registration(regUsername, regPassword);
            }
            HibernateUtil.getSessionFactory().close();
            break;
        }
    }

    public static void adminFunc(){
        while (true) {
            MenuDAO menuDAO = new MenuDAO();
            Scanner scan = new Scanner(System.in);
            String chooseOpAdmin;
            String addDish;
            String descDish;
            int dishDelete;
            int dishUpdate;
            String updateDish;
            String updateDishDesc;
            int approveOrder;
            int resetOrderCount = 0;
            System.out.println("Welcome! Which operation you would like to execute?\n" +
                    "1. For adding a new dish.\n" +
                    "2. For deleting a dish.\n" +
                    "3. For editing a dish.\n" +
                    "4. For approving orders.\n" +
                    "5. For log out.\n" +
                    "Please enter the number of the option:");
            chooseOpAdmin = scan.next();
            if (chooseOpAdmin.equals("1")) {
                System.out.println("Name of the dish?:");
                scan.nextLine();
                addDish = scan.nextLine();
                System.out.println("Description of the dish?:");
                descDish = scan.nextLine();
                System.out.println("Adding the dish!");
                Menu newDish = new Menu(addDish, descDish);
                menuDAO.addDish(newDish);
            } else if (chooseOpAdmin.equals("2")) {
                menuDAO.allDishesUser();
                System.out.println("\nOrdered dishes are not currently shown!\n");
                System.out.println("Enter the ID of the dish to delete:");
                dishDelete = scan.nextInt();
                menuDAO.deleteDishByID(dishDelete);
            } else if (chooseOpAdmin.equals("3")) {
                menuDAO.allDishesUser();
                System.out.println("\nOrdered dishes are not currently shown!\n");
                System.out.println("Enter id of the dish to edit:\n");
                dishUpdate = scan.nextInt();
                System.out.println("Enter new name of the dish:\n");
                scan.nextLine();
                updateDish = scan.nextLine();
                System.out.println("Enter new description of the dish:\n");
                updateDishDesc = scan.nextLine();
                menuDAO.editDish(dishUpdate, updateDish, updateDishDesc);
                System.out.println("Dish updated!");
            }
            else if (chooseOpAdmin.contains("4")) {
                System.out.println("All orders:\n");
                menuDAO.allDishesAdmin();
                System.out.println("Write the id of the order you want to approve:\n");
                approveOrder = scan.nextInt();
                menuDAO.approveOrder(approveOrder, resetOrderCount);
                System.out.println("Order approved!");
            }
            else {
                System.out.println("Logging out!");
                break;
            }
        }
    }
    public static void userFunc() {
        while (true) {
            MenuDAO menuDAO = new MenuDAO();
            Scanner scan = new Scanner(System.in);
            int pickDish;
            int orderCount;
            String chooseOpUser;
            System.out.println("\nPlease select the operation by number:\n" +
                    "1. For searching for dishes.\n" +
                    "2. For log out.\n");
            chooseOpUser = scan.next();
            if (chooseOpUser.equals("1")) {
                menuDAO.allDishesUser();
                System.out.println("Write the id of the dish you want to order:");
                pickDish = scan.nextInt();
                System.out.println("How many orders are you going to make?:");
                orderCount = scan.nextInt();
                menuDAO.orderFood(pickDish, orderCount);
                System.out.println("Order complete!");
            }
            else {
                System.out.println("Logging out!");
                break;
            }
        }
    }
}