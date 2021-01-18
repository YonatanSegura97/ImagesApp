package com.segura.imagesapp.di

import android.app.Application
import androidx.room.Room
import com.segura.imagesapp.data.dataSource.ImagesRemoteDataSource
import com.segura.imagesapp.data.repository.ImageRepositoryImpl
import com.segura.imagesapp.data.repository.ImagesRepositoryOld
import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.domain.useCase.GetImagesUseCase
import com.segura.imagesapp.local.PhotosDao
import com.segura.imagesapp.local.PhotosDatabase
import com.segura.imagesapp.network.UrlInterceptor
import com.segura.imagesapp.presentation.ui.favorites.FavoriteViewModel
import com.segura.imagesapp.presentation.ui.detail.PhotoDetailViewModel
import com.segura.imagesapp.presentation.ui.home.HomeViewModel
import com.segura.imagesapp.presentation.ui.profile.ProfileViewModel
import com.segura.imagesapp.presentation.ui.search.SearchViewModel
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
    viewModel { FavoriteViewModel(get()) }
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
    ): ImagesRepositoryOld {
        return ImagesRepositoryOld(imagesRemoteDataSource, dao)
    }
    single { provideRepository(get(), get()) }


    //CLEAN --
    single<ImagesRepository> {
        ImageRepositoryImpl(
            get()
        )
    }

}

val useCaseModule = module {
    single { GetImagesUseCase(get()) }
}
val re = module {

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
