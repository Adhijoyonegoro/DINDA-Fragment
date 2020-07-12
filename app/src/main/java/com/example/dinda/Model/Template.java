package com.example.dinda.Model;

public class Template {
    String companyOffice, posCode, diId, kategori1, kategori2, uom, typeOperation, maxValue, descOperation, diIdRef, diIdDesc, condition;

    public Template(String companyOffice, String posCode, String diId, String kategori1, String kategori2, String uom, String typeOperation, String maxValue, String descOperation, String diIdRef, String diIdDesc, String condition) {
        this.companyOffice = companyOffice;
        this.posCode = posCode;
        this.diId = diId;
        this.kategori1 = kategori1;
        this.kategori2 = kategori2;
        this.uom = uom;
        this.typeOperation = typeOperation;
        this.maxValue = maxValue;
        this.descOperation = descOperation;
        this.diIdRef = diIdRef;
        this.diIdDesc = diIdDesc;
        this.condition = condition;
    }

    public String getCompanyOffice() {
        return companyOffice;
    }

    public void setCompanyOffice(String companyOffice) {
        this.companyOffice = companyOffice;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getDiId() {
        return diId;
    }

    public void setDiId(String diId) {
        this.diId = diId;
    }

    public String getKategori1() {
        return kategori1;
    }

    public void setKategori1(String kategori1) {
        this.kategori1 = kategori1;
    }

    public String getKategori2() {
        return kategori2;
    }

    public void setKategori2(String kategori2) {
        this.kategori2 = kategori2;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getDescOperation() {
        return descOperation;
    }

    public void setDescOperation(String descOperation) {
        this.descOperation = descOperation;
    }

    public String getDiIdRef() {
        return diIdRef;
    }

    public void setDiIdRef(String diIdRef) {
        this.diIdRef = diIdRef;
    }

    public String getDiIdDesc() {
        return diIdDesc;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setDiIdDesc(String diIdDesc) {
        this.diIdDesc = diIdDesc;
    }
}
