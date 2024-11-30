package white_space_steganography;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;




public class StegoMain {

    enum OperationMode {
        ENCODING,
        DECODING
    }

    public static void main(String[] args) {


        ///////// variables
        Steganography steganography = new Steganography();

        String fileName = "randomText.txt";
        File file = new File(fileName);

        // double space = 0 in binary
        String doubleSpace = "  ";
        // tab = 1 in binary
        String tab = "\t";
        // triple space = delimiter between words
        String tripleSpace = "   ";
        String row;
        ////////////// variables
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose a mode:");
        System.out.println("1: Encoding");
        System.out.println("2: Decoding");

        int choice = scanner.nextInt();
        OperationMode mode;

        // Map user input to enum values
        switch (choice) {
            case 1:
                mode = OperationMode.ENCODING;
                break;

            case 2:
                mode = OperationMode.DECODING;
                break;

            default:
                System.out.println("Invalid choice. Exiting program.");
                scanner.close();
                return; // Exit program
        }

        // Use the enum to determine the operation
        if (mode == OperationMode.ENCODING) {
            System.out.println("Performing encoding...");

            //// START OF ENCODING

            System.out.println("Need fresh file every time you run program. Can't hide message into file where there is already hidden message.");
            System.out.println("For each letter there must be 8 lines of text.");
            System.out.println("Only use capital letters A-Z");
            System.out.println("Enter the message you want to hide: ");


            // store hidden message into Array list
            Scanner scanner2 = new Scanner(System.in);
            String message = scanner2.nextLine();

            for (int i = 0; i < message.length(); i++) {
                steganography.messageToHide.add(message.charAt(i));
            }
            scanner2.close();



            System.out.println(steganography.getFirstDigitDecimalNumber());
            System.out.println(steganography.getMessageToHide());

            // ----------------------- ENCODING ---------------------------------

            for (int i = 0; i < steganography.messageToHide.size(); i++) {

                // if found space in message
                if (steganography.messageToHide.get(i) == ' ') {
                    steganography.charToAdd = tripleSpace;
                    steganography.addCharacterToNextLine(fileName, steganography.charToAdd);
                    continue;
                }

                steganography.letter = steganography.messageToHide.get(i);
                System.out.println(steganography.getLetter());
                steganography.encodingConvertLetterToDecimalNumber(steganography.letter);
                System.out.println(steganography.getDecNumberInAsciiTable());


                steganography.encodingSeparateFirstDigit();
                steganography.encodingSeparateSecondDigit();


                steganography.encodingConvertFirstNumberToBits();


                // ---------------- zapisovanie do suboru ------------------
                for (int j = 0; j < steganography.fourBitsArray.length; j++) {
                    if (steganography.fourBitsArray[j] == 0) {
                        steganography.charToAdd = doubleSpace;
                        steganography.addCharacterToNextLine(fileName, steganography.charToAdd);
                    } else {
                        steganography.charToAdd = tab;
                        steganography.addCharacterToNextLine(fileName, steganography.charToAdd);
                    }
                }
                // ---------------- zapisovanie do suboru ------------------

                steganography.resetFourBitsArray();
                steganography.encodingConvertSecondNumberToBits();
                System.out.println(Arrays.toString(steganography.fourBitsArray));

                // ---------------- zapisovanie do suboru ------------------
                for (int j = 0; j < steganography.fourBitsArray.length; j++) {
                    if (steganography.fourBitsArray[j] == 0) {
                        steganography.charToAdd = doubleSpace;
                        steganography.addCharacterToNextLine(fileName, steganography.charToAdd);
                    } else {
                        steganography.charToAdd = tab;
                        steganography.addCharacterToNextLine(fileName, steganography.charToAdd);
                    }
                }
                // ---------------- zapisovanie do suboru ------------------



                //resetting array
                steganography.resetFourBitsArray();
                // resetting firstDigitDecimalNumber, secondDigitDecimalNumber, decNumberInAsciiTable
                steganography.resetFirstSecondAndAscii();




            }
            //resetting array
            steganography.resetFourBitsArray();
            // resetting firstDigitDecimalNumber, secondDigitDecimalNumber, decNumberInAsciiTable
            steganography.resetFirstSecondAndAscii();

            // END OF ENCODING
            // ----------------------------------------------------------------


        } else if (mode == OperationMode.DECODING) {
            System.out.println("Performing decoding...");

            // ------------------------------ DECODING -----------------------
            //resetting array
            steganography.resetFourBitsArray();
            // resetting firstDigitDecimalNumber, secondDigitDecimalNumber, decNumberInAsciiTable
            steganography.resetFirstSecondAndAscii();


            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                while ((row = reader.readLine()) != null) {

                    // check for space between words
                    if (row.contains(tripleSpace)) {
                        steganography.decodingAddSpaceIntoArrayList();
                        continue; // when found triple space in text file ignore rest of the code and go to another iteration cause it messed up a result
                    }

                    // Check for double space
                    if (row.contains(doubleSpace)) {
                        System.out.println("Found double space");


                        steganography.decodingAddBit_0_IntoArray();
                        System.out.println("pridalo 0" + Arrays.toString(steganography.fourBitsArray));

                        // checking if fourBitsArray array is full
                        if (steganography.fourBitsArray[3] < 2) {
                            System.out.println("som v if array is full");
                            if (steganography.firstDigitDecimalNumber == 0) {
                                System.out.println("som v druhov if");
                                //Decoding.ConvertBinaryToDecimalAndReset(fourBitsArray, firstDigitDecimalNumber, secondDigitDecimalNumberBuffer, isSecondDigitEmpty);
                                steganography.decodingConvertBinaryArrayToFirstDigitAndResetArray();
                                System.out.println("first digit je: " + steganography.getFirstDigitDecimalNumber());
                                //System.out.println(Decoding.ConvertBinaryToDecimalAndReset(fourBitsArray, firstDigitDecimalNumber, secondDigitDecimalNumberBuffer, isSecondDigitEmpty));
                            } else {

                                steganography.decodingConvertBinaryArrayToSecondDigitAndResetArray();
                                steganography.decodingConcatenateTwoNumbers();

                                System.out.println("halo" + steganography.getDecNumberInAsciiTable());
                                // resetujem first aj second

                                //decoding.resetVariables(firstDigitDecimalNumber,secondDigitDecimalNumberBuffer);
                                //decoding.resetFirstDigitDecimalNumberBuffer(decoding.firstDigitDecimalNumber);
                                // decoding.resetSecondDigitDecimalNumberBuffer(decoding.secondDigitDecimalNumberBuffer);
                                //steganography.setFirstDigitDecimalNumber(0);
                                // steganography.setSecondDigitDecimalNumber(0);


                                steganography.decodingConvertNumberAndAddItToArrayList();
                                steganography.resetFirstSecondAndAscii();
                                // reset ascii number
                                //decoding.resetDecNumberInAsciiTable(decoding.decNumberInAsciiTable);
                                //steganography.setDecNumberInAsciiTable(0);
                            }

                        }


                    }

                    // Check for tab
                    if (row.contains(tab)) {
                        System.out.println("Found tab");

                        steganography.decodingAddBit_1_IntoArray();
                        System.out.println("pridlo 1" + Arrays.toString(steganography.fourBitsArray));

                        // checking if fourBitsArray array is full
                        if (steganography.fourBitsArray[3] < 2) {
                            if (steganography.firstDigitDecimalNumber == 0) {
                                //Decoding.ConvertBinaryToDecimalAndReset(fourBitsArray, firstDigitDecimalNumber, secondDigitDecimalNumberBuffer, isSecondDigitEmpty);
                                steganography.decodingConvertBinaryArrayToFirstDigitAndResetArray();
                                //System.out.println(Decoding.ConvertBinaryToDecimalAndReset(fourBitsArray, firstDigitDecimalNumber, secondDigitDecimalNumberBuffer, isSecondDigitEmpty));
                            } else {

                                steganography.decodingConvertBinaryArrayToSecondDigitAndResetArray();
                                steganography.decodingConcatenateTwoNumbers();

                                System.out.println("halo" + steganography.getDecNumberInAsciiTable());
                                // resetujem first aj second

                                //decoding.resetVariables(firstDigitDecimalNumber,secondDigitDecimalNumberBuffer);
                                //decoding.resetFirstDigitDecimalNumberBuffer(decoding.firstDigitDecimalNumber);
                                // decoding.resetSecondDigitDecimalNumberBuffer(decoding.secondDigitDecimalNumberBuffer);
                                //steganography.setFirstDigitDecimalNumber(0);
                                // steganography.setSecondDigitDecimalNumber(0);

                                // add letter into array list
                                steganography.decodingConvertNumberAndAddItToArrayList();
                                // resetting firstDigitDecimalNumber, secondDigitDecimalNumber, decNumberInAsciiTable
                                steganography.resetFirstSecondAndAscii();
                                // reset ascii number
                                //decoding.resetDecNumberInAsciiTable(decoding.decNumberInAsciiTable);
                                // steganography.setDecNumberInAsciiTable(0);
                            }

                        }


                    }


                }
            } catch (IOException e) {
                System.out.println("Conditions weren't met or something went wrong " + e.getMessage());
                System.exit(0);
            }

            System.out.println(steganography.getHiddenMessage());

            for (int i = 0; i < steganography.hiddenMessage.size(); i++) {
                System.out.print(steganography.hiddenMessage.get(i));
            }


            // ---------------------------- END OF DECODING -------------------------
        }

        scanner.close();








    }
}
