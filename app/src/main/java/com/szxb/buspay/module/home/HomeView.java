package com.szxb.buspay.module.home;

import com.szxb.buspay.base.BaseView;
import com.szxb.buspay.entity.ByCardEntity;

/**
 * 作者：Evergarden on 2017/7/21 10:46
 * QQ：1941042402
 */

public interface HomeView extends BaseView {


    void MenuGo();

    void SetText(String LineNo);

    void SetPrice(String Price);

  //   void CardPay(ByCardEntity byCardEntity);//刷卡状态


}
