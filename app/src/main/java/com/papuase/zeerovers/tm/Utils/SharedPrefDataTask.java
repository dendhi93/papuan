package com.papuase.zeerovers.tm.Utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefDataTask {


    public static final String SP_BALIEMS_APP = "PapuanApp";

    //-->> DataLokasi
    public static final String NoTask = "NoTask";
    public static final String VID = "VID";
    public static final String ID = "ID";
    public static final String DES = "DES";
    public static final String SID = "SID";
    public static final String IPLAN = "IPLAN";
    public static final String IdATM = "IdATM";
    public static final String LaporanPengaduan = "LaporanPengaduan";
    public static final String IdStatusKoordinator = "IdStatusKoordinator";
    public static final String idJenisTask = "idJenisTask";
    public static final String TglBerangkat = "TglBerangkat";
    public static final String TglSelesaiKerjaan = "TglSelesaiKerjaan";
    public static final String TglPulang = "TglPulang";
    public static final String Hub = "Hub";
    public static final String TglStatusPerbaikan = "TglStatusPerbaikan";
    public static final String CatatanKoordinator = "CatatanKoordinator";



    public static final String VIDDATABARANGSAVE = "VIDDATABARANGSAVE";
    public static final String IDDATABARANGSAVE = "IDDATABARANGSAVE";
    public static final String NOTASKDATABARANGSAVE = "NOTASKDATABARANGSAVE";


    public static final String TanggalTask = "TanggalTask";
    public static final String StatusTask = "StatusTask";

    public static final String statusDataLokasi = "FlagDataLokasi";
    public static final String statusGeneralInfo = "FlagGeneralInfo";
    public static final String statusDataTeknis = "FlagDataTeknis";
    public static final String statusDataBarang = "FlagDataBarang";
    public static final String statusUploadPhoto = "FlagUploadPhoto";
    public static final String statusDataInstallasi = "FlagDataInstallasi";
    public static final String statusDataSurvey = "FlagDataSurvey";



    public static final String NAMAREMOTE = "NAMAREMOTE";
    public static final String AlamatInstall = "AlamatInstall";
    public static final String PROVINSI = "PROVINSI";
    public static final String KOTA = "KOTA";
    public static final String KANWIL = "KANWIL";
    public static final String KANCAINDUK = "KANCAINDUK";
    public static final String CustPIC = "CustPIC";
    public static final String CustPIC_Phone = "CustPIC_Phone";
    public static final String IdJarkom = "IdJarkom";
    public static final String IdSatelite = "IdSatelite";
    public static final String Latitude = "Latitude";
    public static final String Longitude = "Longitude";
    public static final String AlamatSekarang = "AlamatSekarang";
    public static final String Catatan = "Catatan";



    public static final String FAIL_HW = "FAIL_HW";
    public static final String SQF = "SQF";
    public static final String INITIAL_ESNO = "INITIAL_ESNO";
    public static final String CARRIER_TO_NOICE = "CARRIER_TO_NOICE";
    public static final String HasilXPOLL = "HasilXPOLL";
    public static final String CPI = "CPI";
    public static final String OperatorSatelite = "OperatorSatelite";
    public static final String OperatorHelpDesk = "OperatorHelpDesk";
    public static final String OutPLN = "OutPLN";
    public static final String AktifitasSolusi = "AktifitasSolusi";
    public static final String OutUPS = "OutUPS";
    public static final String UPSforBackup = "UPSforBackup";
    public static final String SuhuRuangan = "SuhuRuangan";
    public static final String TypeMounting = "TypeMounting";
    public static final String PanjangKabel = "PanjangKabel";
    public static final String LetakAntena = "LetakAntena";
    public static final String LetakModem = "LetakModem";
    public static final String KondisiBangungan = "KondisiBangungan";
    public static final String AnalisaProblem = "AnalisaProblem";


    public static final String FILEID = "FILEID";
    public static final String VIDINPT = "VIDINPT";

    public static final String NamaBarang = "NamaBarang";
    public static final String SerialNumber = "SerialNumber";


    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefDataTask(Context context) {
        sp = context.getSharedPreferences(SP_BALIEMS_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getVIDDATABARANGSAVE() {
        return sp.getString(VIDDATABARANGSAVE,"");
    }

    public String getIDDATABARANGSAVE() {
        return sp.getString(IDDATABARANGSAVE,"");
    }

    public String getNOTASKDATABARANGSAVE() {
        return sp.getString(NOTASKDATABARANGSAVE,"");
    }

    public String getStatusDataLokasi() {
        return sp.getString(statusDataLokasi,"");
    }

    public String getStatusGeneralInfo() {
        return sp.getString(statusGeneralInfo,"");
    }

    public String getStatusDataTeknis() {
        return sp.getString(statusDataTeknis,"");
    }

    public String getStatusDataBarang() {
        return sp.getString(statusDataBarang,"");
    }

    public String getStatusUploadPhoto() {
        return sp.getString(statusUploadPhoto,"");
    }

    public String getStatusDataInstallasi() {
        return sp.getString(statusDataInstallasi,"");
    }

    public String getStatusDataSurvey() {
        return sp.getString(statusDataSurvey,"");
    }

    public String getTanggalTask() {
        return sp.getString(TanggalTask, "");
    }

    public String getStatusTask() {
        return sp.getString(StatusTask,"");
    }

    public String getNoTask() {
        return sp.getString(NoTask, "");
    }

    public String getVID() {
        return sp.getString(VID, "");
    }

    public String getSID() {
        return sp.getString(SID,"");
    }

    public String getIPLAN() {
        return sp.getString(IPLAN,"");
    }

    public String getIdATM() {
        return sp.getString(IdATM,"");
    }

    public String getLaporanPengaduan() {
        return sp.getString(LaporanPengaduan,"");
    }

    public String getIdStatusKoordinator() {
        return sp.getString(IdStatusKoordinator,"");
    }

    public String getIdJenisTask() {
        return sp.getString(idJenisTask,"");
    }

    public String getTglBerangkat() {
        return sp.getString(TglBerangkat,"");
    }

    public String getTglSelesaiKerjaan() {
        return sp.getString(TglSelesaiKerjaan,"");
    }

    public String getTglPulang() {
        return sp.getString(TglPulang,"");
    }

    public String getTglStatusPerbaikan() {
        return sp.getString(TglStatusPerbaikan,"");
    }

    public String getCatatanKoordinator() {
        return sp.getString(CatatanKoordinator,"");
    }

    public String getNAMAREMOTE() {
        return sp.getString(NAMAREMOTE,"");
    }

    public String getAlamatInstall() {
        return sp.getString(AlamatInstall,"");
    }

    public String getPROVINSI() {
        return sp.getString(PROVINSI,"");
    }

    public String getKOTA() {
        return sp.getString(KOTA,"");
    }

    public String getKANWIL() {
        return sp.getString(KANWIL,"");
    }

    public String getKANCAINDUK() {
        return sp.getString(KANCAINDUK,"");
    }

    public String getCustPIC() {
        return sp.getString(CustPIC,"");
    }

    public String getCustPIC_Phone() {
        return sp.getString(CustPIC_Phone,"");
    }

    public String getIdJarkom() {
        return sp.getString(IdJarkom,"");
    }

    public String getIdSatelite() {
        return sp.getString(IdSatelite, "");
    }

    public String getHub() {
        return sp.getString(Hub,"");
    }

    public String getLatitude() {
        return sp.getString(Latitude,"");
    }

    public String getLongitude() {
        return sp.getString(Longitude,"");
    }

    public String getAlamatSekarang() {
        return sp.getString(AlamatSekarang,"");
    }

    public String getCatatan() {
        return sp.getString(Catatan,"");
    }



    public String getSpBaliemsApp() {
        return sp.getString(SP_BALIEMS_APP,"");
    }

    public String getFailHw() {
        return sp.getString(FAIL_HW,"");
    }

    public String getSQF() {
        return sp.getString(SQF,"");
    }

    public String getInitialEsno() {
        return sp.getString(INITIAL_ESNO,"");
    }

    public String getCarrierToNoice() {
        return sp.getString(CARRIER_TO_NOICE,"");
    }

    public String getHasilXPOLL() {
        return sp.getString(HasilXPOLL,"");
    }

    public String getCPI() {
        return sp.getString(CPI,"");
    }

    public String getOperatorSatelite() {
        return sp.getString(OperatorSatelite,"");
    }

    public String getOperatorHelpDesk() {
        return sp.getString(OperatorHelpDesk,"");
    }

    public String getOutPLN() {
        return sp.getString(OutPLN,"");
    }

    public String getAktifitasSolusi() {
        return sp.getString(AktifitasSolusi,"");
    }

    public String getOutUPS() {
        return sp.getString(OutUPS,"");
    }

    public String getUPSforBackup() {
        return sp.getString(UPSforBackup,"");
    }

    public String getSuhuRuangan() {
        return sp.getString(SuhuRuangan,"");
    }

    public String getTypeMounting() {
        return sp.getString(TypeMounting,"");
    }

    public String getPanjangKabel() {
        return sp.getString(PanjangKabel,"");
    }

    public String getLetakAntena() {
        return sp.getString(LetakAntena,"");
    }

    public String getLetakModem() {
        return sp.getString(LetakModem,"");
    }

    public String getKondisiBangungan() {
        return sp.getString(KondisiBangungan,"");
    }

    public String getAnalisaProblem() {
        return sp.getString(AnalisaProblem,"");
    }

    public String getNamaBarang() {
        return sp.getString(NamaBarang,"");
    }

    public String getSerialNumber() {
        return sp.getString(SerialNumber,"");
    }
}