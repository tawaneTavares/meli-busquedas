package com.tawane.meli

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
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
        .components {
            add(SvgDecoder.Factory())
        }
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
                .directory(this.cacheDir.resolve("image_cache"))
                .maxSizePercent(FIVE_PERCENT)
                .build()
        }
        .crossfade(true)
        .logger(DebugLogger())
        .build()
}
