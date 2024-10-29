//package utils;
//
//import javax.crypto.*;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.Arrays;
//import java.util.Base64;
//
//public class EncryptionUtil {
//    private static final String CIPHER_ALGORITHM = "AES";
//
//    /* *
//     * @param data to be encrypted
//     * @throws Exception Exception
//     * @description : To encrypt the test data or property using AES encryption algorithm
//     * @return The encrypted value as a Base64-encoded string.
//     */
//    public static String encrypt(String data) throws Exception {
//        try {
//            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            byte[] encryptedData = cipher.doFinal(data.getBytes());
//            return Base64.getEncoder().encodeToString(encryptedData);
//        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
//                 InvalidKeyException e) {
//            System.out.println("Failed to encrypt data" + e);
//            throw new Exception("Failed to encrypt data", e);
//        }
//    }
//
//    /* *
//     * @param encryptedData to be decrypted
//     * @throws Exception Exception
//     * @description : To decrypt the encrypted test data or property using AES encryption algorithm
//     * @return The decrypted value as a string.
//     */
//    public static String decrypt(String encryptedData) throws Exception {
//        byte[] encryptedTextByte = Base64.getDecoder().decode(encryptedData);
//        try {
//            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            byte[] decryptedData = cipher.doFinal(encryptedTextByte);
//            return new String(decryptedData);
//        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
//                 InvalidKeyException e) {
//            System.out.println("Failed to decrypt data" + e);
//            throw new Exception("Failed to decrypt data", e);
//        }
//    }
//
//    /* *
//     * @param encodedKey Base64-encoded key string.
//     * @description : To decode the Base64-encoded key string and converts it back to a SecretKey object.
//     * @return The decoded SecretKey object.
//     */
//    public static SecretKey decodeKey(String encodedKey) {
//        // Decode the Base64-encoded key string back to a byte array
//        byte[] keyBytes = Base64.getDecoder().decode(encodedKey);
//        // Convert the byte array back to a SecretKey
//        return new SecretKeySpec(keyBytes, CIPHER_ALGORITHM);
//    }
//
//    /* *
//     * @description : To generate a secret key object using AES encryption algorithm.
//     * @return Base64-encoded secret key string
//     */
//    public static String generateKey() throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance(CIPHER_ALGORITHM);
//        keyGenerator.init(128);
//        SecretKey secretKey = keyGenerator.generateKey();
//        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
//    }
//
//    /* *
//     * @description : To encrypt a set of test data or properties using AES encryption algorithm
//     * Update the dataSet array with the test data set or properties
//     * This will print the tests data or the property along with the corresponding encrypted string
//     */
//
//    public static void encryptProperties() {
//        //Values to be encrypted in comma separated format
//        String[] dataSet = {"*data"};
//        Arrays.asList(dataSet).forEach((data) -> {
//            try {
//                System.out.println(data+" : "+encrypt(data));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//}
