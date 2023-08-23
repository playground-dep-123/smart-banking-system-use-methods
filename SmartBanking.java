import java.util.Arrays;
import java.util.Scanner;

public class SmartBanking {
    private static final Scanner SCANNER = new Scanner(System.in);

    static int deleteCount = 0;
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
        final String TRANSFER = "\u2795 Transfer Money";
        final String CHECK_BALANCE = "🖨 Check Balance";
        final String DELETE_AC = "\u274C Delete Account";

        // Arrays

        String screen = DASHBOARD;

        do {
            System.out.println(CLEAR);
            final String APP_TITLE = String.format("%s%s%s",
                    COLOR_BLUE_BOLD, screen, RESET);

            System.out.println("\t" + APP_TITLE + "\n");
            System.out.println(Arrays.deepToString(bankAccount));

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
                    System.out.printf("\tNew Student ID: SDB-%05d \n", (bankAccount.length + 1 + deleteCount));
                    String id = String.format("SDB-%05d", bankAccount.length + 1 + deleteCount);

                    name = nameValidation();
                    deposit = depositWithdrawValidation(1);
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
                    id = accountValidtion();
                    System.out.printf("\tCurrent Balance:%s\n", balance(id));
                    double depositAmount = depositWithdrawValidation(2);
                    depositAndWithdraw(depositAmount, "deposit", id);
                    System.out.printf("\tNew Balance:%s\n", balance(id));

                    System.out.print("\tDo you want to continue adding (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))
                        continue;

                    screen = DASHBOARD;
                    break;

                case WITHDRAW_MONEY:

                    id = accountValidtion();
                    System.out.printf("\tCurrent Balance:%s\n", balance(id));
                    double withdrawAmount = depositWithdrawValidation(3);
                    depositAndWithdraw(withdrawAmount, "withdraw", id);

                    /* */
                    System.out.printf("\tNew Balance:%s\n", balance(id));

                    System.out.print("\tDo you want to continue adding (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))
                        continue;

                    screen = DASHBOARD;
                    break;

                case TRANSFER:
                    String from_Id = accountValidtion();
                    System.out.printf("\tFrom A/C Name: %s\n", nameSearch(from_Id));
                    String to_Id = accountValidtion();
                    System.out.printf("\tTo A/C Name: %s\n", nameSearch(to_Id));
                    System.out.printf("\tFrom A/C Balance: %s\n", balance(from_Id));
                    System.out.printf("\tTo A/C Balance: %s\n", balance(to_Id));
                    System.out.print("\tEnter Amount:");
                    double amount = depositWithdrawValidation(4);
                    depositAndWithdraw(amount, "transfer", from_Id);
                    transferProcss(from_Id, to_Id, amount);
                    System.out.printf("\tNew From A/C Balance:%s\n", balance(from_Id));
                    System.out.printf("\tNew To A/C Balance:%s\n", balance(to_Id));
                    System.out.print("\tDo you want to continue adding (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))
                        continue;

                    screen = DASHBOARD;
                    break;

                case CHECK_BALANCE:
                    id = accountValidtion();
                    System.out.printf("\tName: %s\n", nameSearch(id));
                    System.out.printf("\tCurrent A/C Balance: %s\n", balance(id));
                    System.out.printf("\tAvailable Balance Withdraw: %s\n", withDrawAmount(balance(id)));
                    System.out.print("\tDo you want to continue adding (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))
                        continue;

                    screen = DASHBOARD;
                    break;

                case DELETE_AC:
                    id = accountValidtion();
                    name = nameSearch(id);
                    System.out.printf("\tName: %s\n", nameSearch(id));
                    System.out.printf("\tCurrent A/C Balance: %s\n", balance(id));
                    System.out.print("\tAre You Sure to Delete (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("N")) {

                        screen = DASHBOARD;
                        break;
                    } else {

                        deleteAc(id);
                        System.out.printf(SUCCESS_MSG,
                                String.format("%s:%s has been Deleted successfully", id, name));

                    }
                    System.out.print("\tDo you want to continue Deleting (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))
                        continue;

                    screen = DASHBOARD;
                    break;

            }

        } while (true);

    }

    private static void deleteAc(String id) {
        int index = -1;
        String[][] temp = new String[bankAccount.length - 1][];
        for (int i = 0; i < bankAccount.length; i++) {
            if (id.equals(bankAccount[i][0])) {
                index = i;
                break;
            }
        }
        for (int i = 0, k = 0; i < bankAccount.length; i++, k++) {

            if (i == index) {
                k--;
                continue;
            }
            temp[k] = bankAccount[i];
        }
        bankAccount = temp;
        deleteCount++;
    }

    private static double withDrawAmount(String balance) {
        double amount = Double.valueOf(balance);
        amount -= 500;
        if (amount < 500)
            return 0;
        return amount;
    }

    private static void transferProcss(String fromId, String toId, double amount) {

        double amountWithFee = amount + (amount / 100 * 2);
        for (int i = 0; i < bankAccount.length; i++) {
            if (fromId.equals(bankAccount[i][0])) {
                double from_balance = Double.valueOf(bankAccount[i][2]);
                from_balance -= amountWithFee;
                bankAccount[i][2] = String.valueOf(from_balance);

            }
            if (toId.equals(bankAccount[i][0])) {
                double to_balance = Double.valueOf(bankAccount[i][2]);
                to_balance += amount;
                bankAccount[i][2] = String.valueOf(to_balance);

            }
        }

    }

    private static String nameSearch(String id) {

        String name = "";
        for (int i = 0; i < bankAccount.length; i++) {
            if (id.equals(bankAccount[i][0])) {
                name = bankAccount[i][1];
                break;
            }

        }
        return name;
    }

    private static void depositAndWithdraw(double amount, String type, String id) {

        int index = -1;
        double existBalance = 0.0;

        for (int i = 0; i < bankAccount.length; i++) {
            if (id.equals(bankAccount[i][0])) {
                index = i;
                existBalance = Double.valueOf(bankAccount[i][2]);
            }
        }
        loop: switch (type) {
            case "deposit":
                existBalance += amount;
                bankAccount[index][2] = String.valueOf(existBalance);
                break;

            case "withdraw":
                final double MIN_AMOUNT_VALUE = 500;

                existBalance -= MIN_AMOUNT_VALUE;
                if (existBalance >= MIN_AMOUNT_VALUE) {
                    existBalance += MIN_AMOUNT_VALUE;
                    existBalance -= amount;
                    bankAccount[index][2] = String.valueOf(existBalance);
                    break;

                } else {
                    System.out.printf("\t%sInsufficient Amount%s\n", COLOR_RED_BOLD,
                            RESET);
                    break loop;
                }
            case "transfer":
                existBalance -= 500;
                if (existBalance >= 500) {
                    existBalance += 500;
                    break;

                } else {
                    System.out.printf("\t%sInsufficient Amount%s\n", COLOR_RED_BOLD,
                            RESET);
                    break loop;
                }

        }
    }

    // Currrent Balance
    private static String balance(String id) {
        String value = "";
        for (int i = 0; i < bankAccount.length; i++) {

            if (id.equals(bankAccount[i][0])) {
                value = bankAccount[i][2];

            }

        }
        return value;

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
    public static double depositWithdrawValidation(int type) {

        final String INITIALAMOUNT = "\tEnter Initial Deposit:";
        final String DEPOSITAMOUNT = "\tDeposit Amount: ";
        final String WITHDRAWAMOUNT = "\tWithdraw Amount: ";
        boolean valid;
        double amount;
        loop: do {
            valid = true;
            if (type == 1)
                System.out.print(INITIALAMOUNT);
            else if (type == 2)
                System.out.print(DEPOSITAMOUNT);
            else if (type == 3)
                System.out.print(WITHDRAWAMOUNT);

            amount = SCANNER.nextDouble();
            SCANNER.nextLine();

            switch (type) {
                case 1:
                    if (amount < 5000) {
                        System.out.printf("\t%sInitial deposit should be more than 5000%s\n", COLOR_RED_BOLD,
                                RESET);
                        valid = false;
                        break;

                    }
                case 2:
                    if (amount < 500) {
                        System.out.printf("\t%sInsufficient Deposit Amount%s\n", COLOR_RED_BOLD,
                                RESET);
                        valid = false;
                        continue loop;

                    } else
                        break;
                case 3:
                    if (amount < 100) {
                        System.out.printf("\t%sInsufficient Withdraw Amount%s\n", COLOR_RED_BOLD,
                                RESET);
                        valid = false;
                        amount = 0;
                        continue loop;

                    } else
                        break;

                case 4:
                    if (amount < 100) {
                        System.out.printf("\t%sInsufficient Withdraw Amount%s\n", COLOR_RED_BOLD,
                                RESET);
                        valid = false;
                        amount = 0;
                        continue loop;
                    }

            }

        } while (!valid);
        return amount;

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

    public static String accountValidtion() {
        boolean valid = true;
        String id;
        String accountNumber = "";
        loop: do {
            valid = true;
            System.out.print("\tEnter A/C Number: ");
            id = SCANNER.nextLine().toUpperCase().strip();
            if (id.isBlank()) {
                System.out.printf(ERROR_MSG, "ID can't be empty");
                valid = false;
                continue;
            } else if (!id.startsWith("SDB-") || id.length() < 5) {
                System.out.printf(ERROR_MSG, "Invalid ID format");
                valid = false;
                continue;
            } else {
                String number = id.substring(4);
                for (int i = 0; i < number.length(); i++) {
                    if (!Character.isDigit(number.charAt(i))) {
                        System.out.printf(ERROR_MSG, "Invalid ID format");
                        valid = false;
                        continue;
                    }
                }
            }

            boolean val = false;
            for (int i = 0; i < bankAccount.length; i++) {

                if (id.equals(bankAccount[i][0])) {
                    accountNumber = id;
                    val = true;
                    break;
                }
                val = false;

            }
            if (val == false) {

                System.out.printf(ERROR_MSG, "Not Found");
                valid = false;
                continue loop;

            }
        } while (!valid);
        return id;

    }

}
