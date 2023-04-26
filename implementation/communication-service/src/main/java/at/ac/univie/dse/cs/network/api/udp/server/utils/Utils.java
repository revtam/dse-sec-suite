package at.ac.univie.dse.cs.network.api.udp.server.utils;

import java.util.Random;
import java.util.UUID;

public class Utils {

    private static final Random RANDOM;

    static {
        RANDOM  = new Random();
    }

    public static String getStringRandomId(){
        return UUID.randomUUID().toString();
    }

    public static String getStringRandomSequenceNumber(int bound){
        return Integer.toString(getRandomSequenceNumber(bound));
    }

    public static int getRandomSequenceNumber(int bound){
        return RANDOM.nextInt(bound);
    }
}
