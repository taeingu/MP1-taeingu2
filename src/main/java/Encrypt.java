import java.util.Scanner;

/**
 * A class that "encrypts" data through a simple transformation.
 * <p>
 * The provided code is incomplete. Modify it so that it works properly and passes the unit tests in
 * <code>EncryptTest.java</code>.
 *
 * @see <a href="https://cs125.cs.illinois.edu/MP/1/">MP1 Documentation</a>
 * @see <a href="https://en.wikipedia.org/wiki/Caesar_cipher">Caesar Cipher Documentation</a>
 */
public class Encrypt {

    /**
     * the minimum shift the user can put.
     */
    public static final int MIN_SHIFT = -1024;

    /**
     * the maximum shift the user can put.
     */
    public static final int MAX_SHIFT = 1024;

    /** Transformation start. */
    public static final int TRANSFORM_START = (int) ' ';

    /** Transformation end. */
    public static final int TRANSFORM_END = (int) '~';

    /** Modulo to use for our transformation. */
    public static final int TRANSFORM_MODULUS = TRANSFORM_END - TRANSFORM_START + 1;

    /** the maximum value of character value. */
    public static final int MAX_CHARVAL = 126;

    /** the minimum value of character value. */
    public static final int MIN_CHARVAL = 32;

    /**
     * Encrypt a single line of text using a rotate-N transformation.
     * <p>
     * The printable range of ASCII characters starts at decimal value 32 (' ') and ends at 126
     * ('~'). You should shift characters within this range by the shift value provided. For
     * example, ' ' (32) shift 1 becomes '!' (33), while '~' (126) shift 1 wraps around and becomes
     * ' ' (32). You may want to explore modular arithmetic to simplify the transformation.
     * <p>
     * Your function should return a new character array, not modify the one that it is passed.
     * <p>
     * Both encrypter and decrypter may receive invalid inputs. If the character array contains invalid
     * characters (outside of the range defined above), or if the shift value is outside the range
     * defined above (e.g., larger than MAX_SHIFT), you should return null.
     * <p>
     * <strong>Your solution must match the expected output exactly, otherwise you will not receive
     * credit.</strong>
     * <p>
     * Complete the Javadoc comment for this function and write it.
     * <p>
     * @param line the array of char that is going to be encrypted
     * @param shift the int value that each char shifts to
     * @return encrypter the array of encrypted message.
     * </p>
     * @see <a href="http://www.asciitable.com/">ASCII Character Table</a>
     */
    public static char[] encrypter(final char[] line, final int shift) {
        char[] encrypted;
        encrypted = new char[line.length];
        for (int i = 0; i < line.length; i++) {
            int charVal = line[i] + shift;
            if (shift > MAX_SHIFT || shift < MIN_SHIFT) {
                return null;
            }
            if ((int) line[i] > TRANSFORM_END || (int) line[i] < TRANSFORM_START) {
                return null;
            }
            while (charVal > MAX_CHARVAL) {
                charVal = charVal - TRANSFORM_MODULUS;
            }
            while (charVal < MIN_CHARVAL) {
                charVal = charVal + TRANSFORM_MODULUS;
            }
            encrypted[i] = (char) charVal;
        }
        return encrypted;
    }

    /**
     * Decrypt a single line of text using a rotate-N transformation.
     * <p>
     * See comment for encrypter above.
     *
     * @param line array of characters to decrypt
     * @param shift amount to shift each character
     * @return line decrypted by rotating the specified amount
     * @see <a href="http://www.asciitable.com/">ASCII Character Table</a>
     */
    public static char[] decrypter(final char[] line, final int shift) {
        char[] decrypted;
        decrypted = new char[line.length];
        for (int i = 0; i < line.length; i++) {
            int originalChar = line[i] - shift;
            if (shift > MAX_SHIFT || shift < MIN_SHIFT) {
                return null;
            }
            if ((int) line[i] > TRANSFORM_END || (int) line[i] < TRANSFORM_START) {
               return null;
            }
            while (originalChar > MIN_CHARVAL) {
                originalChar = originalChar - TRANSFORM_MODULUS;
            }
            while (originalChar < MIN_CHARVAL) {
                originalChar = originalChar + TRANSFORM_MODULUS;
            }

            decrypted[i] = (char) originalChar;
        }
        return decrypted;
    }

    /* ********************************************************************************************
     * You do not need to modify code below this comment.
     **********************************************************************************************/

    /**
     * Solicits a single line of text from the user, encrypts it using a random shift, and then
     * decrypts it.
     * <p>
     * You are free to review this function, but should not modify it. Note that this function is
     * not tested by the test suite, as it is purely to aid your own interactive testing.
     *
     * @param unused unused input arguments
     */
    @SuppressWarnings("resource")
    public static void main(final String[] unused) {

        String linePrompt = "Enter a line of text, or a blank line to exit:";
        String shiftPrompt = "Enter an integer to shift by:";

        /*
         * Two steps here: first get a line, then a shift integer.
         */
        Scanner lineScanner = new Scanner(System.in);
        repeat: while (true) {
            String line = null;
            Integer shift = null;

            System.out.println(linePrompt);
            //noinspection LoopStatementThatDoesntLoop
            while (lineScanner.hasNextLine()) {
                line = lineScanner.nextLine();
                if (line.equals("")) {
                    break repeat;
                } else {
                    break;
                }
            }

            System.out.println(shiftPrompt);
            while (lineScanner.hasNextLine()) {
                Scanner intScanner = new Scanner(lineScanner.nextLine());
                if (intScanner.hasNextInt()) {
                    shift = intScanner.nextInt();
                    if (intScanner.hasNext()) {
                        shift = null;
                        System.out.println("Invalid input: please enter only a single integer.");
                    }
                } else {
                    System.out.println("Invalid input: please enter an integer.");
                }
                intScanner.close();
                if (shift != null) {
                    break;
                }
            }

            if (line == null || line.equals("")) {
                throw new RuntimeException("Should have a line at this point");
            }
            if (shift == null) {
                throw new RuntimeException("Should have a shift value at this point");
            }

            char[] originalCharacterArray = line.toCharArray();
            char[] encryptedCharacterArray = encrypter(originalCharacterArray, shift);
            char[] decryptedCharacterArray = decrypter(encryptedCharacterArray, shift);

            System.out.println("Encrypted line with ROT-" + shift + ":");
            assert encryptedCharacterArray != null;
            System.out.println(String.valueOf(encryptedCharacterArray));
            System.out.println("Decrypted line:");
            System.out.println(String.valueOf(decryptedCharacterArray));
            System.out.println("Original line:");
            System.out.println(String.valueOf(originalCharacterArray));
        }
        lineScanner.close();
    }
}
