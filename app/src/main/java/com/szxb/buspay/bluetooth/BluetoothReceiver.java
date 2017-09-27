package com.szxb.buspay.bluetooth;


import java.lang.reflect.Method;


import com.szxb.buspay.db.sp.FetchAppConfig;
import com.szxb.buspay.util.ClsUtils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * 作者：Evergarden on 2017/7/20
 * QQ：1941042402
 */
public class BluetoothReceiver extends BroadcastReceiver{

	String pin = "1234";  //此处为你要连接的蓝牙设备的初始密钥，一般为1234或0000
	public BluetoothReceiver() {
	}
	//广播接收器，当远程蓝牙设备被发现时，回调函数onReceiver()会被执行
	@Override
	public void onReceive(Context context, Intent intent) {
		BluetoothDevice btDevice=null;  //创建一个蓝牙device对象
		String action = intent.getAction(); //得到action
		// 从Intent中获取设备对象
		btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		if(action.equals("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED")){
			int state = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, BluetoothAdapter.ERROR);

			System.out.println(state);
		}

		if(action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) //得到的action，会等于PAIRING_REQUEST
		{
			if(btDevice.getName().contains(FetchAppConfig.BluetoothDevice()))
			{
				try {
					//1.确认配对
					ClsUtils.setPairingConfirmation(btDevice.getClass(), btDevice, true);
					//2.终止有序广播
					Log.i("order...", "isOrderedBroadcast:"+isOrderedBroadcast()+",isInitialStickyBroadcast:"+isInitialStickyBroadcast());
					abortBroadcast();//如果没有将广播终止，则会出现一个一闪而过的配对框。
					//3.调用setPin方法进行配对...
					boolean ret = ClsUtils.setPin(btDevice.getClass(), btDevice, pin);

//					BluetoothChatService mChatService = new BluetoothChatService(
//						);
//					mChatService.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else
				Log.e("提示信息", "这个设备不是目标蓝牙设备");

		}
	}

	private void unpairDevice(BluetoothDevice device) {
		try {
			Method m = device.getClass()
					.getMethod("removeBond", (Class[]) null);
			m.invoke(device, (Object[]) null);
		} catch (Exception e) {

		}

	}
}