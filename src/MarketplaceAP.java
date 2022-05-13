package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarketplaceAP {

    class Pair<T1, T2> {
        public T1 one;
        public T2 two;

        public Pair(T1 one, T2 two) {
            this.one = one;
            this.two = two;
        }
    }

    /** Connection to the JDBC. */
    Connection conn;

    /**
     * Constructs a MarketplaceAP object.
     */
    public MarketplaceAP() {
        String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
        String username = "schen53";
        String password = "200275706";

        try {
            Class.forName("oracle.jdbc.OracleDriver");

            conn = DriverManager.getConnection(jdbcURL, username, password);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Closes the JDBC connection.
     * 
     * @param connection
     *            connection to be closed
     */
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    // /**
    //  * Closes the SQL statement.
    //  * 
    //  * @param statement SQL statement to be closed
    //  */
    // static void close(Statement statement) {
    //     if (statement != null) {
    //         try {
    //             statement.close();
    //         } catch (Throwable t) {
    //             t.printStackTrace();
    //             System.exit(0);
    //         }
    //     }
    // }

    // /**
    //  * Closes the ResultSet.
    //  * 
    //  * @param resultSet ResultSet to be closed
    //  */
    // static void close(ResultSet resultSet) {
    //     if (resultSet != null) {
    //         try {
    //             resultSet.close();
    //         } catch (Throwable t) {
    //             t.printStackTrace();
    //             System.exit(0);
    //         }
    //     }
    // }

    /**
     * Interface for setting up parameters.
     */
    interface ParameterSetter {

        /**
         * Sets parameters with a PreparedStatement.
         * 
         * @param preparedStatement
         *            PreparedStatement to set parameters with
         * @throws SQLException
         *             if an error occurs
         */
        void setParams(PreparedStatement preparedStatement) throws SQLException;

    }

    /**
     * Executes a PreparedStatement update with given ParameterSetter.
     * 
     * @param sql
     *            SQL to be executed
     * @param parameterSetter
     *            ParameterSetter object to set parameters with
     * @throws SQLException
     *             if an error occurs
     */
    private void executeUpdate(String sql, ParameterSetter parameterSetter) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.clearParameters();

            parameterSetter.setParams(statement);

            statement.executeUpdate();
        }
    }

    interface ResultSetGetter<T> {
        T getResults(ResultSet rs) throws SQLException;
    }

    interface ResultSetDoer {
        void doThings(ResultSet rs) throws SQLException;
    }

    /**
     * Executes a PreparedStatement query.
     * 
     * @param sql
     *            SQL to be executed
     * @param parameterSetter
     *            ParameterSetter object to set parameters with
     * @throws SQLException
     *             if an error occurs
     */
    // private ResultSet executeQuery(String sql) throws SQLException {
    //     try (Statement statement = conn.createStatement()) {
    //         ResultSet rs = statement.executeQuery(sql);
    //         return rs;
    //     }
    // }

    private <T> T executeQuery(String sql, ResultSetGetter<T> thingGetter) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            return thingGetter.getResults(rs);
        }
    }

    private void executeQuery(String sql, ResultSetDoer thingDoer) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            thingDoer.doThings(rs);
        }
    }

    

    /**
     * Executes a PreparedStatement query with given ParameterSetter.
     * 
     * @param sql
     *            SQL to be executed
     * @param parameterSetter
     *            ParameterSetter object to set parameters with
     * @throws SQLException
     *             if an error occurs
     */
    // private ResultSet executeQuery(String sql, ParameterSetter parameterSetter) throws SQLException {
    //     try (PreparedStatement statement = conn.prepareStatement(sql)) {
    //         statement.clearParameters();

    //         parameterSetter.setParams(statement);

    //         ResultSet rs = statement.executeQuery();
    //         return rs;
    //     }
    // }

    private <T> T executeQuery(String sql, ParameterSetter parameterSetter, ResultSetGetter<T> thingGetter) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.clearParameters();

            parameterSetter.setParams(statement);

            ResultSet rs = statement.executeQuery();
            return thingGetter.getResults(rs);
        }
    }

    private void executeQuery(String sql, ParameterSetter parameterSetter, ResultSetDoer thingDoer) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.clearParameters();

            parameterSetter.setParams(statement);

            ResultSet rs = statement.executeQuery();
            thingDoer.doThings(rs);
        }
    }

    /**
     * Returns the userID of the user in the database if the user exists.
     * 
     * @param userID
     *            id of the user to look for in the database
     * @param password
     *            password of the user to look for in the database
     * @return userID of the user in the database if the user exists, null otherwise
     * @throws SQLException
     *             if an error occurs
     */
    public boolean validUser(String userID, String password) throws SQLException {
        boolean exists = executeQuery(
            "SELECT user_id FROM marketplace_user WHERE user_id = ? AND password = ?",
            (stmt) -> {
                stmt.setString(1, userID);
                stmt.setString(2, password);
            },
            (rs) -> { return rs.next(); }
        );
        return exists;
    }

    /**
     * Returns the result of a sample query.
     * 
     * @param queryNumber
     *            number of the sample query to show
     * @return result of the sample query
     * @throws SQLException
     *             if an error occurs
     */
    public void showQuery(String queryNumber) throws SQLException {
        if (queryNumber.equals("1")) {
            showQuery1();
        } else if (queryNumber.equals("2")) {
            showQuery2();
        } else if (queryNumber.equals("3")) {
            showQuery3();
        } else if (queryNumber.equals("4")) {
            showQuery4();
        } else if (queryNumber.equals("5")) {
            showQuery5();
        } else if (queryNumber.equals("6")) {
            showQuery6();
        } else if (queryNumber.equals("7")) {
            showQuery7();
        } else if (queryNumber.equals("8")) {
            showQuery8();
        }
    }

    /**
     * Directly prints a specific query given the ResultSet object.
     * 
     * @param rs
     *            ResultSet object to be printed
     * @throws SQLException
     *             if an error occurs
     */
    private void printQuery(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int numCol = rsmd.getColumnCount();
        System.out.println();
        while (rs.next()) {
            for (int i = 1; i <= numCol; i++) {
                if (i > 1) {
                    System.out.print("    ");
                }
                String colName = rsmd.getColumnName(i);
                String colVal = rs.getString(i);
                System.out.print(colName + ": " + colVal);
            }
            System.out.println();
        }
    }

    /**
     * Prints the results of sample query 1.
     * 
     * @throws SQLException
     *             if an error occurs
     */
    private void showQuery1() throws SQLException {
        executeQuery(
            "SELECT * "
            + "FROM customer C "
            + "WHERE NOT EXISTS ( "
            + "    SELECT * "
            + "    FROM customer_enrollment CE, loyalty_program L "
            + "    WHERE CE.customer_id = C.user_id AND CE.lp_code = L.code AND L.brand_guid = 'Brand02' "
            + ")",
            (rs) -> {
                printQuery(rs);
            }
        );
    }

    /**
     * Prints the results of sample query 2.
     * 
     * @throws SQLException
     *             if an error occurs
     */
    private void showQuery2() throws SQLException {
        executeQuery(
            "SELECT CE.customer_id, CE.lp_code "
            + "FROM customer_enrollment CE "
            + "WHERE NOT EXISTS ( "
            + "    SELECT * "
            + "    FROM wallet_activity WA "
            + "    WHERE WA.lp_code = CE.lp_code AND EXISTS ( "
            + "        SELECT C.user_id "
            + "        FROM customer C "
            + "        WHERE CE.customer_id = C.user_id AND C.wallet_id = WA.wallet_id "
            + "    ) "
            + ")",
            (rs) -> {
                printQuery(rs);
            }
        );
    }

    /**
     * Prints the results of sample query 3.
     * 
     * @throws SQLException
     *             if an error occurs
     */
    private void showQuery3() throws SQLException {
        executeQuery(
            "SELECT * "
            + "FROM reward R "
            + "WHERE EXISTS ( "
            + "    SELECT * "
            + "    FROM loyalty_program L "
            + "    WHERE R.lp_code = L.code AND L.brand_guid = 'Brand01' "
            + ")",
            (rs) -> {
                printQuery(rs);
            }
        );
    }

    /**
     * Prints the results of sample query 4.
     * 
     * @throws SQLException
     *             if an error occurs
     */
    private void showQuery4() throws SQLException {
        executeQuery(
            "SELECT * "
            + "FROM loyalty_program L "
            + "WHERE EXISTS ( "
            + "    SELECT RER.rule_code "
            + "    FROM reward_earning_rule RER, activity_category AC "
            + "    WHERE RER.activity_category_code = AC.activity_category_code AND AC.name = 'Refer a friend' AND L.code IN ( "
            + "        SELECT RR.lp_code "
            + "        FROM reward_rule RR "
            + "        WHERE RR.rule_code = RER.rule_code "
            + "    ) "
            + ")",
            (rs) -> {
                printQuery(rs);
            }
        );
    }

    /**
     * Prints the results of sample query 5.
     * 
     * @throws SQLException
     *             if an error occurs
     */
    private void showQuery5() throws SQLException {
        executeQuery(
            "SELECT A.activity_category_code, COUNT(*) "
            + "FROM activity A "
            + "WHERE EXISTS ( "
            + "    SELECT WA.activity_id "
            + "    FROM wallet_activity WA, loyalty_program L "
            + "    WHERE WA.activity_id = A.activity_id AND L.code = WA.lp_code AND L.brand_guid = 'Brand01' "
            + ") "
            + "GROUP BY A.activity_category_code",
            (rs) -> {
                printQuery(rs);
            }
        );
    }

    /**
     * Prints the results of sample query 6.
     * 
     * @throws SQLException
     *             if an error occurs
     */
    private void showQuery6() throws SQLException {
        executeQuery(
            "SELECT C.user_id "
            + "FROM customer C "
            + "WHERE 2 <= ( "
            + "    SELECT COUNT(*) "
            + "    FROM redeemed_reward RR, loyalty_program L "
            + "    WHERE RR.customer_id = C.user_id AND RR.lp_code = L.code AND L.brand_guid = 'Brand01' "
            + ")",
            (rs) -> {
                printQuery(rs);
            }
        );
    }

    /**
     * Prints the results of sample query 7.
     * 
     * @throws SQLException
     *             if an error occurs
     */
    private void showQuery7() throws SQLException {
        executeQuery(
            "SELECT * " +
            "FROM brand B " +
            "WHERE NOT EXISTS ( " +
            "    SELECT RR.lp_code " +
            "    FROM loyalty_program L, redeemed_reward RR " +
            "    WHERE L.brand_guid = B.user_id AND RR.lp_code = L.code " +
            ") OR 500 > ( " +
            "    SELECT SUM(RR.num_points) " +
            "    FROM loyalty_program L, redeemed_reward RR " +
            "    WHERE L.brand_guid = B.user_id AND RR.lp_code = L.code " +
            ")",
            (rs) -> {
                printQuery(rs);
            }
        );
    }

    /**
     * Prints the results of sample query 8.
     * 
     * @throws SQLException
     *             if an error occurs
     */
    private void showQuery8() throws SQLException {
        executeQuery(
            "SELECT COUNT(*) "
            + "FROM wallet_activity WA "
            + "WHERE WA.wallet_id IN ( "
            + "    SELECT C.wallet_id "
            + "    FROM customer C "
            + "    WHERE C.user_id = 'C0003' "
            + ") AND WA.lp_code IN ( "
            + "    SELECT L.code "
            + "    FROM loyalty_program L "
            + "    WHERE L.brand_guid = 'Brand02' "
            + ") AND WA.activity_id IN ( "
            + "    SELECT A.activity_id "
            + "    FROM activity A "
            + "    WHERE A.activity_date >= '1 AUG 2021' AND A.activity_date <= '30 SEP 2021'"
            + ")",
            (rs) -> {
                printQuery(rs);
            }
        );
    }

    /**
     * Creates a new brand in the database.
     * 
     * @param brandID
     *            id of the brand to be created
     * @param brandPassword
     *            password of the brand to be created
     * @param brandName
     *            name of the brand to be created
     * @param brandAddress
     *            address of the brand to be created
     * @throws SQLException
     *             if an error occurs
     */
    public void createBrand(String brandID, String brandPassword, String brandName, String brandAddress)
            throws SQLException {
        long today = new java.util.Date().getTime();
        Date brandJoinDate = new Date(today);

        executeUpdate(
            "INSERT INTO marketplace_user (user_id, password) "
            + "VALUES (?, ?)", (stmt) -> {
                stmt.setString(1, brandID);
                stmt.setString(2, brandPassword);
        });

        executeUpdate(
            "INSERT INTO brand (user_id, name, address, join_date) "
            + "VALUES (?, ?, ?, ?)", (stmt) -> {
                stmt.setString(1, brandID);
                stmt.setString(2, brandName);
                stmt.setString(3, brandAddress);
                stmt.setDate(4, brandJoinDate);
        });
    }

    /**
     * Creates a new customer in the database.
     * 
     * @param customerID
     *            id of the customer to be created
     * @param customerPassword
     *            password of the customer to be created
     * @param customerAddress
     *            address of the customer to be created
     * @param customerName
     *            name of the customer to be created
     * @param customerPhone
     *            phone number of the customer to be created
     * @throws SQLException
     *             if an error occurs
     */
    public void createCustomer(String customerID, String customerPassword, String customerName, String customerAddress,
            long customerPhone) throws SQLException { // TODO test that long works as intended
        executeUpdate(
            "INSERT INTO marketplace_user (user_id, password) "
            + "VALUES(?,?)", (stmt) -> {
                stmt.setString(1, customerID);
                stmt.setString(2, customerPassword);
        });

        executeUpdate(
            "INSERT INTO customer (user_id, name, address, phone, wallet_id) "
            + "VALUES(?, ?, ?, ?, ?)", (stmt) -> {
                stmt.setString(1, customerID);
                stmt.setString(2, customerName);
                stmt.setString(3, customerAddress);
                stmt.setLong(4, customerPhone);
                stmt.setString(5, customerID);
        });
    }

    /**
     * Finds a user's type in the database.
     * 
     * @param userID
     *            id of the user to find the type of
     * @return "brand" if the user's type is a brand, "customer" if the user's type
     *         is customer, and "admin" if the user's type is admin
     * @throws SQLException
     *             if an error occurs
     */
    public String findUserType(String userID) throws SQLException {
        boolean isCustomer = executeQuery(
            "SELECT * FROM customer WHERE user_id = ?",
            (stmt) -> { stmt.setString(1, userID); },
            (rs) -> { return rs.next(); }
        );
        if (isCustomer) {
            return "customer";
        }

        boolean isBrand = executeQuery(
            "SELECT * FROM brand WHERE user_id = ?",
            (stmt) -> { stmt.setString(1, userID); },
            (rs) -> { return rs.next(); }
        );
        if (isBrand) {
            return "brand";
        }

        boolean isAdmin = executeQuery(
            "SELECT * FROM marketplace_admin WHERE user_id = ?",
            (stmt) -> { stmt.setString(1, userID); },
            (rs) -> { return rs.next(); }
        );
        if (isAdmin) {
            return "admin";
        }

        return null;
    }

    /**
     * Chooses an activity type to add to a brand's loyalty program in the database.
     * 
     * @param lpCode
     *            code of the loyalty program that will have the activity types
     *            added to
     * @param activityName
     *            name of the activity type to add to the loyalty program
     * @throws SQLException
     *             if an error occurs
     */
    public void chooseActivityCategory(String lpCode, String activityName) throws SQLException {
        String actCatCode = executeQuery(
            "SELECT activity_category_code FROM activity_category WHERE name = ?",
            (stmt) -> { stmt.setString(1, activityName); },
            (rs) -> {
                rs.next();
                return rs.getString("activity_category_code");
            }
        );

        executeUpdate(
            "INSERT INTO lp_activity_category (lp_code, activity_category_code) "
            + "VALUES (?, ?)", (stmt) -> {
                stmt.setString(1, lpCode);
                stmt.setString(2, actCatCode);
        });
    }

    /**
     * Chooses a reward type to add to a brand's loyalty program in the database.
     * 
     * @param lpCode
     *            code of the loyalty program that will have the reward types
     *            added
     * @param rewardName
     *            name of the reward category to add to the loyalty program
     * @param quantity
     *            number of instances of the reward type to add to the loyalty
     *            program
     * @throws SQLException
     *             if an error occurs
     */
    public void chooseRewardCategory(String lpCode, String rewardName, int quantity) throws SQLException {
        String rewardCatCode = executeQuery(
            "SELECT reward_category_code FROM reward_category WHERE name = ?",
            (stmt) -> { stmt.setString(1, rewardName); },
            (rs) -> {
                rs.next();
                return rs.getString("reward_category_code");
            }
        );

        executeUpdate(
            "INSERT INTO lp_reward_category (lp_code, reward_category_code) "
            + "VALUES (?, ?)", (stmt) -> {
                stmt.setString(1, lpCode);
                stmt.setString(2, rewardCatCode);
        });

        int rewardId = executeQuery(
            "SELECT MAX(reward_id) AS max FROM reward WHERE lp_code = ?",
            (stmt) -> { stmt.setString(1, lpCode); },
            (rs) -> {
                rs.next();
                return rs.getInt("max") + 1;
            }
        );

        executeUpdate(
            "INSERT INTO reward (reward_id, lp_code, reward_category_code, num_instances) " +
            "VALUES (?, ?, ?, ?)",
            (stmt) -> {
                stmt.setInt(1, rewardId);
                stmt.setString(2, lpCode);
                stmt.setString(3, rewardCatCode);
                stmt.setInt(4, quantity);
            }
        );

        if (rewardName == "Gift Card") {
            executeUpdate(
                "INSERT INTO gift_card (reward_id, lp_code, amount, expiry_date) " +
                "VALUES (?, ?, 25.00, '12 DEC 2021')",
                (stmt) -> {
                    stmt.setInt(1, rewardId);
                    stmt.setString(2, lpCode);
                }
            );
        } else if (rewardName == "Free Product") {
            executeUpdate(
                "INSERT INTO free_product (reward_id, lp_code, product_name) " +
                "VALUES (?, ?, 'PlayStation')",
                (stmt) -> {
                    stmt.setInt(1, rewardId);
                    stmt.setString(2, lpCode);
                }
            );
        }
    }

    /**
     * Sets up the tiers of a brand's loyalty program in the database.
     * 
     * @param lpCode
     *            code of the loyalty program that will have tiers added to it
     * @param numTiers
     *            number of tiers to add to the loyalty program
     * @param tierNames
     *            array of names of the tiers being added to the loyalty program
     * @param tierPointRequirements
     *            array of points required for each tier being added to the loyalty
     *            program
     * @param tierMultipliers
     *            array of point multipliers for each tier being added to the
     *            loyalty program
     * @throws SQLException
     *             if an error occurs
     */
    public void setUpTiers(String lpCode, int numTiers, String[] tierNames, int[] tierPointRequirements,
            int[] tierMultipliers) throws SQLException {
        for (int i = 0; i < numTiers; i++) {
            final int level = i;
            String tierName = tierNames[i];
            int tierPointRequirement = tierPointRequirements[i];
            int tierMultiplier = tierMultipliers[i];

            executeUpdate(
                "INSERT INTO tier (lp_code, name, tier_level, points_required, multiplier) "
                + "VALUES (?, ?, ?, ?, ?)", (stmt) -> {
                    stmt.setString(1, lpCode);
                    stmt.setString(2, tierName);
                    stmt.setInt(3, level);
                    stmt.setInt(4, tierPointRequirement);
                    stmt.setInt(5, tierMultiplier);
            });
        }
    }

    public boolean activityCategoryExists(String categoryCode) throws SQLException {
        return executeQuery(
            "SELECT * FROM activity_category WHERE activity_category_code = ?",
            (stmt) -> {
                stmt.setString(1, categoryCode);
            },
            (rs) -> { return rs.next(); }
        );
    }

    public boolean rewardCategoryExists(String categoryCode) throws SQLException {
        return executeQuery(
            "SELECT * FROM reward_category WHERE reward_category_code = ?",
            (stmt) -> {
                stmt.setString(1, categoryCode);
            },
            (rs) -> { return rs.next(); }
        );
    }

    /**
     * Adds an RE rule mapping an activity category to a number of points to add to
     * a brand's loyalty program in the database.
     * 
     * @param lpCode
     *            code of the loyalty program that will have the RE rule added
     * @param reRuleID
     *            id of the RE rule to add to the loyalty program
     * @param activityCategory
     *            activity category associated with the RE rule to add to the
     *            loyalty program
     * @param points
     *            number of points associated with the RE rule to add to the loyalty
     *            program
     * @throws SQLException
     *             if an error occurs
     */
    public void addRERule(String lpCode, String reRuleID, String activityCategory, int points) throws SQLException {
        executeUpdate(
            "INSERT INTO reward_rule (rule_code, lp_code, num_points, version) "
            + "VALUES (?, ?, ?, 1)", (stmt) -> {
                stmt.setString(1, reRuleID);
                stmt.setString(2, lpCode);
                stmt.setInt(3, points);
        });

        executeUpdate(
            "INSERT INTO reward_earning_rule (rule_code, lp_code, activity_category_code) "
            + "VALUES (?, ?, ?)", (stmt) -> {
                stmt.setString(1, reRuleID);
                stmt.setString(2, lpCode);
                stmt.setString(3, activityCategory);
        });
    }

    /**
     * Updates an RE rule mapping an activity category to a number of points for a
     * brand's loyalty program in the database.
     * 
     * @param lpCode
     *            code of the loyalty program will have the RE rule
     *            updated
     * @param reRuleID
     *            id of the RE rule to update in the loyalty program
     * @param activityCategory
     *            activity category associated with the RE rule to update in the
     *            loyalty program
     * @param points
     *            number of points associated with the RE rule to update in the
     *            loyalty program
     * @throws SQLException
     *             if an error occurs
     */
    public void updateRERule(String lpCode, String reRuleID, String activityCategory, int points) throws SQLException {
        // int version;
        // try (
        //     ResultSet rs = executeQuery(
        //         "SELECT R.version FROM reward_rule R WHERE R.rule_code = ? " +
        //         "AND R.rule_code IN (SELECT RER.rule_code FROM reward_redeeming_rule RER)",
        //         (stmt) -> {
        //             stmt.setString(1, reRuleID);
        //         }
        //     )
        // ) {
        //     rs.next();
        //     version = rs.getInt("version");
        // }

        // executeUpdate(
        //     "INSERT INTO reward_rule (rule_code, lp_code, num_points, version) " +
        //     "VALUES (?, ?, ?, ?)",
        //     (stmt) -> {
        //         stmt.setString(1, reRuleID);
        //         stmt.setString(2, lpCode);
        //         stmt.setInt(2, points);
        //         stmt.setInt(3, version + 1);
        //     }
        // );

        executeUpdate(
            "INSERT INTO reward_rule (rule_code, lp_code, num_points) " +
            "VALUES (?, ?, ?)",
            (stmt) -> {
                stmt.setString(1, reRuleID);
                stmt.setString(2, lpCode);
                stmt.setInt(3, points);
            }
        );

        executeUpdate(
            "INSERT INTO reward_earning_rule (rule_code, lp_code, activity_category_code) " +
            "VALUES (?, ?, ?)",
            (stmt) -> {
                stmt.setString(1, reRuleID);
                stmt.setString(2, lpCode);
                stmt.setString(3, activityCategory);
            }
        );
    }

    /**
     * Adds an RR rule mapping a reward category to a number of points to add to a
     * brand's loyalty program in the database.
     * 
     * @param lpCode
     *            code of the loyalty program that will have the RR rule added
     * @param rrRuleID
     *            id of the RR rule to add to the loyalty program
     * @param rewardCategory
     *            reward category associated with the RR rule to add to the loyalty
     *            program
     * @param points
     *            number of points associated with the RR rule to add to the loyalty
     *            program
     * @throws SQLException
     *             if an error occurs
     */
    public void addRRRule(String lpCode, String rrRuleID, String rewardCategory, int points) throws SQLException {
        executeUpdate(
            "INSERT INTO reward_rule (rule_code, lp_code, num_points, version) "
            + "VALUES (?, ?, ?, 1)", (stmt) -> {
                stmt.setString(1, rrRuleID);
                stmt.setString(2, lpCode);
                stmt.setInt(3, points);
        });

        executeUpdate(
            "INSERT INTO reward_redeeming_rule (rule_code, lp_code, reward_category_code) "
            + "VALUES (?, ?, ?)", (stmt) -> {
                stmt.setString(1, rrRuleID);
                stmt.setString(2, lpCode);
                stmt.setString(3, rewardCategory);
        });
    }

    /**
     * Updates an RR rule mapping a reward category to a number of points for a
     * brand's loyalty program in the database.
     * 
     * @param lpCode
     *            code of the loyalty program that will have the RR rule
     *            updated
     * @param reRuleID
     *            id of the RR rule to update in the loyalty program
     * @param rewardCategory
     *            reward category associated with the RR rule to update in the
     *            loyalty program
     * @param points
     *            number of points associated with the RR rule to update in the
     *            loyalty program
     * @throws SQLException
     *             if an error occurs
     */
    public void updateRRRule(String lpCode, String rrRuleID, String rewardCategory, int points) throws SQLException {
        // int version;
        // try (
        //     ResultSet rs = executeQuery(
        //         "SELECT R.version FROM reward_rule R WHERE R.rule_code = ? " +
        //         "AND R.rule_code IN (SELECT RRR.rule_code FROM reward_redeeming_rule RRR)",
        //         (stmt) -> {
        //             stmt.setString(1, rrRuleID);
        //         }
        //     )
        // ) {
        //     rs.next();
        //     version = rs.getInt("version");
        // }

        // executeUpdate(
        //     "INSERT INTO reward_rule (rule_code, lp_code, num_points, version) " +
        //     "VALUES (?, ?, ?, ?)",
        //     (stmt) -> {
        //         stmt.setString(1, rrRuleID);
        //         stmt.setString(2, lpCode);
        //         stmt.setInt(2, points);
        //         stmt.setInt(3, version + 1);
        //     }
        // );

        executeUpdate(
            "INSERT INTO reward_rule (rule_code, lp_code, num_points) " +
            "VALUES (?, ?, ?)",
            (stmt) -> {
                stmt.setString(1, rrRuleID);
                stmt.setString(2, lpCode);
                stmt.setInt(3, points);
            }
        );

        executeUpdate(
            "INSERT INTO reward_redeeming_rule (rule_code, lp_code, reward_category_code) " +
            "VALUES (?, ?, ?)",
            (stmt) -> {
                stmt.setString(1, rrRuleID);
                stmt.setString(2, lpCode);
                stmt.setString(3, rewardCategory);
            }
        );
    }

    /**
     * Checks whether or not a brand's loyalty program is valid in the database.
     * 
     * @param brandID
     *            id of the brand whose loyalty program is to be validated
     * @return true if the brand's loyalty program is valid, false otherwise
     * @throws SQLException
     *             if an error occurs
     */
    public boolean validateLoyaltyProgram(String lpCode) throws SQLException {
        boolean rulesExist = executeQuery(
            "SELECT RER.rule_code, RRR.rule_code " +
            "FROM reward_earning_rule RER, reward_redeeming_rule RRR " +
            "WHERE RER.lp_code = ? AND RRR.lp_code = ?",
            (stmt) -> {
                stmt.setString(1, lpCode); 
                stmt.setString(2, lpCode);
            },
            (rs) -> { return rs.next(); }
        );
        
        if (!rulesExist) {
            return false;
        }

        boolean isTiered = executeQuery(
            "SELECT type FROM loyalty_program WHERE code = ?",
            (stmt) -> { stmt.setString(1, lpCode); },
            (rs) -> {
                rs.next();
                return rs.getString("type") == "tiered";
            }
        );

        if (isTiered) {
            boolean hasTiers = executeQuery(
                "SELECT * FROM tier WHERE lp_code = ?",
                (stmt) -> { stmt.setString(1, lpCode); },
                (rs) -> { return rs.next(); }
            );

            if (!hasTiers) {
                return false;
            }

            boolean isUnordered = executeQuery(
                "SELECT T1.name FROM tier T1 " +
                "WHERE EXISTS (" +
                "    SELECT T2.name FROM tier T2 " +
                "    WHERE T1.lp_code = ? AND T1.lp_code = T2.lp_code " +
                "        AND T1.tier_level < T2.tier_level AND T1.point_required >= T2.points_required" +
                ")",
                (stmt) -> { stmt.setString(1, lpCode); },
                (rs) -> { return rs.next(); }
            );

            if (isUnordered) {
                return false;
            }
        }

        executeUpdate(
            "UPDATE loyalty_program SET status = 'Active' WHERE code = ?",
            (stmt) -> { stmt.setString(1, lpCode); }
        );

        return true;
    }

    public String getLoyaltyProgramCode(String brandID) throws SQLException {
        return executeQuery(
            "SELECT * FROM loyalty_program WHERE brand_guid = ?",
            (stmt) -> { stmt.setString(1, brandID); },
            (rs) -> {
                if (rs.next()) {
                    return rs.getString("code");
                } else {
                    return null;
                }
            }
        );
    }

    /**
     * Returns a list of all loyalty programs in the database.
     * 
     * @return list of all loyalty programs in the database
     * @throws SQLException
     *             if an error occurs
     */
    public String[] getAllLoyaltyPrograms() throws SQLException {
        return executeQuery(
            "SELECT * FROM loyalty_program WHERE status = 'Active'",
            (rs) -> {
                List<String> loyaltyProgs = new ArrayList<>();
                while (rs.next()) {
                    String lpCode = rs.getString("code");
                    String brandID = rs.getString("brand_guid");
                    String loyaltyProgramName = rs.getString("name");
                    String loyaltyProgramType = rs.getString("type");
                    loyaltyProgs.add("Loyalty Program Code: " + lpCode + ", Brand ID: " + brandID + ", Name: " + loyaltyProgramName + ", Type: " + loyaltyProgramType);
                }
                String[] loyaltyProgsArr = loyaltyProgs.toArray(new String[0]);
                return loyaltyProgsArr;
            }
        );
    }

    /**
     * Shows all information about a brand in the database.
     * 
     * @param brandID
     *            id of the brand to show information for
     * @throws SQLException
     *             if an error occurs
     */
    public boolean showBrandInfo(String brandID) throws SQLException {
        boolean exists = executeQuery(
            "SELECT * FROM brand WHERE user_id = ?",
            (stmt) -> { stmt.setString(1, brandID); },
            (rs) -> {
                return rs.next();
            }
        );

        if (!exists) {
            return false;
        }
        
        executeQuery(
            "SELECT * FROM brand WHERE user_id = ?",
            (stmt) -> { stmt.setString(1, brandID); },
            (rs) -> {
                printQuery(rs);
            }
        );

        return true;
    }

    /**
     * Shows all information about a customer in the database.
     * 
     * @param customerID
     *            id of the customer to show information for
     * @throws SQLException
     *             if an error occurs
     */
    public boolean showCustomerInfo(String customerID) throws SQLException {
        boolean exists = executeQuery(
            "SELECT * FROM customer WHERE user_id = ?",
            (stmt) -> { stmt.setString(1, customerID); },
            (rs) -> {
                return rs.next();
            }
        );

        if (!exists) {
            return false;
        }
        
        executeQuery(
            "SELECT * FROM customer WHERE user_id = ?",
            (stmt) -> { stmt.setString(1, customerID); },
            (rs) -> {
                printQuery(rs);
            }
        );

        return true;
    }

    /**
     * Adds an activity category to the database.
     * 
     * @param activityName
     *            name of the activity to be added
     * @param activityID
     *            id of the activity to be added
     * @throws SQLException
     *             if an error occurs
     */
    public void addActivityCategory(String activityName, String activityID) throws SQLException {
        executeUpdate(
            "INSERT INTO activity_category (activity_category_code, name) " 
            + "VALUES (?, ?)", (stmt) -> {
                stmt.setString(1, activityID);
                stmt.setString(2, activityName);
        });
    }

    /**
     * Adds a reward category to the database.
     * 
     * @param rewardName
     *            name of the reward to be added
     * @param rewardID
     *            id of the reward to be added
     * @throws SQLException
     *             if an error occurs
     */
    public void addRewardCategory(String rewardName, String rewardID) throws SQLException {
        executeUpdate(
            "INSERT INTO reward_category (reward_category_code, name) "
            + "VALUES (?, ?)", (stmt) -> {
                stmt.setString(1, rewardID);
                stmt.setString(2, rewardName);
        });
    }

    /**
     * Enrolls a customer to a brand's loyalty program in the database.
     * 
     * @param customerID
     *            id of the customer to be enrolled
     * @param lpCode
     *            code of the loyalty program the customer will be
     *            enrolled in
     * @throws SQLException
     *             if an error occurs
     */
    public boolean enrollInLoyaltyProgram(String customerID, String lpCode) throws SQLException {
        try {
            executeUpdate(
                "INSERT INTO customer_enrollment (customer_id, lp_code, num_points, tier_level) "
                + "VALUES (?, ?, 0, 0)", (stmt) -> {
                    stmt.setString(1, customerID);
                    stmt.setString(2, lpCode);
            });
        } catch (SQLException ex) {
            if (ex.getMessage().toLowerCase().contains("customer_already_enrolled")) {
                return false;
            }
            throw ex;
        }
        return true;
    }

    /**
     * Returns a list of all loyalty programs that the given customer is enrolled
     * in.
     * 
     * @param customerID
     *            id of the customer to find enrolled loyalty programs for
     * @return list of all loyalty programs that the given customer is enrolled in
     * @throws SQLException
     *             if an error occurs
     */
    public String[] getAllEnrolledLoyaltyPrograms(String customerID) throws SQLException {
        return executeQuery(
            "SELECT L.code, L.name "
            + "FROM loyalty_program L "
            + "WHERE L.code IN ( "
            + "    SELECT CE.lp_code "
            + "    FROM customer_enrollment CE "
            + "    WHERE CE.lp_code = L.code AND CE.customer_id = ? "
            + ")",
            (stmt) -> { stmt.setString(1, customerID); },
            (rs) -> {
                List<String> loyaltyProgDescs = new ArrayList<>();
                while (rs.next()) {
                    String lpCode = rs.getString("code");
                    String loyaltyProgramName = rs.getString("name");
                    loyaltyProgDescs.add("Loyalty Program Code: " + lpCode + ", Name: " + loyaltyProgramName);
                }
                String[] loyaltyProgsArr = loyaltyProgDescs.toArray(new String[0]);
                return loyaltyProgsArr;
            }
        );
    }

    /**
     * Returns a list of all activities associated with the given loyalty program.
     * 
     * @param lpCode
     *            code of loyalty program to find activities for
     * @return list of all activities associated with the given loyalty program
     * @throws SQLException
     *             if an error occurs
     */
    public String[] getLoyaltyProgramActivities(String lpCode) throws SQLException {
        return executeQuery(
            "SELECT AC.name "
            + "FROM activity_category AC "
            + "WHERE AC.activity_category_code IN ( "
            + "    SELECT LAC.activity_category_code "
            + "    FROM lp_activity_category LAC "
            + "    WHERE LAC.lp_code = ? "
            + ")",
            (stmt) -> { stmt.setString(1, lpCode); },
            (rs) -> {
                List<String> activityCategoryNames = new ArrayList<>();
                while (rs.next()) {
                    String activityCategory = rs.getString("name");
                    activityCategoryNames.add(activityCategory);
                }
                String[] actCatNamesArr = activityCategoryNames.toArray(new String[0]);
                return actCatNamesArr;
            }
        );
    }

    /**
     * Shows the contents of a customer's wallet.
     * 
     * @param customerID
     *            id of the customer to show the wallet for
     * @throws SQLException
     *             if an error occurs
     */
    public void viewWallet(String customerID) throws SQLException {
        executeQuery(
            "SELECT activity_id, lp_code, points, re_rule_code " +
            "FROM wallet_activity " +
            "WHERE wallet_id = ( " +
            "    SELECT wallet_id " +
            "    FROM customer " +
            "    WHERE user_id = ? " +
            ")",
            (stmt) -> { stmt.setString(1, customerID); },
            (rs) -> {
                printQuery(rs);
            }
        );
    }

    /**
     * Returns a list of all available rewards that can be redeemed by the give
     * customer.
     * 
     * @param customerID
     *            id of the customer to get all available rewards for
     * @return list of all available rewards that can be redeemed by the give
     *         customer
     * @throws SQLException
     *             if an error occurs
     */
    public String[] getAvailableRewardsToRedeem(String customerID) throws SQLException {
        // TODO 16
        return executeQuery(
            "SELECT RW.lp_code, RW.reward_id, RW.reward_category_code " +
            "FROM reward RW " +
            "WHERE RW.reward_category_code IN ( " +
            "    SELECT RRR.reward_category_code " +
            "    FROM reward_rule R, reward_redeeming_rule RRR " +
            "    WHERE RW.lp_code = R.lp_code AND R.rule_code = RRR.rule_code AND R.lp_code = RRR.lp_code " +
            "    AND R.num_points <= ( " +
            "        SELECT CE.num_points " +
            "        FROM customer_enrollment CE " +
            "        WHERE CE.lp_code = R.lp_code AND CE.customer_id = ? " +
            "    ) " +
            ") " +
            "ORDER BY RW.lp_code",
            (stmt) -> { stmt.setString(1, customerID); },
            (rs) -> {
                List<String> rewardsAvailableDesc = new ArrayList<>();
                while (rs.next()) {
                    String lpCode = rs.getString("lp_code");
                    int rewardId = rs.getInt("reward_id");
                    String rewardCatCode = rs.getString("reward_category_code");

                    String rewardCategoryName = executeQuery(
                        "SELECT name FROM reward_category WHERE reward_category_code = ?",
                        (stmt) -> { stmt.setString(1, rewardCatCode); },
                        (rsCatName) -> {
                            rsCatName.next();
                            return rsCatName.getString("name");
                        }
                    );

                    rewardsAvailableDesc.add("Loyalty Program Code: " + lpCode + ", Reward Category: " + rewardCategoryName
                            + ", Reward ID: " + rewardId);
                }
                String[] rewardsAvailableDescArr = rewardsAvailableDesc.toArray(new String[0]);
                return rewardsAvailableDescArr;
            }
        );
    }

    /**
     * Redeems reward(s) for the given customer.
     * 
     * @param customerID
     *            id of the customer redeeming the reward(s)
     * @param rewardID
     *            id of the reward(s) to be redeemed
     * @param lpCode
     *            code of the loyalty program whose brand will have the reward
     *            redeemed from
     * @param amount
     *            amount of the reward to be redeemed
     * @return true if the given reward(s) can be redeemed by the given customer,
     *         false otherwise
     * @throws SQLException
     *             if an error occurs
     */
    public boolean redeemRewards(String customerID, int rewardID, String lpCode, int amount) throws SQLException {
        // final int instancesLeft;
        // try (ResultSet rs = executeQuery("SELECT num_instances FROM reward WHERE reward_id = ? AND lp_code = ?", (stmt) -> {
        //     stmt.setInt(1, rewardID);
        //     stmt.setString(2, lpCode);
        // })) {
        //     rs.next();
        //     int numInstances = rs.getInt("num_instances");
        //     if (numInstances < amount) {
        //         return false;
        //     }
        //     instancesLeft = numInstances - amount;
        // }

        // executeUpdate("UPDATE reward SET num_instances = ? WHERE reward_id = ?", (stmt) -> {
        //     stmt.setInt(1, instancesLeft);
        //     stmt.setInt(2, rewardID);
        // });

        java.util.Date now = new java.util.Date();
        java.sql.Date nowSql = new java.sql.Date(now.getTime());

        int numPoints = executeQuery(
            "SELECT R.num_points "
            + "FROM reward_rule R, reward_redeeming_rule RRR "
            + "WHERE R.rule_code = RRR.rule_code AND R.lp_code = RRR.lp_code AND RRR.reward_category_code IN ("
            + "    SELECT RW.reward_category_code "
            + "    FROM reward RW "
            + "    WHERE RW.reward_id = ?"
            + ")",
            (stmt) -> { stmt.setInt(1, rewardID); },
            (rs) -> {
                rs.next();
                return rs.getInt("num_points") * amount;
            }
        );

        // triggers will handle point subtraction
        try {
            executeUpdate(
                "INSERT INTO redeemed_reward (customer_id, reward_id, lp_code, num_points, num_instances, reward_date) "
                + "VALUES (?, ?, ?, ?, ?, ?)",
                (stmt) -> {
                    stmt.setString(1, customerID);
                    stmt.setInt(2, rewardID);
                    stmt.setString(3, lpCode);
                    stmt.setInt(4, numPoints);
                    stmt.setInt(5, amount);
                    stmt.setDate(6, nowSql);
            });
        } catch (SQLException ex) {
            // if check constraint violation then this reward cannot be redeemed
            String message = ex.getMessage();
            if (message.toLowerCase().contains("num_points_nonnegative") || message.toLowerCase().contains("num_instances_nonnegative")) {
                return false;
            }
            throw ex;
        }
        return true;
    }

    /**
     * Returns a list of all gift cards redeemed by the given customer in the given
     * loyalty program.
     * 
     * @param customerID
     *            id of the customer to find redeemed gift cards for
     * @param lpCode
     *            code of the loyalty program to find the redeemed gift cards in
     * @return list of all gift cards redeemed by the given customer in the given
     *         loyalty program
     * @throws SQLException
     *             if an error occurs
     */
    public String[] getRedeemedGiftCards(String customerID, String lpCode) throws SQLException {
        return executeQuery(
            "SELECT reward_id, num_instances FROM redeemed_reward WHERE customer_id = ? AND lp_code = ? AND num_instances > 0",
            (stmt) -> {
                stmt.setString(1, customerID);
                stmt.setString(2, lpCode);
            },
            (rs) -> {
                List<String> redeemedGiftCards = new ArrayList<>();
                while (rs.next()) {
                    String rewardId = rs.getString("reward_id");
                    int numInstances = rs.getInt("num_instances");
                    redeemedGiftCards.add("Gift Card ID: " + rewardId + ", Quantity: " + numInstances);
                }
                String[] redeemedGiftCardsArr = redeemedGiftCards.toArray(new String[0]);
                return redeemedGiftCardsArr;
            }
        );
    }

    /**
     * Makes a purchase with an optional gift card for a loyalty program that the
     * given customer is enrolled in.
     * 
     * @param customerID
     *            id of the customer making the purchase
     * @param lpCode
     *            code of the LP that the customer is making a purchase for
     * @param amount amount of money an item being purchased costs
     * @param giftCardID
     *            id of the gift card to be used with the purchase
     * @throws SQLException
     *             if an error occurs
     */
    public void makePurchase(String customerID, String lpCode, double purchaseAmount, int giftCardID) throws SQLException {
        double amount = purchaseAmount;
        if (giftCardID != -1) {
            int giftCardAmount = executeQuery(
                "SELECT amount FROM gift_card WHERE lp_code = ? AND reward_id = ?",
                (stmt) -> {
                    stmt.setString(1, lpCode);
                    stmt.setInt(2, giftCardID);
                },
                (rs) -> {
                    rs.next();
                    return rs.getInt("amount");
                }
            );

            int numCards = executeQuery(
                "SELECT num_instances FROM redeemed_reward WHERE customer_id = ? AND lp_code = ? AND reward_id = ?",
                (stmt) -> {
                    stmt.setString(1, customerID);
                    stmt.setString(2, lpCode);
                    stmt.setInt(3, giftCardID);
                },
                (rs) -> {
                    rs.next();
                    return rs.getInt("num_instances");
                }
            );
            executeUpdate(
                "UPDATE redeemed_reward SET num_instances = ? WHERE customer_id = ? AND lp_code = ? AND reward_id = ?",
                (stmt) -> {
                    stmt.setInt(1, numCards - 1);
                    stmt.setString(2, customerID);
                    stmt.setString(3, lpCode);
                    stmt.setInt(4, giftCardID);
                }
            );

            // we should not log activity which will add additional points if gift card purchase was made
            if (giftCardAmount >= purchaseAmount) {
                return;
            }
            amount = purchaseAmount - giftCardAmount;
        }

        java.util.Date now = new java.util.Date();
        java.sql.Date nowSql = new java.sql.Date(now.getTime());

        int nextActivityId = executeQuery(
            "SELECT MAX(activity_id) AS highest_id FROM activity",
            (rs) -> {
                rs.next();
                return rs.getInt("highest_id") + 1;
            }
        );

        executeUpdate(
            "INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date) "
            + "VALUES (?, ?, 'A01', ?)", (stmt) -> {
                stmt.setInt(1, nextActivityId);
                stmt.setString(2, customerID);
                stmt.setDate(3, nowSql);
        });

        final double amt = amount;
        executeUpdate(
            "INSERT INTO purchase (activity_id, amount) "
            + "VALUES (?, ?)", (stmt) -> {
                stmt.setInt(1, nextActivityId);
                stmt.setDouble(2, amt);
        });
        
        String walletId = executeQuery(
            "SELECT wallet_id FROM customer WHERE user_id = ?",
            (stmt) -> { stmt.setString(1, customerID); },
            (rs) -> {
                rs.next();
                return rs.getString("wallet_id");
            }
        );

        executeUpdate(
            "INSERT INTO wallet_activity (wallet_id, activity_id, lp_code) "
            + "VALUES (?, ?, ?)", (stmt) -> {
                stmt.setString(1, walletId);
                stmt.setInt(2, nextActivityId);
                stmt.setString(3, lpCode);
        });
    }

    /**
     * Leaves a review for a loyalty program that the given customer is enrolled in.
     * 
     * @param customerID
     *            id of the customer leaving the review
     * @param lpCode
     *            code of the loyalty program that the customer is leaving a review for
     * @param review
     *            content of the review being left by the customer
     * @throws SQLException
     *             if an error occurs
     */
    public void leaveReview(String customerID, String lpCode, String review) throws SQLException {
        java.util.Date now = new java.util.Date();
        java.sql.Date nowSql = new java.sql.Date(now.getTime());

        int nextActivityId = executeQuery(
            "SELECT MAX(activity_id) AS highest_id FROM activity",
            (rs) -> {
                rs.next();
                return rs.getInt("highest_id") + 1;
            }
        );

        executeUpdate(
            "INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date) "
            + "VALUES (?, ?, 'A02', ?)", (stmt) -> {
                stmt.setInt(1, nextActivityId);
                stmt.setString(2, customerID);
                stmt.setDate(3, nowSql);
        });

        executeUpdate(
            "INSERT INTO review (activity_id, content) "
            + "VALUES (?, ?)", (stmt) -> {
                stmt.setInt(1, nextActivityId);
                stmt.setString(2, review);
        });

        String walletId = executeQuery(
            "SELECT wallet_id FROM customer WHERE user_id = ?",
            (stmt) -> { stmt.setString(1, customerID); },
            (rs) -> {
                rs.next();
                return rs.getString("wallet_id");
            }
        );

        executeUpdate(
            "INSERT INTO wallet_activity (wallet_id, activity_id, lp_code) "
            + "VALUES (?, ?, ?)", (stmt) -> {
                stmt.setString(1, walletId);
                stmt.setInt(2, nextActivityId);
                stmt.setString(3, lpCode);
        });
    }

    /**
     * Refers a friend for the loyalty program that the given customer is enrolled
     * in.
     * 
     * @param customerID
     *            id of the customer referring a friend
     * @param lpCode
     *            code of the loyalty program that the customer is referring a friend for
     * @throws SQLException
     *             if an error occurs
     */
    public void referFriend(String customerID, String lpCode) throws SQLException {
        java.util.Date now = new java.util.Date();
        java.sql.Date nowSql = new java.sql.Date(now.getTime());

        int nextActivityId = executeQuery(
            "SELECT MAX(activity_id) AS highest_id FROM activity",
            (rs) -> {
                rs.next();
                return rs.getInt("highest_id") + 1;
            }
        );

        executeUpdate(
            "INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date) "
            + "VALUES (?, ?, 'A03', ?)", (stmt) -> {
                stmt.setInt(1, nextActivityId);
                stmt.setString(2, customerID);
                stmt.setDate(3, nowSql);
        });

        executeUpdate(
            "INSERT INTO friend_reference (activity_id) "
            + "VALUES (?)", (stmt) -> {
                stmt.setInt(1, nextActivityId);
        });

        String walletId = executeQuery(
            "SELECT wallet_id FROM customer WHERE user_id = ?",
            (stmt) -> { stmt.setString(1, customerID); },
            (rs) -> {
                rs.next();
                return rs.getString("wallet_id");
            }
        );

        executeUpdate(
            "INSERT INTO wallet_activity (wallet_id, activity_id, lp_code) "
            + "VALUES (?, ?, ?)", (stmt) -> {
                stmt.setString(1, walletId);
                stmt.setInt(2, nextActivityId);
                stmt.setString(3, lpCode);
        });
    }

    /**
     * Does an activity for the loyalty program that the given customer is enrolled
     * in.
     * 
     * @param customerID
     *            id of the customer referring a friend
     * @param lpCode
     *            code of the loyalty program the customer is doing the activity for
     * @param activityName
     *            name of the activity to be done by the customer
     * @throws SQLException
     *             if an error occurs
     */
    public void doActivity(String customerID, String lpCode, String activityName) throws SQLException {
        java.util.Date now = new java.util.Date();
        java.sql.Date nowSql = new java.sql.Date(now.getTime());

        int nextActivityId = executeQuery(
            "SELECT MAX(activity_id) AS highest_id FROM activity",
            (rs) -> {
                rs.next();
                return rs.getInt("highest_id") + 1;
            }
        );

        String activityCategoryCode = executeQuery(
            "SELECT activity_category_code FROM activity_category WHERE name = ?",
            (stmt) -> { stmt.setString(1, activityName); },
            (rs) -> {
                rs.next();
                return rs.getString("activity_category_code");
            }
        );

        executeUpdate(
            "INSERT INTO activity (activity_id, customer_id, activity_category_code, activity_date) "
            + "VALUES (?, ?, ?, ?)", (stmt) -> {
                stmt.setInt(1, nextActivityId);
                stmt.setString(2, customerID);
                stmt.setString(3, activityCategoryCode);
                stmt.setDate(4, nowSql);
        });

        String walletId = executeQuery(
            "SELECT wallet_id FROM customer WHERE user_id = ?",
            (stmt) -> { stmt.setString(1, customerID); },
            (rs) -> {
                rs.next();
                return rs.getString("wallet_id");
            }
        );

        executeUpdate(
            "INSERT INTO wallet_activity (wallet_id, activity_id, lp_code) "
            + "VALUES (?, ?, ?)", (stmt) -> {
                stmt.setString(1, walletId);
                stmt.setInt(2, nextActivityId);
                stmt.setString(3, lpCode);
        });
    }

    /**
     * Returns a list of all activity categories in the database.
     * 
     * @return list of all activity categories in the database
     * @throws SQLException
     *             if an error occurs
     */
    public String[] getAllActivityCategories() throws SQLException {
        return executeQuery(
            "SELECT name FROM activity_category",
            (rs) -> {
                List<String> categories = new ArrayList<>();
                while (rs.next()) {
                    categories.add(rs.getString("name"));
                }
                String[] catArr = categories.toArray(new String[0]);
                return catArr;
            }
        );
    }

    /**
     * Returns a list of all reward categories in the database.
     * 
     * @return list of all reward categories in the database
     * @throws SQLException
     *             if an error occurs
     */
    public String[] getAllRewardCategories() throws SQLException {
        return executeQuery(
            "SELECT name FROM reward_category",
            (rs) -> {
                List<String> categories = new ArrayList<>();
                while (rs.next()) {
                    categories.add(rs.getString("name"));
                }
                String[] catArr = categories.toArray(new String[0]);
                return catArr;
            }
        );
    }

    /**
     * Adds a regular loyalty program to the database.
     * 
     * @param brandID
     *            id of the brand that the loyalty program will be created for
     * @param loyaltyProgramName
     *            name of the loyalty program to be created
     */
    public boolean addRegularLoyaltyProgram(String brandID, String loyaltyProgramName) throws SQLException {
        boolean brandHasLP = executeQuery(
            "SELECT * FROM loyalty_program WHERE brand_guid = ?",
            (stmt) -> { stmt.setString(1, brandID); },
            (rs) -> { return rs.next(); }
        );

        if (brandHasLP) {
            return false;
        }
        
        String lpCode = executeQuery(
            "SELECT code FROM loyalty_program WHERE type = 'regular'",
            (rs) -> {
                int maxCode = 0;
                while (rs.next()) {
                    int code = Integer.parseInt(rs.getString("code").substring(3)); // strip 'RLP'
                    maxCode = Math.max(code, maxCode);
                }
                return "RLP" + String.format("%02d", maxCode + 1);
            }
        );
        executeUpdate(
            "INSERT INTO loyalty_program (code, type, name, brand_guid, status) "
            + "VALUES (?, 'regular', ?, ?, 'Inactive')", (stmt) -> {
                stmt.setString(1, lpCode);
                stmt.setString(2, loyaltyProgramName);
                stmt.setString(3, brandID);
        });

        return true;
    }

    /**
     * Adds a tiered loyalty program to the database.
     * 
     * @param brandID
     *            id of the brand that the loyalty program will be created for
     * @param loyaltyProgramName
     *            name of the loyalty program to be created
     */
    public boolean addTieredLoyaltyProgram(String brandID, String loyaltyProgramName) throws SQLException {
        boolean brandHasLP = executeQuery(
            "SELECT * FROM loyalty_program WHERE brand_guid = ?",
            (stmt) -> { stmt.setString(1, brandID); },
            (rs) -> { return rs.next(); }
        );

        if (brandHasLP) {
            return false;
        }
        
        String lpCode = executeQuery(
            "SELECT code FROM loyalty_program WHERE type = 'tiered'",
            (rs) -> {
                int maxCode = 0;
                while (rs.next()) {
                    int code = Integer.parseInt(rs.getString("code").substring(3)); // strip 'TLP'
                    maxCode = Math.max(code, maxCode);
                }
                return "TLP" + String.format("%02d", maxCode + 1);
            }
        );
        executeUpdate(
            "INSERT INTO loyalty_program (code, type, name, brand_guid, status) "
            + "VALUES (?, 'tiered', ?, ?, 'Inactive')", (stmt) -> {
                stmt.setString(1, lpCode);
                stmt.setString(2, loyaltyProgramName);
                stmt.setString(3, brandID);
        });

        return true;
    }
}
