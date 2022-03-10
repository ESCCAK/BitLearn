package esccak.bitlearn.services;

import esccak.bitlearn.entitys.Block;
import esccak.bitlearn.util.SHA256;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class ValidateBlockServiceTest {



    @Test
    public void validate_genesis_block() {

        Block genesis = new Block();
        genesis.setPrevHash("0");
        genesis.setContent("hola");
        Block lastValidatedBlock = null;
        try {
            genesis.setHash(SHA256.toHexString(SHA256.getSHA(genesis.getContent().getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assertEquals(true, ValidateBlockService.validBlockService(genesis, lastValidatedBlock));

    }

    @Test
    public void genesis_block_is_invalid() {

        Block genesis = new Block();
        genesis.setPrevHash("0");
        genesis.setContent("hola");
        Block lastValidatedBlock = null;
        try {
            genesis.setHash(SHA256.toHexString(SHA256.getSHA(genesis.getContent().getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //change the content -
        genesis.setContent("hola1");
        assertEquals(false, ValidateBlockService.validBlockService(genesis, lastValidatedBlock));

    }



    @Test
    public void block_is_valid() {

        Block genesis = new Block();
        genesis.setPrevHash("0");
        genesis.setContent("hola");
        Block lastValidatedBlock = null;
        try {
            genesis.setHash(SHA256.toHexString(SHA256.getSHA(genesis.getContent().getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (ValidateBlockService.validBlockService(genesis, lastValidatedBlock))
        {
            return;
        }
        lastValidatedBlock = genesis;

        Block block_1 = new Block();
        block_1.setContent("mundo");
        block_1.setPrevHash(genesis.getHash());
        try {
            block_1.setHash(SHA256.toHexString(SHA256.getSHA(block_1.getContent().getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        assertEquals(true, ValidateBlockService.validBlockService(genesis, lastValidatedBlock));

    }
    @Test
    public void block_is_invalid() {

        Block genesis = new Block();
        genesis.setPrevHash("0");
        genesis.setContent("hola");
        Block lastValidatedBlock = null;
        try {
            genesis.setHash(SHA256.toHexString(SHA256.getSHA(genesis.getContent().getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (!ValidateBlockService.validBlockService(genesis, lastValidatedBlock))
        {
            System.out.println("Exception");
            return;
        }
        lastValidatedBlock = genesis;

        Block block_1 = new Block();
        block_1.setContent("mundo");
        block_1.setPrevHash(genesis.getHash());
        try {
            block_1.setHash(SHA256.toHexString(SHA256.getSHA(block_1.getContent().getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        lastValidatedBlock.setContent("bye");
        assertEquals(false, ValidateBlockService.validBlockService(genesis, lastValidatedBlock));

    }


}