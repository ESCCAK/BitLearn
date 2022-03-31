package esccak.bitlearn.services;

import esccak.bitlearn.entitys.Block;
import esccak.bitlearn.util.SHA256;

import java.security.NoSuchAlgorithmException;

public class ValidateDificultService {

    public static boolean blockHashMatchDificultService(Block block, int dificulty)
    {


        try {

            StringBuffer strDificulty = new StringBuffer();

            for (int i = 0; i < dificulty; i++) {
                strDificulty.append("0");
            }



            String sha = SHA256.toHexString(SHA256.getSHA(block.getContent().getBytes()));


            if (sha.length() == 64 - dificulty)
            {
                System.out.println("valid");
                return true;
            }
            else
                return false;


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }


        
    }




}
