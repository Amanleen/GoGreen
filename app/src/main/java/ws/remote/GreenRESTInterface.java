package ws.remote;

import model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Tejal Shah.
 */
public interface GreenRESTInterface {

    @POST("UserServlet")
    Call<User> createUser(@Body User user);

}
