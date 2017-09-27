package com.szxb.buspay.entity;

/**
 * 作者：Evergarden on 2017/7/19 14:42
 * QQ：1941042402
 */

public class MachineStatus {

    private final boolean firmware;//固件状态
    private final String SN;//机器设备号
    private final String PSAM;//psam状态
    private final boolean ConnectNet;//网络状态

    public boolean isFirmware() {
        return firmware;
    }



    public String getSN() {
        return SN;
    }



    public String getPSAM() {
        return PSAM;
    }



    public boolean isConnectNet() {
        return ConnectNet;
    }



    public MachineStatus(boolean firmware, String SN, String PSAM, boolean connectNet) {
        this.firmware = firmware;
        this.SN = SN;
        this.PSAM = PSAM;
        ConnectNet = connectNet;
    }

    public static class Bulider{
        private boolean firmware;
        private String SN;
        private String PSAM;
        private boolean ConnectNet;


        public Bulider firmware(boolean firmware){
             this.firmware=firmware;
            return this;
        }

        public Bulider SN(String SN){
            this.SN=SN;
            return this;
        }


        public Bulider PSAM(String PSAM){
            this.PSAM=PSAM;
            return this;
        }

        public Bulider ConnectNet(boolean ConnectNet){
          this.ConnectNet=ConnectNet;
            return this;
        }



        public MachineStatus builder(){

            return new MachineStatus(this);
        }

    }


    public MachineStatus(Bulider bulider) {
        firmware=bulider.firmware;
        SN=bulider.SN;
        PSAM=bulider.PSAM;
        ConnectNet=bulider.ConnectNet;
    }

    @Override
    public String toString() {
        return "MachineStatus{" +
                "firmware=" + firmware +
                ", SN='" + SN + '\'' +
                ", PSAM='" + PSAM + '\'' +
                ", ConnectNet=" + ConnectNet +
                '}';
    }
}
