package ua.goit.jdbc.view;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Console implements View{
    private final Scanner scanner;
    private final OutputStream out;

    public Console(InputStream in, OutputStream out) {
        scanner = new Scanner(in);
        this.out = out;
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public void write(String message) {
        try {
            out.write(message.getBytes());
            out.write("\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
