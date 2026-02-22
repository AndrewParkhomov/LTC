package parkhomov.andrew.ltc.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import parkhomov.andrew.ltc.analytics.AnalyticsService
import parkhomov.andrew.ltc.analytics.createAnalyticsService
import parkhomov.andrew.ltc.domain.CompareLensStorage
import parkhomov.andrew.ltc.domain.DefaultCompareLensStorage
import parkhomov.andrew.ltc.provider.ReviewManager
import parkhomov.andrew.ltc.toast.ToastProvider
import parkhomov.andrew.ltc.toast.ToastProviderImpl

val otherModule =
    module {
        singleOf(::DefaultCompareLensStorage) bind CompareLensStorage::class
        singleOf(::ToastProviderImpl) bind ToastProvider::class
        singleOf(::ReviewManager)
        single<AnalyticsService> { createAnalyticsService() }
    }
