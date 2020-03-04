package sap.gb.cloud.command;


import lombok.Getter;

@Getter
public class CommandResolver {
    private Command command;

    public RequestBuilder resolve(String userInput) {
        String[] preform;
        int indexOfCommand = 0;
        int indexOfServiceValue = 0;
        preform = userInput.split(" ");
        try {
            command = Command.valueOf(preform[indexOfCommand].toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
        preform[indexOfServiceValue] = command.getServerServiceValue();
        return new RequestBuilder(preform);
    }
}
