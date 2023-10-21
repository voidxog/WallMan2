package com.colorata.wallman.core.data

object Strings {
    val invalidUrl: Polyglot = simplifiedLocaleOf("Invalid Link", "Неправильная ссылка")
    val required: Polyglot = simplifiedLocaleOf("*required", "*обязательно")
    val configureWidget = simplifiedLocaleOf("Configure widget shape", "Настройте форму виджета")
    val actualVersion = simplifiedLocaleOf(AppConfiguration.VERSION_NAME)
    val scallop = simplifiedLocaleOf("Scallop", "Узор")
    val clever = simplifiedLocaleOf("Clever", "Клевер")
    val square = simplifiedLocaleOf("Square", "Квадрат")
    val circle = simplifiedLocaleOf("Circle", "Круг")
    val flower = simplifiedLocaleOf("Flower", "Цветок")


    val dynamic = simplifiedLocaleOf("Dynamic", "Динамические")
    val dynamicUnavailable =
        simplifiedLocaleOf("Dynamic (Not available)", "Динамические (Недоступно)")
    val static = simplifiedLocaleOf("Static", "Статические")
    val main = simplifiedLocaleOf("Main", "Главная")
    val explore = simplifiedLocaleOf("Explore", "Познавайте")
    val features = simplifiedLocaleOf("Features", "Функции")
    val more = simplifiedLocaleOf("More", "Больше")
    val exploreNew = simplifiedLocaleOf("Explore new", "Познавайте больше")
    val goToMaps = simplifiedLocaleOf("Go to maps", "Найти на карте")
    val ok = simplifiedLocaleOf("Ok", "Ок")
    val confirm = simplifiedLocaleOf("Confirm", "Подтвердить")
    val size = simplifiedLocaleOf("Size: %s MB", "Размер: %s MB")
    val categories = simplifiedLocaleOf("Categories", "Категории")
    val adaptWallpaperToTheme =
        simplifiedLocaleOf("Adapt wallpaper to theme", "Адаптировать обои под тему")

    val new = simplifiedLocaleOf("NEW")
    val retro = simplifiedLocaleOf("RETRO")

    val mapsAppNotInstalled =
        simplifiedLocaleOf("Maps app is not installed", "Приложение для карт не установлено")
    val wallpaperNotFound = simplifiedLocaleOf("Wallpaper not found", "Обои не найдены")
    val browserNotInstalled =
        simplifiedLocaleOf("Browser is not installed", "Браузер не установлен")
    val permissionNeeded = simplifiedLocaleOf("Permission needed", "Нужно разрешение")
    val permissionNeededDescription =
        simplifiedLocaleOf(
            "WallMan needs \"Install unknown apps\" permission because wallpaper " +
                    "packs are packaged into apps",
            "WallMan нужно разрешение \"Устанавливать неизвестные приложения\", " +
                    "потому что паки обоев являются приложениями"
        )

    val performanceWarning = simplifiedLocaleOf(
        "Download anyway?",
        "Все равно скачать?"
    )
    val performanceWarningDescription = simplifiedLocaleOf(
        "These wallpapers can be demanding on performance. On low-performance devices, they can slow down and consume more power.",
        "Эти обои могут быть требовательны к производительности. На малопроизводительных устройствах они могут тормозить и потреблять больше энергии"
    )

    val remove = simplifiedLocaleOf("Remove", "Удалить")
    val install = simplifiedLocaleOf("Install", "Установить")
    val cancel = simplifiedLocaleOf("Cancel", "Отмена")
    val download = simplifiedLocaleOf("Download", "Скачать")
    val downloadManager = simplifiedLocaleOf("Download Manager", "Менеджер загрузки")
    val downloading = simplifiedLocaleOf("Downloading", "Скачивание")
    val errors = simplifiedLocaleOf("Errors", "Ошибки")
    val clearCache = simplifiedLocaleOf("Clear cache", "Очистить кеш")
    val original = simplifiedLocaleOf("Original", "Оригинал")
    val mirrors = simplifiedLocaleOf("Mirrors", "Зеркала")
    val mirror = simplifiedLocaleOf("Mirror", "Зеркало")
    val mirror1 = simplifiedLocaleOf("Mirror 1", "Зеркало 1")
    val githubMirror = simplifiedLocaleOf("Github Mirror", "Зеркало Github")

    val aboutWallMan = simplifiedLocaleOf("About WallMan", "О WallMan")
    val versionOfWallMan = simplifiedLocaleOf("Version of WallMan", "Версия WallMan")
    val developer = simplifiedLocaleOf("Developer", "Разработчик")
    val colorata = simplifiedLocaleOf("Colorata", "Colorata")
    val groupInTelegram = simplifiedLocaleOf("Group in Telegram", "Группа в Telegram")
    val tapToOpen = simplifiedLocaleOf("Tap to open", "Нажмите, чтобы открыть")
    val gitlab = simplifiedLocaleOf("Gitlab", "Gitlab")
    val supportWithQiwi = simplifiedLocaleOf("Support with Qiwi", "Поддержать с помощью Qiwi")
    val reportBug = simplifiedLocaleOf("Report bug", "Сообщить об ошибке")
    val copyLogs = simplifiedLocaleOf("Copy logs", "Скопировать журнал сообщений")
    val logs = simplifiedLocaleOf("Logs", "Журнал сообщений")
    val tapToCopy = simplifiedLocaleOf("Tap to copy", "Нажмите, чтобы скопировать")
    val requiresAccountInGitlab =
        simplifiedLocaleOf("Requires account on Gitlab", "Необходим аккаунт на Gitlab")

    val animations = simplifiedLocaleOf("Animations", "Анимации")
    val animationsDescription = simplifiedLocaleOf("Customize app animations", "Настройте анимации приложения")
    val slide = simplifiedLocaleOf("Slide", "Скольжение")
    val scale = simplifiedLocaleOf("Scale", "Масштаб")
    val fade = simplifiedLocaleOf("Fade", "Угасание")

    val memoryOptimization = simplifiedLocaleOf("Memory Optimization", "Оптимизация памяти")

    val appMotion = simplifiedLocaleOf("App Motion", "Анимации приложения")
    val makeMotionYourOwn =
        simplifiedLocaleOf("Make motion your own", "Настройте анимацию под себя")
    val keepYourMemoryFree = simplifiedLocaleOf(
        "Keep your memory free of unnecessary files", "Держите память телефона в чистоте"
    )
    val youCanAddOtherMirrorIfCurrentDoesNotWork = simplifiedLocaleOf(
        "You can add other mirror if current doesn't work",
        "Вы можете добавить новое зеркало, если текущее не работает"
    )
    val contactInfoDevelopersMore = simplifiedLocaleOf(
        "Contact info, developers, more", "Контактная информация, разработчики и так далее"
    )

    val full = simplifiedLocaleOf("Full", "Полные")
    val fullAnimationExperience =
        simplifiedLocaleOf("Full animation experience", "Полный опыт анимации")
    val simplified = simplifiedLocaleOf("Simplified", "Упрощенные")
    val someAnimationsWereSimplified =
        simplifiedLocaleOf("Some animations were simplified", "Некоторые анимации упрощены")

    object ApplyStates {
        val notApplied = simplifiedLocaleOf("Apply", "Установить")
        val sure = simplifiedLocaleOf("Sure?", "Уверены?")
        val applying = simplifiedLocaleOf("Applying...", "Установка...")
        val applied = simplifiedLocaleOf("Applied", "Установлено")
        val error = simplifiedLocaleOf("Error", "Ошибка")
    }
}