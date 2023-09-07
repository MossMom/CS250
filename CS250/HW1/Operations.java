package CS250.HW1;

public class Operations {
// Task 1
    static void checkArgs(String[] args) { // Check if number of args is correct (3)
        if(args.length == 3) {
            System.out.println("Correct number of arguments given.");
        } else {
            System.out.println("Incorrect number of arguments have been provided. Program Terminating!");
            System.exit(1); // Exit code 1 to signify Task 1 failed
        }
    }
// Task 2
    static void identifyNums(String[] args) { // Identify which numbering system is used for all 3 args
        for (int i = 0; i < args.length; i++) {
            if(args[i].length() < 2) { //If the arg isn't at least 2 characters long (such as single digit decimal nums)
                System.out.println(args[i]+"=Decimal"); // "All other args will be treated as Decimal"
                break;
            }
            String id = args[i].substring(0,2).toLowerCase(); // Set "id" to the 1st and 2nd characters of args[i]
            switch (id) {
                case "0b": // Check for binary id
                    System.out.println(args[i]+"=Binary");
                    break;
                case "0x": // Check for hex id
                    System.out.println(args[i]+"=Hexadecimal");
                    break;
                default: // "All other args will be treated as Decimal"
                    System.out.println(args[i]+"=Decimal");
                    break;
            }
        }
    }
// Task 3
    static void verifyNums(String[] args) { // Verify that each arg is a valid number of its type
        int wrong = 0; // Used to track amt of invalid nums
        for (int i = 0; i < args.length; i++) {
            String id = identifyNum(args[i]); // Get the number's ID
            String num = args[i];
            String num2 = num.substring(2,num.length()); // Remove ID from num
            switch (id) {
                case "0b": // Binary numbers
                    Boolean validBin = iterateVerify(num2, (int)'0', (int)'1'); // Send num without its id to verify helper function
                    if (!validBin)
                        wrong++; // Add 1 to wrong if not valid, used to terminate after checking
                    System.out.println(num+"="+validBin);
                    break;
                case "0x": // Hexadecimal numbers
                    Boolean validHex = iterateVerify(num2, (int)'0', (int)'9', (int)'a', (int)'f'); // Send num without its id to hex specific verify helper function
                    if (!validHex)
                        wrong++; // Add 1 to wrong if not valid
                    System.out.println(num+"="+validHex);
                    break;
                default: // Decimal numbers
                    Boolean validDec = iterateVerify(num2, (int)'0', (int)'9'); // Send num without its id to verify helper function
                    if (!validDec)
                        wrong++; // Add 1 to wrong if not valid
                    System.out.println(num+"="+validDec);
                    break;
            }
        }
        if (wrong > 0) // If any were invalid, terminate program
            System.exit(3);
    }
// Helper Functions
// Identify & return the numbering system id of a provided arg
    static String identifyNum(String arg) {
        if(arg.length() < 2) { //If the arg isn't at least 2 characters long
            return arg; //Return the single decimal's char
        }
        return arg.substring(0,2).toLowerCase(); // Returns 0b, 0x, or the decimal's first 2 chars
    }
// Iterate through a string and use ascii comparisons to determine validity of binary or decimal numbers
    static boolean iterateVerify(String arg, int min, int max){ 
        for (int i = 0; i < arg.length(); i++) {
            int ascii = arg.charAt(i);
            if (!(min <= ascii && ascii <= max)) { // Check if the character's ascii value is NOT within its system's "valid range"
                return false;
            }
        }
        return true;
    }
// Also iterate through a string and use ascii comparisons to determine validity of hex numbers
    static boolean iterateVerify(String arg, int minNum, int maxNum, int minChar, int maxChar){
        arg = arg.toLowerCase(); // Set any letters to lowercase
        for (int i = 0; i < arg.length(); i++) {
            int ascii = arg.charAt(i); // Grab a character and store as its ascii value
            if (!((minNum <= ascii && ascii <= maxNum)||(minChar <= ascii && ascii <= maxChar))) { // Check if the character's ascii value is NOT within its system's "valid range"
                return false;
            }
        }
        return true;
    }

// Main loop
    public static void main(String[] args) {
        // TODO REPLACE TEST INPUT WITH ^ MAIN'S ARGS!!!
            String[] args2 = {"10", "0b10010110", "0xF3A"};
        
        // Task 1
        System.out.println("Task 1"); // WORKS
        checkArgs(args2);

        // Task 2
        System.out.println("\nTask 2"); // WORKS
        identifyNums(args2);

        // Task 3
        System.out.println("\nTask 3"); // WORKS
        verifyNums(args2);

        // Task 4
        System.out.println("\nTask 4\n-"); // TODO

        // Task 5
        System.out.println("\nTask 5\n-"); // TODO

        // Task 6
        System.out.println("\nTask 6\n-"); // TODO

        // Task 7
        System.out.println("\nTask 7\n-"); // TODO

        // Task 8
        System.out.println("\nTask 8\n-"); // TODO
    }
}