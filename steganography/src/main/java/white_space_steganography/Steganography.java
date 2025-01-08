package white_space_steganography;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class Steganography {

    /**
     * Sequentially assigned character(A-Z) from messageToHide (message you want to hide)
     */
    public char letter;
    public String charToAdd;
    /**
     * First digit from 2-digit number(decNumberInAsciiTable).
     * Default value 0
     */
    public int firstDigitDecimalNumber;
    /**
     * second digit from 2-digit number(decNumberInAsciiTable)
     * Default value 0
     */
    public int secondDigitDecimalNumber;
    /**
     * 2-digit Decimal number that represent some character from ASCII Table.
     * firstDigitDecimalNumber + secondDigitDecimalNumber = decNumberInAsciiTable
     * Default value 0
     */
    public int decNumberInAsciiTable;
    /**
     * Store hidden message that is decoded from the file.
     */
    public ArrayList<Character> hiddenMessage;
    /**
     * Store message that is encoded in to the file.
     */
    public ArrayList<Character> messageToHide;
    /**
     * binary array in size of 4 bits. Contains exactly 4 integers (0 or 1).
     * Default value is [2,2,2,2]
     */
    public int[] fourBitsArray;

    public Steganography() {
        this.letter = '\u0000';     // Set to null character
        this.charToAdd = null;
        this.firstDigitDecimalNumber = 0;
        this.secondDigitDecimalNumber = 0;
        this.decNumberInAsciiTable = 0;
        this.hiddenMessage = new ArrayList<>();
        this.messageToHide = new ArrayList<>();
        this.fourBitsArray = new int[4];
    }

    // local variable
    private static int currentLine = 0;  // line counter

    public char getLetter() {
        return letter;
    }

    public String getCharToAdd() {
        return charToAdd;
    }

    public int getSecondDigitDecimalNumber() {
        return secondDigitDecimalNumber;
    }

    public int[] getFourBitsArray() {
        return fourBitsArray;
    }

    public static int getCurrentLine() {
        return currentLine;
    }

    public ArrayList<Character> getHiddenMessage() {
        return hiddenMessage;
    }

    public ArrayList<Character> getMessageToHide() {
        return messageToHide;
    }

    public int getFirstDigitDecimalNumber() {
        return firstDigitDecimalNumber;
    }

    public int getDecNumberInAsciiTable() {
        return decNumberInAsciiTable;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public void setCharToAdd(String charToAdd) {
        this.charToAdd = charToAdd;
    }

    public void setFirstDigitDecimalNumber(int firstDigitDecimalNumber) {
        this.firstDigitDecimalNumber = firstDigitDecimalNumber;
    }

    public void setSecondDigitDecimalNumber(int secondDigitDecimalNumber) {
        this.secondDigitDecimalNumber = secondDigitDecimalNumber;
    }

    public void setDecNumberInAsciiTable(int decNumberInAsciiTable) {
        this.decNumberInAsciiTable = decNumberInAsciiTable;
    }

    public void setHiddenMessage(ArrayList<Character> hiddenMessage) {
        this.hiddenMessage = hiddenMessage;
    }

    public void setMessageToHide(ArrayList<Character> messageToHide) {
        this.messageToHide = messageToHide;
    }

    public void setFourBitsArray(int[] fourBitsArray) {
        this.fourBitsArray = fourBitsArray;
    }

    public static void setCurrentLine(int currentLine) {
        Steganography.currentLine = currentLine;
    }

    /**
     * Encoding process: Convert letter to Decimal Number
     *
     * @param letter sequentially assigned character(A-Z) from messageToHide (message you want to hide)
     */
    public void encodingConvertLetterToDecimalNumber(char letter) {

        if(!Character.isUpperCase(letter)){
            throw new IllegalArgumentException("Letter is not capital or you used different characters then [A-Z]");
        }

        decNumberInAsciiTable = (int) letter;

    }


    /**
     * Adding character at the end of line in text file.
     *
     * @param filePath  Path to text file.
     * @param charToAdd Character that is added at the end of line
     */
    public void addCharacterToNextLine(String filePath, String charToAdd) {
        List<String> lines = new ArrayList<>();

        // Read all lines from the file and add them to provisional array list
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("File not found: " + e.getMessage());
            return;
        }

        // Check if the file has enough lines
        if (currentLine < lines.size()) {

            // update line(current line(number), get current line from array list, add character at the end of line)
            lines.set(currentLine, lines.get(currentLine) + charToAdd);

            // Move to the next line
            currentLine++;
        } else {
            System.out.println("Not enough lines.");
            return;
        }

        // Write encoded lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error while writing to the file: " + e.getMessage());
        }
    }

    /**
     * Encoding process: Separate first digit from 2-digit number(decNumberInAsciiTable).
     */
    public void encodingSeparateFirstDigit() {
        firstDigitDecimalNumber = decNumberInAsciiTable / 10;

    }

    /**
     * Encoding process: Separate second digit from 2-digit number(decNumberInAsciiTable).
     */
    public void encodingSeparateSecondDigit() {

        secondDigitDecimalNumber = decNumberInAsciiTable % 10;
    }

    /**
     * Convert first digit from 2-digit number(decNumberInAsciiTable) to 4 bits binary representation.
     */
    public void encodingConvertFirstNumberToBits() {
        for (int i = 3; i >= 0; i--) {
            fourBitsArray[i] = firstDigitDecimalNumber % 2;
            firstDigitDecimalNumber = firstDigitDecimalNumber / 2;

        }
    }

    /**
     * Convert second digit from 2-digit number(decNumberInAsciiTable) to 4 bits binary representation.
     */
    public void encodingConvertSecondNumberToBits() {
        for (int i = 3; i >= 0; i--) {
            fourBitsArray[i] = secondDigitDecimalNumber % 2;
            secondDigitDecimalNumber = secondDigitDecimalNumber / 2;

        }
    }

    /**
     * Reset fourBitsArray to default value. set all element to number 2. Different from binary numbers (0-1)
     */
    public void resetFourBitsArray() {
        Arrays.fill(fourBitsArray, 2);


    }

    /**
     * Adding space character into array list as delimiter between words
     */
    public void decodingAddSpaceIntoArrayList() {
        hiddenMessage.add(' ');
    }

    /**
     * Adding binary digit 0 into array(4 bits array) sequentially.
     */
    public void decodingAddBit_0_IntoArray() {
        if (fourBitsArray[0] == 2) {
            fourBitsArray[0] = 0;

        } else if (fourBitsArray[1] == 2) {
            fourBitsArray[1] = 0;

        } else if (fourBitsArray[2] == 2) {
            fourBitsArray[2] = 0;

        } else if (fourBitsArray[3] == 2) {
            fourBitsArray[3] = 0;

        }
    }

    /**
     * Converts binary array into first digit of 2-digit number (decNumberInAsciiTable)
     */
    public void decodingConvertBinaryArrayToFirstDigitAndResetArray() {
        for (int i = 0; i < fourBitsArray.length; i++) {
            firstDigitDecimalNumber = (firstDigitDecimalNumber << 1) | fourBitsArray[i];

        }
        resetFourBitsArray();
    }

    /**
     * Converts binary array into second digit of 2-digit number (decNumberInAsciiTable)
     */
    public void decodingConvertBinaryArrayToSecondDigitAndResetArray() {
        for (int i = 0; i < fourBitsArray.length; i++) {
            secondDigitDecimalNumber = (secondDigitDecimalNumber << 1) | fourBitsArray[i];

        }
        resetFourBitsArray();

    }

    /**
     * Link together first digit and second digit to 2-digit number(decNumberInAsciiTable)
     */
    public void decodingConcatenateTwoNumbers() {
        // for testing purposes making sure both digits are in range 0 - 9
        if (firstDigitDecimalNumber < 0 || firstDigitDecimalNumber > 9 || secondDigitDecimalNumber < 0 || secondDigitDecimalNumber > 9) {
            throw new IllegalArgumentException("File has already hidden message in it or you didn't met the requirements (one or both digits are outside of range 0 - 9)");

        }

        decNumberInAsciiTable = Integer.parseInt(String.valueOf(firstDigitDecimalNumber) + String.valueOf(secondDigitDecimalNumber));

    }

    /**
     * Converts decimal number to character that it represents in ASCII table and add that character to array list
     */

    public void decodingConvertNumberAndAddItToArrayList() {
        hiddenMessage.add((char) decNumberInAsciiTable);
    }

    /**
     * Adding binary digit 1 into array(4 bits array) sequentially.
     */
    public void decodingAddBit_1_IntoArray() {

        //if statement is checking if indexes are already occupied

        //index 0
        if (fourBitsArray[0] == 2) {
            fourBitsArray[0] = 1;
            //index 1
        } else if (fourBitsArray[1] == 2) {
            fourBitsArray[1] = 1;
            //index 2
        } else if (fourBitsArray[2] == 2) {
            fourBitsArray[2] = 1;
            //index 3
        } else if (fourBitsArray[3] == 2) {
            fourBitsArray[3] = 1;

        }
    }

    /**
     * Resets variables: firstDigitDecimalNumber, secondDigitDecimalNumber, decNumberInAsciiTable to default value = 0
     */
    public void resetFirstSecondAndAscii() {
        firstDigitDecimalNumber = 0;
        secondDigitDecimalNumber = 0;
        decNumberInAsciiTable = 0;
    }
}
