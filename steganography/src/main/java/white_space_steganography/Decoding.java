package white_space_steganography;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Decoding {
    public static void main(String[] args) {

        Steganography steganography = new Steganography();

        String fileName = "randomText.txt";
        File file = new File(fileName);

        // Zero width space = 0 in binary
        String zwsp = "\u200B";
        // Zero width joiner = 1 in binary
        String zwj = "\u200D";
        // Zero width non-joiner = delimiter between words
        String zwnj = "\u200C";
        String row;


        // ---------- DECODING ------------
        {
            //resetting array
            steganography.resetFourBitsArray();
            // resetting firstDigitDecimalNumber, secondDigitDecimalNumber, decNumberInAsciiTable
            steganography.resetFirstSecondAndAscii();


            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                while ((row = reader.readLine()) != null) {

                    // Check for triple space (space between words)
                    if (row.contains(zwnj)) {
                        steganography.decodingAddSpaceIntoArrayList();
                        continue; // when found triple space in text file ignore rest of the code and go to another iteration cause it messed up a result
                    }

                    // Check for Zero width space
                    if (row.contains(zwsp)) {


                        // Add bit "0" into array
                        steganography.decodingAddBit_0_IntoArray();


                        // Checking if fourBitsArray is full
                        if (steganography.fourBitsArray[3] < 2) {
                            // Check if first digit is empty
                            if (steganography.firstDigitDecimalNumber == 0) {

                                // Convert array to first digit
                                steganography.decodingConvertBinaryArrayToFirstDigitAndResetArray();

                            } else {
                                // Convert array to second digit
                                steganography.decodingConvertBinaryArrayToSecondDigitAndResetArray();
                                // link first and second digit into 2-digit number
                                steganography.decodingConcatenateTwoNumbers();
                                // Convert that 2-digit number into ASCII representation and add it to array list
                                steganography.decodingConvertNumberAndAddItToArrayList();
                                // Reset to default (0)
                                steganography.resetFirstSecondAndAscii();

                            }
                        }
                    }

                    // Check for Zero width joiner
                    if (row.contains(zwj)) {

                        // Add bit "1" into array
                        steganography.decodingAddBit_1_IntoArray();

                        // Checking if fourBitsArray is full
                        if (steganography.fourBitsArray[3] < 2) {
                            // Check if first digit is empty
                            if (steganography.firstDigitDecimalNumber == 0) {
                                // Convert array to first digit
                                steganography.decodingConvertBinaryArrayToFirstDigitAndResetArray();
                            } else {

                                // Convert array to second digit
                                steganography.decodingConvertBinaryArrayToSecondDigitAndResetArray();
                                // link first and second digit into 2-digit number
                                steganography.decodingConcatenateTwoNumbers();
                                // Convert that 2-digit number into ASCII representation and add it to array list
                                steganography.decodingConvertNumberAndAddItToArrayList();
                                // Reset to default (0)
                                steganography.resetFirstSecondAndAscii();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Conditions weren't met or something went wrong " + e.getMessage());
                System.exit(0);
            }

            // print hidden message
            System.out.println("Hidden message is: ");
            for (int i = 0; i < steganography.hiddenMessage.size(); i++) {
                System.out.print(steganography.hiddenMessage.get(i));
            }


        }// ------ END OF DECODING ----------
    }
}
