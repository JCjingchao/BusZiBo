package com.szxb.buspay.module.home;

import android.util.Log;
import android.widget.TextView;

import com.szxb.buspay.BusApp;
import com.szxb.buspay.R;
import com.szxb.buspay.base.BaseMVPActivity;
import com.szxb.buspay.entity.QRCode;
import com.szxb.buspay.entity.QRScanMessage;
import com.szxb.buspay.interfaces.OnPushTask;
import com.szxb.buspay.task.TaskHandler;
import com.szxb.buspay.util.Config;
import com.szxb.buspay.util.Constant;
import com.szxb.buspay.util.ParamsUtil;
import com.szxb.buspay.util.rx.RxBus;
import com.szxb.buspay.util.sound.SoundPoolUtil;
import com.szxb.buspay.util.tip.BusToast;
import com.szxb.xblog.XBLog;

import java.util.Map;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：Evergarden on 2017/7/21 10:44
 * QQ：1941042402
 */

public class HomeActivity extends BaseMVPActivity<HomeView, HomePresenter> implements HomeView, OnPushTask {

    private TaskHandler handler;
    private TextView time, Line;
    private Subscription sub;

    @Override
    protected int rootView() {
        return R.layout.view_home;
    }
    //
    @Override
    protected void initView() {
        Line = (TextView) findViewById(R.id.home_station_name);
        time = (TextView) findViewById(R.id.home_station_time);
        handler = new TaskHandler(this);
        super.initView();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.CloseSkipEvent();
    }


    @Override
    protected void initData() {
        XBLog.d("Start");
        mPresenter.init(this);
        receiverNews();
    }


    //扫码后
    private void receiverNews() {
        sub = RxBus.getInstance().toObservable(QRScanMessage.class)
                .filter(new Func1<QRScanMessage, Boolean>() {
                    @Override
                    public Boolean call(QRScanMessage qrScanMessage) {
                        Log.d("HomeActivity",
                                "call(HomeActivity.java:117)" + qrScanMessage.toString());
                        switch (qrScanMessage.getResult()) {
                            case QRCode.EC_SUCCESS://验码成功
                                SoundPoolUtil.play(1);
                                BusToast.showToast(BusApp.getInstance(), "刷码成功", true);
                                return true;
                            case QRCode.QR_ERROR://非腾讯或者小兵二维码
                                SoundPoolUtil.play(4);
                                BusToast.showToast(BusApp.getInstance(), "二维码有误", false);
                                break;
                            case QRCode.SOFTWARE_EXCEPTION:
                                SoundPoolUtil.play(6);
                                BusToast.showToast(BusApp.getInstance(), "软件出现异常", false);
                                break;
                            case QRCode.EC_FORMAT://二维码格式错误
                                SoundPoolUtil.play(7);
                                BusToast.showToast(BusApp.getInstance(), "二维码格式错误", false);
                                break;
                            case QRCode.EC_CARD_PUBLIC_KEY://卡证书公钥错误
                                SoundPoolUtil.play(4);
                                BusToast.showToast(BusApp.getInstance(), "二维码有误", false);
                                break;
                            case QRCode.EC_CARD_CERT://卡证书签名错误
                                SoundPoolUtil.play(4);
                                BusToast.showToast(BusApp.getInstance(), "二维码有误", false);
                                break;
                            case QRCode.EC_USER_PUBLIC_KEY://卡证书用户公钥错误
                                SoundPoolUtil.play(4);
                                BusToast.showToast(BusApp.getInstance(), "二维码有误", false);
                                break;
                            case QRCode.EC_USER_SIGN://二维码签名错误
                                SoundPoolUtil.play(11);
                                BusToast.showToast(BusApp.getInstance(), "二维码签名错误", false);
                                break;
                            case QRCode.EC_CARD_CERT_TIME://卡证书过期
                                SoundPoolUtil.play(12);
                                BusToast.showToast(BusApp.getInstance(), "卡证书过期", false);
                                break;
                            case QRCode.EC_CODE_TIME://二维码过期
                                SoundPoolUtil.play(13);
                                BusToast.showToast(BusApp.getInstance(), "二维码过期", false);
                                break;
                            case QRCode.EC_FEE://超出最大金额
                                SoundPoolUtil.play(14);
                                BusToast.showToast(BusApp.getInstance(), "超出最大金额", false);
                                break;
                            case QRCode.EC_BALANCE://余额不足
                                SoundPoolUtil.play(15);
                                BusToast.showToast(BusApp.getInstance(), "余额不足", false);
                                break;
                            case QRCode.EC_OPEN_ID://输入的openid不符
                                SoundPoolUtil.play(4);
                                BusToast.showToast(BusApp.getInstance(), "二维码有误", false);
                                break;
                            case QRCode.EC_PARAM_ERR://参数错误
                                SoundPoolUtil.play(17);
                                BusToast.showToast(BusApp.getInstance(), "参数错误", false);
                                break;
                            case QRCode.EC_MEM_ERR://申请内存错误
                                SoundPoolUtil.play(18);
                                BusToast.showToast(BusApp.getInstance(), "申请内存错误", false);
                                break;
                            case QRCode.EC_CARD_CERT_SIGN_ALG_NOT_SUPPORT://卡证书签名算法不支持
                                SoundPoolUtil.play(4);
                                BusToast.showToast(BusApp.getInstance(), "二维码有误", false);
                                break;
                            case QRCode.EC_MAC_ROOT_KEY_DECRYPT_ERR://加密的mac根密钥解密失败
                                SoundPoolUtil.play(4);
                                BusToast.showToast(BusApp.getInstance(), "二维码有误", false);
                                break;
                            case QRCode.EC_MAC_SIGN_ERR://mac校验失败
                                SoundPoolUtil.play(21);
                                BusToast.showToast(BusApp.getInstance(), "mac校验失败", false);
                                break;
                            case QRCode.EC_QRCODE_SIGN_ALG_NOT_SUPPORT://二维码签名算法不支持
                                SoundPoolUtil.play(4);
                                BusToast.showToast(BusApp.getInstance(), "二维码有误", false);
                                break;
                            case QRCode.EC_SCAN_RECORD_ECRYPT_ERR://扫码记录加密失败
                                SoundPoolUtil.play(23);
                                BusToast.showToast(BusApp.getInstance(), "扫码记录加密失败", false);
                                break;
                            case QRCode.EC_SCAN_RECORD_ECODE_ERR://扫码记录编码失败
                                SoundPoolUtil.play(24);
                                BusToast.showToast(BusApp.getInstance(), "扫码记录编码失败", false);
                                break;
                            case QRCode.MY_QR_INSTALL_SUCCESS://小兵二维码验证成功
                                break;
                            case QRCode.EC_FAIL://系统异常
                                SoundPoolUtil.play(25);
                                BusToast.showToast(BusApp.getInstance(), "系统异常", false);
                                break;
                            case QRCode.REFRESH_QR_CODE://请刷新二维码
                                SoundPoolUtil.play(26);
                                BusToast.showToast(BusApp.getInstance(), "请刷新二维码", false);
                                break;

                            default:

                                SoundPoolUtil.play(5);
                                BusToast.showToast(BusApp.getInstance(), "验码失败", false);
                                break;
                        }
                        return false;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Action1<QRScanMessage>() {
                    @Override
                    public void call(QRScanMessage qrScanMessage) {
                        if (qrScanMessage == null) return;
                        Map<String, Object> map = ParamsUtil.requestMap(qrScanMessage.getPosRecord());
                        mPresenter.requestTX(Config.FETCH_DEBIT_WHAT, Config.XBPAY, map);
                    }
                });

    }

    @Override
    public void SetText(String LineNo) {
        Line.setText(LineNo);
    }

    @Override
    public void MenuGo() {
        handler.sendMessage(handler.obtainMessage(Constant.MenuG0));
    }

    @Override
    protected HomePresenter getChildPresenter() {
        return new HomePresenter(this);
    }


    @Override
    public void SetPrice(String Price) {

        ((TextView) findViewById(R.id.Price)).setText("票价：" + Price + "元");
    }





    @Override
    public void ThreeKey() {

        //消费记录查询
        mPresenter.CloseSkipEvent();
        handler.sendMessage(handler.obtainMessage(Constant.RecordGo));
        super.ThreeKey();
    }

    @Override
    public void FourKey() {
        super.FourKey();



        mPresenter.CloseSkipEvent();
        handler.sendMessage(handler.obtainMessage(Constant.MenuG0));
    }

    @Override
    public void task(final Object entity) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                time.setText(entity + "");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.CloseSkipEvent();
        if (sub.isUnsubscribed()) {
            sub.unsubscribe();
        }
        SoundPoolUtil.release();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.init(this);
    }

    @Override
    public void task(int type, Object entity) {
        switch (type) {
            case Constant.CardPayMessage:
                handler.sendMessage(handler.obtainMessage(type, entity));
                break;
            case Constant.KeyMessage:
                byte[] b = (byte[]) entity;
                KeyEvent(b);
                break;
            case Constant.Sgin:
                handler.sendMessage(handler.obtainMessage(type));
                break;

        }

    }

    @Override
    public void onSuccess(int what, String msg) {
        Log.d("HomeActivity",
                "onSuccess(HomeActivity.java:279)" + msg);
    }

    @Override
    public void onFail(int what, String msg) {
        Log.d("HomeActivity",
                "onFail(HomeActivity.java:285)" + msg);
    }

    @Override
    public void message(String msg) {

    }
}
