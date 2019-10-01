package com.dracoo.papuamobile.Model;

public class ListSpdModel {

    private String NoTask;
    private String NamaTask;
    private String NamaTeknisi;
    private String IdTeknisi;
    private String TypeTeknisi;
    private String total;
    private String approve;
    private String TanggalTask;
    private String totalsuk;
    private String sisa;

    public ListSpdModel(String noTask, String namaTask, String namaTeknisi, String idTeknisi, String typeTeknisi, String total, String approve, String tanggalTask, String totalsuk, String sisa) {
        this.NoTask = noTask;
        this.NamaTask = namaTask;
        this.NamaTeknisi = namaTeknisi;
        this.IdTeknisi = idTeknisi;
        this.TypeTeknisi = typeTeknisi;
        this.total = total;
        this.approve = approve;
        this.TanggalTask = tanggalTask;
        this.totalsuk = totalsuk;
        this.sisa = sisa;
    }

    public String getNamaTask() {
        return NamaTask;
    }

    public void setNamaTask(String namaTask) {
        NamaTask = namaTask;
    }

    public String getNoTask() {
        return NoTask;
    }

    public void setNoTask(String noTask) {
        NoTask = noTask;
    }

    public String getNamaTeknisi() {
        return NamaTeknisi;
    }

    public void setNamaTeknisi(String namaTeknisi) {
        NamaTeknisi = namaTeknisi;
    }

    public String getIdTeknisi() {
        return IdTeknisi;
    }

    public void setIdTeknisi(String idTeknisi) {
        IdTeknisi = idTeknisi;
    }

    public String getTypeTeknisi() {
        return TypeTeknisi;
    }

    public void setTypeTeknisi(String typeTeknisi) {
        TypeTeknisi = typeTeknisi;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getTanggalTask() {
        return TanggalTask;
    }

    public void setTanggalTask(String tanggalTask) {
        TanggalTask = tanggalTask;
    }

    public String getTotalsuk() {
        return totalsuk;
    }

    public void setTotalsuk(String totalsuk) {
        this.totalsuk = totalsuk;
    }

    public String getSisa() {
        return sisa;
    }

    public void setSisa(String sisa) {
        this.sisa = sisa;
    }
}


