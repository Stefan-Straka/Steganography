package steganography_tests;

import org.junit.jupiter.api.*;
import white_space_steganography.Steganography;

import java.util.ArrayList;
import java.util.Arrays;

public class SteganographyTest {

    Steganography steganography = new Steganography();

    @Order(2)
    @Tags({@Tag("API"), @Tag("ALL")})
    @Test
    void decodingConcatenateTwoNumbers_validDigits_returnsDecNumberInAsciiTable() {
        steganography.setFirstDigitDecimalNumber(6);
        steganography.setSecondDigitDecimalNumber(9);

        steganography.decodingConcatenateTwoNumbers();
        Assertions.assertEquals(69, steganography.getDecNumberInAsciiTable());
    }

    @Test
    void decodingConcatenateTwoNumbers_invalidDigits_throwsException() {
        steganography.setFirstDigitDecimalNumber(-6);
        steganography.setSecondDigitDecimalNumber(7);


        Assertions.assertThrows(IllegalArgumentException.class, steganography::decodingConcatenateTwoNumbers);
    }

    @Test
    void decodingConcatenateTwoNumbers_outOfRangeDigits_throwsException() {
        steganography.setFirstDigitDecimalNumber(13);
        steganography.setSecondDigitDecimalNumber(5);


        Assertions.assertThrows(IllegalArgumentException.class, steganography::decodingConcatenateTwoNumbers);
    }

    @Test
    void encodingConvertLetterToDecimalNumber_validCharacter_returnsDecNumberInAsciiTable() {
        steganography.encodingConvertLetterToDecimalNumber('P');
        Assertions.assertEquals(80, steganography.getDecNumberInAsciiTable());


    }

    @Test
    void encodingConvertLetterToDecimalNumber_invalidCharacter_returnsDecNumberInAsciiTable() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> steganography.encodingConvertLetterToDecimalNumber('p'));

    }

    @Test
    void encodingSeparateSecondDigit() {
        steganography.setDecNumberInAsciiTable(82);

        steganography.encodingSeparateSecondDigit();
        Assertions.assertEquals(2, steganography.getSecondDigitDecimalNumber());
    }

    @Test
    void arraylist() {
        steganography.setDecNumberInAsciiTable(78);
        steganography.decodingConvertNumberAndAddItToArrayList();

        ArrayList<Character> expectedList = new ArrayList<>();
        expectedList.add('N'); // Add a single character

        Assertions.assertEquals(expectedList, steganography.getHiddenMessage());
    }

    @Test
    void correctSizeOfArrayList() {
        steganography.setDecNumberInAsciiTable(88);
        steganography.decodingConvertNumberAndAddItToArrayList();
        steganography.decodingAddSpaceIntoArrayList();

        Assertions.assertEquals(2, steganography.hiddenMessage.size());

    }

    @Test
    void charactersAtCorrectPlacesInArrayList() {
        steganography.setDecNumberInAsciiTable(89);
        steganography.decodingConvertNumberAndAddItToArrayList();

        steganography.setDecNumberInAsciiTable(90);
        steganography.decodingConvertNumberAndAddItToArrayList();

        steganography.setDecNumberInAsciiTable(83);
        steganography.decodingConvertNumberAndAddItToArrayList();

        Assertions.assertEquals(Character.valueOf('Z'), steganography.hiddenMessage.get(1));

    }

    @Test
    void matchingArrays() {
        steganography.resetFourBitsArray();

        steganography.decodingAddBit_1_IntoArray();
        steganography.decodingAddBit_1_IntoArray();
        steganography.decodingAddBit_1_IntoArray();
        steganography.decodingAddBit_0_IntoArray();

        int[] expectedArray = {1, 1, 1, 0};

        Assertions.assertArrayEquals(expectedArray, steganography.getFourBitsArray());
    }


    @Test
    void convertFirstBinaryToDecimal() {
        steganography.resetFourBitsArray();

        steganography.decodingAddBit_0_IntoArray();
        steganography.decodingAddBit_1_IntoArray();
        steganography.decodingAddBit_1_IntoArray();
        steganography.decodingAddBit_0_IntoArray();

        steganography.decodingConvertBinaryArrayToFirstDigitAndResetArray();

        Assertions.assertEquals(6, steganography.getFirstDigitDecimalNumber());
    }
}
