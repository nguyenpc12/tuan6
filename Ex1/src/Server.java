import java.io.*;
import java.net.*;

public class Server {

    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server đang chờ kết nối...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Có client mới kết nối!");

            // Tạo luồng để xử lý mỗi client riêng biệt
            new Thread(() -> {
                try (
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ) {
                    int count = 1;
                    while (count <= 1000) {
                        out.println(count);
                        count++;
                        Thread.sleep(1000); // Gửi 1 số mỗi giây
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

//Khởi tạo ServerSocket trên cổng PORT.
//Chờ kết nối từ client.
//Khi có client kết nối, tạo một luồng mới để xử lý client riêng biệt.
//Trong luồng xử lý client:
//Tạo PrintWriter để gửi dữ liệu cho client.
//Tạo BufferedReader để nhận dữ liệu từ client.
//Khởi tạo biến count bằng 1.
//Vòng lặp từ 1 đến 1000:
//Gửi số count đến client.
//Tăng count lên 1.
//Ngủ 1 giây.
//Đóng kết nối với client.