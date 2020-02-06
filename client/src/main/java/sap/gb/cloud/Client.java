package sap.gb.cloud;



import camunication.DataPackageTransmitter;
import data.PushDataPackage;
import sap.gb.cloud.connect.Connector;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {
        Connector connector = new Connector();
        DataPackageTransmitter cpt = new DataPackageTransmitter(connector.getConnection());
        cpt.send(new PushDataPackage("/Users/mihailmihail/Documents/one.txt"));
    }
}
