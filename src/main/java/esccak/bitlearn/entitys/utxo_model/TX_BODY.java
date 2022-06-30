package esccak.bitlearn.entitys.utxo_model;

import java.io.Serializable;
import java.util.ArrayList;

public class TX_BODY implements Serializable {

    private ArrayList inputs;
    private ArrayList outputs;

    public TX_BODY(ArrayList inputs, ArrayList outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public ArrayList getOutputs() {
        return outputs;
    }

    public void setOutputs(ArrayList outputs) {
        this.outputs = outputs;
    }

    public ArrayList getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList inputs) {
        this.inputs = inputs;
    }
}
