package kei.su.sales.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by  on 4/12/2019.
 */
object NewsApiService {
    //creating a Network Interceptor to add api_key in all the request as authInterceptor
    private val interceptor = Interceptor { chain ->
        val url = chain.request().url().newBuilder().addQueryParameter("apiKey", "75761294f91d4658b6ac33c8788454cd").build()
        val request = chain.request()
                .newBuilder()
//                .url(url)
                .build()
        chain.proceed(request)
    }
    // we are creating a networking client using OkHttp and add our authInterceptor.
    private val apiClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()//.client(apiClient)
                .baseUrl("https://positioning-test.mapsted.com/api/Values/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    val newsApi: NewsApiInterface = getRetrofit().create(NewsApiInterface::class.java)
}