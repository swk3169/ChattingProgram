package kr.co.swk.tcptest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	// 1. TCP ������ IP�� PORT�� ����� �Ҵ�
	// �����δ� ������ IP���ٴ� �������� �ۼ��ϴ� ���� ����.
	private static final String SERVER_IP = "192.168.120.1";
	private static final int SERVER_PORT = 5000;

	public static void main(String[] args) {
		Socket socket = null;

		try {
			// 2. ������ ������ ���� ������ ����
			socket = new Socket();

			// 3. ������ ������ ������ ���ϰ� ����(connect)
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
