package com.kargo.common.util;


import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class SecurityInfoUtil {
    private static String characterEncoding = "UTF-8";
    private static Cipher encryptCipher;

    private static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }

    private static SecretKey makeKey(String[] keyArr) throws Exception {
        byte[] rawkey1 = hexToBytes(keyArr[0]);
        byte[] rawkey2 = hexToBytes(keyArr[1]);
        byte[] rawkey3 = hexToBytes(keyArr[2]);
        byte[] rawkey = new byte[24];
        System.arraycopy(rawkey1, 0, rawkey, 0, 8);
        System.arraycopy(rawkey2, 0, rawkey, 8, 8);
        System.arraycopy(rawkey3, 0, rawkey, 16, 8);
        DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESEDE");
        SecretKey key = keyfactory.generateSecret(keyspec);
        return key;
    }


    public static String padright(String s, char c) throws Exception {
        s = s.trim();

        int iNum = s.length() % 8;
        if (iNum == 0) {
            return s;
        } else {
            StringBuffer d = new StringBuffer(s.length() + (8 - iNum));
            d.append(s);
            for (int i = 0; i < (8 - iNum); i++) {
                d.append(c);
            }
            return d.toString();
        }
    }

    public static byte[] getEncryptedMessage(String terminalid, String merchantid) throws Exception {
        byte[] encryptedDataStringBytes = null;
        try {
            String[] keyArr = new String[3];
            keyArr[0] = "CBE1704CDF2009CCCBE1704CDF2009CC";
            keyArr[1] = "ADF1036CFA1983EFCBE1704CDF2009CC";
            keyArr[2] = "CBE1704CDF2009CCCBE1704CDF2009CC";

            SecretKey myDesKey = makeKey(keyArr);
            encryptCipher = Cipher.getInstance("DESEDE/ECB/NoPadding", "SunJCE");
            encryptCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

            Security.addProvider(new com.sun.crypto.provider.SunJCE());

            String dataString = terminalid + "|" + merchantid;

            dataString = padright(dataString, ' ');
            byte[] dataStringBytes = dataString.getBytes(characterEncoding);
            encryptedDataStringBytes = encryptCipher.doFinal(dataStringBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return encryptedDataStringBytes;
    }

    public static String byteToHexString(byte ib) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    public static String byteArrayToHexString(String terminalid, String merchantid) {
        byte[] bytearray = new byte[0];
        try {
            bytearray = getEncryptedMessage(terminalid, merchantid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String strDigest = "";
        for (int i = 0; i < bytearray.length; i++) {
            strDigest += byteToHexString(bytearray[i]);
        }
        return strDigest;
    }
}