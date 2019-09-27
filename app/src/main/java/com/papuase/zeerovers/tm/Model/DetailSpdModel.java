package com.papuase.zeerovers.tm.Model;

public class DetailSpdModel {

    private String noTask;
    private String ipLan;
    private String flagconfirm;
    private String namaRemote;
    private String vid;
    private String totalPengeluaran;

    public DetailSpdModel(String noTask, String flagconfirm, String ipLan, String namaRemote, String vid, String totalPengeluaran) {
        this.noTask = noTask;
        this.flagconfirm = flagconfirm;
        this.ipLan = ipLan;
        this.namaRemote = namaRemote;
        this.vid = vid;
        this.totalPengeluaran = totalPengeluaran;
    }


    public String getFlagconfirm() {
        return flagconfirm;
    }

    public void setFlagconfirm(String flagconfirm) {
        this.flagconfirm = flagconfirm;
    }

    public String getNoTask() {
        return noTask;
    }

    public void setNoTask(String noTask) {
        this.noTask = noTask;
    }

    public String getIpLan() {
        return ipLan;
    }

    public void setIpLan(String ipLan) {
        this.ipLan = ipLan;
    }

    public String getNamaRemote() {
        return namaRemote;
    }

    public void setNamaRemote(String namaRemote) {
        this.namaRemote = namaRemote;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getTotalPengeluaran() {
        return totalPengeluaran;
    }

    public void setTotalPengeluaran(String totalPengeluaran) {
        this.totalPengeluaran = totalPengeluaran;
    }
}


