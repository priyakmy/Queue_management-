package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mcura on 11/6/2015.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VPatmedrecord implements Serializable {

    @SerializedName("Date")
    @Expose
    private String Date;
    @SerializedName("Doctor")
    @Expose
    private Doctor Doctor;
    @SerializedName("EntryTypeId")
    @Expose
    private Integer EntryTypeId;
    @SerializedName("Mrno")
    @Expose
    private Integer Mrno;
    @SerializedName("RecNatureId")
    @Expose
    private Integer RecNatureId;
    @SerializedName("RecordId")
    @Expose
    private Integer RecordId;
    @SerializedName("UserRoleId")
    @Expose
    private Integer UserRoleId;

    /**
     *
     * @return
     * The Date
     */
    public String getDate() {
        return Date;
    }

    /**
     *
     * @param Date
     * The Date
     */
    public void setDate(String Date) {
        this.Date = Date;
    }

    /**
     *
     * @return
     * The Doctor
     */
    public Doctor getDoctor() {
        return Doctor;
    }

    /**
     *
     * @param Doctor
     * The Doctor
     */
    public void setDoctor(Doctor Doctor) {
        this.Doctor = Doctor;
    }

    /**
     *
     * @return
     * The EntryTypeId
     */
    public Integer getEntryTypeId() {
        return EntryTypeId;
    }

    /**
     *
     * @param EntryTypeId
     * The EntryTypeId
     */
    public void setEntryTypeId(Integer EntryTypeId) {
        this.EntryTypeId = EntryTypeId;
    }

    /**
     *
     * @return
     * The Mrno
     */
    public Integer getMrno() {
        return Mrno;
    }

    /**
     *
     * @param Mrno
     * The Mrno
     */
    public void setMrno(Integer Mrno) {
        this.Mrno = Mrno;
    }

    /**
     *
     * @return
     * The RecNatureId
     */
    public Integer getRecNatureId() {
        return RecNatureId;
    }

    /**
     *
     * @param RecNatureId
     * The RecNatureId
     */
    public void setRecNatureId(Integer RecNatureId) {
        this.RecNatureId = RecNatureId;
    }

    /**
     *
     * @return
     * The RecordId
     */
    public Integer getRecordId() {
        return RecordId;
    }

    /**
     *
     * @param RecordId
     * The RecordId
     */
    public void setRecordId(Integer RecordId) {
        this.RecordId = RecordId;
    }

    /**
     *
     * @return
     * The UserRoleId
     */
    public Integer getUserRoleId() {
        return UserRoleId;
    }

    /**
     *
     * @param UserRoleId
     * The UserRoleId
     */
    public void setUserRoleId(Integer UserRoleId) {
        this.UserRoleId = UserRoleId;
    }

}