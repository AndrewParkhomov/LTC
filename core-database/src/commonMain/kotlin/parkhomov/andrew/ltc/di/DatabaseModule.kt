package parkhomov.andrew.ltc.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import parkhomov.andrew.ltc.database.AppDatabase
import parkhomov.andrew.ltc.database.DefaultRefractiveIndexRepository
import parkhomov.andrew.ltc.database.RefractiveIndexRepository
import parkhomov.andrew.ltc.database.createDatabase

val databaseModule = module {
    single<AppDatabase> { createDatabase() }
    single { get<AppDatabase>().refractiveIndexDao() }
    singleOf(::DefaultRefractiveIndexRepository) bind RefractiveIndexRepository::class
}
