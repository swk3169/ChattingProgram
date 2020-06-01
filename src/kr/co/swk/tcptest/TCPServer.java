package kr.co.swk.tcptest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void main(String[] args) {
		final int SERVER_PORT = 5000;

		ServerSocket serverSocket = null;

		try {
			// 1. ���� ���� ��ü ����
			serverSocket = new ServerSocket();

			// 2. ������ ȣ��Ʈ�� ��Ʈ�� binding
			String localHostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(localHostAddress, SERVER_PORT));
			System.out.println("[server] binding! \naddress: " + localHostAddress + ", port:" + SERVER_PORT);

			// 3. Ŭ���̾�Ʈ�κ��� ���� ��û�� �� ������ ���
			// ���� ��û�� ���� �������� ������ block �����̸�,
			// TCP ���� ������ 3-way handshake�� ������ �Ǹ� ����� ���� Socket ��ü�� ��ȯ��
			// TCP ������ java���� ó�����ָ�, �� ���������δ� �ü���� ó���Ѵ�.
			Socket socket = serverSocket.accept();

			// 4. ���� ��û�� ���� ������ �Ǿ��ٴ� �޽��� ���
			InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostName = remoteSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = remoteSocketAddress.getPort();
			System.out.println(
					"[server] connected! \nconnected socket address:" + remoteHostName + ", port:" + remoteHostPort);

			// // ����
			// // TCP ������ ���� Ŭ���̾�Ʈ�� ��û�� ó�� �� �� �־�� �ϰ�, ���ÿ� �� Ŭ���̾�Ʈ��� ����� �� �� �־��
			// �մϴ�.
			// // �����带 ���� TCPServer�� ���� �޼���� ���ѷ����� ���鼭 ��û�� ��� �޾Ƶ��̰�, ��û�� ���� ���ο�
			// �����带 �����Ͽ� �� Ŭ���̾�Ʈ��� ����� �� �� �ֵ��� �ϴ� ������ ���Դϴ�.
			// while(true) {
			// // 3. Ŭ���̾�Ʈ�κ��� ���� ��û�� �� ������ ���
			// Socket socket = serverSocket.accept();
			//
			// // 4. ���� ��û�� ���� ������ �Ǿ��ٴ� �޽��� ���
			// new ProcessThread(socket).start();
			// }

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
