package com.mcura.jaideep.queuemanagement.retrofit;

import com.mcura.jaideep.queuemanagement.Model.AppointmentNature;
import com.mcura.jaideep.queuemanagement.Model.Area;
import com.mcura.jaideep.queuemanagement.Model.AvailableTokenList;
import com.mcura.jaideep.queuemanagement.Model.AvialbaleTokenListbydate;
import com.mcura.jaideep.queuemanagement.Model.CheckHospitalPatModel;
import com.mcura.jaideep.queuemanagement.Model.City;
import com.mcura.jaideep.queuemanagement.Model.Country;
import com.mcura.jaideep.queuemanagement.Model.CurrentTokenModel;
import com.mcura.jaideep.queuemanagement.Model.DocAccountListModel;
import com.mcura.jaideep.queuemanagement.Model.Doctor;
import com.mcura.jaideep.queuemanagement.Model.DoctorListModel;
import com.mcura.jaideep.queuemanagement.Model.FeeFetch;
import com.mcura.jaideep.queuemanagement.Model.FillCashCardResponseModel;
import com.mcura.jaideep.queuemanagement.Model.GenerateCashCardModel;
import com.mcura.jaideep.queuemanagement.Model.GenerateCashCardModel_v1;
import com.mcura.jaideep.queuemanagement.Model.GenerateTokenResultModel;
import com.mcura.jaideep.queuemanagement.Model.GetLabIdModel;
import com.mcura.jaideep.queuemanagement.Model.GetLabTransactionsByMrnoSubtenantModel;
import com.mcura.jaideep.queuemanagement.Model.GetNatureByUserRoleModel;
import com.mcura.jaideep.queuemanagement.Model.GetOrderBoothListModel;
import com.mcura.jaideep.queuemanagement.Model.GetPatDemographics;
import com.mcura.jaideep.queuemanagement.Model.GetPatientByHospitalNoModel;
import com.mcura.jaideep.queuemanagement.Model.GetPharmacyTransactionsByMrnoSubtenantModel;
import com.mcura.jaideep.queuemanagement.Model.GetServiceListModel;
import com.mcura.jaideep.queuemanagement.Model.GetSubTenantLogoModel;
import com.mcura.jaideep.queuemanagement.Model.GetSubtenantDetailsModel;
import com.mcura.jaideep.queuemanagement.Model.GetUserPhotoModel;
import com.mcura.jaideep.queuemanagement.Model.GetappointmentModel1;
import com.mcura.jaideep.queuemanagement.Model.HospitalName;
import com.mcura.jaideep.queuemanagement.Model.LabOrderStatusGetModel;
import com.mcura.jaideep.queuemanagement.Model.LabPharmacyPostResponseModel;
import com.mcura.jaideep.queuemanagement.Model.LastBillDetailModel;
import com.mcura.jaideep.queuemanagement.Model.LoginModel;
import com.mcura.jaideep.queuemanagement.Model.MainModel;
import com.mcura.jaideep.queuemanagement.Model.OrderBoothSearchListModel;
import com.mcura.jaideep.queuemanagement.Model.PatDemoGraphics;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.Model.PharmacyOrderGetModel;
import com.mcura.jaideep.queuemanagement.Model.PharmacyOrderTransactionDetail.PharmacyOrderTxnWithDetailByOrdIdModel;
import com.mcura.jaideep.queuemanagement.Model.PharmacyTransactionDatum;
import com.mcura.jaideep.queuemanagement.Model.PostActivityTrackerModel.PostActivityTrackerModel;
import com.mcura.jaideep.queuemanagement.Model.PostPaymentModel;
import com.mcura.jaideep.queuemanagement.Model.QueueStatus;
import com.mcura.jaideep.queuemanagement.Model.ScheduleModel;
import com.mcura.jaideep.queuemanagement.Model.SearchCashCardModel;
import com.mcura.jaideep.queuemanagement.Model.SearchHospital;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.Model.State;
import com.mcura.jaideep.queuemanagement.Model.TokenStatusModel;
import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.Model.TransactionDetail;
import com.mcura.jaideep.queuemanagement.Model.UserInfoModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by mcura on 11/5/2015.
 */
public interface MCuraEndPointInterface {
    @POST("/UpdateAppointment_Block")
    void updateAppointment_Block(@Body JsonObject mObj, Callback<String> restCallback);

    //TokenView_Other
    //TokenView_Other_v2
    @GET("/TokenView_Other")
    void tokenViewOther(@Query("UserRoleId") int userRoleId, @Query("SubTenantId") int subtanentId, @Query("ScheduleId") int scheduleId, @Query("Date") String date, Callback<QueueStatus[]> restCallback);

    //Patient_Check_In
    //Patient_Check_In_v1
    //Patient_Check_In_v2
    //Patient_Check_In_WithPayment
    //Patient_Check_In_WithPayment_v1
    @POST("/Patient_Check_In_WithPayment_v1")
    void patient_Check_In_version1(@Body JsonObject mObj, Callback<GenerateTokenResultModel> restCallback);


    //Patient_Check_In
    //Patient_Check_In_WithOutPayment
    //Patient_Check_In_WithOutPayment_v1
    @POST("/Patient_Check_In_WithOutPayment_v1")
    void patient_Check_In(@Body JsonObject mObj, Callback<GenerateTokenResultModel> restCallback);

    //Generate_Token_Chart
    //Generate_Token_Chart_v1
    @POST("/Generate_Token_Chart")
    void generate_Token_Chart(@Body JsonObject mObj, Callback<GenerateTokenResultModel> restCallback);

    @POST("/End_OPD")
    void end_OPD(@Body JsonObject mObj, Callback<GenerateTokenResultModel> restCallback);

    @GET("/Patient_Visit_Entry")
    void patient_Visit_Entry(@Query("MRNo") int mrno, @Query("UserRoleId") int userRoleId, @Query("SubTenantId") int subtanentId, @Query("ScheduleId") int scheduleId, @Query("Date") String date, Callback<GenerateTokenResultModel> restCallback);

    @POST("/patientCheckOut")
    void patientCheckOut(@Body JsonObject mObj, Callback<GenerateTokenResultModel> restCallback);

    @GET("/SearchPatient")
    void getSearchPatient(@Query("UserRoleID") int userRoleID, @Query("Searchkey") String searchKey, @Query("sub_tenant_id") int tenant_id,
                          Callback<PatientSearchModel[]> restCallback);

    //SearchPatient_v1
    //searchpatient_v2
    @GET("/searchpatient_v2")
    void searchPatient_v1(@Query("index") int index, @Query("RowSize") int RowSize, @Query("UserRoleID") int userRoleID, @Query("SearchType") String searchType, @Query("Searchkey") String searchKey, @Query("Subtenantid") int tenant_id,
                          Callback<SearchPatientModel> restCallback);

    @GET("/GetPatient")
    void getPatient(@Query("MRNO") int mrNo, @Query("UserRoleID") int userRoleID, @Query("sub_tenant_id") int tenant_id,
                    Callback<MainModel> restCallback);

    @GET("/Current_Token")
    void current_Token(@Query("UserRoleId") int userRoleID, @Query("SubTenantId") int sub_tenant_id, @Query("ScheduleId") int scheduleId, @Query("Date") String date,
                       Callback<CurrentTokenModel[]> restCallback);

    @GET("/Next_Availabel_Token")
    void next_Availabel_Token(@Query("UserRoleId") int userRoleID, @Query("SubTenantId") int sub_tenant_id, @Query("ScheduleId") int scheduleId, @Query("Date") String date,
                              Callback<CurrentTokenModel[]> restCallback);

    //Token_Status_v1
    //Token_Status
    @GET("/Token_Status_v1")
    void token_Status(@Query("Mrno") int mrno, @Query("UserRoleId") int userRoleID, @Query("SubTenantId") int sub_tenant_id, @Query("ScheduleId") int scheduleId, @Query("Date") String date, Callback<TokenStatusModel[]> restCallback);

    @GET("/GetLogin")
    void getLogin(@Query("UseraName") String username, @Query("PWD") String pwd, Callback<LoginModel> restCallback);

    @GET("/GetRefrealDoctors")
    void getRefrealDoctors(@Query("sub_tenant_id") int sub_tenant_id, @Query("UserRoleID") int userRoleID, Callback<Doctor[]> restCallback);

    //getSchedule_Day
    //getSchedule_Day_v1
    @GET("/getSchedule_Day_v1")
    void getSchedule_Day(@Query("userRoleId") int userRoleID, @Query("CurDate") String currDate, Callback<ScheduleModel[]> restCallback);

    @GET("/Cancel_Token_List")
    void cancel_Token_List(@Query("MRNo") int mrno, @Query("UserRoleId") int userRoleId, @Query("SubTenantId") int subtanentId, @Query("ScheduleId") int scheduleId, @Query("Date") String date, Callback<GenerateTokenResultModel> restCallback);

    @GET("/Available_Token_List")
    void available_Token_List(@Query("UserRoleId") int userRoleId, @Query("SubTenantId") int subtanentId, @Query("ScheduleId") int scheduleId, @Query("Date") String date, Callback<AvailableTokenList[]> restCallback);

    //Move_Token_List_v1
    //Move_Token_List
    @GET("/Move_Token_List_v1")
    void move_Token_List(@Query("MRNo") int mrno, @Query("UserRoleId") int userRoleId, @Query("SubTenantId") int subtanentId, @Query("ScheduleId") int scheduleId, @Query("TokenNo") int tokenno, @Query("Date") String date, Callback<GenerateTokenResultModel> restCallback);

    @GET("/getSubTenant")
    void getHospital(@Query("userroleid") int userroleid, Callback<SearchHospital[]> resCallback);

    @GET("/GetSubTenantOne")
    void getSubTenantOne(@Query("SubTenantID") int subTenantID, @Query("userroleid") int userroleid, Callback<HospitalName> resCallback);

    //List_DoctorsBySubTenantId
    //List_DoctorsBySubTenantId_v2
    //List_DoctorsBySubTenantId_v3
    @GET("/List_DoctorsBySubTenantId_v3")
    void list_DoctorsBySubTenantId(@Query("SubTenantId") int subTenantId, Callback<DoctorListModel[]> resCallback);

    @GET("/GetCountry")
    void getCountry(Callback<Country[]> resCallback);

    @GET("/GetState2")
    void getState(@Query("CountryID") int CountryID, Callback<State[]> resCallback);

    @GET("/GetCity2")
    void getCity(@Query("StateID") int StateID, Callback<City[]> resCallback);

    @GET("/getAppNature")
    void getAppNature(Callback<AppointmentNature[]> resCallback);

    //InsertAppointments_v3
    //InsertAppointments_v2
    //InsertAppointments_v1
    //InsertAppointments
    @POST("/InsertAppointments_v3")
    void insertAppointments(@Body JsonObject mObj, Callback<String> restCallback);

    @POST("/PostAddress")
    void postAddress(@Body JsonObject mObj, Callback<String> restCallback);

    @POST("/PostAddress_v1_1")
    void postAddress_v1_1(@Body JsonObject mObj, Callback<String> restCallback);

    @POST("/PostContactDetails")
    void postContactDetails(@Body JsonObject mObj, Callback<String> restCallback);

    @POST("/PostContactDetails_v1_1")
    void postContactDetails_v1_1(@Body JsonObject mObj, Callback<String> restCallback);

    @POST("/PostPatient2")
    void postPatient2(@Body JsonObject mObj, Callback<String> restCallback);


    //PostPatientUser_v3
    //PostPatientUser_v2
    //PostPatientUser_v1
    //PostPatientUser
    @POST("/PostPatientUser_v3")
    void postPatientUser(@Body JsonObject mObj, Callback<String> restCallback);

    @GET("/getPatDemographics")
    void getPatDemographics(@Query("Mrno") int mrno, @Query("UserRoleID") int userroleid, Callback<PatDemoGraphics> resCallback);

    @POST("/FileUploadImage")
    void fileUploadImage(@Body JsonObject mObj, Callback<String> restCallback);

    @POST("/UpdatePatientImage")
    void updatePatientImage(@Body JsonObject mObj, Callback<String> restCallback);

    @GET("/GetCityArea")
    void getCityArea(@Query("cityId") int cityId, Callback<Area[]> resCallback);

    @GET("/Schedule_Status")
    void scheduleStatus(@Query("ScheduleId") int scheduleId, Callback<GenerateTokenResultModel> resCallback);

    @POST("/AddDrMessage")
    void addDrMessage(@Body JsonObject mObj, Callback<String> restCallback);

    @POST("/UpdatePatientDetails")
    void updatePatientDetails(@Body JsonObject mObj, Callback<String> restCallback);

    @GET("/GetSubTenant_Logo")
    void getSubTenant_Logo(@Query("UserRoleId") int userRoleId, @Query("SubTenantId") int subTenantId, Callback<GetSubTenantLogoModel> resCallback);

    @GET("/GetUser_Photo")
    void getUser_Photo(@Query("UserRoleId") int userRoleId, Callback<GetUserPhotoModel> resCallback);

    @GET("/GetPatientByHospitalNo")
    void getPatientByHospitalNo(@Query("HospitalNo") String hospitalNo, Callback<GetPatientByHospitalNoModel> resCallback);

    @GET("/CheckHospitalPat")
    void checkHospitalPat(@Query("UserRoleId") int userRoleId, @Query("SubTenantId") int subTenantId, @Query("HospitalNo") String hospitalNo, Callback<CheckHospitalPatModel> resCallback);

    //Avialbale_TokenList_bydate
    //Avialbale_TokenList_bydate_v1
    //Avialbale_TokenList_bydate_v2
    @GET("/Avialbale_TokenList_bydate_v2")
    void avialbale_TokenList_bydate(@Query("SubTenantId") int subTenantId, @Query("Date") String date, Callback<AvialbaleTokenListbydate[]> resCallback);

    @GET("/FeeFetch")
    void feeFetch(@Query("UserRoleId") int userRoleId, @Query("AppNatureId") int appNatureId, @Query("SubtenantId") int subtenantId, Callback<FeeFetch> resCallback);

    @POST("/PostPayment")
    void postPayment(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    @GET("/GetNatureByUserRole")
    void getNatureByUserRole(@Query("UserRoleId") int userRoleId, @Query("SubtenantId") int subtenantId, Callback<GetNatureByUserRoleModel[]> resCallback);

    @GET("/DocAccountList")
    void docAccountList(@Query("UserRoleId") int userRoleId, @Query("fromDate") String fromDate, @Query("toDate") String toDate, Callback<DocAccountListModel[]> resCallback);

    @GET("/GetUser")
    void getUserInfo(@Query("UserRoleID") int userRoleId, Callback<UserInfoModel> restCallback);

    @GET("/LastBillDetail")
    void lastBillDetail(@Query("UserRoleId") int userRoleId, @Query("Mrno") String mrno, Callback<LastBillDetailModel> restCallback);

    @GET("/GetServiceList")
    void getServiceList(@Query("subtenantID") int subtenantID, Callback<GetServiceListModel[]> restCallback);

    @GET("/OrderBoothSearchList")
    void orderBoothSearchList(@Query("SubTenantId") int subtenantID, @Query("userroleid") int userroleid, @Query("dateFrom") String dateFrom, @Query("dateto") String dateto, Callback<OrderBoothSearchListModel[]> restCallback);

    @GET("/SearchCashCard")
    void searchCashCard(@Query("Mrno") int mrno, @Query("SubTenantId") int subTenantId, @Query("HospitalNo") String HospitalNo, Callback<SearchCashCardModel> restCallback);

    @POST("/GenerateCashCard")
    void generateCashCard(@Body JsonObject mObj, Callback<GenerateCashCardModel_v1> restCallback);

    //FillCashCard_v1
    //FillCashCard_v2
    @POST("/FillCashCard_v2")
    void fillCashCard_v1(@Body JsonObject mObj, Callback<FillCashCardResponseModel> restCallback);

    @GET("/GetOrderBoothList")
    void GetOrderBoothList(@Query("SubTenantId") int subtenantID, @Query("userroleid") int userroleid, @Query("dateFrom") String dateFrom, @Query("dateto") String dateto, Callback<GetOrderBoothListModel> restCallback);

    //PostPayment_v2
    //PostPayment_v1
    @POST("/PostPayment_v1")
    void postPayment_v1(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);


    //PostPayment_v2
    //PostPayment_v4
    @POST("/PostPaymentDocFee_v4")
    void PostPaymentDocFee(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    @POST("/PostPaymentDocFee_v5")
    void PostPaymentDocFee_v5(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    //PostPaymentPharmacyFee_v4
    //PostPaymentPharmacyFee_v5
    //PostPaymentPharmacyFee_v6
    //PostPaymentPharmacyFee_v7
    @POST("/PostPaymentPharmacyFee_v7")
    void PostPaymentPharmacyFee(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    //PostPaymentLabFee_v4
    //PostPaymentLabFee_v5
    //PostPaymentLabFee_v6
    @POST("/PostPaymentLabFee_v7")
    void PostPaymentLabFee(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    @POST("/PostPayment_v3")
    void PostPayment_v3(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    @GET("/VerifyOTP")
    void verifyOTP(@Query("Mrno") int mrno, @Query("otp") int otp, Callback<PostPaymentModel> restCallback);

    @GET("/LabOrderStatusGet")
    void labOrderStatusGet(@Query("UserRoleID") int UserRoleID, @Query("Mrno") int Mrno, @Query("Subtenantid") int Subtenantid, Callback<LabOrderStatusGetModel[]> restCallback);

    @GET("/PharmacyOrderGet")
    void pharmacyOrderGet(@Query("UserRoleID") int UserRoleID, @Query("Mrno") int Mrno, @Query("Subtenantid") int Subtenantid, Callback<PharmacyOrderGetModel[]> restCallback);

    @POST("/updateHospitalNoByMrno")
    void updateHospitalNoByMrno(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    @GET("/GetPharmacyTransactionsByMrnoSubtenant")
    void getPharmacyTransactionsByMrnoSubtenant(@Query("UserRoleId") int UserRoleId, @Query("Mrno") String Mrno, @Query("SubtenantId") int Subtenantid, @Query("Status") int Status, Callback<GetPharmacyTransactionsByMrnoSubtenantModel> restCallback);

    @GET("/GetLabTransactionsByMrnoSubtenant")
    void getLabTransactionsByMrnoSubtenant(@Query("UserRoleId") int UserRoleId, @Query("Mrno") String Mrno, @Query("SubtenantId") int Subtenantid, @Query("Status") int Status, Callback<GetLabTransactionsByMrnoSubtenantModel> restCallback);

    @GET("/AppointmentBookedOrNot")
    void appointmentBookedOrNot(@Query("MRNo") String MRNo, @Query("UserRoleId") String UserRoleId, @Query("ScheduleId") String ScheduleId, @Query("Date") String date, Callback<PostPaymentModel> restCallback);

    @GET("/AppBooKedOrNotByMrnoGetAppId")
    void appBooKedOrNotByMrnoGetAppId(@Query("Mrno") String MRNo, @Query("userRoleId") String UserRoleId, @Query("scheduleId") String ScheduleId, @Query("date") String date, Callback<PostPaymentModel> restCallback);

    @GET("/GetAppSlotsByScheduleId")
    void getAppSlotsByScheduleId(@Query("userRoleId") int userRoleId, @Query("date") String date, @Query("subTenantId") int subTenantId, @Query("scheduleId") int scheduleId, Callback<GetappointmentModel1[]> restCallback);

    @GET("/GetSubtenantDetailsBySubTenantId")
    void getSubtenantDetailsBySubTenantId(@Query("SubtenantId") int subtenantId, @Query("UserRoleId") int userRoleId, Callback<GetSubtenantDetailsModel> restCallback);

    @GET("/GetHospitalId")
    void getHospitalId(@Query("subtenantId") int subtenantId, Callback<String> restCallback);

    @POST("/PostLabGrpTestOrder_v5")
    void postLabGrpTestOrder_v5(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    @POST("/PostLabOrderdetails_v2")
    void postLabOrderdetails_v2(@Body JsonObject mObj, Callback<LabPharmacyPostResponseModel> restCallback);

    @GET("/GetLab")
    void getLab(@Query("UserRoleID") int userRoleID,@Query("SchedulesID") int schedulesID, Callback<GetLabIdModel[]> restCallback);

    @GET("/TransactionDetailBySubTenant_v2")
    void transactionDetailBySubTenant_v1(@Query("subTenantId") int subTenantId,@Query("fromDate") String fromDate,@Query("toDate") String toDate, Callback<TransactionDetail> restCallback);

    @GET("/GetPharmacyOrderTxnWithDetailByOrdId")
    void getPharmacyOrderTxnWithDetailByOrdId(@Query("UserRoleId") int userRoleId,@Query("prescriptionId") int prescriptionId, Callback<PharmacyOrderTxnWithDetailByOrdIdModel> restCallback);

    @POST("/postActivityTracker_v2")
    void postActivityTracker(@Body JsonObject jsonObject, Callback<PostActivityTrackerModel> restCallback);
}
