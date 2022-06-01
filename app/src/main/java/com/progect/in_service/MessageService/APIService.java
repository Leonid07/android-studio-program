package com.progect.in_service.MessageService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAJG1uuXQ:APA91bGp6x5-9021ScI9XkVyo-wmSp2LJ_L9bKLRz1vmi0jB1AapfoOAI5HVdByQIbpsrmYIeESyAxk1PmbbPuzKsctVaHOeuYDzx59l77DkeBMr7V0u19OrCmEp2mzT00mmOmf4eIDA"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}
