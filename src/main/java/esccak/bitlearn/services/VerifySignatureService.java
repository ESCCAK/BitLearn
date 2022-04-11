package esccak.bitlearn.services;

import java.security.*;

public class VerifySignatureService {

    public static boolean isValidSignature(byte[]msg, PublicKey publicKey, byte[]sig)
    {
        Signature signature= null;
        try {
            signature = Signature.getInstance("SHA256withECDSA", "SunEC");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return false;
        }

        try {
            signature.initVerify(publicKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }

        try {
            signature.update(msg);
            boolean validSignature = signature.verify(sig);
            return validSignature;

        } catch (SignatureException e) {
            e.printStackTrace();
            return false;
        }


    }

}
