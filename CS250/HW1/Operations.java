package CS250.HW1;

public class Operations {

// Task 1 ~~~~~~~~~~
// Check if number of args is correct (3)
    static void checkArgs(String[] args) {
        if(args.length == 3) {
            System.out.println("Correct number of arguments given.");
        } else {
            System.out.println("Incorrect number of arguments have been provided. Program Terminating!");
            System.exit(1); // Exit code 1 to signify Task 1 failed
        }
    }

// Task 2 ~~~~~~~~~~
 // Identify which numbering system is used for all 3 args
    static void identifyNums(String[] args) {
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

// Task 3 ~~~~~~~~~~
// Verify that each arg is a valid number of its type
    static void verifyNums(String[] args) {
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

// Task 4 ~~~~~~~~~~
// Convert all nums to other number systems
    static void convertNums(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String start = args[i], bin = "0b", dec = "", hex = "0x"; // Set starting values
            String id = identifyNum(start), num = start;
            String num2 = start.substring(2,start.length()); // Remove ID from start

            switch (identifyNum(start)) { // Conversions
                case "0b": // If start is a binary number
                    bin = start; // No change
                    dec += toDecimal(num2, id);
                    hex += toHex(num2, id);
                    break;
                case "0x": // If start is a hex number
                    bin += toBinary(num2, id);
                    dec += toDecimal(num2, id);
                    hex = start; // No change
                    break;
                default: // If start is a decimal number
                    bin += toBinary(num, id);
                    dec = start; // No change
                    hex += toHex(num, id);
                    break;
            }

            System.out.print("Start=" + start); // Printing of values
            System.out.print(",Binary=" + bin);
            System.out.print(",Decimal=" + dec);
            System.out.println(",Hexadecimal=" + hex);
        }
    }

// Task 5 ~~~~~~~~~~
// Calculate ones complement
    static void onesComplement(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String start = args[i];
            String bin, ones;
            String id = identifyNum(start), num = start;
            String num2 = start.substring(2,start.length()); // Remove ID from start

            switch (identifyNum(start)) { // Conversions
                case "0b": // If start is a binary number
                    bin = num2;
                    ones = complementConversion(bin, false);
                    break;
                case "0x": // If start is a hex number
                    bin = toBinary(num2, id);
                    ones = complementConversion(bin, false);
                    break;
                default: // If start is a decimal number
                    bin = toBinary(num, id);
                    ones = complementConversion(bin, false);
                    break;
            }
            
            System.out.print(start + "="); // Printing
            System.out.print(bin + "=>");
            System.out.println(ones);
        }
    }

// Task 6 ~~~~~~~~~~
// Calculate twos complement
    static void twosComplement(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String start = args[i];
            String bin, ones;
            String id = identifyNum(start), num = start;
            String num2 = start.substring(2,start.length()); // Remove ID from start

            switch (identifyNum(start)) { // Conversions
                case "0b": // If start is a binary number
                    bin = num2;
                    ones = complementConversion(bin, true);
                    break;
                case "0x": // If start is a hex number
                    bin = toBinary(num2, id);
                    ones = complementConversion(bin, true);
                    break;
                default: // If start is a decimal number
                    bin = toBinary(num, id);
                    ones = complementConversion(bin, true);
                    break;
            }
            
            System.out.print(start + "="); // Printing
            System.out.print(bin + "=>");
            System.out.println(ones);
        }
    }

// Task 7 ~~~~~~~~~~
// Calculate OR, AND, & XOR as binary
    static void bitwiseLogic(String[] args) {
            String id0 = identifyNum(args[0]), num0 = args[0], bin0; // Initalize each of the 3 argument's variables
            String num02 = num0.substring(2,num0.length()); // Remove ID from start
            String id1 = identifyNum(args[1]), num1 = args[1], bin1; // INIT
            String num12 = num1.substring(2,num1.length()); // -ID
            String id2 = identifyNum(args[2]), num2 = args[2], bin2; // INIT
            String num22 = num2.substring(2,num2.length()); // -ID

            switch (identifyNum(num0)) { // Conversions of first number
                case "0b": // If start is a binary number
                    bin0 = num02;
                    break;
                case "0x": // If start is a hex number
                    bin0 = toBinary(num02, id0);
                    break;
                default: // If start is a decimal number
                    bin0 = toBinary(num0, id0);
                    break;
            }
            switch (identifyNum(num1)) { // Conversions of second number
                case "0b": // If start is a binary number
                    bin1 = num12;
                    break;
                case "0x": // If start is a hex number
                    bin1 = toBinary(num12, id1);
                    break;
                default: // If start is a decimal number
                    bin1 = toBinary(num1, id1);
                    break;
            }
            switch (identifyNum(num2)) { // Conversions of third number
                case "0b": // If start is a binary number
                    bin2 = num22;
                    break;
                case "0x": // If start is a hex number
                    bin2 = toBinary(num22, id2);
                    break;
                default: // If start is a decimal number
                    bin2 = toBinary(num2, id2);
                    break;
            }

            String[] bins = {bin0, bin1, bin2};

            String resultOR = bitOR(bins, false); // Collect operation outcomes
            String resultAND = bitAND(bins);
            String resultXOR = bitOR(bins, true);

            System.out.println(bin0 + "|" + bin1 + "|" + bin2 + "=" + resultOR); // Printing first opperation
            System.out.println(bin0 + "&" + bin1 + "&" + bin2 + "=" + resultAND); // Printing second opperation
            System.out.println(bin0 + "^" + bin1 + "^" + bin2 + "=" + resultXOR); // Printing third opperation
        }

// Task 8 ~~~~~~~~~~
// Shift binary of each arg by 2 spaces, both ways
    static void binaryShift(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String start = args[i];
            String bin;
            String id = identifyNum(start), num = start;
            String num2 = start.substring(2,start.length()); // Remove ID from start

            switch (identifyNum(start)) { // Conversions
                case "0b": // If start is a binary number
                    bin = num2;
                    break;
                case "0x": // If start is a hex number
                    bin = toBinary(num2, id);
                    break;
                default: // If start is a decimal number
                    bin = toBinary(num, id);
                    break;
            }
            
            System.out.print(bin + "<<2=" + shift(bin, -2) + ","); // Printing
            System.out.println(bin + ">>2=" + shift(bin, 2));
        }
    }

    // ~~~~~~~~~~ Helper Functions ~~~~~~~~~~
// Identify & return the numbering system id ----------
    static String identifyNum(String arg) {
        if(arg.length() < 2) { //If the arg isn't at least 2 characters long
            return arg; //Return the single decimal's char
        }
        return arg.substring(0,2).toLowerCase(); // Returns 0b, 0x, or the decimal's first 2 chars
    }

// Verify values of binary or decimal numbers ----------
    static boolean iterateVerify(String arg, int min, int max) {
        for (int i = 0; i < arg.length(); i++) {
            int ascii = arg.charAt(i);
            if (!(min <= ascii && ascii <= max)) { // Check if the character's ascii value is NOT within its system's "valid range"
                return false;
            }
        }
        return true;
    }

// Verify values of hex numbers ----------
    static boolean iterateVerify(String arg, int minNum, int maxNum, int minChar, int maxChar) {
        arg = arg.toLowerCase(); // Set any letters to lowercase
        for (int i = 0; i < arg.length(); i++) {
            int ascii = arg.charAt(i); // Grab a character and store as its ascii value
            if (!((minNum <= ascii && ascii <= maxNum)||(minChar <= ascii && ascii <= maxChar))) { // Check if the character's ascii value is NOT within its system's "valid range"
                return false;
            }
        }
        return true;
    }

// Convert to binary ----------
    static String toBinary(String arg, String id) {
        String output = "", rev = "";
        char temp;
        int div, mod;

        switch (id) {
            case "0x": // Hex->Bin conversion (hex->dec->bin)
                arg = toDecimal(arg, id);
                arg = toBinary(arg, "dec");
                output = arg;
                break;
            
            case "0b": // If a bin num was given, return arg
                output = arg;
                break;
        
            default: // Dec->Bin conversion (used by hex's conversion)
                div = getIntString(arg, 10);
                while (div > 1) {
                        mod = div % 2;
                        output = output + mod;
                        div /= 2;
                }
                output += div % 2; // Last binary digit
                for (int i = 0; i < output.length(); i++) { // Reverse the string
                    temp = output.charAt(i);
                    rev = temp + rev;
                }
                output = rev;
                break;
        }
        return output;
    }

// Convert to decimal ----------
    static String toDecimal(String arg, String id) {
        String output = "";

        int value = 0;
        switch (id) {
            case "0b": // Bin->Dec conversion
                value = getIntString(arg, 2);
                output += value;
                break;
            
            case "0x": // Hex->Dec conversion
                value = getIntString(arg, 16);
                output += value;
                break;
        
            default: // If a dec num was given, return arg
                output = arg;
                break;
        }
        return output;
    }

// Convert to hexadecimal ----------
    static String toHex(String arg, String id) {
        String output = "";

        switch (id) {
            case "0b": // Bin->Hex conversion
                arg = toDecimal(arg, id);
                arg = toHex(arg, "dec");
                output = arg;
                break;
            
            case "0x": // If a hex num was given, return arg
                output = arg;
                break;
        
            default: // Dec->Hex conversion
                int div = getIntString(arg, 10);
                int mod;
                String result = "";
                char hexs[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
                while (div > 1) {
                        mod = div % 16;
                        result = hexs[mod] + "";
                        output = result + output;
                        div /= 16;
                }
                break;
        }
        return output;
    }

// Get number from string, doing base conversions ----------
    static int getIntString(String value, int base) {
        int output = 0;
        int add = 0;

        for (int i = 1; i <= value.length(); i++) { // For each "digit" of the number
            add = getIntChar(value.charAt(i-1)); // Return the int value of the char ('3' = 3, 'B' = 11. etc)
            add *= exp(base, value.length()-i); // Convert base and give the proper placement (1000 vs 10)
            output += add;
        }
        return output;
    }

// Get int value from chars '0'-'9' & 'A'-'F' ----------
    static int getIntChar(char value) {
        if ((48 <= (int)value && (int)value <= 57)) { // if the ascii is within 0-9 on ascii table
            return (int)value - 48; // return ascii - 48, which gives a range of 0-9
        }
        switch ((int)value) {
            case 97: // A
                return 10;
            case 98: // B
                return 11;
            case 99: // C
                return 12;
            case 100: // D
                return 13;
            case 101: // E
                return 14;
            case 102: // F
                return 15;
            default: // Shouldn't be reached
                return 999;
        }
    }

// Conversions for ones/twos complement ----------
    static String complementConversion(String arg, Boolean twos) {
        String output = "";

        for (int i = 0; i < arg.length(); i++) {
            if (arg.charAt(i) == '1') { // Simply add opposite characters to the output
                output += "0";
            } else {
                output += "1";
            }
        }
        if (twos) { // If doing twos complement, add 0b1
            output = binaryAddition(output, "1");
        }
        return output;
    }

// Add binary number strings ----------
    static String binaryAddition(String arg, String add) {
        String output = "";
        int length = arg.length();
        int arg2 = getIntString(arg, 2); // Convert to decimal
        int add2 = getIntString(add, 2);
        String result = arg2 + add2 + ""; // Add decimals
        output = toBinary(result, "dec"); // Convery back to binary

        while (output.length() < length) { // Add leading 0s if resulting binary number is less digits
            output = "0" + output;
        }
        return output;
    }

// Calculates OR / XOR of given binary ----------
    static String bitOR(String args[], Boolean x) {
        String output = "";
        int maxLength = -99;

        if (args[0].length() > args[1].length()) { // Find longest binary number
            if (args[0].length() > args[2].length()) { // Bin0 is longest
                maxLength = args[0].length();
            }
        } else if (args[1].length() > args[2].length()) { // Bin1 is longest
            maxLength = args[1].length();
        } else { // Bin2 is longest
            maxLength = args[2].length();
        }
        for (int i = 0; i < args.length; i++) { // Add leading 0s to the shorter numbers so they're all the same length
            while (args[i].length() < maxLength) {
                args[i] = "0" + args[i];
            }
        }
        if (!x) { // OR calculation
            for (int i = 0; i < maxLength; i++) {
                if (((int)args[0].charAt(i)-48) + ((int)args[1].charAt(i)-48) + ((int)args[2].charAt(i)-48) > 0) { // Checks if any of the characters, when added together, are more than 0 (meaning at least one of them is 1)
                    output += "1";
                } else {
                    output += "0";
                }
            }
        } else { // XOR calculation
            for (int i = 0; i < maxLength; i++) {
                char temp;
                if (((int)args[0].charAt(i)-48) + ((int)args[1].charAt(i)-48) == 1) { // Checks if any of the characters, when added together, equal exactly 1 (meaning only one of them is a 1)
                    temp = '1';
                } else {
                    temp = '0';
                }
                if ((int)temp-48 + ((int)args[2].charAt(i)-48) == 1) {
                    output += "1";
                } else {
                    output += "0";
                }
            }
        }
        return output;
    }

// Calculates AND of given binary ----------
    static String bitAND(String args[]) {
        String output = "";
        int maxLength = -99;

        if (args[0].length() > args[1].length()) { // Find longest binary number
            if (args[0].length() > args[2].length()) { // Bin0 is longest
                maxLength = args[0].length();
            }
        } else if (args[1].length() > args[2].length()) { // Bin1 is longest
            maxLength = args[1].length();
        } else { // Bin2 is longest
            maxLength = args[2].length();
        }
        for (int i = 0; i < args.length; i++) { // Add leading 0s to the shorter numbers so they're all the same length
            while (args[i].length() < maxLength) {
                args[i] = "0" + args[i];
            }
        }
        for (int i = 0; i < maxLength; i++) { // AND calculation
            if (((int)args[0].charAt(i)-48) * ((int)args[1].charAt(i)-48) * ((int)args[2].charAt(i)-48) > 0) { // Checks if any of the characters, when multiplied together, are more than 0 (meaning none of them is 0)
                output += "1";
            } else {
                output += "0";
            }
        }

        return output;
    }

// Returns a string of shifted binary ----------
    static String shift(String arg, int direction) {
        String output = arg;

        if (direction > 0) { // Right shift (1001 >>2 = 10)
            output = arg.substring(0, arg.length()-direction);
        } else if (direction < 0) { // Left shift (1001 <<2 = 100100)
            for (int i = 0; i < direction*-1; i++) {
                output += "0";
            }
        }

        return output;
    }

// Calculate exponents ----------
    static int exp(int base, int power) {
        int output = 1;
        int exp = power;
        while (exp != 0) {
            output *= base;
            exp--;
        }
        return output;
    }

// ~~~~~~~~~~ Main loop ~~~~~~~~~~
    public static void main(String[] args) {

        // Task 1
        System.out.println("Task 1"); // WORKS
        checkArgs(args);

        // Task 2
        System.out.println("\nTask 2"); // WORKS
        identifyNums(args);

        // Task 3
        System.out.println("\nTask 3"); // WORKS
        verifyNums(args);

        // Task 4
        System.out.println("\nTask 4"); // WORKS
        convertNums(args);

        // Task 5
        System.out.println("\nTask 5"); // WORKS
        onesComplement(args);

        // Task 6
        System.out.println("\nTask 6"); // WORKS
        twosComplement(args);

        // Task 7
        System.out.println("\nTask 7"); // WORKS
        bitwiseLogic(args);

        // Task 8
        System.out.println("\nTask 8"); // WORKS
        binaryShift(args);
    }
}