package esccak.bitlearn.entitys.utxo_model;

import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.util.ArrayList;

public class Transaction implements Serializable{


    private String txId;

    private TX_BODY tx_body;

    public Transaction(TX_BODY tx_body) {
        this.tx_body = tx_body;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public TX_BODY getTx_body() {
        return tx_body;
    }

    public void setTx_body(TX_BODY tx_body) {
        this.tx_body = tx_body;
    }



    public  byte[] toBytes()
    {

        byte[] data = SerializationUtils.serialize(this.getTx_body());
        return data;
    }

    public static Transaction deserialize(byte[] bytes)
    {

        Transaction transaction = SerializationUtils.deserialize(bytes);
        TXOutput out =(TXOutput) transaction.getTx_body().getOutputs().get(0);
        System.out.println(out.getAmount());
        return transaction;
    }

    public ArrayList getInputs()
    {
        return this.getTx_body().getInputs();
    }

    public ArrayList getOutputs()
    {
        return this.getTx_body().getOutputs();
    }
//body
        //inputs
        //output


}
