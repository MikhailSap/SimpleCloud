package sap.gb.cloud.service;


import java.net.Socket;

public class ReportReceiveService extends ReceiveService {

    public ReportReceiveService(Socket connection) {
        super(connection);
    }

    @Override
    public boolean execute() {
        try {
            int messageLength = in.readInt();
            byte[] bytes = new byte[messageLength];
            in.read(bytes);
            String report = new String(bytes);
            System.out.println(report);
            if (report.startsWith("auth failed") || report.equals("user not found")) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
