package com.example.lnp.API;

public class API {


    //This API is Used For Login User
    public static final String LOGIN_API = "http://192.168.1.3:8080/rest/auth/login";
    //This API is Used For Signup User
    public static final String SIGNUP_API = "http://192.168.1.3:8080/rest/auth/signup";

    //This API is Used For Insert The Data in Utility Service
    public static final String INSERT_UTILITY_SERVICE_API = "http://192.168.1.3:8080/rest/service/save";


    //This API is Used For Read The Data in Utility Service
    public static final String READ_UTILITY_SERVICE_DATA_API = "http://192.168.1.3:8080/rest/service/get";

    //This API is used For Read The Data From User Details
    public static final String READ_USER_DETAILS_API ="http://192.168.1.3:8080/rest/user/get/";
    //This API is Used To Update The User Information
    public static final String UPDATE_USER_INFORMATION_API="http://192.168.1.3:8080/rest/user/update";
}
