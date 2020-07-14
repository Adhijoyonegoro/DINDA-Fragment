package com.example.dinda.Model;

public class History {
    String tdate, kategori1, company_office, afd, save, sent;

    public History(String tdate, String kategori1, String company_office, String afd, String save, String sent) {
        this.tdate = tdate;
        this.kategori1 = kategori1;
        this.company_office = company_office;
        this.afd = afd;
        this.save = save;
        this.sent = sent;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public String getKategori1() {
        return kategori1;
    }

    public void setKategori1(String kategori1) {
        this.kategori1 = kategori1;
    }

    public String getCompany_office() {
        return company_office;
    }

    public void setCompany_office(String company_office) {
        this.company_office = company_office;
    }

    public String getAfd() {
        return afd;
    }

    public void setAfd(String afd) {
        this.afd = afd;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }
}
