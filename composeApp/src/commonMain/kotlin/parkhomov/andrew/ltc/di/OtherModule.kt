package parkhomov.andrew.ltc.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import parkhomov.andrew.ltc.domain.CompareLensStorage
import parkhomov.andrew.ltc.domain.DefaultCompareLensStorage

val otherModule =
    module {
        singleOf(::DefaultCompareLensStorage) bind CompareLensStorage::class
    }
