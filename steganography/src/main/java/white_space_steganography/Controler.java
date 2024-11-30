package white_space_steganography;

import java.util.Scanner;

public class Controler {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose a mode:");
        System.out.println("1: Encoding");
        System.out.println("2: Decoding");

        int choice = scanner.nextInt();
        OperationMode mode;


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
                return;
        }

        // Run the appropriate class based on choice
        if (mode == OperationMode.ENCODING) {
            Encoding.main(null);
        } else if (mode == OperationMode.DECODING) {
            Decoding.main(null);
        }


    }
}
