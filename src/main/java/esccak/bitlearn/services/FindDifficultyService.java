package esccak.bitlearn.services;

import esccak.bitlearn.entitys.Block;
import esccak.bitlearn.util.SHA256;

import java.security.NoSuchAlgorithmException;

public class FindDifficultyService {

    public static void findDifficultService(Block block, int dificulty)
    {
        StringBuffer strDificulty = new StringBuffer();

        double random = 1;
        String sha = null;

        for (int i = 0; i < dificulty; i++) {
            strDificulty.append("0");
        }



        String content = block.getContent();
        do {
            block.setContent(content + random);
            random +=1;
            try {
                sha = SHA256.toHexString(SHA256.getSHA(block.getContent().getBytes()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } while(sha.length() != 64 - dificulty);
    }

}
