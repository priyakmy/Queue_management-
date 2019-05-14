package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mCURA1 on 10/4/2016.
 */
public class DoctorList {
    private String user_role_id;
    private String docName;
    private String dept_id;

    public String getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(String user_role_id) {
        this.user_role_id = user_role_id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }
}
