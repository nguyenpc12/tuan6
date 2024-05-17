import java.io.*;
import java.net.*;

public class Client {

    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("localhost", PORT);
        System.out.println("Đã kết nối đến server!");

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Nhận được số: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//Khởi tạo Socket kết nối đến server trên cổng PORT.
//Tạo BufferedReader để nhận dữ liệu từ server.
//Vòng lặp:
//Nhận dữ liệu từ server.
//In dữ liệu nhận được ra màn hình.
//Đóng kết nối với server.