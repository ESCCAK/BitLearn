package esccak.bitlearn.services;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class GenerateKeyPairService {



    public static KeyPair generateKeyPairService()
    {
        KeyPairGenerator g = null;
        try {
            g = KeyPairGenerator.getInstance("EC", "SunEC");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        }
        ECGenParameterSpec ecsp = new ECGenParameterSpec("secp224r1");

        try {
            g.initialize(ecsp);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        }

        KeyPair kp = g.genKeyPair();
        return kp;
    }
}
