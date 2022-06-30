package esccak.bitlearn.entitys.utxo_model;

import java.io.Serializable;

public class TXOutput implements Serializable {

    private String address;
    private long amount;

    public TXOutput(String address, long amount) {
        this.address = address;
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
