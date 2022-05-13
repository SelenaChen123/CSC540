package src;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * User interface for the customer loyalty marketplace.
 */
public class MarketplaceUI {

    /** Console scanner for processing user input. */
    public static Scanner console;

    /** MarketplaceAP object that accesses the database. */
    MarketplaceAP ap;

    /**
     * Constructs a MarketplaceUI object by creating a console scanner,
     * MarketplaceUtils object, and a MarketplaceAP object.
     */
    public MarketplaceUI() {
        console = new Scanner(System.in);
        ap = new MarketplaceAP();
    }

    /**
     * Main menu that provides options for logging in, signing up, showing queries,
     * and exiting the program.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void homeMenu() throws SQLException {
        // TODO fix the spacing between all menus
        // TODO fix all javadoc
        System.out.println("\n1. Login");
        System.out.println("2. Sign Up");
        System.out.println("3. Show Queries");
        System.out.println("4. Exit");

        String input = console.nextLine().trim();

        if (input.isEmpty() || (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4"))) {
            System.out.println("\nInvalid Option");

            homeMenu();
        }

        if (input.equals("1")) {
            loginMenu();
        } else if (input.equals("2")) {
            userTypeMenu();
        } else if (input.equals("3")) {
            showQueriesMenu();
        } else if (input.equals("4")) {
            System.exit(0);
        }
    }

    /**
     * User interface for logging in.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void loginMenu() throws SQLException {
        System.out.println("\nA. User ID");
        String userID = console.nextLine().trim();

        System.out.println("\nB. Password");
        String password = console.nextLine().trim();

        System.out.println("\n1. Sign In");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();

        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Sign In");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            if (!ap.validUser(userID, password)) {
                System.out.println("\nLogin Incorrect");

                loginMenu();
            }

            String userType = ap.findUserType(userID);

            if ("brand".equals(userType)) {
                brandMenu(userID);
            } else if ("customer".equals(userType)) {
                customerMenu(userID);
            } else if ("admin".equals(userType)) {
                adminMenu();
            }
        } else if (input.equals("2")) {
            homeMenu();
        }
    }

    /**
     * User interface for choosing the user type when signing up.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void userTypeMenu() throws SQLException {
        System.out.println("\n1. Brand Sign-Up");
        System.out.println("2. Customer Sign-Up");
        System.out.println("3. Go Back");

        String input = console.nextLine().trim();

        if (input.isEmpty() || (!input.equals("1") && !input.equals("2") && !input.equals("3"))) {
            System.out.println("\nInvalid Option");

            userTypeMenu();
        }

        if (input.equals("1")) {
            brandSignUpMenu();
        } else if (input.equals("2")) {
            customerSignUpMenu();
        } else if (input.equals("3")) {
            homeMenu();
        }
    }

    /**
     * User interface for showing queries.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void showQueriesMenu() throws SQLException {
        System.out.println("\n1. List all customers that are not part of Brand02's program");
        System.out.println(
                "2. List customers that have joined a loyalty program but have not participated in any activity in that program (list the customerid and the loyalty program id).");
        System.out.println("3. List the rewards that are part of Brand01 loyalty program.");
        System.out.println(
                "4. List all the loyalty programs that include \"refer a friend\" as an activity in at least one of their reward rules.");
        System.out.println(
                "5. For Brand01, list for each activity type in their loyalty program, the number instances that have occurred.");
        System.out.println("6. List customers of Brand01 that have redeemed at least twice.");
        System.out.println("7. All brands where total number of points redeemed overall is less than 500 points.");
        System.out.println(
                "8. For Customer C0003, and Brand02, number of activities they have done in the period of 08/1/2021 and 9/30/2021.");
        System.out.println("9. Go Back");

        String input = console.nextLine().trim();

        List<String> valid = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        if (!valid.contains(input)) {
            System.out.println("\nInvalid Option");

            userTypeMenu();
        }

        if (input.equals("9")) {
            homeMenu();
        } else {
            ap.showQuery(input);

            showQueriesMenu();
        }
    }

    /**
     * User interface for signing up as a brand.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void brandSignUpMenu() throws SQLException {
        System.out.println("\nA. User ID");
        String brandID = console.nextLine().trim();

        System.out.println("\nB. Password");
        String brandPassword = console.nextLine().trim();

        System.out.println("\nC. Name");
        String brandName = console.nextLine().trim();

        System.out.println("\nD. Address");
        String brandAddress = console.nextLine().trim();

        System.out.println("\n1. Sign Up");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();
        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Sign Up");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            ap.createBrand(brandID, brandPassword, brandName, brandAddress);

            System.out.println("\nAccount Successfully Created");

            loginMenu();
        } else if (input.equals("2")) {
            userTypeMenu();
        }
    }

    /**
     * User interface for signing up as a customer.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void customerSignUpMenu() throws SQLException {
        System.out.println("\nA. User ID");
        String customerID = console.nextLine().trim();

        System.out.println("\nB. Password");
        String customerPassword = console.nextLine().trim();

        System.out.println("\nC. Name");
        String customerName = console.nextLine().trim();

        System.out.println("\nD. Address");
        String customerAddress = console.nextLine().trim();

        System.out.println("\nE. Phone Number");
        String customerPhoneString = console.nextLine().trim();
        int customerPhone = 0;
        try {
            customerPhone = Integer.parseInt(customerPhoneString);
        } catch (NumberFormatException nfe) {
            System.out.println("\nInvalid Phone Number");

            customerSignUpMenu();
        }

        System.out.println("\n1. Sign Up");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();
        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Sign Up");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            ap.createCustomer(customerID, customerPassword, customerName, customerAddress, customerPhone);

            System.out.println("\nAccount Successfully Created");

            loginMenu();
        } else if (input.equals("2")) {
            userTypeMenu();
        }
    }

    /**
     * User interface for the landing page of a brand.
     * 
     * @param brandID
     *            id of the brand to show the interface for
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void brandMenu(String brandID) throws SQLException {
        System.out.println("\n1. Add Loyalty Program");
        System.out.println("2. Add RE Rules");
        System.out.println("3. Update RE Rule");
        System.out.println("4. Add RR Rules");
        System.out.println("5. Update RR Rules");
        System.out.println("6. Validate Loyalty Program");
        System.out.println("7. Log Out");

        String input = console.nextLine().trim();

        if (input.isEmpty() || (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")
                && !input.equals("5") && !input.equals("6") && !input.equals("7"))) {
            System.out.println("\nInvalid Option");

            brandMenu(brandID);
        }

        String lpCode = ap.getLoyaltyProgramCode(brandID);

        if (input.equals("1")) {
            addLoyaltyProgramMenu(brandID);
        } else if (input.equals("2")) {
            addRERulesMenu(lpCode, brandID);
        } else if (input.equals("3")) {
            updateRERulesMenu(lpCode, brandID);
        } else if (input.equals("4")) {
            addRRRulesMenu(lpCode, brandID);
        } else if (input.equals("5")) {
            updateRRRulesMenu(lpCode, brandID);
        } else if (input.equals("6")) {
            validateLoyaltyProgramMenu(brandID, lpCode);
        } else if (input.equals("7")) {
            homeMenu();
        }
    }

    /**
     * User interface for the landing page of a customer.
     * 
     * @param id
     *            id of the customer to show the interface for
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void customerMenu(String customerID) throws SQLException {
        System.out.println("\n1. Enroll in Loyalty Program");
        System.out.println("2. Reward Activities");
        System.out.println("3. View Wallet");
        System.out.println("4. Redeem Points");
        System.out.println("5. Log Out");

        String input = console.nextLine().trim();

        if (input.isEmpty() || (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")
                && !input.equals("5"))) {
            System.out.println("\nInvalid Option");

            customerMenu(customerID);
        }

        if (input.equals("1")) {
            enrollInLoyaltyProgramMenu(customerID);
        } else if (input.equals("2")) {
            viewActivitesMenu(customerID);
        } else if (input.equals("3")) {
            viewWalletMenu(customerID);
        } else if (input.equals("4")) {
            redeemPointsMenu(customerID);
        } else if (input.equals("5")) {
            homeMenu();
        }
    }

    /**
     * User interface for the landing page of an admin.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void adminMenu() throws SQLException {
        System.out.println("\n1. Add Brand");
        System.out.println("2. Add Customer");
        System.out.println("3. Show Brand's Info");
        System.out.println("4. Show Customer's Info");
        System.out.println("5. Add Activity Type");
        System.out.println("6. Add Reward Type");
        System.out.println("7. Log Out");

        String input = console.nextLine().trim();

        if (input.isEmpty() || (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")
                && !input.equals("5") && !input.equals("6") && !input.equals("7"))) {
            System.out.println("\nInvalid Option");

            adminMenu();
        }

        if (input.equals("1")) {
            addBrandMenu();
        } else if (input.equals("2")) {
            addCustomerMenu();
        } else if (input.equals("3")) {
            showBrandMenu();
        } else if (input.equals("4")) {
            showCustomerMenu();
        } else if (input.equals("5")) {
            addActivityCategoryMenu();
        } else if (input.equals("6")) {
            addRewardCategoryMenu();
        } else if (input.equals("7")) {
            homeMenu();
        }
    }

    /**
     * User interface for a brand adding a loyalty program.
     * 
     * @param brandID
     *            id of the brand to add a loyalty program for
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void addLoyaltyProgramMenu(String brandID) throws SQLException {
        System.out.println("\nA. Name");
        String loyaltyProgramName = console.nextLine().trim();

        System.out.println("\n1. Regular");
        System.out.println("2. Tier");
        System.out.println("3. Go Back");

        String input = console.nextLine().trim();

        while (input.isEmpty() || (!input.equals("1") && !input.equals("2") && !input.equals("3"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Regular");
            System.out.println("2. Tier");
            System.out.println("3. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            if (ap.addRegularLoyaltyProgram(brandID, loyaltyProgramName)) {
                String lpCode = ap.getLoyaltyProgramCode(brandID);

                addRegularLoyaltyProgramMenu(brandID, loyaltyProgramName, lpCode);
            } else {
                System.out.println("\nYou already have created a loyalty program");
                brandMenu(brandID);
            }
        } else if (input.equals("2")) {
            if (ap.addTieredLoyaltyProgram(brandID, loyaltyProgramName)) {
                String lpCode = ap.getLoyaltyProgramCode(brandID);

                addTieredLoyaltyProgramMenu(brandID, loyaltyProgramName, lpCode);
            } else {
                System.out.println("\nYou already have created a loyalty program");
                brandMenu(brandID);
            }
        } else if (input.equals("3")) {
            brandMenu(brandID);
        }
    }

    /**
     * User interface for a brand adding RE rules.
     * 
     * @param lpCode
     *            code of the loyalty program that will have the RE rules added to
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void addRERulesMenu(String lpCode, String brandID) throws SQLException {
        System.out.println("\nA. RE Rule ID");
        String reRuleID = console.nextLine().trim();
        if (reRuleID.length() > 6) {
            System.out.println("\nRE Rule ID must be at most 6 characters");
            addRERulesMenu(lpCode, brandID);
        }

        System.out.println("\nB. Activity Category Code");
        String activityCategory = console.nextLine().trim();
        if (!ap.activityCategoryExists(activityCategory)) {
            System.out.println("\nAn activity category with this code does not exist");
            addRERulesMenu(lpCode, brandID);
        }

        System.out.println("\nC. Number of Points");
        String pointString = console.nextLine().trim();
        int points = 0;
        try {
            points = Integer.parseInt(pointString);
        } catch (NumberFormatException nfe) {
            System.out.println("\nInvalid Number of Points");

            addRERulesMenu(lpCode, brandID);
        }

        System.out.println("\n1. Add RE Rule");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();

        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Add RE Rule");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            ap.addRERule(lpCode, reRuleID, activityCategory, points);

            addRERulesMenu(lpCode, brandID);
        } else if (input.equals("2")) {
            brandMenu(brandID);
        }
    }

    /**
     * User interface for a brand updating RE rules.
     * 
     * @param lpCode
     *            id of the brand whose loyalty program will have the RE rules
     *            updated for
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void updateRERulesMenu(String lpCode, String brandID) throws SQLException {
        System.out.println("\nA. RE Rule ID");
        String reRuleID = console.nextLine().trim();
        if (reRuleID.length() > 6) {
            System.out.println("\nRE Rule ID must be at most 6 characters");
            updateRERulesMenu(lpCode, brandID);
        }

        System.out.println("\nB. Activity Category Code");
        String activityCategory = console.nextLine().trim();
        if (!ap.activityCategoryExists(activityCategory)) {
            System.out.println("\nAn activity category with this code does not exist");
            updateRERulesMenu(lpCode, brandID);
        }

        System.out.println("\nC. Number of Points");
        String pointString = console.nextLine().trim();
        int points = 0;
        try {
            points = Integer.parseInt(pointString);
        } catch (NumberFormatException nfe) {
            System.out.println("\nInvalid Number of Points");

            updateRERulesMenu(lpCode, brandID);
        }

        System.out.println("\n1. Update RE Rule");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();

        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Update RE Rule");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            ap.updateRERule(lpCode, reRuleID, activityCategory, points);

            updateRERulesMenu(lpCode, brandID);
        } else if (input.equals("2")) {
            brandMenu(brandID);
        }
    }

    /**
     * User interface for a brand adding RR rules.
     * 
     * @param brandID
     *            id of the brand whose loyalty program will have the RR rules added
     *            to
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void addRRRulesMenu(String lpCode, String brandID) throws SQLException {
        System.out.println("\nA. RR Rule ID");
        String rrRuleID = console.nextLine().trim();
        if (rrRuleID.length() > 6) {
            System.out.println("\nRR Rule ID must be at most 6 characters");
            addRRRulesMenu(lpCode, brandID);
        }

        System.out.println("\nB. Reward Category Code");
        String rewardCategory = console.nextLine().trim();
        if (!ap.rewardCategoryExists(rewardCategory)) {
            System.out.println("\nA reward category with this code does not exist");
            addRRRulesMenu(lpCode, brandID);
        }

        System.out.println("\nC. Number of Points");
        String pointString = console.nextLine().trim();
        int points = 0;
        try {
            points = Integer.parseInt(pointString);
        } catch (NumberFormatException nfe) {
            System.out.println("\nInvalid Number of Points");

            addRRRulesMenu(lpCode, brandID);
        }

        System.out.println("\n1. Add RR Rule");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();

        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Add RR Rule");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            ap.addRRRule(lpCode, rrRuleID, rewardCategory, points);

            addRRRulesMenu(lpCode, brandID);
        } else if (input.equals("2")) {
            brandMenu(brandID);
        }
    }

    /**
     * User interface for a brand updating RR rules.
     * 
     * @param brandID
     *            id of the brand whose loyalty program will have the RR rules
     *            updated for
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void updateRRRulesMenu(String lpCode, String brandID) throws SQLException {
        System.out.println("\nA. RR Rule ID");
        String rrRuleID = console.nextLine().trim();
        if (rrRuleID.length() > 6) {
            System.out.println("\nRE Rule ID must be at most 6 characters");
            updateRRRulesMenu(lpCode, brandID);
        }

        System.out.println("\nB. Reward Category Code");
        String rewardCategory = console.nextLine().trim();
        if (!ap.rewardCategoryExists(rewardCategory)) {
            System.out.println("\nA reward category with this code does not exist");
            updateRRRulesMenu(lpCode, brandID);
        }

        System.out.println("\nC. Number of Points");
        String pointString = console.nextLine().trim();
        int points = 0;
        try {
            points = Integer.parseInt(pointString);
        } catch (NumberFormatException nfe) {
            System.out.println("\nInvalid Number of Points");

            updateRERulesMenu(lpCode, brandID);
        }

        System.out.println("\n1. Update RR Rule");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();

        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Update RR Rule");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            ap.updateRRRule(lpCode, rrRuleID, rewardCategory, points);

            updateRRRulesMenu(lpCode, brandID);
        } else if (input.equals("2")) {
            brandMenu(brandID);
        }
    }

    /**
     * User interface for a brand validating a loyalty program.
     * 
     * @param brandID
     *            id of the brand to validate a loyalty program for
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void validateLoyaltyProgramMenu(String brandID, String lpCode) throws SQLException {
        System.out.println("\n1. Validate");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();

        if (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            validateLoyaltyProgramMenu(brandID, lpCode);
        }

        if (input.equals("1")) {
            if (ap.validateLoyaltyProgram(lpCode)) {
                System.out.println("\nLoyalty Program Successfully Validated");

                brandMenu(brandID);
            } else {
                System.out.println("\nInvalid Loyalty Program");

                brandMenu(brandID);
            }
        } else if (input.equals("2")) {
            brandMenu(brandID);
        }
    }

    /**
     * User interface for a brand adding a regular loyalty program.
     * 
     * @param brandID
     *            id of the brand to add a regular loyalty program for
     * @param loyaltyProgramName
     *            name of the regular loyalty program to be created
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void addRegularLoyaltyProgramMenu(String brandID, String loyaltyProgramName, String lpCode)
            throws SQLException {
        System.out.println("\n1. Activity Types");
        System.out.println("2. Reward Types");
        System.out.println("3. Go Back");

        String input = console.nextLine().trim();

        if (input.isEmpty() || (!input.equals("1") && !input.equals("2") && !input.equals("3"))) {
            System.out.println("\nInvalid Option");

            addRegularLoyaltyProgramMenu(brandID, loyaltyProgramName, lpCode);
        }

        if (input.equals("1")) {
            chooseActivityCategoriesMenu(brandID, loyaltyProgramName, lpCode);
        } else if (input.equals("2")) {
            chooseRewardCategoriesMenu(brandID, loyaltyProgramName, lpCode);
        } else if (input.equals("3")) {
            addLoyaltyProgramMenu(brandID);
        }
    }

    /**
     * User interface for a brand adding a tiered loyalty program.
     * 
     * @param brandID
     *            id of the brand to add a tiered loyalty program for
     * @param loyaltyProgramName
     *            name of the tiered loyalty program to be created
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void addTieredLoyaltyProgramMenu(String brandID, String loyaltyProgramName, String lpCode)
            throws SQLException {
        System.out.println("\n1. Tiers Set up");
        System.out.println("2. Activity Types");
        System.out.println("3. Reward Types");
        System.out.println("4. Go Back");

        String input = console.nextLine().trim();

        if (input.isEmpty() || (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4"))) {
            System.out.println("\nInvalid Option");

            addTieredLoyaltyProgramMenu(brandID, loyaltyProgramName, lpCode);
        }

        if (input.equals("1")) {
            setUpTiersMenu(brandID, loyaltyProgramName, lpCode);
        } else if (input.equals("2")) {
            chooseActivityCategoriesMenu(brandID, loyaltyProgramName, lpCode);
        } else if (input.equals("3")) {
            chooseRewardCategoriesMenu(brandID, loyaltyProgramName, lpCode);
        } else if (input.equals("4")) {
            addLoyaltyProgramMenu(brandID);
        }
    }

    /**
     * User interface for a brand to choose activity types to add to their loyalty
     * program.
     * 
     * @param brandID
     *            id of the brand to add activity types for
     * @param loyaltyProgramName
     *            name of the loyalty program to add activity types for
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void chooseActivityCategoriesMenu(String brandID, String loyaltyProgramName, String lpCode)
            throws SQLException {
        String[] activityCategories = ap.getAllActivityCategories();
        boolean validInput = false;
        String activityName = null;
        do {
            System.out.println();
            for (int i = 0; i < activityCategories.length; i++) {
                System.out.println(i + 1 + ". " + activityCategories[i]);
            }
            System.out.println(activityCategories.length + 1 + ". Go Back");
            String chosenActivity = console.nextLine().trim();

            for (int i = 0; i < activityCategories.length; i++) {
                if (chosenActivity.isEmpty()) {
                    break;
                } else if (chosenActivity.equals("" + (activityCategories.length + 1))) {
                    addRegularLoyaltyProgramMenu(brandID, loyaltyProgramName, lpCode);
                } else if (chosenActivity.equals("" + (i + 1))) {
                    validInput = true;
                    activityName = activityCategories[i];

                    break;
                }
            }

            if (!validInput) {
                System.out.println("\nInvalid Option");
            }
        } while (!validInput);

        ap.chooseActivityCategory(lpCode, activityName);
        System.out.println("\nActivity Category Added to Loyalty Program");

        chooseActivityCategoriesMenu(brandID, loyaltyProgramName, lpCode);
    }

    /**
     * User interface for a brand to choose reward types to add to their loyalty
     * program.
     * 
     * @param brandID
     *            id of the brand to add reward types for
     * @param loyaltyProgramName
     *            name of the loyalty program to add reward types for
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void chooseRewardCategoriesMenu(String brandID, String loyaltyProgramName, String lpCode)
            throws SQLException {
        System.out.println("\nA. Quantity");
        String quantityString = console.nextLine().trim();
        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException nfe) {
            System.out.println("\nInvalid Quantity");

            chooseRewardCategoriesMenu(brandID, loyaltyProgramName, lpCode);
        }

        if (quantity <= 0) {
            System.out.println("\nInvalid Quantity");

            chooseRewardCategoriesMenu(brandID, loyaltyProgramName, lpCode);
        }

        String[] rewardCategories = ap.getAllRewardCategories();
        boolean validInput = false;
        String rewardName = null;
        do {
            System.out.println();
            for (int i = 0; i < rewardCategories.length; i++) {
                System.out.println(i + 1 + ". " + rewardCategories[i]);
            }
            System.out.println(rewardCategories.length + 1 + ". Go Back");
            String chosenReward = console.nextLine().trim();

            for (int i = 0; i < rewardCategories.length; i++) {
                if (chosenReward.isEmpty()) {
                    break;
                } else if (chosenReward.equals("" + (rewardCategories.length + 1))) {
                    addRegularLoyaltyProgramMenu(brandID, loyaltyProgramName, lpCode);
                } else if (chosenReward.equals("" + (i + 1))) {
                    validInput = true;
                    rewardName = rewardCategories[i];

                    break;
                }
            }

            if (!validInput) {
                System.out.println("\nInvalid Option");
            }
        } while (!validInput);

        ap.chooseRewardCategory(lpCode, rewardName, quantity);

        chooseRewardCategoriesMenu(brandID, loyaltyProgramName, lpCode);
    }

    /**
     * User interface for a brand setting up the tiers of their loyalty program.
     * 
     * @param brandID
     *            id of the brand to set up the tiers of their loyalty program for
     * @param loyaltyProgramName
     *            name of the tiered loyalty program to create tiers for
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void setUpTiersMenu(String brandID, String loyaltyProgramName, String lpCode) throws SQLException {
        System.out.println("\nA. Number of Tiers (1-3)");
        String numTiersString = console.nextLine().trim();
        int numTiers = 0;
        try {
            numTiers = Integer.parseInt(numTiersString);

            if (numTiers < 1 || numTiers > 3) {
                System.out.println("\nInvalid Number of Tiers");

                setUpTiersMenu(brandID, loyaltyProgramName, lpCode);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("\nInvalid Number of Tiers");

            setUpTiersMenu(brandID, loyaltyProgramName, lpCode);
        }

        System.out.println("\nB. Name of the Tiers (Increasing Order of Precedence)");
        String[] tierNames = new String[numTiers];
        for (int i = 0; i < numTiers; i++) {
            tierNames[i] = console.nextLine().trim();
        }

        System.out.println("\nC. Points Required for Each Tier");
        int[] tierPointRequirements = new int[numTiers];
        for (int i = 0; i < numTiers; i++) {
            String tierPointRequirementString = console.nextLine().trim();
            int tierPointRequirement = 0;
            try {
                tierPointRequirement = Integer.parseInt(tierPointRequirementString);
            } catch (NumberFormatException nfe) {
                System.out.println("\nInvalid Points");

                setUpTiersMenu(brandID, loyaltyProgramName, lpCode);
            }
            tierPointRequirements[i] = tierPointRequirement;
        }

        System.out.println("\nD. Multipliers for Each Tier");
        int[] tierMultipliers = new int[numTiers];
        for (int i = 0; i < numTiers; i++) {
            String tierMultiplierString = console.nextLine().trim();
            int tierMultiplier = 0;
            try {
                tierMultiplier = Integer.parseInt(tierMultiplierString);
            } catch (NumberFormatException nfe) {
                System.out.println("\nInvalid Multiplier");

                setUpTiersMenu(brandID, loyaltyProgramName, lpCode);
            }
            tierMultipliers[i] = tierMultiplier;
        }

        System.out.println("\n1. Set Up");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();

        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Set Up");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            ap.setUpTiers(lpCode, numTiers, tierNames, tierPointRequirements, tierMultipliers);

            addTieredLoyaltyProgramMenu(brandID, loyaltyProgramName, lpCode);
        } else if (input.equals("2")) {
            addTieredLoyaltyProgramMenu(brandID, loyaltyProgramName, lpCode);
        }
    }

    /**
     * User interface for a customer enrolling in a loyalty program
     * 
     * @param customerID
     *            id of the customer to enroll in a loyalty program
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void enrollInLoyaltyProgramMenu(String customerID) throws SQLException {
        System.out.println("\nChoose from the list of available loyalty programs");

        String[] loyaltyPrograms = ap.getAllLoyaltyPrograms();
        System.out.println();
        for (int i = 0; i < loyaltyPrograms.length; i++) {
            char letter = (char) ('A' + i);
            System.out.println(letter + ". " + loyaltyPrograms[i]);
        }
        String chosenLoyaltyProgram = console.nextLine().trim();

        boolean validInput = !chosenLoyaltyProgram.isEmpty();
        String lpCode = null;
        for (int i = 0; i < loyaltyPrograms.length; i++) {
            char letter = (char) ('A' + i);
            if (chosenLoyaltyProgram.isEmpty()) {
                break;
            } else if (chosenLoyaltyProgram.equals("" + letter)) {
                validInput = true;
                lpCode = loyaltyPrograms[i].substring(loyaltyPrograms[i].indexOf("Loyalty Program Code: ") + 22,
                        loyaltyPrograms[i].indexOf(", Brand ID: "));

                break;
            }
        }

        if (!validInput) {
            System.out.println("\nInvalid Option");

            enrollInLoyaltyProgramMenu(customerID);
        }

        System.out.println("\n1. Enroll in Loyalty Program");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();
        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Enroll in Loyalty Program");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            if (ap.enrollInLoyaltyProgram(customerID, lpCode)) {
                System.out.println("\nEnrollment successfully saved");
            } else {
                System.out.println("\nYou are already enrolled in this loyalty program");
            }

            customerMenu(customerID);
        } else if (input.equals("2")) {
            customerMenu(customerID);
        }
    }

    /**
     * User interface for a customer viewing available reward activities
     * 
     * @param customerID
     *            id of the customer to view available reward activities for
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void viewActivitesMenu(String customerID) throws SQLException {
        System.out.println("\nChoose from the list of enrolled loyalty programs");
        String[] enrolledLoyaltyPrograms = ap.getAllEnrolledLoyaltyPrograms(customerID);
        System.out.println();
        for (int i = 0; i < enrolledLoyaltyPrograms.length; i++) {
            char letter = (char) ('A' + i);
            System.out.println(letter + ". " + enrolledLoyaltyPrograms[i]);
        }
        String chosenLoyaltyProgram = console.nextLine().trim();

        boolean validInput = false;
        String lpCode = null;
        for (int i = 0; i < enrolledLoyaltyPrograms.length; i++) {
            char letter = (char) ('A' + i);
            if (chosenLoyaltyProgram.isEmpty()) {
                break;
            } else if (chosenLoyaltyProgram.equals("" + letter)) {
                validInput = true;
                lpCode = enrolledLoyaltyPrograms[i].substring(
                        enrolledLoyaltyPrograms[i].indexOf("Loyalty Program Code: ") + 22,
                        enrolledLoyaltyPrograms[i].indexOf(", Name: "));
                break;
            }
        }

        if (!validInput) {
            System.out.println("\nInvalid Option");

            viewActivitesMenu(customerID);
        }

        System.out.println("\nChoose from the list of available activities");
        String[] loyaltyProgramActivities = ap.getLoyaltyProgramActivities(lpCode);
        String activityName = null;
        do {
            System.out.println();
            for (int i = 0; i < loyaltyProgramActivities.length; i++) {
                System.out.println(i + 1 + ". " + loyaltyProgramActivities[i]);
            }
            System.out.println(loyaltyProgramActivities.length + 1 + ". Go Back");
            String chosenActivity = console.nextLine().trim();

            validInput = false;
            for (int i = 0; i < loyaltyProgramActivities.length; i++) {
                if (chosenActivity.isEmpty()) {
                    break;
                } else if (chosenActivity.equals("" + (loyaltyProgramActivities.length + 1))) {
                    customerMenu(customerID);
                } else if (chosenActivity.equals("" + (i + 1))) {
                    validInput = true;
                    activityName = loyaltyProgramActivities[i];

                    break;
                }
            }

            if (!validInput) {
                System.out.println("Invalid Option");
            }
        } while (!validInput);

        viewSpecificActivityMenu(customerID, lpCode, activityName);
    }

    /**
     * User interface for a customer viewing their wallet.
     * 
     * @param customerID
     *            id of the customer to view wallet for
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void viewWalletMenu(String customerID) throws SQLException {
        ap.viewWallet(customerID);

        System.out.println("\n1. Go Back");

        String input = console.nextLine().trim();

        while (input.isEmpty() || (!input.equals("1"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            customerMenu(customerID);
        }
    }

    /**
     * User interface for a customer redeeming points.
     * 
     * @param customerID
     *            id of the customer to redeem points for
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void redeemPointsMenu(String customerID) throws SQLException {
        String[] availableRewards = ap.getAvailableRewardsToRedeem(customerID);
        if (availableRewards != null) {
            System.out.println("\nChoose from the list of available rewards");
            char lastLetter = 'A';
            System.out.println();
            for (int i = 0; i < availableRewards.length; i++) {
                char letter = (char) ('A' + i);

                if (i == availableRewards.length) {
                    lastLetter = letter;
                }

                System.out.println(letter + ". " + availableRewards[i]);
            }
            String chosenReward = console.nextLine().trim();

            boolean validInput = false;
            String rewardChosen = null;
            for (int i = 0; i < availableRewards.length; i++) {
                char letter = (char) ('A' + i);
                if (chosenReward.isEmpty()) {
                    break;
                } else if (chosenReward.equals("" + letter)) {
                    validInput = true;
                    rewardChosen = availableRewards[i];
                    break;
                }
            }

            if (!validInput) {
                System.out.println("\nInvalid Option");

                redeemPointsMenu(customerID);
            }

            System.out.println(lastLetter + ". Amount");
            String amountString = console.nextLine().trim();
            int amount = 0;
            try {
                amount = Integer.parseInt(amountString);

                if (amount < 1) {
                    System.out.println("\nInvalid Amount");

                    redeemPointsMenu(customerID);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("\nInvalid Amount");

                redeemPointsMenu(customerID);
            }

            System.out.println("\n1. Select Reward");
            System.out.println("2. Go Back");

            String input = console.nextLine().trim();

            while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
                System.out.println("\nInvalid Option");

                System.out.println("\n1. Select Reward");
                System.out.println("2. Go Back");

                input = console.nextLine().trim();
            }

            if (input.equals("1")) {
                String lpCode = rewardChosen.substring(rewardChosen.indexOf("Loyalty Program Code: ") + 22,
                        rewardChosen.indexOf(", Reward Category"));
                String rewardIDString = rewardChosen.substring(rewardChosen.indexOf("Reward ID: ") + 11);
                int rewardID = Integer.parseInt(rewardIDString);

                if (ap.redeemRewards(customerID, rewardID, lpCode, amount)) {
                    if (amount > 1) {
                        System.out.println("\nRewards Successfully Redeemed");
                    } else {
                        System.out.println("\nReward Successfully Redeemed");
                    }

                    viewActivitesMenu(customerID);
                } else {

                }
            } else if (input.equals("2")) {
                customerMenu(customerID);
            }
        } else {
            System.out.println("\nNo available rewards to redeem");
            System.out.println("1. Go Back");

            String input = console.nextLine().trim();

            if (input.isEmpty() || !input.equals("1")) {
                System.out.println("\nInvalid Option");

                System.out.println("\n1. Go Back");

                input = console.nextLine().trim();
            }

            customerMenu(customerID);
        }
    }

    /**
     * User interface for an admin adding a brand.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void addBrandMenu() throws SQLException {
        System.out.println("\nA. Brand ID");
        String brandId = console.nextLine().trim();

        System.out.println("\nB. Password");
        String brandPassword = console.nextLine().trim();

        System.out.println("\nC. Name");
        String brandName = console.nextLine().trim();

        System.out.println("\nD. Address");
        String brandAddress = console.nextLine().trim();

        System.out.println("\n1. Add Brand");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();

        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Add Brand");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            ap.createBrand(brandId, brandPassword, brandName, brandAddress);

            System.out.println("\nBrand Successfully Created");

            adminMenu();
        } else if (input.equals("2")) {
            adminMenu();
        }
    }

    /**
     * User interface for an admin adding a customer.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void addCustomerMenu() throws SQLException {
        System.out.println("\nA. Customer ID");
        String customerID = console.nextLine().trim();

        System.out.println("\nB. Password");
        String customerPassword = console.nextLine().trim();

        System.out.println("\nC. Name");
        String customerName = console.nextLine().trim();

        System.out.println("\nD. Address");
        String customerAddress = console.nextLine().trim();

        System.out.println("\nE. Phone Number");
        String phoneString = console.nextLine().trim();
        long customerPhone = 0;
        try {
            customerPhone = Long.parseLong(phoneString);
        } catch (NumberFormatException nfe) {
            System.out.println("\nInvalid Phone Number");

            addCustomerMenu();
        }

        System.out.println("\n1. Add Customer");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();

        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Add Customer");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            ap.createCustomer(customerID, customerPassword, customerName, customerAddress, customerPhone);

            System.out.println("\nCustomer Successfully Created");

            adminMenu();
        } else if (input.equals("2")) {
            adminMenu();
        }
    }

    /**
     * User interface for an admin viewing a brand's info.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void showBrandMenu() throws SQLException {
        System.out.println("\nA. Brand ID");
        String brandID = console.nextLine().trim();

        System.out.println("\n1. Show Brand Info");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();
        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Show Brand Info");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            if (!ap.showBrandInfo(brandID)) {
                System.out.println("\nBrand with this ID does not exist");
            }

            showBrandMenu();
        } else if (input.equals("2")) {
            adminMenu();
        }
    }

    /**
     * User interface for an admin viewing a customer's info.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void showCustomerMenu() throws SQLException {
        System.out.println("\nA. Customer ID");
        String customerID = console.nextLine().trim();

        System.out.println("\n1. Show Customer Info");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();
        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Show Customer Info");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            if (!ap.showCustomerInfo(customerID)) {
                System.out.println("\nCustomer with this ID does not exist");
            }

            showCustomerMenu();
        } else if (input.equals("2")) {
            adminMenu();
        }
    }

    /**
     * User interface for an admin adding an activity type.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void addActivityCategoryMenu() throws SQLException {
        System.out.println("\nA. Activity Name");
        String activityName = console.nextLine().trim();

        System.out.println("\nB. Activity ID");
        String activityID = console.nextLine().trim();

        System.out.println("\n1. Add Activity Category");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();
        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Add Activity Category");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            ap.addActivityCategory(activityName, activityID);

            addActivityCategoryMenu();
        } else if (input.equals("2")) {
            adminMenu();
        }
    }

    /**
     * User interface for an admin adding a reward type.
     * 
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    public void addRewardCategoryMenu() throws SQLException {
        System.out.println("\nA. Reward Name");
        String rewardName = console.nextLine().trim();

        System.out.println("\nB. Reward ID");
        String rewardID = console.nextLine().trim();

        System.out.println("\n1. Add Reward Category");
        System.out.println("2. Go Back");

        String input = console.nextLine().trim();
        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println("\nInvalid Option");

            System.out.println("\n1. Add Reward Category");
            System.out.println("2. Go Back");

            input = console.nextLine().trim();
        }

        if (input.equals("1")) {
            ap.addRewardCategory(rewardName, rewardID);

            addRewardCategoryMenu();
        } else if (input.equals("2")) {
            adminMenu();
        }
    }

    /**
     * User interface for a customer viewing a given activity in a specific loyalty
     * program that they're enrolled in.
     * 
     * @param customerID
     *            if od the customer viewing the activity
     * @param brandID
     *            id of the brand whose loyalty program's activities are being
     *            viewed
     * @param activityName
     *            name of the activity to be viewed
     * @throws SQLException
     *             if MarketplaceAP encounters an error
     */
    private void viewSpecificActivityMenu(String customerID, String lpCode, String activityName) throws SQLException {
        if (activityName.equals("Purchase")) {
            System.out.println("\n1. Purchase");
            System.out.println("2. Go Back");

            String input = console.nextLine().trim();
            double purchaseAmount = 0.0;
            while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
                System.out.println("\nInvalid Option");

                System.out.println("\n1. Purchase");
                System.out.println("2. Go Back");

                input = console.nextLine().trim();
            }

            if (input.equals("1")) {
                System.out.println("\nPurchase Amount: ");
                String purchaseAmountString = console.nextLine().trim();
                try {
                    purchaseAmount = Double.parseDouble(purchaseAmountString);
                } catch (NumberFormatException nfe) {
                    System.out.println("\nInvalid Purchase Amount");
        
                    viewSpecificActivityMenu(customerID, lpCode, activityName);
                }

                String[] giftCards = ap.getRedeemedGiftCards(customerID, lpCode);
                int rewardID = -1;
                if (giftCards.length > 0) {
                    boolean validInput = false;
                    String giftCardID = null;
                    do {
                        System.out.println();
                        for (int i = 0; i < giftCards.length; i++) {
                            char letter = (char) ('A' + i);
                            System.out.println(letter + ". " + giftCards[i]);
                        }
                        String chosenGiftCard = console.nextLine().trim();

                        validInput = false;
                        for (int i = 0; i < giftCards.length; i++) {
                            char letter = (char) ('A' + i);
                            if (chosenGiftCard.isEmpty()) {
                                validInput = true;

                                break;
                            } else if (chosenGiftCard.equals("" + letter)) {
                                validInput = true;
                                giftCardID = giftCards[i].substring(giftCards[i].indexOf("Gift Card ID: ") + 14,
                                        giftCards[i].indexOf(", Quantity"));
                                rewardID = Integer.parseInt(giftCardID);
                                break;
                            }
                        }

                        if (!validInput) {
                            System.out.println("\nInvalid Option");
                        }
                    } while (!validInput);
                }

                ap.makePurchase(customerID, lpCode, purchaseAmount, rewardID);

                viewActivitesMenu(customerID);
            } else if (input.equals("2")) {
                customerMenu(customerID);
            }
        } else if (activityName.equals("Leave a review")) {
            System.out.println("\nA. Contents of the Review");
            String review = console.nextLine().trim();

            System.out.println("\n1. Leave a review");
            System.out.println("2. Go Back");

            String input = console.nextLine().trim();
            while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
                System.out.println("\nInvalid Option");

                System.out.println("\n1. Leave a review");
                System.out.println("2. Go Back");

                input = console.nextLine().trim();
            }

            if (input.equals("1")) {
                ap.leaveReview(customerID, lpCode, review);

                viewActivitesMenu(customerID);
            } else if (input.equals("2")) {
                customerMenu(customerID);
            }
        } else if (activityName.equals("Refer a friend")) {
            System.out.println("\n1. Refer a friend");
            System.out.println("2. Go Back");

            String input = console.nextLine().trim();
            while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
                System.out.println("\nInvalid Option");

                System.out.println("\n1. Refer a friend");
                System.out.println("2. Go Back");

                input = console.nextLine().trim();
            }

            if (input.equals("1")) {
                ap.referFriend(customerID, lpCode);

                viewActivitesMenu(customerID);
            } else if (input.equals("2")) {
                customerMenu(customerID);
            }
        } else {
            System.out.println("\n1. " + activityName);
            System.out.println("2. Go Back");

            String input = console.nextLine().trim();
            while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
                System.out.println("\nInvalid Option");

                System.out.println("\n1. " + activityName);
                System.out.println("2. Go Back");

                input = console.nextLine().trim();
            }

            if (input.equals("1")) {
                ap.doActivity(customerID, lpCode, activityName);

                viewActivitesMenu(customerID);
            } else if (input.equals("2")) {
                customerMenu(customerID);
            }
        }
    }

    /**
     * Starts program.
     * 
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        MarketplaceUI ui = new MarketplaceUI();
        try {
            ui.homeMenu();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(1);
        } finally {
            ui.ap.closeConnection();
        }
    }

}
