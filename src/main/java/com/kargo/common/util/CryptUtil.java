package com.kargo.common.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;

public class CryptUtil
{
	//Signature = HexEncode(Base64(HMAC-SHA256(kc-secret-key,UTF-8-Encoding-Of(StringToSign))))
	public static String getSignature(String strToSign, String secretKey)
	{
		String signature = null;
		try 
		{
			Mac mac = Mac.getInstance("HmacSHA256");
			//get the bytes of the hmac key and data string
			byte[] secretByte = secretKey.getBytes("UTF-8");
			byte[] dataBytes = strToSign.getBytes("UTF-8"); //UTF-8-Encoding-Of(StringToSign)
			SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
			mac.init(secret);
			byte[] doFinal = mac.doFinal(dataBytes); //HMAC-SHA256(kc-secret-key,xxx)
			//signature = Base64.encodeBase64String(doFinal); //Base64(xxx)
			//signature = CryptUtil.bytes2Hex(signature.getBytes("UTF-8")); //HexEncode
			//signature = signature.toUpperCase();
			
			//byte[] doFinal = mac.doFinal(dataBytes);
			
			signature = bytes2Hex(doFinal);
			
			
			
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		catch (InvalidKeyException e) 
		{
			e.printStackTrace();
		} 
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return signature;
	}

    public static String md5(String input) 
    {
    	String md5 = null;
    	if (null == input)
    		return null;
    	try 
    	{
		    //Create MessageDigest object for MD5
		    MessageDigest digest = MessageDigest.getInstance("MD5");
	
		    //Update input string in message digest
		    digest.update(input.getBytes(), 0, input.length());
	
		    //Converts message digest value in base 16 (hex)
		    md5 = new BigInteger(1, digest.digest()).toString(16);
		    
		    md5 = md5.toUpperCase();
    	} 
    	catch (NoSuchAlgorithmException e) 
    	{
    		e.printStackTrace();
    	}
    	return md5;
    }


    public static String getZoneKey()
    {
    	char[] alphNum = "ABCDEF0123456789".toCharArray();
    	Random rnd = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10; i++)
		    sb.append(alphNum[rnd.nextInt(alphNum.length)]);
		//sb.append((10000 + rnd.nextInt(90000)) + "");
		String key = sb.toString();
		System.out.println(key);
		return key;
    }
    
    public static String encryptCBC(String strPlainText, String strKey, String strIV)
	{
		try
		{
			SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "AES");

			AlgorithmParameterSpec paramSpec = new IvParameterSpec(strIV.getBytes());

			Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			
			return bytes2Hex(ecipher.doFinal(strPlainText.getBytes("UTF-8")));

		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (BadPaddingException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (InvalidAlgorithmParameterException e)
		{
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public static String decryptCBC(String value, String strKey, String strIV)
	{
		try
		{
			SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "AES");

			AlgorithmParameterSpec paramSpec = new IvParameterSpec(strIV.getBytes());

			Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			
			ecipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			return new String(ecipher.doFinal(hexToBytes(value)));
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch (BadPaddingException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (InvalidAlgorithmParameterException e)
		{
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		return null;
	}
    
    
	public static String bytes2Hex(byte[] bts)
	{
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++)
		{
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1)
			{
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	
	private static byte[] hexToBytes(String str)
	{
		if (str == null)
		{
			return null;
		}
		else if (str.length() < 2)
		{
			return null;
		}
		else
		{
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++)
			{
				buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}

	}
	
	
	public static void main(String[] args)
	{
		
//		String httpBody = "{\"merchantID\":\"11111999990\",\"temrinalID\":\"123456789\",\"mer-OrderID\":\"14101618082389756\"}";
//		
//		String md5 = CryptUtil.md5(httpBody);
//		//Message-Content-MD5 = E2A20B7992AF65F6984ABA15B048C813  
//		System.out.println(md5);
//		
//		String stringToSign = "POST\nE2A20B7992AF65F6984ABA15B048C813\n1419551155\n/api/ver1.1/order";
//		
//		
//		//Signature = C198C66C54A2AB3E8A20C3519A38294A4CCFD8955B4508BB86DC32DE8 33D62AE
//		String ret = CryptUtil.getSignature(stringToSign, "aef123feff");
		System.out.println(decryptCBC("BEAE52D6D1EE6D7F0949040A6E34360A0E44DF54F670846670FCD48FB307E9CA", "E1C956V1GUFEAF3O", "L4DOKCURP7GIZGTT"));
		System.out.println(decryptCBC("6DE7B569C9127D2461CB5DD56D009B8D", "E1C956V1GUFEAF3O", "QXGH9ENGEID7IZUH"));
		System.out.println(encryptCBC("888888009090450477889788999888888009090450477889788999", "E1C956V1GUFEAF3O", "L4DOKCURP7GIZGTT").toUpperCase());

		
	}
}
