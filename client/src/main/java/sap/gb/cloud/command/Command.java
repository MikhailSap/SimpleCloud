package sap.gb.cloud.command;

import lombok.Getter;

@Getter
public enum Command {
    PUSH("Push", "push - send file to server"),
    GET("Get", "get - get file from server"),
    LIST("List", "list - list of files and directories in current directory"),
    RENAME("Rename", "rename - rename file"),
    DELETE("Delete", "delete - delete file"),
    EXIT("Exit", "exit - exit");


    private String serverServiceValue;
    private String description;

    Command(String serverServiceValue, String description) {
        this.serverServiceValue = serverServiceValue;
        this.description = description;
    }
}
