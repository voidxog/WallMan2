package com.colorata.wallman.wallpapers

import com.colorata.wallman.core.data.Polyglot
import com.colorata.wallman.core.data.Strings

enum class WallpaperPacks(
    val urlPart: String,
    val previewName: Polyglot,
    val description: Polyglot = previewName,
    val url: String,
    val packageName: String,
    val packageServiceName: String = packageName,
    val id: Int,
    val checksum: Long,
    val includesDynamic: Boolean = true,
    val previewRes: String,
    val minSdk: Int = 0
) {
    P(
        urlPart = "P",
        previewName = Strings.p,
        url = "P.apk",
        packageName = "com.google.pixel.livewallpaper.pulley",
        id = 0,
        checksum = 7294917,
        previewRes = "p_pulley_preview",
        minSdk = 28
    ),
    P1(
        urlPart = "P1",
        previewName = Strings.p1,
        url = "P1.apk",
        packageName = "com.breel.geswallpapers",
        id = 1,
        checksum = 85330559,
        previewRes = "p1_moab_preview",
        minSdk = 29
    ),
    P2(
        urlPart = "P2",
        previewName = Strings.p2,
        url = "P2.apk",
        packageName = "com.breel.wallpapers",
        id = 2,
        checksum = 169453255,
        previewRes = "p2_honolulu_preview",
        minSdk = 30
    ),
    P3(
        urlPart = "P3",
        previewName = Strings.p3,
        url = "P3.apk",
        packageName = "com.breel.wallpapers18",
        id = 3,
        checksum = 167338589,
        previewRes = "p3_fiji_preview",
        minSdk = 30
    ),
    P4(
        urlPart = "P4",
        previewName = Strings.p4,
        url = "P4.apk",
        packageName = "com.breel.wallpapers19",
        id = 4,
        checksum = 47202066,
        previewRes = "p4_arabia_preview",
        minSdk = 30
    ),
    P4A(
        urlPart = "P4A",
        previewName = Strings.p4a,
        url = "P4a.apk",
        packageName = "com.breel.wallpapers20a",
        id = 5,
        checksum = 9889771,
        previewRes = "p4a_gradient_preview",
        minSdk = 30
    ),
    P5(
        urlPart = "P5",
        previewName = Strings.p5,
        url = "P5.apk",
        packageName = "com.breel.wallpapers20",
        id = 6,
        checksum = 50256888,
        previewRes = "p5_stack_preview",
        minSdk = 30
    ),
    P6(
        urlPart = "P6",
        previewName = Strings.p6,
        url = "P6.apk",
        packageName = "com.google.pixel6.livewallpaper",
        packageServiceName = "com.google.pixel.wallpapers21",
        id = 7,
        checksum = 162961629,
        previewRes = "p6_blooming_botanicals_v1_preview",
        minSdk = 28
    ),
    P6_EXT(
        urlPart = "P6_EXT",
        previewName = Strings.p6_ext,
        url = "P6_EXT.apk",
        packageName = "com.google.pixel7.livewallpaper",
        packageServiceName = "com.google.pixel.wallpapers22.lightfieldflower",
        id = 8,
        checksum = 191212605,
        previewRes = "p6_ext_boat_orchid",
        minSdk = 31
    ),
    P6A(
        urlPart = "P6A",
        previewName = Strings.p6a,
        url = "P6a.apk",
        packageName = "com.google.pixel6a.livewallpaper",
        id = 9,
        checksum = 0,
        previewRes = "p6a_landscapes_v1_dark_preview",
        includesDynamic = false
    ),
    P7(
        urlPart = "P7",
        previewName = Strings.p7,
        url = "P7.apk",
        packageName = "com.google.pixel7.livewallpaper",
        id = 10,
        checksum = 0,
        previewRes = "p7_pro_lemongrass_light_preview",
        includesDynamic = false
    ),
    P7A(
        urlPart = "P7A",
        previewName = Strings.p7a,
        url = "P7.apk",
        packageName = "com.google.pixel7a.livewallpaper",
        id = 11,
        checksum = 0,
        previewRes = "p7_pro_lemongrass_light_preview",
        includesDynamic = false
    ),
    PFOLD(
        urlPart = "PFOLD",
        previewName = Strings.pfold,
        url = "PFOLD.apk",
        packageName = "com.trzpro.pixelfold",
        packageServiceName = "com.trzpro",
        id = 12,
        checksum = 47208128,
        previewRes = "pfold_licorice_preview",
        minSdk = 28
    ),
    P8(
        urlPart = "P8",
        previewName = Strings.p8,
        url = "P8.apk",
        packageName = "com.google.pixel8a.livewallpaper",
        id = 13,
        checksum = 0,
        previewRes = "p8_haze_dark",
        includesDynamic = false
    ),
    P8A(
        urlPart = "P8A",
        previewName = Strings.p8a,
        url = "P8A.apk",
        packageName = "com.google.pixel8a.livewallpaper",
        id = 14,
        checksum = 0,
        previewRes = "p8a_emerald_dark",
        includesDynamic = false
    ),
}

fun WallpaperPacks.sizeInMb() = checksum / 1024 / 1024