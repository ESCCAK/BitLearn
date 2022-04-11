package esccak.bitlearn.services;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class DigitalSignaturesTest {

    @Test
    public void return_true_when_sign_is_valid() {


        byte[] sign;
        KeyPair keys = GenerateKeyPairService.generateKeyPairService();
        PrivateKey privateKey = keys.getPrivate();
        PublicKey publicKey = keys.getPublic();

        //get another private key
        KeyPair alter_keys = GenerateKeyPairService.generateKeyPairService();
        PrivateKey invalidKey = keys.getPrivate();

        try {
            sign = SignMessageService.signMessage(invalidKey, "hola".getBytes("UTF-8"));
            assertTrue(VerifySignatureService.isValidSignature("hola".getBytes("UTF-8"),publicKey, sign));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



    }


    @Test
    public void return_false_when_sign_is_invalid() {


        byte[] sign;
        KeyPair keys = GenerateKeyPairService.generateKeyPairService();
        PublicKey publicKey = keys.getPublic();

        KeyPair alterKeys = GenerateKeyPairService.generateKeyPairService();

        PrivateKey alterPrivateKey = alterKeys.getPrivate();

        try {
            sign = SignMessageService.signMessage(alterPrivateKey, "hola".getBytes("UTF-8"));
            assertFalse(VerifySignatureService.isValidSignature("hola".getBytes("UTF-8"),publicKey, sign));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



    }



}
