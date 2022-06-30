package esccak.bitlearn.services;

import esccak.bitlearn.entitys.utxo_model.TXInput;
import esccak.bitlearn.entitys.utxo_model.TXOutput;
import esccak.bitlearn.entitys.utxo_model.TX_BODY;
import esccak.bitlearn.entitys.utxo_model.Transaction;
import esccak.bitlearn.util.SHA256;
import org.junit.Test;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class UTXOModelTest {

    @Test
    public void return_true_when_is_a_valid_coinbase_tx() {

        //coinbase txs don't consume any output therefore input must be null

        TXOutput tx_output = new TXOutput("address",6);
        ArrayList outputs = new ArrayList();
        outputs.add(tx_output);
        TX_BODY tx_body = new TX_BODY(null,outputs);
        Transaction tx = new Transaction(tx_body);


        byte[] bytes = tx.toBytes();

        try {
            tx.setTxId(SHA256.toHexString(SHA256.getSHA(bytes)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        assertEquals(true, ValidateTransactionService.isValidCoinbaseTX(tx));


    }


    @Test
    public void return_true_when_is_a_valid_tx() {

        //valid tx consume an UTXO
        //the id must be the hash of the tx_body
        //the input must counsume a valid UTXO
        //ndvalid signature


        HashMap<String, Transaction> utxos = new HashMap<String, Transaction>();

        KeyPair keys = GenerateKeyPairService.generateKeyPairService();
        PrivateKey privateKey = keys.getPrivate();
        PublicKey publicKey = keys.getPublic();


        TXOutput tx_output = null;

        try {
            String address = SHA256.toHexString(SHA256.getSHA(publicKey.toString().getBytes())).toString();

            tx_output = new TXOutput(address,6);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        ArrayList outputs = new ArrayList();
        outputs.add(tx_output);
        TX_BODY tx_body = new TX_BODY(null,outputs);
        Transaction tx = new Transaction(tx_body);



        //obtain the hash from the tx_body
        byte[] bytes = tx.toBytes();

        try {
            tx.setTxId(SHA256.toHexString(SHA256.getSHA(bytes)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //add to the the utxos
        try {
            byte b = new Integer(0).byteValue();
            byte[] txindex = tx.getTxId().getBytes();
            byte[] c = new byte[txindex.length + 1];
            System.arraycopy(txindex, 0, c, 0, txindex.length);
            c[c.length - 1] = b;

            //System.arraycopy(b, 0, c, bytes.length, b);

            utxos.put(SHA256.toHexString(SHA256.getSHA(c)), tx);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //generate the keys to obtain the address to send
        KeyPair keys2 = GenerateKeyPairService.generateKeyPairService();

        PrivateKey privateKey2 = keys2.getPrivate();
        PublicKey publicKey2 = keys2.getPublic();
        TXOutput tx_output2 = null;
        try {
            String address = SHA256.toHexString(SHA256.getSHA(publicKey2.toString().getBytes())).toString();
            tx_output2 = new TXOutput(address,3);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        ArrayList outputs2 = new ArrayList();
        outputs2.add(tx_output2);

        ArrayList inputs = new ArrayList();
        TXInput input = new TXInput();
        input.setTx_id(tx.getTxId());
        input.setOut_index(0);
        input.setSignature(SignMessageService.signMessage(privateKey,tx.getTxId().getBytes()));

        //TODO
        //create a new input to send me back the remain coins

        inputs.add(input);
        TX_BODY tx_body2 = new TX_BODY(inputs,outputs2);
        Transaction tx2 = new Transaction(tx_body2);



        assertEquals(true, ValidateTransactionService.isValidTx(tx2, utxos, 0, publicKey));

    }




    @Test
    public void return_false_when_signature_is_invalid() {



        HashMap<String, Transaction> utxos = new HashMap<String, Transaction>();

        KeyPair keys = GenerateKeyPairService.generateKeyPairService();
        PrivateKey privateKey = keys.getPrivate();
        PublicKey publicKey = keys.getPublic();


        TXOutput tx_output = null;
        try {
            tx_output = new TXOutput(SHA256.toHexString(SHA256.getSHA(publicKey.toString().getBytes())).toString(),6);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        ArrayList outputs = new ArrayList();
        outputs.add(tx_output);
        TX_BODY tx_body = new TX_BODY(null,outputs);
        Transaction tx = new Transaction(tx_body);




        byte[] bytes = tx.toBytes();

        try {
            tx.setTxId(SHA256.toHexString(SHA256.getSHA(bytes)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        try {
            byte b = new Integer(0).byteValue();
            byte[] txindex = tx.getTxId().getBytes();
            byte[] c = new byte[txindex.length + 1];
            System.arraycopy(txindex, 0, c, 0, txindex.length);
            c[c.length - 1] = b;

            //System.arraycopy(b, 0, c, bytes.length, b);

            utxos.put(SHA256.toHexString(SHA256.getSHA(c)), tx);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        KeyPair keys2 = GenerateKeyPairService.generateKeyPairService();

        PrivateKey privateKey2 = keys2.getPrivate();
        PublicKey publicKey2 = keys2.getPublic();
        TXOutput tx_output2 = null;
        try {
            tx_output2 = new TXOutput(SHA256.toHexString(SHA256.getSHA(publicKey2.toString().getBytes())).toString(),3);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        ArrayList outputs2 = new ArrayList();
        outputs2.add(tx_output2);

        ArrayList inputs = new ArrayList();
        TXInput input = new TXInput();
        input.setTx_id(tx.getTxId());
        input.setOut_index(0);
        input.setSignature(SignMessageService.signMessage(privateKey2,tx.getTxId().getBytes()));

        inputs.add(input);
        TX_BODY tx_body2 = new TX_BODY(inputs,outputs2);
        Transaction tx2 = new Transaction(tx_body2);



        assertEquals(true, ValidateTransactionService.isValidTx(tx2, utxos, 0, publicKey2));

    }


    @Test
    public void return_false_when_is_a_invalid_utxo() {
        {




            HashMap<String, Transaction> utxos = new HashMap<String, Transaction>();

            KeyPair keys = GenerateKeyPairService.generateKeyPairService();
            PrivateKey privateKey = keys.getPrivate();
            PublicKey publicKey = keys.getPublic();


            TXOutput tx_output = null;
            try {
                tx_output = new TXOutput(SHA256.toHexString(SHA256.getSHA(publicKey.toString().getBytes())).toString(),6);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            ArrayList outputs = new ArrayList();
            outputs.add(tx_output);
            TX_BODY tx_body = new TX_BODY(null,outputs);
            Transaction tx = new Transaction(tx_body);




            byte[] bytes = tx.toBytes();

            try {
                tx.setTxId(SHA256.toHexString(SHA256.getSHA(bytes)));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }


            try {
                byte b = new Integer(0).byteValue();
                byte[] txindex = tx.getTxId().getBytes();
                byte[] c = new byte[txindex.length + 1];
                System.arraycopy(txindex, 0, c, 0, txindex.length);
                c[c.length - 1] = b;

                //System.arraycopy(b, 0, c, bytes.length, b);

                utxos.put(SHA256.toHexString(SHA256.getSHA(c)), tx);


            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            KeyPair keys2 = GenerateKeyPairService.generateKeyPairService();

            PrivateKey privateKey2 = keys2.getPrivate();
            PublicKey publicKey2 = keys2.getPublic();
            TXOutput tx_output2 = null;
            try {
                tx_output2 = new TXOutput(SHA256.toHexString(SHA256.getSHA(publicKey2.toString().getBytes())).toString(),3);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            ArrayList outputs2 = new ArrayList();
            outputs2.add(tx_output2);

            ArrayList inputs = new ArrayList();
            TXInput input = new TXInput();
            input.setTx_id(tx.getTxId() + "invalid utxo");
            input.setOut_index(0);
            input.setSignature(SignMessageService.signMessage(privateKey2,tx.getTxId().getBytes()));

            inputs.add(input);
            TX_BODY tx_body2 = new TX_BODY(inputs,outputs2);
            Transaction tx2 = new Transaction(tx_body2);



            assertEquals(false, ValidateTransactionService.isValidTx(tx2, utxos, 0, publicKey2));

        }
    }

    @Test
    public void return_false_when_is_a_invalid_index_tx_out() {
        {


            HashMap<String, Transaction> utxos = new HashMap<String, Transaction>();

            KeyPair keys = GenerateKeyPairService.generateKeyPairService();
            PrivateKey privateKey = keys.getPrivate();
            PublicKey publicKey = keys.getPublic();


            TXOutput tx_output = null;
            try {
                tx_output = new TXOutput(SHA256.toHexString(SHA256.getSHA(publicKey.toString().getBytes())).toString(),6);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            ArrayList outputs = new ArrayList();
            outputs.add(tx_output);
            TX_BODY tx_body = new TX_BODY(null,outputs);
            Transaction tx = new Transaction(tx_body);




            byte[] bytes = tx.toBytes();

            try {
                tx.setTxId(SHA256.toHexString(SHA256.getSHA(bytes)));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }


            try {
                byte b = new Integer(1).byteValue();
                byte[] txindex = tx.getTxId().getBytes();
                byte[] c = new byte[txindex.length + 1];
                System.arraycopy(txindex, 0, c, 0, txindex.length);
                c[c.length - 1] = b;


                utxos.put(SHA256.toHexString(SHA256.getSHA(c)), tx);


            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            KeyPair keys2 = GenerateKeyPairService.generateKeyPairService();

            PrivateKey privateKey2 = keys2.getPrivate();
            PublicKey publicKey2 = keys2.getPublic();
            TXOutput tx_output2 = null;
            try {
                tx_output2 = new TXOutput(SHA256.toHexString(SHA256.getSHA(publicKey2.toString().getBytes())).toString(),3);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            ArrayList outputs2 = new ArrayList();
            outputs2.add(tx_output2);

            ArrayList inputs = new ArrayList();
            TXInput input = new TXInput();
            input.setTx_id(tx.getTxId());
            input.setOut_index(0);
            input.setSignature(SignMessageService.signMessage(privateKey2,tx.getTxId().getBytes()));

            inputs.add(input);
            TX_BODY tx_body2 = new TX_BODY(inputs,outputs2);
            Transaction tx2 = new Transaction(tx_body2);



            assertEquals(false, ValidateTransactionService.isValidTx(tx2, utxos, 0, publicKey2));

        }
    }



}
