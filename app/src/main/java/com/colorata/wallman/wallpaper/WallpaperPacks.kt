package com.colorata.wallman.wallpaper

import androidx.annotation.DrawableRes
import com.colorata.wallman.R
import com.colorata.wallman.arch.Polyglot
import com.colorata.wallman.arch.Strings

enum class WallpaperPacks(
    val urlPart: String,
    val previewName: Polyglot,
    val description: Polyglot,
    val url: String,
    val packageName: String,
    val packageServiceName: String = packageName,
    val id: Int,
    val checksum: Long,
    val includesDynamic: Boolean = true,
    @DrawableRes val previewRes: Int
) {
    P(
        urlPart = "P",
        previewName = Strings.Wallpapers.Packs.p,
        url = "P.apk",
        packageName = "com.google.pixel.livewallpaper.pulley",
        id = 0,
        checksum = 7294917,
        previewRes = R.drawable.p_pulley_preview,
        description = Strings.Wallpapers.Packs.p
    ),
    P1(
        urlPart = "P1",
        previewName = Strings.Wallpapers.Packs.p1,
        url = "P1.apk",
        packageName = "com.breel.geswallpapers",
        id = 1,
        checksum = 85330559,
        previewRes = R.drawable.p1_moab_preview,
        description = Strings.Wallpapers.Packs.p1
    ),
    P2(
        urlPart = "P2",
        previewName = Strings.Wallpapers.Packs.p2,
        url = "P2.apk",
        packageName = "com.breel.wallpapers",
        id = 2,
        checksum = 169453255,
        previewRes = R.drawable.p2_honolulu_preview,
        description = Strings.Wallpapers.Packs.p2
    ),
    P3(
        urlPart = "P3",
        previewName = Strings.Wallpapers.Packs.p3,
        url = "P3.apk",
        packageName = "com.breel.wallpapers18",
        id = 3,
        checksum = 167338589,
        previewRes = R.drawable.p3_fiji_preview,
        description = Strings.Wallpapers.Packs.p3
    ),
    P4(
        urlPart = "P4",
        previewName = Strings.Wallpapers.Packs.p4,
        url = "P4.apk",
        packageName = "com.breel.wallpapers19",
        id = 4,
        checksum = 47202066,
        previewRes = R.drawable.p4_arabia_preview,
        description = Strings.Wallpapers.Packs.p4
    ),
    P4A(
        urlPart = "P4A",
        previewName = Strings.Wallpapers.Packs.p4a,
        url = "P4a.apk",
        packageName = "com.breel.wallpapers20a",
        id = 5,
        checksum = 9889771,
        previewRes = R.drawable.p4a_gradient_preview,
        description = Strings.Wallpapers.Packs.p4a
    ),
    P5(
        urlPart = "P5",
        previewName = Strings.Wallpapers.Packs.p5,
        url = "P5.apk",
        packageName = "com.breel.wallpapers20",
        id = 6,
        checksum = 50256888,
        previewRes = R.drawable.p5_stack_preview,
        description = Strings.Wallpapers.Packs.p5
    ),
    P6(
        urlPart = "P6",
        previewName = Strings.Wallpapers.Packs.p6,
        url = "P6.apk",
        packageName = "com.google.pixel6.livewallpaper",
        packageServiceName = "com.google.pixel.wallpapers21",
        id = 7,
        checksum = 162961629,
        previewRes = R.drawable.p6_blooming_botanicals_v1_preview,
        description = Strings.Wallpapers.Packs.p6
    ),
    P6_EXT(
        urlPart = "P6_EXT",
        previewName = Strings.Wallpapers.Packs.p6_ext,
        url = "P6_EXT.apk",
        packageName = "com.google.pixel7.livewallpaper",
        packageServiceName = "com.google.pixel.wallpapers22.lightfieldflower",
        id = 8,
        checksum = 191212605,
        previewRes = R.drawable.p6_ext_boat_orchid,
        description = Strings.Wallpapers.Packs.p6_ext
    ),
    P6A(
        urlPart = "P6A",
        previewName = Strings.Wallpapers.Packs.p6a,
        url = "P6a.apk",
        packageName = "com.google.pixel6a.livewallpaper",
        id = 9,
        checksum = 0,
        previewRes = R.drawable.p6a_landscapes_v1_dark_preview,
        includesDynamic = false,
        description = Strings.Wallpapers.Packs.p6a
    ),
    P7(
        urlPart = "P7",
        previewName = Strings.Wallpapers.Packs.p7,
        url = "P7.apk",
        packageName = "com.google.pixel7.livewallpaper",
        id = 10,
        checksum = 0,
        previewRes = R.drawable.p7_pro_lemongrass_light_preview,
        includesDynamic = false,
        description = Strings.Wallpapers.Packs.p7
    ),
    P7A(
        urlPart = "P7A",
        previewName = Strings.Wallpapers.Packs.p7a,
        url = "P7.apk",
        packageName = "com.google.pixel7a.livewallpaper",
        id = 11,
        checksum = 0,
        previewRes = R.drawable.p7_pro_lemongrass_light_preview,
        includesDynamic = false,
        description = Strings.Wallpapers.Packs.p7a
    ),
    PFOLD(
        urlPart = "PFOLD",
        previewName = Strings.Wallpapers.Packs.pfold,
        url = "PFOLD.apk",
        packageName = "com.trzpro.pixelfold",
        packageServiceName = "com.trzpro",
        id = 12,
        checksum = 47208128,
        previewRes = R.drawable.pfold_licorice_preview,
        description = Strings.Wallpapers.Packs.pfold
    )
}

fun WallpaperPacks.sizeInMb() = checksum / 1024 / 1024