/** Main class for reading bytes from a file, loading them into an array of bitSet objects and finally creating an array of address objects
 * @author SCTMIC015
 */

import java.io.*;
import java.util.Arrays;
import java.util.BitSet;

public class Test {

    public static void main (String[] args) throws IOException {

        String inputFile = args[0];   // Input File

        byte[] bytes = getByte(inputFile); // Read input file to bytes
        BitSet bits = new BitSet(512);
        bits = BitSet.valueOf(bytes); // convert bytes to bitSet

        // Create an array of smaller bitSets for each address (addressBitsArray).
        int length = bytes.length / 8;
        BitSet[] addressBitsArray = new BitSet[length];
        for (int i = 0; i < length; i ++) {
            addressBitsArray[i] = new BitSet(64);
            for (int j = 0; j < 64; j ++) {
                int index = (i * 64) + j;
                if (bits.get(index)){
                    addressBitsArray[i].set(j);
                    //System.out.print(1);
                }
                //else System.out.print(0);
            }
            //System.out.println();
        }


        // Reverse all the bits in each bitSet in the addressBitsArray
        for (int i = 0; i < length; i ++) {
            BitSet temp = addressBitsArray[i];
            addressBitsArray[i] = new BitSet(64);    // possibly poor coding
            for (int j = 63; j >= 0; j --) {
                if (temp.get(j)) {
                    addressBitsArray[i].set(63 - j);
                }
            }
        }

        /**
        // Print The address and Offset bits
        System.out.println();
        for (int j = 0; j < length; j ++) {
            for (int i = 52; i < 64; i++) {
                if (addressBitsArray[j].get(i)) {
                    if (i == 57) System.out.print(" : ");
                    System.out.print(1);
                } else {
                    if (i == 57) System.out.print(" : ");
                    System.out.print(0);
                }
            }
            System.out.println();
        }
        **/

        // Create an array of Address objects using the bitSets in addressBitsArray
        Address[] addresses = new Address[addressBitsArray.length];
        for (int i = 0;i < addressBitsArray.length; i ++){
            Address address = new Address(addressBitsArray[i]);
            addresses[i] = address;
        }

        // Write the translated physical addresess to an output file
        FileWriter writer = new FileWriter("out.txt");
        BufferedWriter buffer = new BufferedWriter(writer);
        for (int i = 0; i < length; i ++){
            System.out.println(addresses[i].bitset2Hex());
            buffer.write(addresses[i].bitset2Hex() + "\n");
        }
        buffer.close();
    }

    /**
     * Method to get byte array from the input file
     * @param inputFile
     * @return Byte[]
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static byte[] getByte(String inputFile) throws IOException, FileNotFoundException {
        byte[] buffer = new byte[1];
        InputStream inputStream = new FileInputStream(inputFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        while (inputStream.read(buffer) != -1){
            outputStream.write(buffer);
        }
        //System.out.print(outputStream);
        inputStream.close();
        outputStream.close();

        return outputStream.toByteArray();
    }
}
