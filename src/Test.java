import java.io.*;
import java.util.Arrays;
import java.util.BitSet;

public class Test {

    public static void main (String[] args) throws IOException {
        String inputFile = "OS1testsequence";   // Input File

        byte[] bytes = getByte(inputFile); // Read input file to bytes
        BitSet bits = new BitSet(512);
        bits = BitSet.valueOf(bytes); // convert bytes to bitset

        BitSet[] addressBitsArray = new BitSet[7]; // Create array of smaller bitsets for each entry. NB The last one is a problem!!
        for (int i = 0; i < 7; i ++) {
            addressBitsArray[i] = new BitSet(64);
            for (int j = 0; j < 64; j ++) {
                int index = (i * 64) + j;
                if (bits.get(index)){
                    addressBitsArray[i].set(j);
                    System.out.print(1);
                }
                else System.out.print(0);
            }
            System.out.println();
        }

        System.out.println();
        // Attempt to reverse the bits of all sub arrays
        for (int i = 0; i < 7; i ++) {
            BitSet temp = addressBitsArray[i];
            addressBitsArray[i] = new BitSet(64);    // possibly poor coding
            for (int j = 63; j >= 0; j --) {
                if (temp.get(j)) {
                    addressBitsArray[i].set(63 - j);
                }
            }
        }


        System.out.println();
        for (int j = 0; j < 7; j ++) {
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

        Address[] addresses = new Address[addressBitsArray.length];
        for (int i = 0;i < addressBitsArray.length; i ++){
            Address address = new Address(addressBitsArray[i]);
            addresses[i] = address;
            //System.out.println();
            //address.printAddressBits();
            //System.out.print(" : ");
            //System.out.println(address.bitToInt());
        }
        for (int i = 0; i < 7; i ++){
            System.out.println(addresses[i].bitset2Hex());
        }
    }
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

    static String bitset2Hex(final BitSet bitset, final int minLength) {
        final StringBuilder result = new StringBuilder();
        for (int bytenum = 0; bytenum < minLength / 2; bytenum++) {
            byte v = 0;
            for (int bit = 0, mask = 0x80; mask >= 0x01; bit++, mask /= 2) {
                if (bitset.get((bytenum * 8) + bit)) {
                    v |= mask;
                }
            }
            result.append(String.format("%02X", v));
        }
        while (result.length() < minLength) {
            result.append("00");
        }
        return result.toString();
    }
}
