import sun.plugin2.message.Message;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 */
public class MD5Util {
    /**
     * MD5加密
     * @param str
     * @return
     */
    public static String encodeStr(String str) throws NoSuchAlgorithmException{
        return  new BigInteger(1,MessageDigest.getInstance("MD5").digest(str.getBytes())).toString(16);
    }
    /**
     * 对文件进行md5加密
     * @param file
     * @return
     */
    public static String encodeFile(File file) throws FileNotFoundException,NoSuchAlgorithmException,IOException{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String result = null;
        BufferedInputStream bis = null;
        byte [] buffer = new byte[1024];
        bis = new BufferedInputStream(new FileInputStream(file));
        while(bis.read(buffer,0,buffer.length)!=-1){
            md5.update(buffer);
        }
        bis.close();
        result = new BigInteger(1,md5.digest()).toString(16);
        return result;
    }
}
