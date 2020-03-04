package sap.gb.cloud.handler;

import sap.gb.cloud.command.Command;
import sap.gb.cloud.command.RequestBuilder;
import sap.gb.cloud.service.FileReceiveService;
import sap.gb.cloud.service.FileSendService;
import sap.gb.cloud.service.ReportReceiveService;
import sap.gb.cloud.service.RequestSendService;

import java.net.Socket;

public class Handler {

    public boolean handle(Socket connection, Command command, RequestBuilder requestBuilder) {
        if (command == Command.PUSH) {
            new FileSendService(connection, requestBuilder).execute();
            return new ReportReceiveService(connection).execute();
        } else if (command == Command.GET) {
            new RequestSendService(connection, requestBuilder).execute();
            return new FileReceiveService(connection).execute();
        } else {
            new RequestSendService(connection, requestBuilder).execute();
            return new ReportReceiveService(connection).execute();
        }
    }
}
