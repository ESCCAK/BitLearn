package esccak.bitlearn.services;
import esccak.bitlearn.entitys.Block;
import esccak.bitlearn.util.SHA256;

import java.io.*;
import java.security.NoSuchAlgorithmException;

public class ValidateBlockService {

    public static boolean validBlockService(Block block, Block lastValidatedBlock)
    {

        String content = block.getContent();

        if(lastValidatedBlock == null)
        {
            try {
                if ( block.getPrevHash().equals("0") &&
                        block.getHash().equals(SHA256.toHexString(SHA256.getSHA(block.getContent().getBytes()))))
                {


                    return true;
                }else
                {
                    return false;
                }


            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return false;
            }

        }

        String lastValidatedContent = lastValidatedBlock.getContent();
        try
        {

            System.out.println("+++" + SHA256.toHexString(SHA256.getSHA(content.getBytes())));
            if (!block.getHash().equals(SHA256.toHexString(SHA256.getSHA(content.getBytes()))))
            {
                return false;
            }

            if ( !lastValidatedBlock.getPrevHash().equals("0") &&
                    !block.getPrevHash().equals(SHA256.toHexString(SHA256.getSHA(lastValidatedContent.getBytes()))))
            {
                System.out.println("Invalid prev hash: ");

                return false;
            }

        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
            return false;
        }

        /*catch(ClassNotFoundException ex1)
        {
            System.out.println("ClassNotFoundException: " + ex1);
        }*/



        return true;


    }

}