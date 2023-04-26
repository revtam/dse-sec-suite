package at.ac.univie.dse;

import at.ac.univie.dse.api.MSCalcAPI;
import at.ac.univie.dse.utilities.configuration.ConfigurationProperties;

import java.io.IOException;

public class MSCalcApplication {
    public static void main(String[] args) throws IOException {
        String identifier = args[0];
        if (args.length < 2) {
            System.out.println("Usage: java -jar MSCalcApplication.jar <identifier> <port>");
            System.exit(1);
        }
        String port = args[1];
        MSCalcAPI.start(identifier, port);
    }
}
