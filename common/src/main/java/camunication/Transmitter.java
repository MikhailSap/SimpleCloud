package camunication;

import data.DataPackage;


public interface Transmitter {
    void send(DataPackage dataPackage);
    DataPackage receive();
}
