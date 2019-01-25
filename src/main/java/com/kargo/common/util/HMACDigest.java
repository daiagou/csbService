package com.kargo.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HMACDigest
{

	private static final Logger logger = LoggerFactory.getLogger(HMACDigest.class);
	
	public static String HMACSHA256(String macData, String macKey)
	{
	        try
			{
				Mac mac = Mac.getInstance("HmacSHA256");
				//get the bytes of the hmac key and data string
				byte[] secretByte = macKey.getBytes("UTF-8");
				byte[] dataBytes = macData.getBytes("UTF-8");
				SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");

				mac.init(secret);
				byte[] doFinal = mac.doFinal(dataBytes);
				
				String checksum = bytes2Hex(doFinal);
				
				return checksum;
			}
			catch (InvalidKeyException e)
			{
				logger.error(e.getMessage(),e);
			}
			catch (NoSuchAlgorithmException e)
			{
				logger.error(e.getMessage(),e);
			}
			catch (UnsupportedEncodingException e)
			{
				logger.error(e.getMessage(),e);
			}
			catch (IllegalStateException e)
			{
				logger.error(e.getMessage(),e);
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

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
//		//String strData="123456";
//		//String stringToSign = "POST\nE2A20B7992AF65F6984ABA15B048C813\n1419551155\n/api/ver1.1/order";
//		String req = "{\"get-ord-details\":false,\"mer-id\":\"18621000021\",\"mer-ord-id\":\"1416200365\",\"ord-create-date\":\"20141117125925\",\"ord-delivery-date\":\"20141117125925\",\"ord-item\":[{\"face-value\":500.0,\"price\":500.0,\"qty\":1,\"upc\":\"8862100000021\"}],\"ord-pymt-rcvd\":false}";
//		//String   req = "{\"get-ord-details\":false,\"mer-id\":\"18621000021\",\"mer-ord-id\":\"V18oHwAAAAABAAEAAABWt3afCAgFAF0R\",\"ord-create-date\":\"20141117150651\",\"ord-delivery-date\":\"20141117150651\",\"ord-item\":[{\"face-value\":0.010,\"price\":0.010,\"qty\":1,\"upc\":\"8862100000021\"}],\"ord-pymt-rcvd\":false}";
//
//		System.out.println("req=>"+req);
//		String str = "POST\n"+CryptUtil.md5(req)+"\n1416200365\n/api/ver1.1/order";
//		//String str = "POST\n"+CryptUtil.md5(req)+"\n1416179211000\n/api/ver1.1/order";
//
//		//String str = "POST\nEBC1C93B147DDE11FB1CD5052D87A38D\n1416171565000\n/api/ver1.1/order";
//
//
//		System.out.println(str);
//		String ret = HMACSHA256(str, "kargocard").toUpperCase();
//		System.out.println(ret);
//		//kc-mer-123456:1C37BDFBF9927A5BCB70982EB0F89633C5FC310304685B473C586147988B30DF
//		//              751D7F7D53D80066CDE7C057CB77D51FE6A1ACF7C21486902BEF9D084228BBD8
//		//                             3BD29DE340BEB9BF8AB871F304C0826077C075052B4FDE90C76E16AED9775841
//
//		//Authorization: kc-mer-123456:3BD29DE340BEB9BF8AB871F304C0826077C075052B4FDE90C76E16AED9775841
//		//               kc-mer-123456:3BD29DE340BEB9BF8AB871F304C0826077C075052B4FDE90C76E16AED9775841
//		//							   F39FB4F672004CD639688F12140672B38518F628F5EC624AC8B0FCE9469879D4
//
//
//
//		System.out.println("==============================================================================");
//		req = "{\"merchantID\":\"11111999990\",\"temrinalID\":\"123456789\",\"mer-OrderID\":\"14101618082389756\"}";
//		System.out.println(req);
//		str = "POST\n"+ CryptUtil.md5(req) + "\n"+1419551155+"\n"+"/api/ver1.1/order";
//		//StringToSign = "POST\nE2A20B7992AF65F6984ABA15B048C813\n1419551155\n/api/ver1.1/order"
//		System.out.println(str);
//		ret = HMACSHA256(str, "aef123feff").toUpperCase();
//		System.out.println(ret);
//		//kc-mer-123456:C198C66C54A2AB3E8A20C3519A38294A4CCFD8955B4508BB86DC32DE833D62AE
//		//kc-mer-123456:C198C66C54A2AB3E8A20C3519A38294A4CCFD8955B4508BB86DC32DE833D62AE


		System.out.println(HMACSHA256("123456","aef123feff"));


	}

}
