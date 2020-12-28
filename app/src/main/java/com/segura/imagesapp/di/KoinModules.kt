package com.segura.imagesapp.di

import android.app.Application
import androidx.room.Room
import com.segura.imagesapp.dataSource.ImagesRemoteDataSource
import com.segura.imagesapp.dataSource.ImagesRepository
import com.segura.imagesapp.local.PhotosDao
import com.segura.imagesapp.local.PhotosDatabase
import com.segura.imagesapp.network.UrlInterceptor
import com.segura.imagesapp.ui.dashboard.DashboardViewModel
import com.segura.imagesapp.ui.detail.PhotoDetailViewModel
import com.segura.imagesapp.ui.home.HomeViewModel
import com.segura.imagesapp.ui.profile.ProfileViewModel
import com.segura.imagesapp.ui.search.SearchViewModel
import com.segura.imagesapp.utils.ConstantsUtils
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
    viewModel { (imageId: String, userId: String) ->
        PhotoDetailViewModel(
            imageId,
            userId,
            get()
        )
    }

    viewModel { (userName: String) -> ProfileViewModel(userName, get()) }
    viewModel { SearchViewModel(get()) }
}

val repositoryModule = module {

    fun provideRepository(
        imagesRemoteDataSource: ImagesRemoteDataSource,
        dao: PhotosDao
    ): ImagesRepository {
        return ImagesRepository(imagesRemoteDataSource, dao)
    }
    single { provideRepository(get(), get()) }

}

val remoteDataSourceModule = module {
    single { UrlInterceptor() }
    single { get<Retrofit>(named("imagesRemote")).create(ImagesRemoteDataSource::class.java) }
    single { provideOkHttpClient(get()) }
    single(named("imagesRemote")) { provideRetrofit(get(), ConstantsUtils.BASE_URL) }


}

val roomModule = module {
    fun provideDatabase(application: Application): PhotosDatabase {
        return Room.databaseBuilder(application, PhotosDatabase::class.java, "PhotosDatabase")
            .build()
    }

    fun providePhotosDao(database: PhotosDatabase): PhotosDao {
        return database.photosDao()
    }
    single { provideDatabase(androidApplication()) }
    single { providePhotosDao(get()) }
}

private fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideOkHttpClient(urlInterceptor: UrlInterceptor): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(urlInterceptor)
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
    return okHttpClientBuilder.build()
}
