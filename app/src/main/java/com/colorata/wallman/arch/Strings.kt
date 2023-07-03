package com.colorata.wallman.arch

import com.colorata.wallman.wallpaper.WallpaperLocale
import kotlinx.collections.immutable.persistentListOf

object Strings {
    val invalidUrl: Polyglot = simplifiedLocaleOf("Invalid Link", "Неправильная ссылка")
    val required: Polyglot = simplifiedLocaleOf("*required", "*обязательно")
    val configureWidget = simplifiedLocaleOf("Configure widget shape", "Настройте форму виджета")
    val actualVersion = simplifiedLocaleOf("Tiramisu01", "Tiramisu01")
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
    val features = simplifiedLocaleOf("Features", "Функции")
    val more = simplifiedLocaleOf("More", "Больше")
    val exploreNew = simplifiedLocaleOf("Explore new", "Познавайте больше")
    val goToMaps = simplifiedLocaleOf("Go to maps", "Найти на карте")
    val size = simplifiedLocaleOf("Size: %s MB", "Размер: %s MB")
    val categories = simplifiedLocaleOf("Categories", "Категории")
    val adaptWallpaperToTheme =
        simplifiedLocaleOf("Adapt wallpaper to theme", "Адаптировать обои под тему")

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

    val aboutWallMan = simplifiedLocaleOf("About WallMan", "О WallMan")
    val versionOfWallMan = simplifiedLocaleOf("Version of WallMan", "Версия WallMan")
    val developer = simplifiedLocaleOf("Developer", "Разработчик")
    val colorata = simplifiedLocaleOf("Colorata", "Colorata")
    val groupInTelegram = simplifiedLocaleOf("Group in Telegram", "Группа в Telegram")
    val tapToOpen = simplifiedLocaleOf("Tap to open", "Нажмите, чтобы открыть")
    val gitlab = simplifiedLocaleOf("Gitlab", "Gitlab")
    val supportWithQiwi = simplifiedLocaleOf("Support with Qiwi", "Поддержать с помощью Qiwi")
    val reportBug = simplifiedLocaleOf("Report bug", "Сообщить об ошибке")
    val requiresAccountInGitlab =
        simplifiedLocaleOf("Requires account on Gitlab", "Необходим аккаунт на Gitlab")

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

    object Wallpapers {
        val moab = WallpaperLocale(
            simplifiedLocaleOf("Arches National Park", "Нац. парк Арки"), simplifiedLocaleOf(
                "In addition to a variety of geological formations, the park contains over 2,000 natural sandstone arches, including the world-famous Delicate Arch.",
                "Наряду с другими чудесами природы в парке находится более 2000 нерукотворных арок из песчаника. В том числе – всемирно известная Изящная арка."
            ), simplifiedLocaleOf(
                "Arches National Park, Moab, Utah", "Нац. парк Арки, Моаб, штат Юта"
            )
        )

        val kent = WallpaperLocale(
            simplifiedLocaleOf("Garden", "Сад"), simplifiedLocaleOf(
                "With its abundance of fruit orchards and hop gardens, Kent is known as The Garden of England.",
                "Из-за многочисленных фруктовых садов и плантаций хмеля Кент называют \"садом Англии\"."
            ), simplifiedLocaleOf(
                "Garden, Kent, United Kingdom", "Сад, Кент, Великобритани"
            )
        )

        val halfDome = WallpaperLocale(
            simplifiedLocaleOf("Half Dome", "Скала Хаф-Доум"), simplifiedLocaleOf(
                "This granite formation in Yosemite Valley, named for its distinct shape, rises more than 4,737 ft above the valley floor.",
                "Название этой гранитной скалы переводится как \"полукупол\". Она возвышается над долиной Йосемити на 1450 метров."
            ), simplifiedLocaleOf(
                "Half Dome, Yosemite National Park, CA",
                "Скала Хаф-Доум в Йосемитском нац. парке, штат Калифорния"
            )
        )

        val sunset = WallpaperLocale(
            simplifiedLocaleOf("Horizon", "Горизонт"), simplifiedLocaleOf(
                "Look to the horizon. The sun rises as your phone charges, and lowers as the battery is used.",
                "Солнце поднимается над горизонтом, пока ваш телефон заряжается, и опускается, когда расходуется заряд батареи."
            ), simplifiedLocaleOf("Horizon", "Горизонт")
        )

        val pantheon = WallpaperLocale(
            simplifiedLocaleOf("Pantheon", "Пантеон"), simplifiedLocaleOf(
                "Almost two thousand years after the Pantheon was built, the circular building remains the world's largest unreinforced concrete dome.",
                "Пантеон построен почти 2000 лет назад, но и сегодня его неармированный бетонный купол остается самым большим в мире."
            ), simplifiedLocaleOf("Pantheon, Rome, Italy", "Пантеон, Рим, Италия")
        )

        val togo = WallpaperLocale(
            simplifiedLocaleOf("Shijuku", "Район Синдзюку"), simplifiedLocaleOf(
                "At 656 ft high, this building in Shinjuku, designed by Yoshikazu Uchida, is the 28th tallest building in Tokyo and the 33rd tallest in Japan.",
                "Это здание в районе Синдзюку высотой 200 метров создал архитектор Йошикацу Йошида. Сегодня оно на 28 месте среди самых высоких зданий Токио и на 33 месте – среди небоскребов Японии."
            ), simplifiedLocaleOf("Shijuku, Tokyo, Japan", "Район Синдзюку, Токио, Япония")
        )

        val whitehaven = WallpaperLocale(
            simplifiedLocaleOf("Whitehaven Beach", "Пляж Уайтхэвен"), simplifiedLocaleOf(
                "Known for its white sands, Whitehaven Beach is made up of 98% pure silica, which gives it a bright white color.",
                "Пляж Уайтхэвен славится своим белоснежным песком, который на 98% состоит из чистого диоксида кремния."
            ), simplifiedLocaleOf(
                "Whitehaven Beach, Queensland, Australia", "Пляж Уайтхэвен, Квинсленд, Австралия"
            )
        )

        val earth = WallpaperLocale(
            simplifiedLocaleOf("Your world", "Солнечная система"), simplifiedLocaleOf(
                "Inspired by Apollo 17's Blue Marble, view Earth from space based on your location, with real-time clouds.",
                "Обои по мотивам знаменитой фотографии, сделанной экипажем корабля \"Аполлон-17\". Вид на Землю из космоса меняется в зависимости от вашего местоположения. Любуйтесь изображением облаков в реальном времени!"
            ), simplifiedLocaleOf("Your world, Solar system", "Солнечная система")
        )

        val canyons = WallpaperLocale(
            simplifiedLocaleOf("Gooey", "Капли на стекле"),
            simplifiedLocaleOf(
                "Touch these adaptable screens to reshape the gooey patterns.",
                "Проведите по экрану или коснитесь его, и изображение изменится."
            ),
            simplifiedLocaleOf("Gooey, Marshmallow", "Капли на стекле: маршмеллоу"),
            persistentListOf(
                simplifiedLocaleOf("Gooey, Licorice", "Капли на стекле: темная ночь"),
                simplifiedLocaleOf("Gooey, Mint", "Капли на стекле: нежная мята"),
                simplifiedLocaleOf("Gooey, Orange crush", "Капли на стекле: солнечный оранжад"),
                simplifiedLocaleOf("Gooey, Vanilla taffy", "Капли на стекле: брызги шампанского"),
                simplifiedLocaleOf("Gooey, Violet dream", "Капли на стекле: северное сияние"),
            )
        )

        val earthOrbit = WallpaperLocale(
            simplifiedLocaleOf("World comes to you", "На орбите"), simplifiedLocaleOf(
                "Journey around the world without packing a suitcase. Your vantage point changes throughout the day.",
                "Чтобы отправиться в кругосветное путешествие, не обязательно паковать чемоданы. Открывайте новые места с течением дня."
            ), simplifiedLocaleOf("World comes to you", "На орбите")
        )

        val honolulu = WallpaperLocale(
            simplifiedLocaleOf("A walk in the clouds", "Атмосферные явления"), simplifiedLocaleOf(
                "Float by the clouds and frequent rain showers that earned Hawaii the nickname “rainbow state.” Kāneʻohe, Hawaii, United States",
                "Проплывите над островами вместе с облаками и проливными дождями, из-за которых Гавайи получили прозвище \"Радужный штат\". Канеохе, Гавайи, США"
            ), simplifiedLocaleOf(
                "A walk in the clouds, Hawaii", "Атмосферные явления, Гавайи"
            )
        )

        val imprint = WallpaperLocale(
            simplifiedLocaleOf("Glimmer", "Цветоформы"),
            simplifiedLocaleOf(
                "Catch a glimmer of color as you move or touch your phone.",
                "Коснитесь экрана или наклоните устройство, и изображение изменится."
            ),
            simplifiedLocaleOf("Glimmer, Candy", "Цветоформы: леденцовое ассорти"),
            persistentListOf(
                simplifiedLocaleOf("Glimmer, Cream", "Цветоформы: кремовая фантазия"),
                simplifiedLocaleOf("Glimmer, Dazzling blue", "Цветоформы: сапфировое мерцание"),
                simplifiedLocaleOf("Glimmer, Early morning", "Цветоформы: рассветные мотивы"),
                simplifiedLocaleOf("Glimmer, Purple haze", "Цветоформы: фиолетовая дымка"),
                simplifiedLocaleOf("Glimmer, Midnight blue", "Цветоформы: синяя вечность"),
            )
        )

        val windy = WallpaperLocale(
            simplifiedLocaleOf("Your whirled", "В погоне за ветром"), simplifiedLocaleOf(
                "Local wind patterns swirl and twirl in real time, based on your location.",
                "Движение потоков ветра в режиме реального времени (c учетом вашего местоположения)."
            ), simplifiedLocaleOf(
                "Your whirled, Blue", "В погоне за ветром: небесная лазурь"
            ), persistentListOf(
                simplifiedLocaleOf("Your whirled, Blush", "В погоне за ветром: закатные краски"),
                simplifiedLocaleOf("Your whirled, Midnight", "В погоне за ветром: полночное небо"),
                simplifiedLocaleOf("Your whirled, Sunset", "В погоне за ветром: закат"),
                simplifiedLocaleOf("Your whirled, Turquoise", "В погоне за ветром: бирюзовый бриз"),
                simplifiedLocaleOf("Your whirled, Sky blue", "В погоне за ветром: синяя даль"),
            )
        )

        val lagos = WallpaperLocale(
            simplifiedLocaleOf("Calming coastline", "Безмятежный прибой"), simplifiedLocaleOf(
                "Watch live turquoise waves lap against Ponta da Piedade, a group of rock formations along the Atlantic coast. Lagos, Portugal",
                "Посмотрите, как бирюзовые волны Атлантического океана накатывают на скалы Понта-да-Пьедаде. Лагуш, Португалия."
            ), simplifiedLocaleOf("Calming coastline, Lagos", "Безмятежный прибой, Лагуш")
        )

        val mars = WallpaperLocale(
            simplifiedLocaleOf("Planet red", "Красная планета"), simplifiedLocaleOf(
                "Glimpse real-time sunrise and sunset over Mars’ ice caps while thin, wispy clouds float by the Valles Marineris canyon.",
                "Вы увидите настоящие закаты и рассветы над ледяными шапками Марса, а также невесомые облака, проплывающие над долинами Маринер."
            ), simplifiedLocaleOf("Planet red", "Красная планета")
        )

        val monumentValley = WallpaperLocale(
            simplifiedLocaleOf("Flying above", "Взгляд с высоты"), simplifiedLocaleOf(
                "Hot-air balloons float by Monument Valley’s red sandstone formations. Monument Valley, Utah, United States",
                "Воздушные шары пролетают над скалами из красного песчаника. Долина монументов, Юта, США."
            ), simplifiedLocaleOf(
                "Flying above, Monument Valley", "Взгляд с высоты, Долина монументов"
            )
        )

        val uyuni = WallpaperLocale(
            simplifiedLocaleOf("Hint of salt", "Вода и соль"), simplifiedLocaleOf(
                "See the world’s largest salt flat, Salar de Uyuni, a breeding ground for several species of flamingos. Salar de Uyuni, Bolivia",
                "Самый большой в мире солончак, на котором обзаводятся потомством несколько видов фламинго. Солончак Уюни, Боливия."
            ), simplifiedLocaleOf(
                "Hint of salt, Salar de Uyuni", "Вода и соль, солончак Уюни"
            )
        )

        val vesuvius = WallpaperLocale(
            simplifiedLocaleOf("Hidden fires", "Внутренний огонь"), simplifiedLocaleOf(
                "Birds soar above Mount Vesuvius volcano, which destroyed Pompeii in AD 79 and erupted in 1944. Mount Vesuvius, Campania, Italy",
                "Полет птиц над Везувием – вулканом, который уничтожил Помпеи в 79 г. н. э. Последнее извержение произошло в 1944 г. Вулкан Везувий, Кампания, Италия."
            ), simplifiedLocaleOf(
                "Hidden fires, Mount Vesuvius", "Внутренний огонь, вулкан Везувий"
            )
        )

        val moon = WallpaperLocale(
            simplifiedLocaleOf("Moon shot", "На обратной стороне"), simplifiedLocaleOf(
                "Get closer to the moon and observe its real-time lunar phases.",
                "Следите за сменой фаз луны в реальном времени."
            ), simplifiedLocaleOf("Moon shot", "На обратной стороне")
        )

        val mexico = WallpaperLocale(
            simplifiedLocaleOf("White Sands", "Белый песок"), simplifiedLocaleOf(
                "Watch wind carry dancing sand across the Sonoran Desert, spanning the United States and Mexico. Sonoran Desert, Mexico",
                "Песок пляшет на крыльях ветра в пустыне, раскинувшейся на территории США и Мексики. Пустыня Сонора, Мексика."
            ), simplifiedLocaleOf(
                "White Sands, Sonoran Desert", "Белый песок: пустыня Сонора"
            )
        )

        val soundViz = WallpaperLocale(
            simplifiedLocaleOf("Groove", "В такт музыке"), simplifiedLocaleOf(
                "Pixel moves to the music...your music. When music is playing, you’ll see colors shift and sway to the rhythm.",
                "Слушайте музыку и смотрите, как цветные волны перемещаются по экрану и танцуют в такт."
            ), simplifiedLocaleOf("Groove, Chill", "В такт музыке: спокойствие"), persistentListOf(
                simplifiedLocaleOf("Groove, Party", "В такт музыке: веселье"),
                simplifiedLocaleOf("Groove, After Dark", "В такт музыке: сумерки")
            )
        )

        val newYork = WallpaperLocale(
            simplifiedLocaleOf("City Silhouette", "Городской пейзаж"),
            simplifiedLocaleOf(
                "Watch the City that Never Sleeps change dynamically throughout the day and night. New York, United States",
                "Город, который никогда не спит. Наблюдайте за сменой дня и ночи в Нью-Йорке."
            ),
            simplifiedLocaleOf("City Silhouette, New York", "Городской пейзаж: Нью-Йорк"),
            persistentListOf(
                simplifiedLocaleOf(
                    "City Silhouette, San Francisco", "Городской пейзаж: Сан-Франциско"
                ),
            )
        )

        val surfAndTurf = WallpaperLocale(
            simplifiedLocaleOf("A Drop in the Ocean", "Капля в море"),
            simplifiedLocaleOf(
                "Feel the current of the world around you, with bodies of water and terrain taking on a watercolor form.",
                "Мир вокруг перетекает в абстрактный акварельный пейзаж."
            ),
            simplifiedLocaleOf("A Drop in the Ocean, Coral", "Капля в море: коралл"),
            persistentListOf(
                simplifiedLocaleOf("A Drop in the Ocean, Seaweed", "Капля в море: водоросли"),
                simplifiedLocaleOf("A Drop in the Ocean, Waves", "Капля в море: волны")
            )
        )

        val pluto = WallpaperLocale(
            simplifiedLocaleOf("New Horizons", "Новые горизонты"), simplifiedLocaleOf(
                "Pluto shows its two faces. Enjoy rich red tones during the day, and a mysterious blue brilliance at night.",
                "Два лица Плутона: красные тона днем и загадочный голубой блеск ночью."
            ), simplifiedLocaleOf("New Horizons", "Новые горизонты")
        )

        val miniman = WallpaperLocale(
            simplifiedLocaleOf("Pixie", "Пикси"), simplifiedLocaleOf(
                "Your playful guide awaits. Pixie magically appears when your phone is at rest and goes on a journey when you unlock your phone.",
                "Ваш забавный компаньон ждет вас. Он появляется из ниоткуда, когда ваш телефон отдыхает. Разблокируйте экран, и Пикси исчезнет."
            ), simplifiedLocaleOf("Pixie, Cobalt", "Пикси: кобальтовый"), persistentListOf(
                simplifiedLocaleOf("Pixie, Periwinkle", "Пикси: фиолетово-голубой"),
                simplifiedLocaleOf("Pixie, Emerald", "Пикси: изумрудный")
            )
        )

        val utah = WallpaperLocale(
            simplifiedLocaleOf("Bird’s-Eye View", "С высоты птичьего полета"), simplifiedLocaleOf(
                "Take a glimpse of Zion National Park’s vermillion cliffs, reaching up to 2,200 feet. Zion National Park, Utah, United States",
                "Взгляните на ярко-красные скалы, достигающие высоты 700 метров. Национальный парк Зайон в штате Юта, США."
            ), simplifiedLocaleOf(
                "Bird’s-Eye View, Zion National Park",
                "С высоты птичьего полета: Национальный парк Зайон"
            )
        )

        val tactile = WallpaperLocale(
            simplifiedLocaleOf("Bloom", "Цветение"), simplifiedLocaleOf(
                "Feel the burst when you touch your screen. Your wallpaper also responds to notifications, unlocking, and more.",
                "Коснитесь экрана, и вы увидите салют. Обои также реагируют на уведомления, разблокировку экрана и многое другое."
            ), simplifiedLocaleOf("Bloom, Sunrise", "Цветение: восход"), persistentListOf(
                simplifiedLocaleOf("Bloom, Cloud", "Цветение: облако"),
                simplifiedLocaleOf("Bloom, Sky", "Цветение: небо"),
                simplifiedLocaleOf("Burst, Forest", "Салют: лес"),
                simplifiedLocaleOf("Burst, Midnight", "Салют: полночь"),
                simplifiedLocaleOf("Burst, Dusk", "Салют: закат")
            )
        )

        val switzerland = WallpaperLocale(
            simplifiedLocaleOf("Light the Way", "Свет в пути"), simplifiedLocaleOf(
                "Escape to the small town of Schwyz, surrounded by the Alps, where the houses come alive at night. Schwyz, Switzerland",
                "Укройтесь от посторонних глаз в альпийском городке Швиц, где дома оживают с наступлением ночи. Швиц, Швейцария."
            ), simplifiedLocaleOf("Light the Way, Schwyz", "Свет в пути: Швиц")
        )

        val fiji = WallpaperLocale(
            simplifiedLocaleOf("Tropical Voyage", "Тропическое путешествие"), simplifiedLocaleOf(
                "Dive into the Malolo Barrier Reef, known for its world-class snorkeling, as boats cruise on by. Malolo Barrier Reef, Fiji",
                "Полюбуйтесь барьерным рифом Малоло, одним из лучших мест для сноркелинга, и проплывающими мимо корабликами. Барьерный риф Малоло, Фиджи."
            ), simplifiedLocaleOf(
                "Tropical Voyage, Malolo Barrier Reef",
                "Тропическое путешествие: барьерный риф Малоло"
            )
        )

        val delight = WallpaperLocale(
            simplifiedLocaleOf("Bubble Up", "Цветные пузыри"), simplifiedLocaleOf(
                "Colors bounce around the screen as you touch or tilt your phone. Keep an eye out for extra moments of bubbly delight.",
                "Коснитесь экрана или покачайте телефон – цветные пузыри начнут перемещаться по экрану и радовать вас."
            ), simplifiedLocaleOf("Bubble Up, Peach", "Цветные пузыри: персик"), persistentListOf(
                simplifiedLocaleOf("Bubble Up, Lemon", "Цветные пузыри: лимон"),
                simplifiedLocaleOf("Bubble Up, Plum", "Цветные пузыри: слива"),
            )
        )

        val arabia = WallpaperLocale(
            simplifiedLocaleOf("Tarout Bay", "Тарут"), simplifiedLocaleOf(
                "Fluffy clouds slowly wash over Tarout Bay, one of the most ancient sites of human civilization in the Arabian Peninsula.",
                "Пушистые облака медленно плывут над островом Тарут – одним из древнейших обитаемых мест на Аравийском полуострове."
            ), simplifiedLocaleOf("Tarout Bay, Saudi Arabia", "Тарут (Саудовская Аравия)")
        )

        val compass = WallpaperLocale(
            simplifiedLocaleOf("Compass", "Компас"), simplifiedLocaleOf(
                "Pick a location, and let the compass show you the way.",
                "Выберите направление, и пусть компас укажет вам путь."
            ), simplifiedLocaleOf("Compass", "Компас")
        )

        val doodle = WallpaperLocale(
            simplifiedLocaleOf("Doodle", "Рисунок"), simplifiedLocaleOf(
                "Tap, drag, fling! This wallpaper is yours to create. Tap customize to explore and start fresh.",
                "Водите пальцами по экрану, нажимайте и перетаскивайте! Создайте свои собственные обои. Чтобы начать, нажмите \"Настроить\"."
            ), simplifiedLocaleOf("Doodle", "Рисунок")
        )

        val garden = WallpaperLocale(
            simplifiedLocaleOf("Garden", "Сад"), simplifiedLocaleOf(
                "Take a moment to rest and enjoy these rustling leaves. Artwork in collaboration with Anna Kövecses.",
                "Отложите дела и насладитесь отдыхом под шум листвы. Создано в сотрудничестве с Анной Ковекзес."
            ), simplifiedLocaleOf("Garden: Leafy", "Сад: листья"), persistentListOf(
                simplifiedLocaleOf("Garden: Rocky", "Сад: камни"),
                simplifiedLocaleOf("Garden: Prickly", "Сад: колючки"),
            )
        )

        val sights = WallpaperLocale(
            simplifiedLocaleOf("Sights From The Sun", "Солнечная система"), simplifiedLocaleOf(
                "Follow along the dance of planets and moons, from the best seat there is: the center of the Solar System.",
                "Солнце – идеальное место, чтобы наблюдать завораживающий танец планет и их спутников."
            ), simplifiedLocaleOf("Sights From The Sun", "Солнечная система")
        )

        val pilatus = WallpaperLocale(
            simplifiedLocaleOf("Mount Pilatus", "Пилатус"), simplifiedLocaleOf(
                "Watch as birds glide along the incline of Mount Pilatus, a mountain massif overlooking Lucerne in Central Switzerland",
                "Наблюдайте за полетом птиц вдоль склонов горного массива Пилатус в швейцарских Альпах, откуда открывается захватывающий вид на город Люцерн."
            ), simplifiedLocaleOf("Mount Pilatus, Switzerland", "Пилатус (Швейцария)")
        )

        val uluru = WallpaperLocale(
            simplifiedLocaleOf("Uluru", "Улуру"), simplifiedLocaleOf(
                "Marvel at one of Australia’s most recognisable natural landmarks, Uluru / Ayers Rock.",
                "Полюбуйтесь на скалу Улуру (Айерс Рок) – одну из самых узнаваемых природных достопримечательностей Австралии."
            ), simplifiedLocaleOf("Uluru, Australia", "Улуру (Австралия)")
        )

        val gradient = WallpaperLocale(
            simplifiedLocaleOf("Eclipse", "Затмение"), simplifiedLocaleOf(
                "Watch the colors ebb and flow as your battery life changes.",
                "Наблюдайте за тем, как переливаются цвета в зависимости от уровня заряда батареи вашего устройства."
            ), simplifiedLocaleOf("Eclipse", "Затмение")
        )

        val conveyor = WallpaperLocale(
            simplifiedLocaleOf("Conveyor Belt", "Конвейерная лента"), simplifiedLocaleOf(
                "Watch as new objects appear, delivered by conveyor belt. Some hint at your phone state, others appear for certain actions.",
                "На конвейерной ленте появляются новые объекты. Некоторые из них сигнализируют о состоянии вашего телефона, другие касаются выполнения определенных действий."
            ), simplifiedLocaleOf("Conveyor Belt", "Конвейерная лента")
        )

        val stack = WallpaperLocale(
            simplifiedLocaleOf("Stepping Stones", "Пирамида из камней"), simplifiedLocaleOf(
                "This sculpture grows as you unlock your phone. But watch out! It wobbles when your phone tilts.",
                "Эта пирамида из камней растет, если телефон разблокирован. Будьте осторожны: она качается, когда вы наклоняете устройство."
            ), simplifiedLocaleOf("Stepping Stones", "Пирамида из камней")
        )

        val sundial = WallpaperLocale(
            simplifiedLocaleOf("Moving Shadows", "Движение теней"), simplifiedLocaleOf(
                "Watch sharp shadows transform as the light slowly changes position throughout the day.",
                "Тени от предметов на экране изменяются по мере движения солнца в течение дня."
            ), simplifiedLocaleOf("Moving Shadows", "Движение теней")
        )

        val pulley = WallpaperLocale(
            simplifiedLocaleOf("Pinball", "Пинбол"), simplifiedLocaleOf(
                "Tilt, tap, or swipe to make the circles move. Inspired by classic pinball.",
                "Чтобы круги двигались, наклоняйте устройство, нажимайте на обои или проводите по ним."
            ), simplifiedLocaleOf("Pinball", "Пинбол")
        )

        val bloomingBotanicals = WallpaperLocale(
            simplifiedLocaleOf("Blooming Botanicals", "Цветущие Растения"),
            simplifiedLocaleOf(
                "Bloom one by one. Grow together.",
                "Цветут один за другим. Растут вместе."
            ),
            simplifiedLocaleOf("Blooming Botanicals", "Цветущие Растения")
        )

        val brightBlooms = WallpaperLocale(
            simplifiedLocaleOf("Bright Blooms", "Яркие Цветы"),
            simplifiedLocaleOf(
                "Bloom one by one. Grow together.",
                "Цветут один за другим. Растут вместе."
            ),
            simplifiedLocaleOf("Bright Blooms", "Яркие Цветы")
        )

        val landscapes = WallpaperLocale(
            simplifiedLocaleOf("Landscapes", "Пейзажи"),
            simplifiedLocaleOf(
                "Vast landscapes transform into a tapestry of colors.",
                "Обширные пейзажи превращаются в разноцветный гобелен."
            ),
            simplifiedLocaleOf("Landscapes", "Пейзажи"),
        )

        val florals = WallpaperLocale(
            simplifiedLocaleOf("Florals", "Цветочные композиции"),
            simplifiedLocaleOf(
                "Elegant lines form an arrangement of faded florals.",
                "Элегантные линии образуют композицию из увядших цветов."
            ),
            simplifiedLocaleOf("Florals", "Цветочные композиции"),
        )

        val cockatoo = WallpaperLocale(
            simplifiedLocaleOf("Cockatoo", "Какаду"),
            simplifiedLocaleOf(
                "Notable, Cockatoo lives only in Australia and New Guinea. And now in lives in your phone.",
                "Примечательно, Какаду живет только в Австралии и Новой Гвинее. Теперь она живет и в вашем телефоне."
            ),
            simplifiedLocaleOf("Cockatoo", "Какаду")
        )

        val piedCrow = WallpaperLocale(
            simplifiedLocaleOf("Pied crow", "Пестрая ворона"),
            simplifiedLocaleOf(
                "The gait of Pied Crow is serious and full of delight, but at the same time light and fast.",
                "Походка Пестрого Ворона серьёзна и полна достоинства, но при этом легка и быстра."
            ),
            simplifiedLocaleOf("Pied crow", "Пестрая ворона"),
        )

        val greenWingedMacaw = WallpaperLocale(
            simplifiedLocaleOf("Green-winged macaw", "Зеленокрылый ара"),
            simplifiedLocaleOf(
                "Green-winged macaws are one of the most colored birds. Although, they're invisible in the foliage of trees.",
                "Зеленокрылые ара одни из самых цветастых птиц. Несмотря на это, они невидимы в листве деревьев."
            ),
            simplifiedLocaleOf("Green-winged macaw", "Зеленокрылый ара")
        )

        val azureJay = WallpaperLocale(
            simplifiedLocaleOf("Azure Jay", "Лазурная сойка"),
            simplifiedLocaleOf(
                "Azure jays have their own language. These are trained birds that have 14 cries in their repertoire for various purposes.",
                "Лазурные сойки имеют свой собственный язык. Это обучаемые птицы, имеющие в своём репертуаре для различных целей 14 криков."
            ),
            simplifiedLocaleOf("Azure Jay", "Лазурная сойка")
        )

        val seagull = WallpaperLocale(
            simplifiedLocaleOf("Seagull", "Чайка"),
            simplifiedLocaleOf(
                "The seagulls are universal. They are the least specialised of all the seabirds, and their body allows for equal adeptness in swimming, flying, and walking.",
                "Чайки - универсальны. Они наименее специализированы из всех морских птиц, и их тело позволяет одинаково хорошо плавать, летать и ходить."
            ),
            simplifiedLocaleOf("Seagull", "Чайка")
        )

        val flamingoFlower = WallpaperLocale(
            simplifiedLocaleOf("Flamingo Flower", "Антуриум"),
            simplifiedLocaleOf(
                "Bloom one by one. Grow together.",
                "Цветут один за другим. Растут вместе."
            )
        )

        val persianButtercup = WallpaperLocale(
            simplifiedLocaleOf("Persian Buttercup", "Лютик азиатский"),
            simplifiedLocaleOf(
                "Bloom one by one. Grow together.",
                "Цветут один за другим. Растут вместе."
            )
        )

        val makiDahlia = WallpaperLocale(
            simplifiedLocaleOf("Maki Dahlia", "Георгина \"Маки\""),
            simplifiedLocaleOf(
                "Bloom one by one. Grow together.",
                "Цветут один за другим. Растут вместе."
            )
        )

        val boatOrchid = WallpaperLocale(
            simplifiedLocaleOf("Boat Orchid", "Цимбидиум"),
            simplifiedLocaleOf(
                "Bloom one by one. Grow together.",
                "Цветут один за другим. Растут вместе."
            )
        )

        val licorice = WallpaperLocale(
            simplifiedLocaleOf("Licorice", "Лакрица"),
            simplifiedLocaleOf(
                "The plumage of elegant birds comes to life as soon as you unlock your phone.",
                "Оперение элегантных птиц оживает, как только вы разблокируете ваш телефон"
            )
        )

        val porcelain = WallpaperLocale(
            simplifiedLocaleOf("Porcelain", "Фарфор"),
            simplifiedLocaleOf(
                "The plumage of elegant birds comes to life as soon as you unlock your phone.",
                "Оперение элегантных птиц оживает, как только вы разблокируете ваш телефон"
            )
        )

        // cotton - grey-crowned crane
        // arctic - blue-throated macaw
        // carbon - andrean condor
        // realr - livingstone's turaco
        val arctic = WallpaperLocale(
            simplifiedLocaleOf("Blue-throated macaw", "Синегорлый ара"),
            simplifiedLocaleOf(
                "The blue-throated macaw is included in the Red Book, it is known for its intelligence and ability to imitate human speech.",
                "Синегорлый ара включен в Красную книгу, он известен своим интеллектом и способностью подражать человеческой речи."
            )
        )

        val carbon = WallpaperLocale(
            simplifiedLocaleOf("Andrean condor", "Андский кондор"),
            simplifiedLocaleOf(
                "The wingspan of the Andean Condor can reach 3 meters, and its weight is 15 kilograms, which makes it one of the largest flying birds in the world.",
                "Размах крыльев Андского кондора может достигать 3 метров, а вес - 15 килограммов, что делает его одной из самых больших летающих птиц в мире."
            )
        )

        val cotton = WallpaperLocale(
            simplifiedLocaleOf("Crane", "Восточный журавль"),
            simplifiedLocaleOf(
                "The Grey crowned crane is listed in the Red Book, it can take off to a fairly high altitude up to 10,000 meters.",
                "Восточный венценосный журавль внесен в Красную книгу, он может взлетать на довольно большую высоту до 10 000 метров."
            ),
            simplifiedLocaleOf("Grey crowned crane", "Восточный венценосный журавль"),
        )

        val realr = WallpaperLocale(
            simplifiedLocaleOf("Livingstone's turaco", "Турако Ливингстона"),
            simplifiedLocaleOf(
                "Livingston's Turaco is considered one of the most beautiful birds, after the rain it shines like a diamond",
                "Турако Ливингстона считается одной из самых красивых птиц, после дождя она сияет, как бриллиант"
            )
        )

        object Packs {
            val p = simplifiedLocaleOf(
                "Pinball Wallpaper Pack", "Пак обоев \"Пинбол\""
            )

            val p1 = simplifiedLocaleOf(
                "Pixel 1 Wallpaper Pack", "Пак обоев \"Pixel 1\""
            )

            val p2 = simplifiedLocaleOf(
                "Pixel 2 Wallpaper Pack", "Пак обоев \"Pixel 2\""
            )

            val p3 = simplifiedLocaleOf(
                "Pixel 3 Wallpaper Pack", "Пак обоев \"Pixel 3\""
            )

            val p4 = simplifiedLocaleOf(
                "Pixel 4 Wallpaper Pack", "Пак обоев \"Pixel 4\""
            )

            val p4a = simplifiedLocaleOf(
                "Pixel 4a Wallpaper Pack", "Пак обоев \"Pixel 4a\""
            )

            val p5 = simplifiedLocaleOf(
                "Pixel 5 Wallpaper Pack", "Пак обоев \"Pixel 5\""
            )

            val p6 = simplifiedLocaleOf(
                "Pixel 6 Wallpaper Pack", "Пак обоев \"Pixel 6\""
            )

            val p6_ext = simplifiedLocaleOf(
                "Pixel 6 Extended Wallpaper Pack", "Расширенный пак обоев \"Pixel 6\""
            )

            val p6a = simplifiedLocaleOf(
                "Pixel 6A Wallpaper Pack", "Пак обоев \"Pixel 6A\""
            )

            val p7 = simplifiedLocaleOf(
                "Pixel 7 Wallpaper Pack", "Пак обоев \"Pixel 7\""
            )

            val p7a = simplifiedLocaleOf(
                "Pixel 7A Wallpaper Pack", "Пак обоев \"Pixel 7A\""
            )

            val pfold = simplifiedLocaleOf(
                "Pixel Fold Wallpaper Pack", "Пак обоев \"Pixel Fold\""
            )
        }

        object Categories {
            val appulse = WallpaperCategoryLocale(
                simplifiedLocaleOf("Appulse", "Парад планет"), simplifiedLocaleOf(
                    "Explore whole Solar System with Your device.",
                    "Исследуйте всю Солнечную систему с Вашим устройством."
                )
            )

            val wonders = WallpaperCategoryLocale(
                simplifiedLocaleOf("Wonders of the World", "Чудеса света"), simplifiedLocaleOf(
                    "Look at Wonders of the World right from Your device.",
                    "Смотрите на чудеса света прямо с Вашего устройства."
                )
            )

            val peaceful = WallpaperCategoryLocale(
                simplifiedLocaleOf("Picturesque places", "Живописные места"), simplifiedLocaleOf(
                    "You can look at 3 things endlessly: how fire burns, how water flows and how seagulls fly.",
                    "Бесконечно можно смотреть на 3 вещи: как горит огонь, как течёт вода и как летают чайки."
                )
            )

            val fancy = WallpaperCategoryLocale(
                simplifiedLocaleOf("Flight of fancy", "Полет фантазии"), simplifiedLocaleOf(
                    "Look at abstractions in a new way.", "Посмотрите на абстракции по-новому."
                )
            )

            val garden = WallpaperCategoryLocale(
                simplifiedLocaleOf("Your little garden", "Твой маленький сад"), simplifiedLocaleOf(
                    "Take a look! A moth orchid has bloomed in your garden!",
                    "Взгляни! В твоем саду расцвела орхидея-мотылек!"
                )
            )

            val birdies = WallpaperCategoryLocale(
                simplifiedLocaleOf("Birdies", "Птички"),
                simplifiedLocaleOf(
                    "Elegant plumage of elegant birds.",
                    "Элегантное оперение элегантных птиц."
                )
            )
        }
    }

    object Badges {
        val download = simplifiedLocaleOf(
            "Before installing this wallpaper you need to download it",
            "Прежде чем установить эти обои, вы должны их скачать"
        )

        val notInstallable = simplifiedLocaleOf(
            "You need to give permission to install this wallpaper to system",
            "Вы должны выдать разрешение приложению устанавливать обои"
        )

        val dynamic = simplifiedLocaleOf(
            "This wallpaper can react to different behaviors",
            "Эти обои могут изменяться в зависимости от обстоятельств."
        )

        val usesX86 = simplifiedLocaleOf(
            "Dynamic wallpapers may not work on this device",
            "Динамические обои могут не работать на вашем устройстве"
        )
    }
}
