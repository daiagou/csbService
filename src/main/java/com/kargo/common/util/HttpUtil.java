package com.kargo.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created with IntelliJ IDEA.
 * User: dannygu
 * Date: 12/3/15
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String SendHTTPRequest(String POST_URL, String HTTP_METHOD, String strCotent){

        try
        {
            URL postUrl = new URL(POST_URL);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();

            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(HTTP_METHOD);  //GET or POST
            connection.setUseCaches(false);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);

            connection.setInstanceFollowRedirects(true);

            if(strCotent.length()>0)
            {
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.write(strCotent.getBytes("UTF-8"));
                out.flush();
                out.close(); // flush and close
            }
            int returnCode = connection.getResponseCode();

            if (returnCode!= HttpURLConnection.HTTP_OK)
            {
                logger.info("HTTP returnCode:" + returnCode);
                logger.info("HTTP returnMessage:"+connection.getResponseMessage());

                InputStream inn = connection.getInputStream();
                InputStreamReader in = new InputStreamReader(inn,"UTF-8");
                BufferedReader reader = new BufferedReader(in);//设置编码,否则中文乱码
                String line="";
                StringBuffer strBuf=new StringBuffer();

                while ((line = reader.readLine()) != null){
                    strBuf.append(line);
                }

                return null;
            }

            InputStreamReader in = new InputStreamReader(connection.getInputStream(),"UTF-8");
            BufferedReader reader = new BufferedReader(in);//设置编码,否则中文乱码
            String line="";
            StringBuffer strBuf=new StringBuffer();
            logger.info("URL=" + POST_URL);
            while ((line = reader.readLine()) != null){
                strBuf.append(line);
            }
            logger.info("Request=" + strCotent.toString());
            logger.info("Reponse=" + strBuf.toString());

            reader.close();
            connection.disconnect();

            return strBuf.toString();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (ProtocolException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String SendHTTPSRequest(String POST_URL, String HTTP_METHOD, String strCotent){

        try
        {
            trustAllHosts();
            URL postUrl = new URL(POST_URL);
            HttpsURLConnection connection = (HttpsURLConnection) postUrl.openConnection();
            connection.setHostnameVerifier(new HostnameVerifier()
            {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(HTTP_METHOD);  //GET or POST
            connection.setUseCaches(false);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);

            connection.setInstanceFollowRedirects(true);

            if(strCotent.length()>0)
            {
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.write(strCotent.getBytes("UTF-8"));
                out.flush();
                out.close(); // flush and close
            }
            int returnCode = connection.getResponseCode();

            if (returnCode!= HttpURLConnection.HTTP_OK)
            {
                logger.info("HTTP returnCode:" + returnCode);
                logger.info("HTTP returnMessage:"+connection.getResponseMessage());

                InputStream inn = connection.getInputStream();
                InputStreamReader in = new InputStreamReader(inn,"UTF-8");
                BufferedReader reader = new BufferedReader(in);//设置编码,否则中文乱码
                String line="";
                StringBuffer strBuf=new StringBuffer();

                while ((line = reader.readLine()) != null){
                    strBuf.append(line);
                }

                return null;
            }

            InputStreamReader in = new InputStreamReader(connection.getInputStream(),"UTF-8");
            BufferedReader reader = new BufferedReader(in);//设置编码,否则中文乱码
            String line="";
            StringBuffer strBuf=new StringBuffer();
            logger.info("URL=" + POST_URL);
            while ((line = reader.readLine()) != null){
                strBuf.append(line);
            }
            logger.info("Request=" + strCotent.toString());
            logger.info("Reponse=" + strBuf.toString());

            reader.close();
            connection.disconnect();

            return strBuf.toString();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (ProtocolException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    private static void trustAllHosts()
    {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager()
        {
            public X509Certificate[] getAcceptedIssuers()
            {
                return new X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
            {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
            {
            }
        } };

        // Install the all-trusting trust manager
        try
        {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//            sslContext=sc;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
