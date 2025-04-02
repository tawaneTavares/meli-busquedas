package com.tawane.meli

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.tawane.data.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

private const val FIFTEEN_PERCENT = 0.15
private const val FIVE_PERCENT = 0.05

@HiltAndroidApp
class MeliApplication :
    Application(),
    ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun newImageLoader(): ImageLoader = ImageLoader.Builder(this)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCache {
            MemoryCache.Builder(this)
                .maxSizePercent(FIFTEEN_PERCENT)
                .strongReferencesEnabled(true)
                .build()
        }
        .diskCachePolicy(CachePolicy.ENABLED)
        .diskCache {
            DiskCache.Builder()
                .maxSizePercent(FIVE_PERCENT)
                .directory(cacheDir)
                .build()
        }
        .logger(DebugLogger())
        .build()
}
