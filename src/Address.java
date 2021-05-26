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

    public void printBits(){
        for (int i = 0; i < bits.length(); i ++){
            if (bits.get(i)){
                System.out.print(1);
            }
            else
                System.out.print(0);
        }
    }

    public void printOutBits(BitSet outBits){
        for (int i = 0; i < bits.length(); i ++){
            if (outBits.get(i)){
                System.out.print(1);
            }
            else
                System.out.print(0);
        }
    }

    // Think working \
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

    // No work yet
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

    // No work yet
    public BitSet map(){
        int virtualInt = bitToInt();
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

    // Got code somewhere on the internet
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
