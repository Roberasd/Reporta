package appdatamx.hackcolima.roberto.reporta.web;

/**
 * Created by Roberto Avalos on 18/10/2015.
 */
public class WebService {
    public static final String baseUrl = "https://reportacolima.herokuapp.com/api/v1/";

    public static String createComplaintUrl(){
        return baseUrl + "reports";
    }

   public static String allComplaintsUrl(){
        return baseUrl + "reports";
    }

    public static String getCheckUserUrl(String id){
        return baseUrl + "users/"+id;
    }

   public static String getComplaintDetailUrl(String id){
        return baseUrl + "reports/"+id;
    }

    public static String getRegisterUserUrl(){
        return baseUrl + "users/";
    }
}
