package ECommerceDBP;


import javax.xml.crypto.Data;
import java.util.Scanner;
import java.sql.*;

public class Main {
    public static void main(String[] args) {

        Admin.loadAllData();
        String userInput;
        int choice;
        Scanner s1 = new Scanner(System.in);
        do {
            System.out.println("0 - Exit");
            System.out.println("1 - Add Seller");
            System.out.println("2 - Add Buyer");
            System.out.println("3 - Add product to seller");
            System.out.println("4 - Add product to buyer");
            System.out.println("5 - Pay For a Buyers Cart");
            System.out.println("6 - Show Buyers Details");
            System.out.println("7 - Show Sellers Details");
            System.out.println("8 - Choose Category to show products from");
            System.out.println("9 - Replace your current cart");
            System.out.print("Enter Choice : ");
            while (true) {
                try {
                    userInput = s1.nextLine();
                    choice = changeToInt(userInput);
                    break;
                } catch (WrongInput e) {
                    System.out.println(e.getMessage());
                }

            }
            switch (choice) {
                case 0:
                    System.out.println("End Program :)");
                    break;
                case 1:
                    Admin.AddSeller(s1);
                    break;
                case 2:
                    Admin.AddBuyer(s1);
                    break;
                case 3:
                    Admin.AddProductToSeller(s1);
                    break;
                case 4:
                    Admin.AddProductToBuyer(s1);
                    break;

                case 5:
                    Admin.PayForBuyersCart(s1);
                    break;

                case 6:
                    Admin.DisplayBuyers();
                    break;
                case 7:
                    Admin.DisplaySellers();
                    break;
                case 8:
                    Admin.displayProductsFromCategory(s1);
                    break;
                case 9:
                    Admin.chooseAnOldCartToAdd(s1);
                    break;
                default: {
                    System.out.println("invalid Choice , Enter a New one :");
                }


            }
        } while (choice != 0);

    }

    public static int changeToInt(String choice) throws WrongInput {
        try {
            return Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            throw new WrongInput("invalid Choice , Enter a New one :");
        }

    }

}