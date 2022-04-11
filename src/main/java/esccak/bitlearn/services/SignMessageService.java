package esccak.bitlearn.services;

import java.security.*;

public class SignMessageService {


    public static byte[] signMessage(PrivateKey privateKey, byte[]msg)
    {
        Signature signature = null;

        byte[] sign;
        try {
            signature = Signature.getInstance("SHA256withECDSA", "SunEC");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        }
        try {
            signature.initSign(privateKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }

        try {
            signature.update(msg);
            sign = signature.sign();
        } catch (SignatureException e) {
            e.printStackTrace();
            return null;
        }



        return sign;
    }
}
