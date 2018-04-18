package desimplementation;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class Encryption {

    Scanner userInput = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out, true);
    String[] RoundKeyArray = new String[16];
    String[] Keys = new String[16];
    String permutatedKey = "";
    String finalResult = "";
    String RoundKey = "";
    String originalKey = "";
    int[] NumLeftShifts = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    int[] PC1 = {
        57, 49, 41, 33, 25, 17, 9,
        1, 58, 50, 42, 34, 26, 18,
        10, 2, 59, 51, 43, 35, 27,
        19, 11, 3, 60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7, 62, 54, 46, 38, 30, 22,
        14, 6, 61, 53, 45, 37, 29,
        21, 13, 5, 28, 20, 12, 4};

    int[] PC2 = {
        14, 17, 11, 24, 1, 5,
        3, 28, 15, 6, 21, 10,
        23, 19, 12, 4, 26, 8,
        16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32};
    int[] IP = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7};
    int[] FP = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25};
    int[] E = {
        32, 1, 2, 3, 4, 5,
        4, 5, 6, 7, 8, 9,
        8, 9, 10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1};
    int[][] S1 = {
        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}};

    int[][] S2 = {
        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}};

    int[][] S3 = {
        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}};

    int[][] S4 = {
        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}};

    int[][] S5 = {
        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}};

    int[][] S6 = {
        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}};

    int[][] S7 = {
        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}};

    int[][] S8 = {
        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}};

    int[] P = {16, 7, 20, 21,
        29, 12, 28, 17,
        1, 15, 23, 26,
        5, 18, 31, 10,
        2, 8, 24, 14,
        32, 27, 3, 9,
        19, 13, 30, 6,
        22, 11, 4, 25};

    // DES can be used to encrypt all types of data from pictures, strings and maybe even videos
    public void Encryption() {

        try {

            /******************************************** 1.SUB  KEYS ********************************************************
             ****************************************************************************************************************/
            pw.println("Enter 64-bit key: ");
            originalKey = userInput.nextLine();
            System.out.println("Original Key in Text: " + originalKey);
            String binaryKey = new BigInteger(originalKey.getBytes()).toString(2);
            System.out.println("Original Key in Binary: " + binaryKey);
            //Key should be atleast 8 characters long

            /*We get the 56-bit permutation from the original key using PC-1*/
            //binaryKey = "0001001100110100010101110111100110011011101111001101111111110001"; 
            for (int i : PC1) {
                permutatedKey += binaryKey.charAt(i - 1);
                //pw.println(permutatedKey);
            }
            pw.println("56 bit Original key:    " + permutatedKey);

            /* Next, split this key into left and right halves, LK and RK, where each half has 28 bits. */
            String LK = permutatedKey.substring(0, 28);
            String RK = permutatedKey.substring(28, 56);

            /* We now perform 16 left circular shifts from the original LK and RK */
            String Lkey = LK;
            String Rkey = RK;
            pw.println();
            int Index = 0;
            for (int a : NumLeftShifts) {
                Lkey = CircularLeftShift(Lkey, a);
                Rkey = CircularLeftShift(Rkey, a);
                Keys[Index] = Lkey + Rkey;
                Index++;
                //pw.println(Lkey);
                //pw.println(Rkey);
            }

            /* Then we build the 16 48-bit sub keys using the PC-2 Permutation table */
            Index = 0;
            for (String key : Keys) {
                for (int j : PC2) {
                    RoundKey += key.charAt(j - 1);
                }
                RoundKeyArray[Index] = RoundKey;
                //pw.println(RoundKeyArray[Index]);
                Index++;
                RoundKey = "";
            }

            /******************************************** 2.ENCODE  MESSAGE **************************************************
             ****************************************************************************************************************/
            pw.println("Enter Plain Text: ");
            originalKey = userInput.nextLine();
            System.out.println("Plain Text: " + originalKey);
            binaryKey = new BigInteger(originalKey.getBytes()).toString(2);
            pw.println("Full Plain Text in Binary:   " + binaryKey);
            pw.println("64-bit Plain Text in Binary: " + binaryKey.substring(0, 64));
            String plainTextBinary = binaryKey;
            //String plainTextBinary = "0000000100100011010001010110011110001001101010111100110111101111";

            /* Make an Initial Permutation on the plain text */
            String IPBinary = "";
            for (int i : IP) {
                IPBinary += plainTextBinary.charAt(i - 1);
            }
            //pw.println( IPBinary);

            /* Divide the permuted block into two halves of 32 bits */
            String LeftIPBinary = IPBinary.substring(0, 32);
            String RightIPBinary = IPBinary.substring(32, 64);

            /* We now proceed through 16 Rounds/iterations using a function f */
            //There are 16 keys thus 16 rounds, as expected
            int counter = 1;
            for (String k : RoundKeyArray) {
                pw.println();
                pw.println("******************* ENCRYPTION ROUND " + counter + " **********************************");
                pw.println("KEY = " + k);

                //Left block becomes right block of previous round
                String LeftBlock = RightIPBinary;
                pw.println("LEFT BLOCK  = " + LeftBlock);

                //Right block is previous left block XOR F(previous left block, round key)
                //To calculate Right block we first expand 32 bit previous right block to 48 bits since the key is 48 bits
                String expand = "";
                for (int i : E) {
                    expand += RightIPBinary.charAt(i - 1);
                    //Each block of 4 original bits has been expanded to a block of 6 output bits
                }

                //we XOR expand and the key since they are now both 48 bits long
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < k.length(); i++) {
                    sb.append((k.charAt(i) ^ expand.charAt(i)));
                }
                String result = sb.toString();
                //pw.println(result);          

                //"S boxes": 8 groups of six bits return as 4 bits in order for the Left block to regain its original 32 bits size
                //String binary = String.format("%8s", Integer.toBinaryString(currentByte)).replace(' ', '0');
                String RB1 = result.substring(0, 6);
                String row1 = String.valueOf(RB1.substring(0, 1) + RB1.substring(5, 6));
                String col1 = String.valueOf(RB1.substring(1, 5));
                int target = S1[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                String binaryTarget = String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');
                //pw.println(binaryTarget);

                String RB2 = result.substring(6, 12);
                row1 = String.valueOf(RB2.substring(0, 1) + RB2.substring(5, 6));
                col1 = String.valueOf(RB2.substring(1, 5));
                target = S2[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB3 = result.substring(12, 18);
                row1 = String.valueOf(RB3.substring(0, 1) + RB3.substring(5, 6));
                col1 = String.valueOf(RB3.substring(1, 5));
                target = S3[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB4 = result.substring(18, 24);
                row1 = String.valueOf(RB4.substring(0, 1) + RB4.substring(5, 6));
                col1 = String.valueOf(RB4.substring(1, 5));
                target = S4[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB5 = result.substring(24, 30);
                row1 = String.valueOf(RB5.substring(0, 1) + RB5.substring(5, 6));
                col1 = String.valueOf(RB5.substring(1, 5));
                target = S5[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB6 = result.substring(30, 36);
                row1 = String.valueOf(RB6.substring(0, 1) + RB6.substring(5, 6));
                col1 = String.valueOf(RB6.substring(1, 5));
                target = S6[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB7 = result.substring(36, 42);
                row1 = String.valueOf(RB7.substring(0, 1) + RB7.substring(5, 6));
                col1 = String.valueOf(RB7.substring(1, 5));
                target = S7[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB8 = result.substring(42, 48);
                row1 = String.valueOf(RB8.substring(0, 1) + RB8.substring(5, 6));
                col1 = String.valueOf(RB8.substring(1, 5));
                target = S8[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');
                //pw.println(binaryTarget);

                //Lastly,to get f, we permutate the output of the S-box(binaryTarget) using table P to obtain the final value 
                String function = "";
                for (int d : P) {
                    function += binaryTarget.charAt(d - 1);
                }
                //pw.println(function);

                //Finally, Previous Left block XOR function value
                sb = new StringBuilder();
                for (int i = 0; i < LeftIPBinary.length(); i++) {
                    sb.append((LeftIPBinary.charAt(i) ^ function.charAt(i)));
                }
                result = sb.toString();

                RightIPBinary = result;
                pw.println("RIGHT BLOCK = " + RightIPBinary);
                result = "";

                counter++;
                pw.println("*************************************************************************");
                if (counter > 16) {
                    // Reversely combine the two blocks to form a 64-bit block
                    result = RightIPBinary + LeftBlock;
                    //pw.println(result);

                    //Final Permutation FP: The Inverse of the Initial permutation IP
                    for (int x : FP) {
                        finalResult += result.charAt(x - 1);
                    }
                    pw.println("\nCIPHER TEXT = " + finalResult);
                }
                LeftIPBinary = LeftBlock;
            }

            
            /************************************************ DECRYPTION ****************************************************
             ****************************************************************************************************************/
            pw.println();
            /* Perform an Initial Permutation on the binary cipher text ==> binaryKey */
            IPBinary = "";
            for (int i : IP) {
                IPBinary += finalResult.charAt(i - 1);
            }

            /* Divide the permuted block into two halves of 32 bits */
            LeftIPBinary = IPBinary.substring(0, 32);
            RightIPBinary = IPBinary.substring(32, 64);

            /* We now proceed through 16 Rounds/iterations each using a unique function f */
            //Go through the 16 keys in teh REVERSE ORDER
            counter = 1;
            String k;
            for (int p = 15; p >= 0; p--) {
                pw.println();
                pw.println("********************DECRYPTION ROUND " + counter + "************************************");
                k = RoundKeyArray[p];
                pw.println("KEY = " + k);

                //Left block becomes right block of previous round
                String LeftBlock = RightIPBinary;
                pw.println("LEFT BLOCK  = " + LeftBlock);

                //Right block is previous left block XOR F(previous left block, round key)
                String expand = "";
                for (int i : E) {
                    expand += RightIPBinary.charAt(i - 1);
                    // each block of 4 original bits has been expanded to a block of 6 output bits
                }

                //we XOR expand and the key since they are now both 48 bits long
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < k.length(); i++) {
                    sb.append((k.charAt(i) ^ expand.charAt(i)));
                }
                String result = sb.toString();         

                //"S boxes" 8 groups of six bits return as 4 bits this in  order to return to original 32 bits size
                String RB1 = result.substring(0, 6);
                String row1 = String.valueOf(RB1.substring(0, 1) + RB1.substring(5, 6));
                String col1 = String.valueOf(RB1.substring(1, 5));
                int target = S1[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                String binaryTarget = String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB2 = result.substring(6, 12);
                row1 = String.valueOf(RB2.substring(0, 1) + RB2.substring(5, 6));
                col1 = String.valueOf(RB2.substring(1, 5));
                target = S2[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB3 = result.substring(12, 18);
                row1 = String.valueOf(RB3.substring(0, 1) + RB3.substring(5, 6));
                col1 = String.valueOf(RB3.substring(1, 5));
                target = S3[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB4 = result.substring(18, 24);
                row1 = String.valueOf(RB4.substring(0, 1) + RB4.substring(5, 6));
                col1 = String.valueOf(RB4.substring(1, 5));
                target = S4[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB5 = result.substring(24, 30);
                row1 = String.valueOf(RB5.substring(0, 1) + RB5.substring(5, 6));
                col1 = String.valueOf(RB5.substring(1, 5));
                target = S5[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB6 = result.substring(30, 36);
                row1 = String.valueOf(RB6.substring(0, 1) + RB6.substring(5, 6));
                col1 = String.valueOf(RB6.substring(1, 5));
                target = S6[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB7 = result.substring(36, 42);
                row1 = String.valueOf(RB7.substring(0, 1) + RB7.substring(5, 6));
                col1 = String.valueOf(RB7.substring(1, 5));
                target = S7[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB8 = result.substring(42, 48);
                row1 = String.valueOf(RB8.substring(0, 1) + RB8.substring(5, 6));
                col1 = String.valueOf(RB8.substring(1, 5));
                target = S8[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                //Lastly,to get f we permutate the output of the S-box using table P 
                String function = "";
                for (int d : P) {
                    function += binaryTarget.charAt(d - 1);
                }

                //Finally, Previous Left block XOR function
                sb = new StringBuilder();
                for (int i = 0; i < LeftIPBinary.length(); i++) {
                    sb.append((LeftIPBinary.charAt(i) ^ function.charAt(i)));
                }
                result = sb.toString();

                RightIPBinary = result;
                pw.println("RIGHT BLOCK = " + RightIPBinary);
                result = "";

                counter++;
                pw.println("************************************************************************");
                if (counter > 16) {
                    //Reverse the order of the two blocks to form a 64-bit block
                    result = RightIPBinary + LeftBlock;

                    //Final Permutation: The inverse of the initial permutation
                    String finalResult = "";
                    for (int x : FP) {
                        finalResult += result.charAt(x - 1);
                    }
                    pw.println("\nDECRYPTED BINARY PLAIN TEXT = " + finalResult);
                   
                    pw.println("DECRYPTED CIPHER = " + intTostr(finalResult, 8));
                    
                }
                LeftIPBinary = LeftBlock;
            }

            pw.println("\n\n\t\t\t\tTHANKS FOR USING MY PROGRAM!\n\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String intTostr( String stream, int size ) { 
        
        String result = "";
        for (int i = 0; i < stream.length(); i += size) {
            result += (stream.substring(i, Math.min(stream.length(), i + size)) + " ");
        }  
        String[] ss = result.split( " " );
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < ss.length; i++ ) { 
            sb.append( (char)Integer.parseInt( ss[i], 2 ) );                                                                                                                                                        
        }   
        return sb.toString();
    }  

    /* Left shift function */
    String CircularLeftShift(String s, int k) {
        
        String result = s.substring(k);
        for (int i = 0; i < k; i++) {
            result += s.charAt(i);
        }
        return result;
    }

    public static void main(String[] args) {
        Encryption myDes = new Encryption();
        myDes.Encryption();
    }

}
//Test Values
//Plain Text: 0123456789ABCDEF
//Key:        133457799BBCDFF1
//Plain Text should be at least 9 characters
//PLAIN TEXT 64 bits 1100000011000100110010001100110011010000110101001101100011011100
//CIPHER             1100110010000100000001001010101111010010000101110100100111101100
//DECRYPTED CIPHER   1100000011000100110010001100110011010000110101001101100011011100

