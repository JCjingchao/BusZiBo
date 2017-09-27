package com.szxb.buspay.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import com.szxb.buspay.util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 作者：Evergarden on 2017/7/20
 * QQ：1941042402
 */

public class BluetoothChatService {
	// Debugging
	private static final String TAG = "BluetoothChatService";
	private static final boolean D = true;


	private static final String NAME = "BluetoothChat";

	// Unique UUID for this application
	// 蓝牙设备对应的UUID（全局唯一标识），只有UUID相同的蓝牙设备才能互联
	private static final UUID MY_UUID = UUID
			.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

	// Member fields
	private final BluetoothAdapter mAdapter;// 本机蓝牙适配器

	private AcceptThread mAcceptThread;// 负责接受来自外部蓝牙设备的连接
	private ConnectThread mConnectThread;// 负责建立Socket连接的线程
	private ConnectedThread mConnectedThread;// 负责收发消息的线程
	private int mState;// 当前状态


	public static final int STATE_NONE = 0;
	public static final int STATE_LISTEN = 1;

	public static final int STATE_CONNECTING = 2;
	public static final int STATE_CONNECTED = 3;
	private Context context;


	public BluetoothChatService(Context context) {
		this.context=context;
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mState = STATE_NONE;


	}




	private synchronized void setState(int state) {
		if (D)
			Log.d(TAG, "setState() " + mState + " -> " + state);
		mState = state;

		System.out.println(mState);
	}


	public synchronized int getState() {
		return mState;
	}


	// 开启聊天服务，启动各个线程
	public synchronized void start() {
		if (D)
			Log.d(TAG, "start");


		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}


		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}


		if (mAcceptThread == null) {
			mAcceptThread = new AcceptThread();
			mAcceptThread.start();
		}
		setState(STATE_LISTEN);
	}


	// 调用相应线程连接外部蓝牙设备
	public synchronized void connect(BluetoothDevice device) {
		if (D)
			Log.d(TAG, "connect to: " + device);

		// Cancel any thread attempting to make a connection
		if (mState == STATE_CONNECTING) {
			if (mConnectThread != null) {
				mConnectThread.cancel();
				mConnectThread = null;
			}
		}

		// Cancel any thread currently running a connection
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}

		// Start the thread to connect with the given device
		mConnectThread = new ConnectThread(device);
		mConnectThread.start();
		setState(STATE_CONNECTING);
	}


	// 调用相关方法建立socket连接，并通知主Activity更新UI
	public synchronized void connected(BluetoothSocket socket,
									   BluetoothDevice device) {
		if (D)
			Log.d(TAG, "connected");

		// Cancel the thread that completed the connection
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}

		// Cancel any thread currently running a connection
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}

		// Cancel the accept thread because we only want to connect to one
		// device
		if (mAcceptThread != null) {
			mAcceptThread.cancel();
			mAcceptThread = null;
		}

		// Start the thread to manage the connection and perform transmissions
		mConnectedThread = new ConnectedThread(socket);
		mConnectedThread.start();



		setState(STATE_CONNECTED);
	}


	// 停止所有线程
	public synchronized void stop() {
		if (D)
			Log.d(TAG, "stop");
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}
		if (mAcceptThread != null) {
			mAcceptThread.cancel();
			mAcceptThread = null;
		}
		setState(STATE_NONE);
	}


	// 调用相关线程发送消息
	public void write(byte[] out) {
		// Create temporary object
		ConnectedThread r;
		// Synchronize a copy of the ConnectedThread
		synchronized (this) {
			if (mState != STATE_CONNECTED)
				return;
			r = mConnectedThread;
		}

		r.write(out);
	}


	// 连接失败对应的处理（通知主Activity显示Toast信息）
	private void connectionFailed() {
		setState(STATE_LISTEN);

		System.out.println("conect failed");

	}


	// 负责接受来自外部蓝牙设备的连接请求，并调用相关线程建立Socket连接
	private class AcceptThread extends Thread {
		// The local server socket
		private final BluetoothServerSocket mmServerSocket;

		public AcceptThread() {
			BluetoothServerSocket tmp = null;

			// Create a new listening server socket
			try {
				tmp = mAdapter
						.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
			} catch (IOException e) {
				Log.e(TAG, "listen() failed", e);
			}
			mmServerSocket = tmp;
		}

		public void run() {
			if (D)
				Log.d(TAG, "BEGIN mAcceptThread" + this);
			setName("AcceptThread");
			BluetoothSocket socket = null;

			// Listen to the server socket if we're not connected
			while (mState != STATE_CONNECTED) {
				try {
					// This is a blocking call and will only return on a
					// successful connection or an exception
					socket = mmServerSocket.accept();
				} catch (IOException e) {
					Log.e(TAG, "accept() failed", e);
					break;
				}

				// If a connection was accepted
				if (socket != null) {
					synchronized (BluetoothChatService.this) {
						switch (mState) {
							case STATE_LISTEN:
							case STATE_CONNECTING:
								// Situation normal. Start the connected thread.
								connected(socket, socket.getRemoteDevice());
								break;
							case STATE_NONE:
							case STATE_CONNECTED:
								// Either not ready or already connected. Terminate
								// new socket.
								try {
									socket.close();
								} catch (IOException e) {
									Log.e(TAG, "Could not close unwanted socket", e);
								}
								break;
						}
					}
				}
			}
			if (D)
				Log.i(TAG, "END mAcceptThread");
		}

		public void cancel() {
			if (D)
				Log.d(TAG, "cancel " + this);
			try {
				mmServerSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "close() of server failed", e);
			}
		}
	}


	// 负责建立Socket连接
	private class ConnectThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;

		public ConnectThread(BluetoothDevice device) {
			mmDevice = device;
			BluetoothSocket tmp = null;

			// Get a BluetoothSocket for a connection with the
			// given BluetoothDevice
			try {
				tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
			} catch (IOException e) {
				Log.e(TAG, "create() failed", e);
			}
			mmSocket = tmp;
		}

		public void run() {
			Log.i(TAG, "BEGIN mConnectThread");
			setName("ConnectThread");

			// Always cancel discovery because it will slow down a connection
			mAdapter.cancelDiscovery();

			// Make a connection to the BluetoothSocket
			try {
				// This is a blocking call and will only return on a
				// successful connection or an exception
				mmSocket.connect();
			} catch (IOException e) {
				connectionFailed();
				// Close the socket
				try {
					mmSocket.close();
				} catch (IOException e2) {
					Log.e(TAG,
							"unable to close() socket during connection failure",
							e2);
				}
				// Start the service over to restart listening mode
				BluetoothChatService.this.start();
				return;
			}

			// Reset the ConnectThread because we're done
			synchronized (BluetoothChatService.this) {
				mConnectThread = null;
			}

			// Start the connected thread
			connected(mmSocket, mmDevice);
		}

		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "close() of connect socket failed", e);
			}
		}
	}

	/**
	 * This thread runs during a connection with a remote device. It handles all
	 * incoming and outgoing transmissions.
	 */
	// 负责用Socket连接收发消息
	private class ConnectedThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final InputStream mmInStream;
		private final OutputStream mmOutStream;

		public ConnectedThread(BluetoothSocket socket) {
			Log.d(TAG, "create ConnectedThread");
			mmSocket = socket;
			InputStream tmpIn = null;
			OutputStream tmpOut = null;

			// Get the BluetoothSocket input and output streams
			try {
				tmpIn = socket.getInputStream();
				tmpOut = socket.getOutputStream();
			} catch (IOException e) {
				Log.e(TAG, "temp sockets not created", e);
			}

			mmInStream = tmpIn;
			mmOutStream = tmpOut;
		}

		public void run() {
			Log.i(TAG, "BEGIN mConnectedThread");
			byte[] buffer = new byte[3];
			int bytes;

			// Keep listening to the InputStream while connected
			// 一直等待接收消息
			while (true) {
				try {
					// Read from the InputStream
					bytes = mmInStream.read(buffer);
					byte[] a = { 0x00, 0x00, 0x01 };
					byte[] b = { 0x00, 0x00, 0x02 };
					System.out.println("接收：successful");
					for (int i = 0; i < buffer.length; i++) {
						System.out.println(buffer[i]);
					}

					//前三字节 接收数据为010101时为获取数据 接收数据为000009时为获取文件
					String A = byte2HexStr(a);
					String B = byte2HexStr(b);
					String C = byte2HexStr(buffer);
					System.out.println(A + "====" + B);

					if (A.equals(C)) {
			//			MediaPlayer mediaPlayer=MediaPlayer.create(context,R.raw.qingzhongshua);
			//			mediaPlayer.start();
						System.out.println("success");
						byte[] APDU  = { 0x01, 0x01, 0x01 };
						write(APDU);

					}

					if(B.equals(C)){
//						MediaPlayer mediaPlayer=MediaPlayer.create(context,R.raw.qcsj);
//						mediaPlayer.start();
						byte[] bfile="Violet is so good".getBytes();
						Utils.byte2File(bfile, Environment.getExternalStorageDirectory()
								.getAbsolutePath(), "/Violet.text");
						byte[] data=Utils.File2byte(Environment.getExternalStorageDirectory()
								.getAbsolutePath()+"/Violet.text");
						byte[] APDU={0x00,0x00,0x09};//APU是手机端的文件或者数据判断的标识符
						byte[] len=(data.length+"").getBytes();//data长度
						byte[] file=Utils.byteMerger(APDU, len);
						byte[] send =Utils.byteMerger(file, data);
						write(send);

						System.out.println("send file success");
					}
					// // Send the obtained bytes to the UI Activity
					// mHandler.obtainMessage(MainActivity.MESSAGE_READ, bytes,
					// -1, buffer)
					// .sendToTarget();
				} catch (IOException e) {
					Log.e(TAG, "disconnected", e);
					// connectionLost();
					try {
						mmInStream.close();
						mmOutStream.close();
						mmSocket.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}
					break;
				}
			}
		}

		// 发送消息
		public void write(byte[] buffer) {
			try {
				mmOutStream.write(buffer);
				System.out.println("发送： success");

			} catch (IOException e) {
				try {
					mmInStream.close();
					mmOutStream.close();
					mmSocket.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}

		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "close() of connect socket failed", e);
			}
		}
	}

	public static String byte2HexStr(byte[] b) {
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);

		}
		return sb.toString().toUpperCase().trim();
	}
}
