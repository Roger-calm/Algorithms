package DES;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES {
	   public static byte[] generateKey() {
	        try {

	            // DES算法要求有一个可信任的随机数源
	            SecureRandom sr = new SecureRandom();

	            // 生成一个DES算法的KeyGenerator对象
	            KeyGenerator kg = KeyGenerator.getInstance("DES");
	            kg.init(sr);

	            // 生成密钥
	            SecretKey secretKey = kg.generateKey();

	            // 获取密钥数据
	            byte[] key = secretKey.getEncoded();

	            return key;
	        } catch (NoSuchAlgorithmException e) {
	            System.err.println("DES算法，生成密钥出错!");
	            e.printStackTrace();
	        }

	        return null;
	   }
	  /**
	   * 加密函数
	   *
	   * @param data
	   *            加密数据
	   * @param key
	   *            密钥
	   * @return 返回加密后的数据
	   */
	   public static byte[] encrypt(byte[] data, byte[] key) {

	        try {

	            // DES算法要求有一个可信任的随机数源
	            SecureRandom sr = new SecureRandom();

	            // 从原始密钥数据创建DESKeySpec对象
	            DESKeySpec dks = new DESKeySpec(key);

	            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
	            // 一个SecretKey对象
	            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	            SecretKey secretKey = keyFactory.generateSecret(dks);

	            // using DES in ECB mode
	            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

	            // 用密匙初始化Cipher对象
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

	            // 执行加密操作
	            byte encryptedData[] = cipher.doFinal(data);

	            System.out.println("ECB加密后的数据16进制表示："+byte2hex(encryptedData));
	            
	            
	            return encryptedData;
	        } catch (Exception e) {
	            System.err.println("DES算法，加密数据出错!");
	            e.printStackTrace();
	        }

	        return null;
	   }

	   /**
	   * 解密函数
	   *
	   * @param data
	   *            解密数据
	   * @param key
	   *            密钥
	   * @return 返回解密后的数据
	   */
	   public static byte[] decrypt(byte[] data, byte[] key) {
	        try {
	            // DES算法要求有一个可信任的随机数源
	            SecureRandom sr = new SecureRandom();

	            // byte rawKeyData[] = /* 用某种方法获取原始密匙数据 */;

	            // 从原始密匙数据创建一个DESKeySpec对象
	            DESKeySpec dks = new DESKeySpec(key);

	            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
	            // 一个SecretKey对象
	            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	            SecretKey secretKey = keyFactory.generateSecret(dks);

	            // using DES in ECB mode
	            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

	            // 用密匙初始化Cipher对象
	            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

	            // 正式执行解密操作
	            byte decryptedData[] = cipher.doFinal(data);
	            System.out.println("ECB解密后的数据16进制表示："+byte2hex(decryptedData));
	            return decryptedData;
	        } catch (Exception e) {
	            System.err.println("DES算法，解密出错。");
	            e.printStackTrace();
	        }

	        return null;
	   }

	   /**
	   * 加密函数
	   *
	   * @param data
	   *            加密数据
	   * @param key
	   *            密钥
	   * @return 返回加密后的数据
	   */
	   public static byte[] CBCEncrypt(byte[] data, byte[] key, byte[] iv) {

	        try {
	            // 从原始密钥数据创建DESKeySpec对象
	            DESKeySpec dks = new DESKeySpec(key);

	            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
	            // 一个SecretKey对象
	            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	            SecretKey secretKey = keyFactory.generateSecret(dks);

	            // Cipher对象实际完成加密操作
	            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	            // 若采用NoPadding模式，data长度必须是8的倍数
	            // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

	            // 用密匙初始化Cipher对象
	            IvParameterSpec param = new IvParameterSpec(iv);
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);

	            // 执行加密操作
	            byte encryptedData[] = cipher.doFinal(data);
	          
	            System.out.println("CBC加密后的数据16进制表示："+byte2hex(encryptedData));
	            
	            return encryptedData;
	        } catch (Exception e) {
	            System.err.println("DES算法，加密数据出错!");
	            e.printStackTrace();
	        }

	        return null;
	   }

	   /**
	   * 解密函数
	   *
	   * @param data
	   *            解密数据
	   * @param key
	   *            密钥
	   * @return 返回解密后的数据
	   */
	   public static byte[] CBCDecrypt(byte[] data, byte[] key, byte[] iv) {
	        try {
	            // 从原始密匙数据创建一个DESKeySpec对象
	            DESKeySpec dks = new DESKeySpec(key);

	            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
	            // 一个SecretKey对象
	            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	            SecretKey secretKey = keyFactory.generateSecret(dks);

	            // using DES in CBC mode
	            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	            // 若采用NoPadding模式，data长度必须是8的倍数
	            // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

	            // 用密匙初始化Cipher对象
	            IvParameterSpec param = new IvParameterSpec(iv);
	            cipher.init(Cipher.DECRYPT_MODE, secretKey, param);

	            // 正式执行解密操作
	            byte decryptedData[] = cipher.doFinal(data);
	            System.out.println("CBC解密后的数据16进制表示："+byte2hex(decryptedData));
	            return decryptedData;
	        } catch (Exception e) {
	            System.err.println("DES算法，解密出错。");
	            e.printStackTrace();
	        }

	        return null;
	   }
	   
	   public static  String byte2hex(byte[] b) //二行制转字符串
	    {
	     String hs="";
	     String stmp="";
	     for (int n=0;n<b.length;n++)
	      {
	       stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
	       /**
	        *  Java的二进制采用补码形式，byte范围是[-128, 127]， 8位
	        *  而Integer.toHexString的参数是int，32位  
	        *  如果不进行&0xff，（将其他位全部置于0）
	        *  那么当一个byte会转换成int时，对于负数，会做位扩展，
	举例来说，一个byte类型的-1（即0xff），会被转换成int 类型的-1（即0xffffffff），
	此时十进制的值，没有变化还是为 -1
	但是二进制以及发生变化
						byte  二进制：0xff    十进制 -1
						int   二进制： 0xffffffff  十进制 -1
	那么转化出的结果就不是我们想要的了。
	使用&0xff，（将其他位全部置于0）使得二进制不发生变化。

	-1 hex  : 0xffffffff
	-1&0xff : 0xff

	        */
	       
	       if (stmp.length()==1) hs=hs+"0"+stmp;
	       else hs=hs+stmp;
	       if (n<b.length-1)  hs=hs+":";
	      }
	     return hs.toUpperCase();
	    }

	   public static void main(String[] args) {
	        try {
	            byte[] key = "11111111".getBytes();
	            byte[] iv = "22222222".getBytes();
	            byte[] data = DES.encrypt("ebc mode test".getBytes(), key);
	            System.out.print("EBC mode:");
	            System.out.println(new String(DES.decrypt(data, key)));
	            System.out.print("CBC mode:");
	            data = DES.CBCEncrypt("cbc mode test".getBytes(), key, iv);
	            System.out.println(new String(DES.CBCDecrypt(data, key, iv)));
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	   }
	}
	  