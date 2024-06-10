package com.example.lnp.API;

public class API {

    public static final String IP_ADDRESS="192.168.1.7";


    //This API is Used For Login User
    public static final String LOGIN_API = "http://"+IP_ADDRESS+":8080/rest/auth/login";
    //This API is Used For Signup User
    public static final String SIGNUP_API = "http://"+IP_ADDRESS+":8080/rest/auth/signup";

    //This API is Used For Insert The Data in Utility Service
    public static final String INSERT_UTILITY_SERVICE_API = "http://"+IP_ADDRESS+":8080/rest/service/save";


    //This API is Used For Read The Data in Utility Service
    public static final String READ_UTILITY_SERVICE_DATA_API = "http://"+IP_ADDRESS+":8080/rest/service/get";

    //This API is used For Read The Data From User Details
    public static final String READ_USER_DETAILS_API ="http://"+IP_ADDRESS+":8080/rest/user/get/";
    //This API is Used To Update The User Information
    public static final String UPDATE_USER_INFORMATION_API="http://"+IP_ADDRESS+":8080/rest/user/update";

    //This API is used to Save UserInformation
    public static  final  String SAVE_USER_INFORMATION_API="http://"+IP_ADDRESS+":8080/rest/user/userdetails";

    //This API is used for Upload Documents Such As Aadhar Card And Pan Card.
    public static final String UPLOAD_IMPORTANT_DOC="http://"+API.IP_ADDRESS+":8080/rest/user/uploadPanAndAadharDetails/";


    //This API is Used For Get Contact Support Data

    public static final String API_CONTACT="http://"+IP_ADDRESS+":8080/rest/adminaccess/findAll";

    //This API is used for update the contact support data.

    public static final String API_CONTACT_UPDATE="http://"+IP_ADDRESS+":8080/rest/adminaccess/update";

    //This API is used to save contact support information
    public static final String API_SAVE_CONTACT_INFORMATION="http://"+IP_ADDRESS+":8080/rest/adminaccess/saveadminaccessinformation";

    //This API is used to get Pending Verification Request from database
    public static final String API_PENDING_VERIFICATION_REQUEST="http://"+IP_ADDRESS+":8080/rest/adminaccess/getpendingverificationuser";

}
