package com.mcura.jaideep.queuemanagement.helper;

/**
 * Created by mCURA1 on 3/7/2017.
 */

public class EnumType {
    public static enum LabObjNature {
        kLabObjNaturePackage(0), kLabObjNatureGroup(1), kLabObjNatureTest(2);
        private int labObjNatureId;

        LabObjNature(int roleId) {
            this.labObjNatureId = roleId;
        }

        public int getID() {
            return labObjNatureId;
        }
    }

    public static enum ServiceRoleId {
        mDoctorServiceRoleId(1), mPharmacyServiceRoleId(2), mLabServiceRoleId(3);
        private int serviceRoleid;

        ServiceRoleId(int serviceRoleid) {
            this.serviceRoleid = serviceRoleid;
        }

        public int getID() {
            return serviceRoleid;
        }
    }

    public static enum ServiceType {
        mDoctorFee243(1), mDoctorFee528(1), mLabBilling528(4), mPharmacyBilling528(5);
        private int id;

        ServiceType(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    public static enum FillCashCardType {
        mFillCashCardSuccessfull(1), mRefundCashCardSuccessfull(2), mErrorInFillingRefund(3), mProvideData(4), mInsufficientBalance(5), mRemainingBalance(6);
        private int id;

        FillCashCardType(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    public static enum AppointmentOrNot {
        mAppointmentAlreadyFixed(1), mNewAppointment(2);
        private int id;

        AppointmentOrNot(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    public static enum LoginType {
        mSGRH(1613), mKIMSDEVELOPMENT(2334), mKIMSPRODUCTION(2268), mFNDS(2413);
        private int id;

        LoginType(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    public static enum PaymentStatusId {
        mPaymentSuccessfull(1), mFillCashCard(2), mBlankHospitalNo(3), mPaymentNotDone(4), mPaymentALreadyDone(5), mPaymentDone(6), mErrorInPayment(7), mPaymentModeNotCorrect(8), mOrderTransactionIdNull(9), mPaymentBlankScheduleId(10);
        private int statusId;

        PaymentStatusId(int statusId) {
            this.statusId = statusId;
        }

        public int getStatusId() {
            return statusId;
        }
    }

    public static enum ActTransactMasterEnum {
        Booking_APT("Booking APT", 1),
        Block_APT("Block APT", 2),
        Unblock_APT("Unblock APT", 3),
        CANCEL_APT("CANCEL APT", 4),
        ReSchedule_APT("ReSchedule APT", 5),
        Schedule_Creation("Schedule Creation", 6),
        Schedule_ReScheduling("Schedule ReScheduling", 7),
        Start_OPD("Start OPD", 8),
        End_OPD("End OPD", 9),
        Check_In("Check In", 10),
        Visiting("Visiting", 11),
        Checkout("Checkout", 12),
        Msg_Broadcast("Msg Broadcast", 13),
        Patient_Record_Upload("Patient Record Upload", 14),
        Attempt_To_Download("Attempt to download", 15),
        User_Login("User Login", 16),
        Register_Patient_From_Queue("Register Patient From Queue", 17),
        Edit_Patient_From_Queue("Edit Patient From Queue", 18),
        No_Show("No Show", 19),
        Register_Patient_From_Top_Panel("Register Patient From Top Panel", 22),
        Register_patient_From_Appointment("Register Patient From Appointment", 23),
        Edit_Patient_From_Top_Panel("Edit Patient From Top Panel", 24);


        String ActTransactMasterTypeName;
        int ActTransactMasterTypeId;

        ActTransactMasterEnum(String toString, int value) {
            ActTransactMasterTypeName = toString;
            ActTransactMasterTypeId = value;
        }
        public String getActTransactMasterTypeName() {
            return ActTransactMasterTypeName;
        }

        public int getActTransactMasterTypeId() {
            return ActTransactMasterTypeId;
        }
    }
}