/** Address class for creating address objects containing the virtual address to physical address mapping
 * @author SCTMIC015
 */

import java.util.BitSet;

public class Address {
    BitSet bits;
    BitSet addressBits;
    BitSet offSetBits;
    final int[] mapping = {2, 4, 1, 7, 3, 5, 6};

    public Address(BitSet bits){
        this.bits = bits;
        //this.addressBits = getAddressBits();
        //this.offSetBits = getOffSetBits();
        //this.mapping = new int[]{2, 4, 1, 7, 3, 5, 6};
    }

    /**
     * Prints the binary representation of the address object
     */
    public void printBits(){
        for (int i = 0; i < bits.length(); i ++){
            if (bits.get(i)){
                System.out.print(1);
            }
            else
                System.out.print(0);
        }
    }

    /**
     * Prints bits of a bitSet object
     * @param outBits
     */
    public void printOutBits(BitSet outBits){
        for (int i = 0; i < bits.length(); i ++){
            if (outBits.get(i)){
                System.out.print(1);
            }
            else
                System.out.print(0);
        }
    }

    /**
     * Converts the binary representation of the objects address number to an integer
     * @return int
     */
    public int bitToInt() {
        int sum = 0;
        int count = 0;
        for (int i = 56; i >= 52; i --){
            if (this.bits.get(i)){
                sum += Math.pow(2, count);
            }
            count ++;
        }
        return sum;
    }

    /**
     * Converts an integer to a String of its binary representation
     * @param num
     * @return
     */
    public String intToBitString(int num){
        StringBuffer str = new StringBuffer();
        int rem = 0;
        while (num > 0){
            rem = num % 2;
            num = num / 2;
            str.append(rem);
        }
        String reverseStr = str.reverse().toString();
        // Add zeros
        String zeros = "";
        for (int i = 0; i < 5-reverseStr.length(); i ++){
            zeros += "0";
        }
        reverseStr = zeros + reverseStr;
        /**
        System.out.println(reverseStr);
        BitSet bits2 = new BitSet(5);
        for (int i =0; i < 5; i ++){
            if (reverseStr.charAt(i) == '1'){
                bits2.set(i);
            }
        }
        //printBits(bits2);
        return bits2; **/
        return reverseStr;
    }

    /**
     * Maps the virtual address of the Address object to the corresponding physical address
     * @return BitSet
     */
    public BitSet map(){
        int virtualInt = bitToInt();
        if (virtualInt < 0 || virtualInt > 6){
            throw new ArrayIndexOutOfBoundsException("Array Index Out of bounds");
        }
        int physicalInt = mapping[virtualInt];
        //System.out.println(physicalInt);
        //System.out.println(intToBitString(physicalInt));
        String reverseString = intToBitString(physicalInt);
        // Working above this point
        BitSet outBits = new BitSet(64);
        for (int i = 0; i < 64; i ++) {
            if (i < 52){
                outBits.set(i, false);
            }
            else if (i >= 52 && i < 57){
                if (reverseString.charAt(i - 52) == '1'){
                    outBits.set(i);
                }
            }
            else {
                if (bits.get(i)){
                    outBits.set(i);
                }
            }
        }
        //System.out.print("This is the virtual:           ");
        //printBits();
        //System.out.println();
        //System.out.print("This the the physical address: ");
        //printOutBits(outBits);
        return outBits;
    }

    /**
     * Converts a bitSet to its hexadecimal representation.
     * @author http://www.java2s.com/example/java/java.util/convert-a-bitset-object-to-a-hexadecimal-string-as-required-by-iso858.html
     * @return String
     */
    public String bitset2Hex() {
        BitSet bitset = map();
        final int minLength = 16;
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
