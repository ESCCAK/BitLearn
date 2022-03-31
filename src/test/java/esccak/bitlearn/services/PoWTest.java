package esccak.bitlearn.services;

import esccak.bitlearn.entitys.Block;
import org.junit.Test;

import static org.junit.Assert.*;

public class PoWTest {

    @Test
    public void return_false_when_block_hash_not_match_dificulty() {


        Block block = new Block();

        block.setContent("hola");
        assertFalse(ValidateDificultService.blockHashMatchDificultService(block, 3));

    }


    @Test
    public void return_true_when_block_hash_match_dificulty() {


        Block block = new Block();
        block.setContent("hola");

        //PoW
        FindDifficultyService.findDifficultService(block,3);


        assertTrue(ValidateDificultService.blockHashMatchDificultService(block, 3));

    }
}