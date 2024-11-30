package white_space_steganography;

import java.io.File;
import java.util.Scanner;

public class Encoding {
    public static void main(String[] args) {


        Steganography steganography = new Steganography();

        String fileName = "randomText.txt";


        // Zero width space = 0 in binary
        String zwsp = "\u200B";
        // Zero width joiner = 1 in binary
        String zwj = "\u200D";
        // Zero width non-joiner = delimiter between words
        String zwnj = "\u200C";



        System.out.println("Need fresh file every time you run program. Can't hide message into file where there is already hidden message.");
        System.out.println("For each letter there must be 8 lines of text.");
        System.out.println("Only use capital letters A-Z");
        System.out.println("Enter the message you want to hide: ");


        // Store hidden message into Array list
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();

        for (int i = 0; i < message.length(); i++) {
            steganography.messageToHide.add(message.charAt(i));
        }
        scanner.close();


        // ------------ ENCODING ---------------
        for (int i = 0; i < steganography.messageToHide.size(); i++) {

            // Check if found space in message
            if (steganography.messageToHide.get(i) == ' ') {
                // Add space character into array list
                steganography.charToAdd = zwnj;
                steganography.addCharacterToNextLine(fileName, steganography.charToAdd);
                continue;
            }

            steganography.letter = steganography.messageToHide.get(i);
            // Convert character (letter) into decimal number that represent it's value in ASCII table
            steganography.encodingConvertLetterToDecimalNumber(steganography.letter);


            // Separate 2-digit number into two 1-digit numbers
            steganography.encodingSeparateFirstDigit();
            steganography.encodingSeparateSecondDigit();

            // Convert first number to its binary representation
            steganography.encodingConvertFirstNumberToBits();


            // Encode appropriate bit into FILE
            for (int j = 0; j < steganography.fourBitsArray.length; j++) {
                if (steganography.fourBitsArray[j] == 0) {
                    steganography.charToAdd = zwsp;
                    steganography.addCharacterToNextLine(fileName, steganography.charToAdd);
                } else {
                    steganography.charToAdd = zwj;
                    steganography.addCharacterToNextLine(fileName, steganography.charToAdd);
                }
            }


            // Resets array
            steganography.resetFourBitsArray();
            // Convert second number to its binary representation
            steganography.encodingConvertSecondNumberToBits();


            // Encode appropriate bit into FILE
            for (int j = 0; j < steganography.fourBitsArray.length; j++) {
                if (steganography.fourBitsArray[j] == 0) {
                    steganography.charToAdd = zwsp;
                    steganography.addCharacterToNextLine(fileName, steganography.charToAdd);
                } else {
                    steganography.charToAdd = zwj;
                    steganography.addCharacterToNextLine(fileName, steganography.charToAdd);
                }
            }


            //resetting array
            steganography.resetFourBitsArray();
            // resetting firstDigitDecimalNumber, secondDigitDecimalNumber, decNumberInAsciiTable
            steganography.resetFirstSecondAndAscii();


        }

        //resetting array
        steganography.resetFourBitsArray();
        // resetting firstDigitDecimalNumber, secondDigitDecimalNumber, decNumberInAsciiTable
        steganography.resetFirstSecondAndAscii();


    }
}
