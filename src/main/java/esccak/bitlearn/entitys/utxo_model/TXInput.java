package esccak.bitlearn.entitys.utxo_model;

import java.io.Serializable;

public class TXInput implements Serializable{

    private String tx_id;//hash de la transaccion donde esta el output
    private int out_index;//indice del output

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    private byte[] signature;


    public String getTx_id() {
        return tx_id;
    }

    public void setTx_id(String tx_id) {
        this.tx_id = tx_id;
    }

    public int getOut_index() {
        return out_index;
    }

    public void setOut_index(int out_index) {
        this.out_index = out_index;
    }
}
