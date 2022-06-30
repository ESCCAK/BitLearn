package esccak.bitlearn.services;

import esccak.bitlearn.entitys.utxo_model.TXInput;
import esccak.bitlearn.entitys.utxo_model.TXOutput;
import esccak.bitlearn.entitys.utxo_model.Transaction;
import esccak.bitlearn.util.SHA256;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashMap;

public class ValidateTransactionService {

    public static boolean isValidCoinbaseTX(Transaction tx)
    {
        if(tx.getTx_body().getInputs() != null)
            return false;
        if (tx.getTx_body().getOutputs() == null)
            return false;

        try {
            if (!tx.getTxId().equals(SHA256.toHexString(SHA256.getSHA(tx.toBytes()))))
                return false;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static boolean isValidTx(Transaction tx, HashMap<String, Transaction> utxos, int index, PublicKey publicKey)
    {

        //tx should consume an output
        //the output should be in the utxos set




        TXInput input = (TXInput) tx.getInputs().get(index);

        if (!VerifySignatureService.isValidSignature(input.getTx_id().getBytes(),  publicKey, input.getSignature()))
            return false;

        byte[] bytes = input.getTx_id().getBytes();

        input.getSignature();

        byte[] c = new byte[bytes.length + 1];

        try {
            byte b = new Integer(index).byteValue();

            System.arraycopy(bytes, 0, c, 0, bytes.length);
            c[c.length - 1] = b;



            if (utxos.get(SHA256.toHexString(SHA256.getSHA(c))) == null)
                return false;


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        Transaction  transaction = null;
        try {
            transaction = utxos.get(SHA256.toHexString(SHA256.getSHA(c)));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        TXOutput output = (TXOutput) transaction.getOutputs().get(index);





        return true;
    }
}
