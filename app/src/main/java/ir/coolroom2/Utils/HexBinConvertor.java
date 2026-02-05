package ir.coolroom2.Utils;

/**
 * Created by amirmgh on 23/11/2017.
 */

public class HexBinConvertor {

    public String hexToBin(String hex) {
        StringBuilder binaries = new StringBuilder();
        for (int i = 0; i < hex.length(); i++) {
            String s = Integer.toBinaryString(Integer.parseInt(String.valueOf(hex.charAt(i)), 16));
            binaries.append(String.format("%4s", s).replace(' ', '0'));
        }
        return binaries.toString();
    }

    public String binToHex(String bin) {
        StringBuilder hexa = new StringBuilder();
        bin = String.format("%" + (bin.length() + (4 - bin.length() % 4)) + "s", bin).replace(' ', '0');
        for (int i = 0; i < bin.length(); i = i + 4) {
            hexa.append(Integer.toHexString(Integer.parseInt(bin.substring(i, i + 4), 2)));
        }
        return hexa.toString();
    }

}
