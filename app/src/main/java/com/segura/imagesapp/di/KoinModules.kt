package com.segura.imagesapp.di

import android.app.Application
import androidx.room.Room
import com.segura.imagesapp.data.dataSource.ImagesRemoteDataSource
import com.segura.imagesapp.data.repository.ImageRepositoryImpl
import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.domain.useCase.*
import com.segura.imagesapp.local.PhotosDao
import com.segura.imagesapp.local.PhotosDatabase
import com.segura.imagesapp.network.UrlInterceptor
import com.segura.imagesapp.presentation.ui.detail.PhotoDetailViewModel
import com.segura.imagesapp.presentation.ui.favorites.FavoriteViewModel
import com.segura.imagesapp.presentation.ui.home.HomeViewModel
import com.segura.imagesapp.presentation.ui.profile.ProfileViewModel
import com.segura.imagesapp.presentation.ui.search.SearchViewModel
import com.segura.imagesapp.utils.ConstantsUtils
import com.segura.imagesapp.utils.DefaultDispatcherProvider
import com.segura.imagesapp.utils.DispatcherProvider
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavoriteViewModel(get(), get(), get()) }
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


val useCaseModule = module {
    factory { GetImagesUseCase(get()) }
    factory { GetImageDetailUseCase(get()) }
    factory { GetFavoriteImagesUseCase(get()) }
    factory { InsertFavoriteImageUseCase(get()) }
    factory { DeleteFavoriteImageUseCase(get()) }
    factory { SearchFavoriteImagesUseCase(get()) }
    factory { GetUserDetailUseCase(get()) }
}


val roomModule = module {

    single { provideDatabase(androidApplication()) }
    single { providePhotosDao(get()) }
}


private fun createRemoteImageService(retrofit: Retrofit): ImagesRemoteDataSource {
    return retrofit.create(ImagesRemoteDataSource::class.java)
}

private fun createImagesRepository(
    remoteDataSource: ImagesRemoteDataSource,
    dispatcherProvider: DefaultDispatcherProvider,
    imagesLocalDataSource: PhotosDao
): ImagesRepository {

    return ImageRepositoryImpl(remoteDataSource, dispatcherProvider, imagesLocalDataSource)
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
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
    return okHttpClientBuilder.build()
}

val networkModule = module {
    single { DefaultDispatcherProvider() } bind DispatcherProvider::class
    single { UrlInterceptor() }
    single { createRemoteImageService(get()) }
    single { provideRetrofit(get(), ConstantsUtils.BASE_URL) }
    single { provideOkHttpClient(get()) }
    single { createImagesRepository(get(), get(), get()) }
}

private fun provideDatabase(application: Application): PhotosDatabase {
    return Room.databaseBuilder(application, PhotosDatabase::class.java, "PhotosDatabase")
        .build()
}

private fun providePhotosDao(database: PhotosDatabase): PhotosDao {
    return database.photosDao()
}

private const val TIME_OUT = 60L
