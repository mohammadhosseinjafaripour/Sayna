package ir.coolroom2.Sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JFP on 12/14/2017.
 */

public class SmsCodeAndDecode {


    private HashMap<String, String> code_hashmap = new HashMap<>();
    private HashMap<String, String> decode_hashmap = new HashMap<>();

    public SmsCodeAndDecode() {
        code_hashmap.put("00", " ");
        code_hashmap.put("01", "!");
        code_hashmap.put("02", "\"");
        code_hashmap.put("03", "#");
        code_hashmap.put("04", "%");
        code_hashmap.put("05", "&");
        code_hashmap.put("06", "'");
        code_hashmap.put("07", "(");
        code_hashmap.put("08", ")");
        code_hashmap.put("09", "*");
        code_hashmap.put("10", "+");
        code_hashmap.put("11", ",");
        code_hashmap.put("12", "-");
        code_hashmap.put("13", ".");
        code_hashmap.put("14", "/");
        code_hashmap.put("15", "0");
        code_hashmap.put("16", "1");
        code_hashmap.put("17", "2");
        code_hashmap.put("18", "3");
        code_hashmap.put("19", "4");
        code_hashmap.put("20", "5");
        code_hashmap.put("21", "6");
        code_hashmap.put("22", "7");
        code_hashmap.put("23", "8");
        code_hashmap.put("24", "9");
        code_hashmap.put("25", ":");
        code_hashmap.put("26", ";");
        code_hashmap.put("27", "<");
        code_hashmap.put("28", "=");
        code_hashmap.put("29", ">");
        code_hashmap.put("30", "?");
        code_hashmap.put("31", "@");
        code_hashmap.put("32", "A");
        code_hashmap.put("33", "B");
        code_hashmap.put("34", "C");
        code_hashmap.put("35", "D");
        code_hashmap.put("36", "E");
        code_hashmap.put("37", "F");
        code_hashmap.put("38", "G");
        code_hashmap.put("39", "H");
        code_hashmap.put("40", "I");
        code_hashmap.put("41", "J");
        code_hashmap.put("42", "K");
        code_hashmap.put("43", "L");
        code_hashmap.put("44", "M");
        code_hashmap.put("45", "N");
        code_hashmap.put("46", "O");
        code_hashmap.put("47", "P");
        code_hashmap.put("48", "Q");
        code_hashmap.put("49", "R");
        code_hashmap.put("50", "S");
        code_hashmap.put("51", "T");
        code_hashmap.put("52", "U");
        code_hashmap.put("53", "V");
        code_hashmap.put("54", "W");
        code_hashmap.put("55", "X");
        code_hashmap.put("56", "Y");
        code_hashmap.put("57", "Z");
        code_hashmap.put("58", "a");
        code_hashmap.put("59", "b");
        code_hashmap.put("60", "c");
        code_hashmap.put("61", "d");
        code_hashmap.put("62", "e");
        code_hashmap.put("63", "f");
        code_hashmap.put("64", "g");
        code_hashmap.put("65", "h");
        code_hashmap.put("66", "i");
        code_hashmap.put("67", "j");
        code_hashmap.put("68", "k");
        code_hashmap.put("69", "l");
        code_hashmap.put("70", "m");
        code_hashmap.put("71", "n");
        code_hashmap.put("72", "o");
        code_hashmap.put("73", "p");
        code_hashmap.put("74", "q");
        code_hashmap.put("75", "r");
        code_hashmap.put("76", "s");
        code_hashmap.put("77", "t");
        code_hashmap.put("78", "u");
        code_hashmap.put("79", "v");
        code_hashmap.put("80", "w");
        code_hashmap.put("81", "x");
        code_hashmap.put("82", "y");
        code_hashmap.put("83", "z");
        code_hashmap.put("84", "$ ");
        code_hashmap.put("85", "$!");
        code_hashmap.put("86", "$\"");
        code_hashmap.put("87", "$#");
        code_hashmap.put("88", "$%");
        code_hashmap.put("89", "$&");
        code_hashmap.put("90", "$a");//here
        code_hashmap.put("91", "$1");
        code_hashmap.put("92", "$2");//here
        code_hashmap.put("93", "$3");//here
        code_hashmap.put("94", "$4");//here
        code_hashmap.put("95", "$5");//here
        code_hashmap.put("96", "$6");//here
        code_hashmap.put("97", "$7");//here
        code_hashmap.put("98", "$8");//here
        code_hashmap.put("99", "$9");//here


        decode_hashmap.put(" ", "00");
        decode_hashmap.put("!", "01");
        decode_hashmap.put("\"", "02");
        decode_hashmap.put("#", "03");
        decode_hashmap.put("%", "04");
        decode_hashmap.put("&", "05");
        decode_hashmap.put("'", "06");
        decode_hashmap.put("(", "07");
        decode_hashmap.put(")", "08");
        decode_hashmap.put("*", "09");
        decode_hashmap.put("+", "10");
        decode_hashmap.put(",", "11");
        decode_hashmap.put("-", "12");
        decode_hashmap.put(".", "13");
        decode_hashmap.put("/", "14");
        decode_hashmap.put("0", "15");
        decode_hashmap.put("1", "16");
        decode_hashmap.put("2", "17");
        decode_hashmap.put("3", "18");
        decode_hashmap.put("4", "19");
        decode_hashmap.put("5", "20");
        decode_hashmap.put("6", "21");
        decode_hashmap.put("7", "22");
        decode_hashmap.put("8", "23");
        decode_hashmap.put("9", "24");
        decode_hashmap.put(":", "25");
        decode_hashmap.put(";", "26");
        decode_hashmap.put("<", "27");
        decode_hashmap.put("=", "28");
        decode_hashmap.put(">", "29");
        decode_hashmap.put("?", "30");
        decode_hashmap.put("@", "31");
        decode_hashmap.put("A", "32");
        decode_hashmap.put("B", "33");
        decode_hashmap.put("C", "34");
        decode_hashmap.put("D", "35");
        decode_hashmap.put("E", "36");
        decode_hashmap.put("F", "37");
        decode_hashmap.put("G", "38");
        decode_hashmap.put("H", "39");
        decode_hashmap.put("I", "40");
        decode_hashmap.put("J", "41");
        decode_hashmap.put("K", "42");
        decode_hashmap.put("L", "43");
        decode_hashmap.put("M", "44");
        decode_hashmap.put("N", "45");
        decode_hashmap.put("O", "46");
        decode_hashmap.put("P", "47");
        decode_hashmap.put("Q", "48");
        decode_hashmap.put("R", "49");
        decode_hashmap.put("S", "50");
        decode_hashmap.put("T", "51");
        decode_hashmap.put("U", "52");
        decode_hashmap.put("V", "53");
        decode_hashmap.put("W", "54");
        decode_hashmap.put("X", "55");
        decode_hashmap.put("Y", "56");
        decode_hashmap.put("Z", "57");
        decode_hashmap.put("a", "58");
        decode_hashmap.put("b", "59");
        decode_hashmap.put("c", "60");
        decode_hashmap.put("d", "61");
        decode_hashmap.put("e", "62");
        decode_hashmap.put("f", "63");
        decode_hashmap.put("g", "64");
        decode_hashmap.put("h", "65");
        decode_hashmap.put("i", "66");
        decode_hashmap.put("j", "67");
        decode_hashmap.put("k", "68");
        decode_hashmap.put("l", "69");
        decode_hashmap.put("m", "70");
        decode_hashmap.put("n", "71");
        decode_hashmap.put("o", "72");
        decode_hashmap.put("p", "73");
        decode_hashmap.put("q", "74");
        decode_hashmap.put("r", "75");
        decode_hashmap.put("s", "76");
        decode_hashmap.put("t", "77");
        decode_hashmap.put("u", "78");
        decode_hashmap.put("v", "79");
        decode_hashmap.put("w", "80");
        decode_hashmap.put("x", "81");
        decode_hashmap.put("y", "82");
        decode_hashmap.put("z", "83");
        decode_hashmap.put("$ ", "84");
        decode_hashmap.put("$!", "85");
        decode_hashmap.put("$\"", "86");
        decode_hashmap.put("$#", "87");
        decode_hashmap.put("$%", "88");
        decode_hashmap.put("$&", "89");
        decode_hashmap.put("$a", "90");//here
        decode_hashmap.put("$1", "91");
        decode_hashmap.put("$2", "92");//here
        decode_hashmap.put("$3", "93");//here
        decode_hashmap.put("$4", "94");//here
        decode_hashmap.put("$5", "95");//here
        decode_hashmap.put("$6", "96");//here
        decode_hashmap.put("$7", "97");//here
        decode_hashmap.put("$8", "98");//here
        decode_hashmap.put("$9", "99");//here

    }

    public String truecent_length(String strOctalNumber, int l) {
        return String.format("%" + l + "s", strOctalNumber).replace(" ", "0");
    }

    public String defrostToDec(String bin) {

        String out = "";

        for (int i = 0; i < bin.length(); i = i + 6) {

            out += String.format("%2s", Integer.parseInt(bin.substring(i, i + 6), 2)).replace(" ", "0");
        }

        return out;
    }

    public String defrostToBin(String dec) {

        String out = "";

        for (int i = 0; i < dec.length(); i = i + 2) {

            out += String.format("%6s", Integer.toBinaryString(Integer.valueOf(dec.substring(i, i + 2)))).replace(" ", "0");
        }

        return out;
    }

    public String code_map(String in) {
        return code_hashmap.get(in);
    }

    public String decode_map(String in) {
        return decode_hashmap.get(in);
    }

    public String hexToBin(String hex) {
        StringBuilder binaries = new StringBuilder();
        for (int i = 0; i < hex.length(); i++) {
            String s = Integer.toBinaryString(Integer.parseInt(String.valueOf(hex.charAt(i)), 16));
            binaries.append(String.format("%4s", s).replace(' ', '0'));
        }
        return binaries.toString();
    }

    public String binToHex(String bin) {
        //(bin.length() + (4 - bin.length() % 4))
        StringBuilder hexa = new StringBuilder();
        bin = String.format("%" + 144 + "s", bin).replace(' ', '0');
        for (int i = 0; i < bin.length(); i = i + 4) {
            hexa.append(Integer.toHexString(Integer.parseInt(bin.substring(i, i + 4), 2)));
        }
        return hexa.toString();
    }

    public String qwe_float(float f, int trucenet) {

        int a = (int) (f * 10);

        if (f > 0) {
            return "1" + truecent_length(String.valueOf(a), trucenet);
        } else if (f == 0) {
            return "0" + truecent_length(String.valueOf(a), trucenet);
        } else {
            return "2" + truecent_length(String.valueOf(-a), trucenet);
        }
    }

    public float ewq_float(String i) {
        float out = 0;
        if (i.substring(0, 1).equals("1")) {
            out = (Float.parseFloat(i.substring(1)) / 10);
        } else if (i.substring(0, 1).equals("2")) {
            out = -(Float.parseFloat(i.substring(1)) / 10);
        }
        return out;
    }

    public int ewq_integer(String i) {
        int out = 0;

        if (i.substring(0, 1).equals("1")) {
            out = (Integer.parseInt(i.substring(1)));
        } else if (i.substring(0, 1).equals("2")) {
            out = -(Integer.parseInt(i.substring(1)));
        }
        return out;
    }

    public String qwe_integer(int i) {

        if (i > 0) {
            return "1" + i;
        } else if (i == 0) {
            return "0" + i;
        } else {
            return "2" + (-i);
        }
    }

    public List<String> split_two_by_two(String input) {
        List<String> output = new ArrayList<>();

        if (input.length() % 2 != 0) {
            input = input + "0";
        }

        for (int i = 0; i < input.length(); i = i + 2) {
            output.add(input.substring(i, i + 2));
        }
        return output;
    }

    public float sub_str_float(String map_charactrer) {
        String temp = "";
        for (int i = 0; i < map_charactrer.length(); i++) {
            temp = temp + decode_map(map_charactrer.substring(i, i + 1));
        }
        float result = ewq_float(temp);
        return result;
    }

    public int sub_str_integer(String map_charactrer) {
        String temp = "";
        for (int i = 0; i < map_charactrer.length(); i++) {
            temp = temp + decode_map(map_charactrer.substring(i, i + 1));
        }
        int result = ewq_integer(temp);
        return result;
    }

    public String decompress(String compressed) {
        String out = "";

        for (int i = 0; i < compressed.length(); i++) {
            if (compressed.charAt(i) == '$') {
                out = out.concat(decode_map(String.valueOf(compressed.charAt(i)) + String.valueOf(compressed.charAt(i + 1))));
                i++;
            } else {
                out = out.concat(decode_map(String.valueOf(compressed.charAt(i))));
            }
        }
        return out;
    }

    public int check_string(String data) {
        int sum = 0;
        for (int i = 0; i < data.length(); i++) {
            switch (data.substring(i, i + 1)) {
                case "[":
                    sum += 2;
                    break;
                case "]":
                    sum += 2;
                    break;
                case "\\":
                    sum += 2;
                    break;
                case "{":
                    sum += 2;
                    break;
                case "}":
                    sum += 2;
                    break;
                case "^":
                    sum += 2;
                    break;
                case "~":
                    sum += 2;
                    break;
                case "`":
                    sum += 91;
                    break;
                case "|":
                    sum += 2;
                    break;
                default:
                    sum += 1;
                    break;
            }
        }
        return sum;
    }


}
