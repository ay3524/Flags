package ay3524.com.flags.utils;

import ay3524.com.flags.model.WorldData;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Ashish on 19-06-2017.
 */

public interface ApiInterface {
    @GET(Constants.GET_JSON_PAGE_TEXT)
    Observable<WorldData> getWorldData();
}
