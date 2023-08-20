import java.util.Arrays;
import java.util.Scanner;

public class SmartBanking {
    private static final Scanner SCANNER = new Scanner(System.in);

    final static String COLOR_BLUE_BOLD = "\033[34;1m";
    final static String COLOR_RED_BOLD = "\033[31;1m";
    final static String COLOR_GREEN_BOLD = "\033[33;1m";
    final static String RESET = "\033[0m";
    final static String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
    final static String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);
    static String[][] bankAccount = new String[0][];

    public static void main(String[] args) {
        final String CLEAR = "\033[H\033[2J";
        final String DASHBOARD = "\u2795 Welcome to Smart Banking System";
        final String NEW_ACC = "\u2795  Open New Account";
        final String DEPOSIT_MONEY = "\u2795 Deposit Money";
        final String WITHDRAW_MONEY = "\u2795 Withdraw Money";
        final String TRANSFER = "\u1F91D Transfer Money";
        final String CHECK_BALANCE = "🖨 Check Balance";
        final String DELETE_AC = "\u274C Delete Account";

        // Arrays

        String[][] account = new String[0][];

        String screen = DASHBOARD;

        do {
            System.out.println(CLEAR);
            final String APP_TITLE = String.format("%s%s%s",
                    COLOR_BLUE_BOLD, screen, RESET);

            System.out.println("\t" + APP_TITLE + "\n");

            switch (screen) {
                case DASHBOARD:

                    System.out.println("\t[1]. Open New Account");
                    System.out.println("\t[2]. Deposit Money");
                    System.out.println("\t[3]. Withdraw Money");
                    System.out.println("\t[4]. Transfer");
                    System.out.println("\t[5]. Check A/C Balance");
                    System.out.println("\t[6]. Delete Account");
                    System.out.println("\t[7]. Exit\n");
                    System.out.print("\tEnter an option to continue: ");
                    int option = SCANNER.nextInt();
                    SCANNER.nextLine();

                    switch (option) {
                        case 1:
                            screen = NEW_ACC;
                            break;
                        case 2:
                            screen = DEPOSIT_MONEY;
                            break;
                        case 3:
                            screen = WITHDRAW_MONEY;
                            break;
                        case 4:
                            screen = TRANSFER;
                            break;
                        case 5:
                            screen = CHECK_BALANCE;
                            break;
                        case 6:
                            screen = DELETE_AC;
                            break;
                        case 7:
                            System.out.println(CLEAR);
                            System.exit(0);
                        default:

                            continue;

                    }

                    break;
                // Create New Account
                case NEW_ACC:
                    String name;
                    double deposit = 0.0;
                    System.out.printf("\tNew Student ID: SDB-%05d \n", (account.length + 1));
                    String id = String.format("SDB-%05d", bankAccount.length + 1);

                    name = nameValidation();
                    deposit = depositValidation();
                    valueInsertToArray(id, name, deposit);
               
                    System.out.println();
                    System.out.printf(SUCCESS_MSG,
                    String.format("%s:%s has been saved successfully", id, name));
                    System.out.print("\tDo you want to continue adding (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))
                        continue;
                    screen = DASHBOARD;
                    break;
                    

                case DEPOSIT_MONEY:

            }

        } while (true);
    }

    // Name Validation Method
    public static String nameValidation() {
        String name;
        boolean valid = true;
        do {
            System.out.print("\tName: ");
            name = SCANNER.nextLine().strip();
            valid = true;
            if (name.isBlank()) {
                System.out.printf("\t%sName can't be empty%s\n", COLOR_RED_BOLD, RESET);
                valid = false;
                continue;
            }
            for (int i = 0; i < name.length(); i++) {
                if (!(Character.isLetter(name.charAt(i)) ||
                        Character.isSpaceChar(name.charAt(i)))) {
                    System.out.printf("\t%sInvalid Name%s\n", COLOR_RED_BOLD, RESET);
                    valid = false;
                    break;
                }
            }
        } while (!valid);
        return name;
    }

    // Deposit Validation
    public static double depositValidation() {
        boolean valid;
        double deposit;
        do {
            valid = true;
            System.out.print("\tEnter Initial Deposit: ");
            deposit = SCANNER.nextDouble();
            SCANNER.nextLine();

            if (deposit < 5000) {
                System.out.printf("\t%sInitial deposit should be more than 5000%s\n", COLOR_RED_BOLD,
                        RESET);
                valid = false;
                continue;

            }

        } while (!valid);
        return deposit;

    }

    // Insert Data into BankAccount Array

    public static void valueInsertToArray(String id, String name, double deposit) {
        String[][] tempAccounts = new String[bankAccount.length + 1][3];
        String depositString = String.valueOf(deposit);
        for (int i = 0; i < bankAccount.length; i++) {
            tempAccounts[i] = bankAccount[i];

        }

        tempAccounts[tempAccounts.length - 1][0] = id;
        tempAccounts[tempAccounts.length - 1][1] = name;
        tempAccounts[tempAccounts.length - 1][2] = depositString;

        bankAccount = tempAccounts;
    }

}
