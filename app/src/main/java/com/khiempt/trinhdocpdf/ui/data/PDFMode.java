package com.khiempt.trinhdocpdf.ui.data;

public class PDFMode {
    int id;
    int size;
    String time;
    String mTenFile;
    String mDuongDang;
    int mLogo;
    int mType;
    String duoiFile;
    boolean isCheckDauTrang;

    public PDFMode(int id, int size, String time, String mTenFile, String mDuongDang, int mLogo, int mType, String duoiFile, boolean isDauTrang) {
        this.id = id;
        this.size = size;
        this.time = time;
        this.mTenFile = mTenFile;
        this.mDuongDang = mDuongDang;
        this.mLogo = mLogo;
        this.mType = mType;
        this.duoiFile = duoiFile;
        this.isCheckDauTrang = isDauTrang;
    }

    public PDFMode(int id, int size, String time, String mTenFile, String mDuongDang, int mLogo) {
        this.id = id;
        this.size = size;
        this.time = time;
        this.mTenFile = mTenFile;
        this.mDuongDang = mDuongDang;
        this.mLogo = mLogo;
    }

    public boolean isDauTrang() {
        return isCheckDauTrang;
    }

    public void setDauTrang(boolean dauTrang) {
        isCheckDauTrang = dauTrang;
    }

    public String getDuoiFile() {
        return duoiFile;
    }

    public void setDuoiFile(String duoiFile) {
        this.duoiFile = duoiFile;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmTenFile() {
        return mTenFile;
    }

    public void setmTenFile(String mTenFile) {
        this.mTenFile = mTenFile;
    }

    public String getmDuongDang() {
        return mDuongDang;
    }

    public void setmDuongDang(String mDuongDang) {
        this.mDuongDang = mDuongDang;
    }

    public int getmLogo() {
        return mLogo;
    }

    public void setmLogo(int mLogo) {
        this.mLogo = mLogo;
    }
}
