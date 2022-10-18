package com.pxh.hab.utils;

import java.util.Random;

public class VerificationCodeUtil {
    public static String getFourRandom(){
        Random random = new Random();
        StringBuilder fourRandom = new StringBuilder(random.nextInt(10000) + "");
        int randLength = fourRandom.length();
        if(randLength<4){
            for(int i=1; i<=4-randLength; i++)
                fourRandom.insert(0, "0");
        }
        return fourRandom.toString();
    }
}
