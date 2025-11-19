package com.sajjady.profilerlab.info

/**
 * Rich copy for every profiler scenario. The texts intentionally mix Farsi explanations with
 * English terminology so that the engineers can easily map what they read to Studio tooling.
 */
object ScenarioInfoRepository {

    private val data = mapOf(
        ScenarioIds.CPU_FREEZE_UI to ScenarioInfo(
            id = ScenarioIds.CPU_FREEZE_UI,
            title = "Freeze UI – Block Main Thread",
            summary = "این سناریو Main Thread را برای ۲٫۵ ثانیه مشغول می‌کند تا بتوانید به‌وضوح فریز شدن UI و فریم‌های قرمز در CPU Profiler و System Trace را ببینید.",
            bodyParagraphs = listOf(
                "هنگامی که دکمه را می‌زنید یک Busy Loop داخل Thread اصلی اجرا می‌شود؛ بنابراین نه فقط رندر بعدی انجام نمی‌شود، بلکه هیچ ورودی کاربر نیز پردازش نمی‌شود. این رفتار در Flame Chart به‌صورت بلاک ممتد روی Thread با نام main ظاهر می‌شود و می‌توانید با حرکت بین Top-down و Bottom-up، توالی متدها را تا سطح blockMainThread دنبال کنید.",
                "چنین توقفی اغلب زمانی رخ می‌دهد که کار IO یا محاسبه سنگین روی Main Thread انجام شود. ثبت این سناریو در کنار Layout Inspector و قسمت Jank Detection به تیم کمک می‌کند الگوهای واقعی Jank را تشخیص دهد و وظایف سنگین را به Dispatcher مناسب یا به Worker ها منتقل کند."
            ),
            usageSteps = listOf(
                "از منوی Run گزینه Profile app را انتخاب و دستگاه موردنظر را متصل کنید.",
                "در Android Studio تب CPU Profiler را باز و حالت 'Trace Record' را روی Sampled یا Instrumented قرار دهید.",
                "دکمه \"Freeze UI\" داخل اپ را لمس کنید و به‌محض اجرا، روی Start Recording در Studio بزنید.",
                "پس از متوقف شدن انیمیشن، ضبط را Stop کنید و نمودارهای Main Thread و UI Thread را بررسی کنید تا ارتفاع بلاک‌ها و زمان دقیق فریز مشخص شود."
            ),
            profilerInsights = listOf(
                "در Summary می‌توانید دقیقا ببینید کدام متد ۱۰۰٪ زمان Main Thread را مصرف کرده و آیا Garbage Collection نیز هم‌زمان رخ داده است.",
                "در System Trace به سراغ بخش Frames بروید تا فریم‌های قرمز (Janky) را ببینید و زمان دقیق Dropped Frame را گزارش دهید.",
                "از View Hierarchy اطمینان بگیرید که هیچ Recomposition یا measure/Layout اضافی هم‌زمان اتفاق نیفتاده است تا علت فریز فقط به محاسبه نسبت داده شود."
            ),
            links = listOf(
                ScenarioLink(
                    label = "Android Developers – CPU profiler basics",
                    url = "https://developer.android.com/studio/profile/cpu-profiler",
                    type = ScenarioLinkType.CHROME
                ),
                ScenarioLink(
                    label = "Android Performance Patterns: Rendering Performance",
                    url = "https://www.youtube.com/watch?v=hQ0XTJqFLIE",
                    type = ScenarioLinkType.YOUTUBE
                )
            )
        ),
        ScenarioIds.CPU_BACKGROUND_LOAD to ScenarioInfo(
            id = ScenarioIds.CPU_BACKGROUND_LOAD,
            title = "Heavy background math",
            summary = "یک کرانچ سنگین روی Dispatcher.Default اجرا می‌شود تا اختلاف بین Thread اصلی و Thread های پس‌زمینه در CPU Profiler مشخص شود.",
            bodyParagraphs = listOf(
                "تابع calculatePiWithLeibniz ده‌ها میلیون Iteration را روی Thread pool انجام می‌دهد. در Trace خروجی می‌توانید مشاهده کنید که چگونه Workload بین چند Thread از نوع Default پخش شده و چه مدت طول کشیده تا Work Stealing انجام شود.",
                "این مثال برای نشان دادن تاثیر الگوریتم‌های بد طراحی‌شده و نبود Cancellation یا Chunking مناسب است. هنگام اجرای سناریو مصرف باتری و دمای دستگاه نیز افزایش می‌یابد که در Energy Profiler قابل مشاهده است."
            ),
            usageSteps = listOf(
                "CPU Profiler را در حالت Instrumented یا Sampled قرار دهید تا بتوانید هر Thread را به صورت مجزا بررسی کنید.",
                "قبل از لمس دکمه، یک Mark (با استفاده از Add Task Label) قرار دهید تا نقطه شروع مشخص باشد.",
                "دکمه \"Heavy CPU on Background\" را بزنید و در حین اجرا Thread های Background را در Flame Chart دنبال کنید.",
                "در پایان اجرای Workload، trace را متوقف کرده و متدهایی که بیشترین Sample را داشته‌اند استخراج کنید."
            ),
            profilerInsights = listOf(
                "Bottom-up View کمک می‌کند متوجه شوید آیا تابع Leibniz یا محاسبات Double precision عامل اصلی است یا اینکه GC نیز وارد چرخه شده است.",
                "در بخش Threads می‌توانید تشخیص دهید آیا Dispatchers.Default فقط دو Thread فعال دارد یا به اندازه کافی Scale می‌شود.",
                "با افزودن Counter به System Trace (از طریق Quick settings) می‌توان دمای CPU یا frequency را نیز هم‌زمان مشاهده کرد."
            ),
            links = listOf(
                ScenarioLink(
                    label = "Record traces with CPU Profiler",
                    url = "https://developer.android.com/studio/profile/cpu-profiler#record-traces",
                    type = ScenarioLinkType.CHROME
                ),
                ScenarioLink(
                    label = "Deep dive: Android Studio Profilers",
                    url = "https://www.youtube.com/watch?v=rfLmOrVrjW8",
                    type = ScenarioLinkType.YOUTUBE
                )
            )
        ),
        ScenarioIds.CPU_ALLOCATION_STORM to ScenarioInfo(
            id = ScenarioIds.CPU_ALLOCATION_STORM,
            title = "Allocation storm",
            summary = "تخصیص هزاران آبجکت کوتاه‌عمر باعث افزایش GC و بار CPU می‌شود؛ این وضعیت فرصت خوبی برای بررسی Heap Timeline است.",
            bodyParagraphs = listOf(
                "در این سناریو هر بار ۲۰۰۰ لیست ۱۰ هزار عضوی ساخته می‌شود و بلافاصله آزاد می‌گردد. در CPU Profiler، تاخیر ایجادشده ناشی از متدهای toString و سازنده MutableList دیده می‌شود، درحالی‌که Memory Profiler جهش‌های Heap و GC را نمایش می‌دهد.",
                "هدف این است که تفاوت بین Allocation Tracker و Event timeline را ببینید و متوجه شوید چگونه کاهش تعداد آبجکت یا استفاده از buffer های قابل استفاده مجدد به بهبود Performance کمک می‌کند."
            ),
            usageSteps = listOf(
                "Profiler را روی حالت 'Record allocations' قرار دهید تا هر ساخت آبجکت ثبت شود.",
                "دکمه \"Allocation Storm\" را بزنید و بلافاصله در Heap Timeline به دنبال افزایش پله‌ای حافظه بگردید.",
                "با انتخاب بخش موردنظر در Timeline، لیست کلاس‌هایی که بیشترین Allocation را داشته‌اند مشاهده کنید.",
                "نتایج را با Capture قبلی مقایسه کرده و تعداد GC و زمان توقف را گزارش دهید."
            ),
            profilerInsights = listOf(
                "در Memory Profiler می‌توانید ببینید آیا Reclaim شدن حافظه سریع انجام می‌شود یا نشت رخ داده است.",
                "Combine کردن Trace CPU و Memory کمک می‌کند بفهمید چه مقدار از زمان CPU صرف فعالیت GC شده است.",
                "Allocation Tracker امکان Export به فایل .alloctrace را می‌دهد تا در Code Review مدارک کافی داشته باشید."
            ),
            links = listOf(
                ScenarioLink(
                    label = "Memory and CPU best practices",
                    url = "https://developer.android.com/topic/performance/memory",
                    type = ScenarioLinkType.CHROME
                ),
                ScenarioLink(
                    label = "Understand garbage collection pauses",
                    url = "https://www.youtube.com/watch?v=G8nDAZdg_WU",
                    type = ScenarioLinkType.YOUTUBE
                )
            )
        ),
        ScenarioIds.MEMORY_LEAKY_ACTIVITY to ScenarioInfo(
            id = ScenarioIds.MEMORY_LEAKY_ACTIVITY,
            title = "Leaky Activity",
            summary = "LeakyActivity عمداً یک reference استاتیک به Context نگه می‌دارد تا بتوانید نشت Activity و رشد Heap را پایش کنید.",
            bodyParagraphs = listOf(
                "پس از باز شدن صفحه، Activity خودش را داخل یک Singleton ذخیره می‌کند. با بستن Activity (دکمه Back) همچنان Object از بین نمی‌رود و می‌توانید با ابزار Heap Dump آن را مشاهده کنید.",
                "این تجربه به شما یاد می‌دهد چرا نگه داشتن reference به View یا Activity در کلاس‌های طولانی‌مدت (مانند Companion object یا Cache) خطرناک است و چگونه می‌توان Leak را در هم‌زیستی با WorkManager یا coroutine ها شناسایی کرد."
            ),
            usageSteps = listOf(
                "Memory Profiler را باز و 'Record Allocations' را فعال کنید.",
                "دکمه \"Open LeakyActivity\" را بزنید و پس از مشاهده صفحه، با Back خارج شوید.",
                "یک Heap Dump بگیرید و کلاس LeakyActivity را جست‌وجو کنید تا reference chain مشخص شود.",
                "Leak را با ابزارهای دیگری مثل LeakCanary نیز مقایسه کنید تا روند تحلیل را مستند سازید."
            ),
            profilerInsights = listOf(
                "با مقایسه تعداد Instances قبل و بعد از بازگشت به صفحه اصلی می‌توانید رشد غیرمنتظره Heap را گزارش کنید.",
                "در Panel References مسیر دقیق نگه‌داری Activity مشخص می‌شود؛ معمولا یک Singleton یا Listener ثبت‌نشده است.",
                "System Trace نشان می‌دهد GC چند بار تلاش کرده Activity را آزاد کند اما موفق نشده است."
            ),
            links = listOf(
                ScenarioLink(
                    label = "Use Memory Profiler to find leaks",
                    url = "https://developer.android.com/studio/profile/memory-profiler",
                    type = ScenarioLinkType.CHROME
                ),
                ScenarioLink(
                    label = "LeakCanary talk – Square",
                    url = "https://www.youtube.com/watch?v=_CruQY55HOk",
                    type = ScenarioLinkType.YOUTUBE
                )
            )
        ),
        ScenarioIds.MEMORY_BITMAP_PRESSURE to ScenarioInfo(
            id = ScenarioIds.MEMORY_BITMAP_PRESSURE,
            title = "Bitmap pressure",
            summary = "تخصیص پی‌درپی Bitmap باعث می‌شود بخش Graphics heap پر شود و می‌توانید تاثیر آن را بر GC و فریز UI بررسی کنید.",
            bodyParagraphs = listOf(
                "تابع allocateBitmaps ده‌ها Bitmap با رزولوشن بالا می‌سازد. هرچند کار در Thread پس‌زمینه انجام می‌شود، فشار زیادی به حافظه می‌آورد و اگر دستگاه شما محدود باشد OutOfMemory نیز قابل مشاهده است.",
                "این تمرین برای سناریوهایی مثل لیست تصاویر، map tiles یا Glide misconfiguration بسیار نزدیک به واقعیت است و کمک می‌کند فشرده‌سازی یا Pool کردن Bitmap ها را ارزیابی کنید."
            ),
            usageSteps = listOf(
                "Memory Profiler را در حالت Live allocation قرار دهید تا نمودار Heap را لحظه‌ای ببینید.",
                "دکمه \"Allocate many Bitmaps\" را لمس کنید و در Panel کلاس‌ها، android.graphics.Bitmap را دنبال کنید.",
                "Heap Dump بگیرید و size هر نمونه را تحلیل کنید؛ سپس Garbage Collection دستی را Trigger کنید تا ببینید چه مقدار حافظه آزاد می‌شود.",
                "نتایج را با ابزار Image Loading library (Glide, Coil) مقایسه کنید تا راه‌حل‌های واقعی پیشنهاد دهید."
            ),
            profilerInsights = listOf(
                "در Pie chart نوع حافظه (App heap، Graphics، Native) مشخص می‌شود و می‌توانید ثابت کنید فشار مربوط به Bitmap است.",
                "با ثبت Allocations می‌توان مسیر دقیق کدی که Bitmap می‌سازد را پیدا کرد.",
                "System Trace نشان می‌دهد آیا GC باعث فریز Main Thread شده یا خیر."
            ),
            links = listOf(
                ScenarioLink(
                    label = "Best practices for bitmaps",
                    url = "https://developer.android.com/topic/performance/graphics/load-bitmap",
                    type = ScenarioLinkType.CHROME
                ),
                ScenarioLink(
                    label = "Glide: Caching Bitmaps talk",
                    url = "https://www.youtube.com/watch?v=K_U3DLtYQ6U",
                    type = ScenarioLinkType.YOUTUBE
                )
            )
        ),
        ScenarioIds.MEMORY_SHORT_LIVED to ScenarioInfo(
            id = ScenarioIds.MEMORY_SHORT_LIVED,
            title = "Short-lived allocations",
            summary = "ایجاد آبجکت‌های لحظه‌ای GC را مجبور به کار مداوم می‌کند و اثر آن در هر دو CPU و Memory Profiler مشخص است.",
            bodyParagraphs = listOf(
                "shortLivedAllocations ساختارهای کوچک زیادی می‌سازد که بلافاصله از بین می‌روند. این همان وضعیتی است که در حلقه‌های Rendering یا Adapter های ضعیف مشاهده می‌کنیم.",
                "هدف این است که بتوانید تعداد GC/minute را اندازه‌گیری کنید و بفهمید کدام بخش اپلیکیشن باعث churn می‌شود."
            ),
            usageSteps = listOf(
                "Memory Profiler را روی بازه کوتاه متمرکز کنید تا GC های پشت سر هم را واضح ببینید.",
                "دکمه \"Short-lived allocations\" را بزنید و بلافاصله Record Allocations را فعال نمایید.",
                "پس از چند ثانیه ضبط را متوقف کنید و در جدول Methods به دنبال سازنده‌هایی بگردید که صدها بار فراخوانی شده‌اند.",
                "گزارشی از تعداد GC و مدت هر Pause تهیه کنید."
            ),
            profilerInsights = listOf(
                "Timeline باید موج دندانه‌ای از افزایش و کاهش Heap نشان دهد؛ اگر خط صاف است سناریو درست اجرا نشده است.",
                "در Panel Allocations می‌توان Stacktrace کامل را Export کرد و برای تیم ارسال نمود.",
                "این سناریو برای مقایسه تاثیر object pooling یا استفاده از inline class ها بسیار مناسب است."
            ),
            links = listOf(
                ScenarioLink(
                    label = "Reduce object allocations",
                    url = "https://developer.android.com/topic/performance/memory-overview",
                    type = ScenarioLinkType.CHROME
                ),
                ScenarioLink(
                    label = "Profile Kotlin allocations",
                    url = "https://www.youtube.com/watch?v=uCpSc4Vqi6Q",
                    type = ScenarioLinkType.YOUTUBE
                )
            )
        ),
        ScenarioIds.NETWORK_BURST to ScenarioInfo(
            id = ScenarioIds.NETWORK_BURST,
            title = "Burst 20 requests",
            summary = "در کمتر از چند ثانیه بیست درخواست HTTP ارسال می‌شود تا بتوانید الگوهای Burst و نحوه صف‌بندی در OkHttp را بررسی کنید.",
            bodyParagraphs = listOf(
                "Network Profiler نمودار throughput را به شکل spikes نشان می‌دهد و می‌توانید مشاهده کنید که آیا همه درخواست‌ها با TLS handshake جداگانه اجرا می‌شوند یا Connection reuse دارید.",
                "این سناریو برای مقایسه با throttling شبکه و تاثیر caching بسیار مفید است."
            ),
            usageSteps = listOf(
                "Network Profiler را باز کرده و ضبط خودکار را فعال بگذارید.",
                "دکمه \"Burst 20 requests\" را بزنید و در جدول درخواست‌ها به سراغ تب 'Request/Response' بروید.",
                "مدت پاسخ هر درخواست، اندازه payload و سربار TLS را یادداشت کنید.",
                "Trace را Export کنید تا بتوانید آن را با تیم بک‌اند به اشتراک بگذارید."
            ),
            profilerInsights = listOf(
                "در Summary نمودارهای هم‌پوشانی درخواست‌ها را بررسی کنید تا ببینید آیا Thread pool اشباع شده است.",
                "با فعال کردن Network Inspector می‌توانید ببینید آیا درخواست‌ها روی HTTP/2 multiplex می‌شوند یا خیر.",
                "System Trace کمک می‌کند تاثیر Burst روی CPU و انرژی را هم‌زمان بسنجید."
            ),
            links = listOf(
                ScenarioLink(
                    label = "Inspect traffic with Network Profiler",
                    url = "https://developer.android.com/studio/profile/network-profiler",
                    type = ScenarioLinkType.CHROME
                ),
                ScenarioLink(
                    label = "Chrome Dev Summit – networking best practices",
                    url = "https://www.youtube.com/watch?v=t4w-0nyrG6Y",
                    type = ScenarioLinkType.YOUTUBE
                )
            )
        ),
        ScenarioIds.NETWORK_POLLING_START to ScenarioInfo(
            id = ScenarioIds.NETWORK_POLLING_START,
            title = "1-second polling",
            summary = "یک Job تکراری به مدت ۶۰ ثانیه هر ثانیه یک درخواست ارسال می‌کند تا بتوانید رفتار long-running polling را مشاهده کنید.",
            bodyParagraphs = listOf(
                "Poll کردن منابع سرور اگر بدون backoff باشد به سرعت باتری را تخلیه می‌کند. با این سناریو تفاوت بین Traffic ثابت و Burst را در Network Profiler خواهید دید.",
                "این رفتار بستر خوبی برای آزمایش WorkManager، JobScheduler یا FCM برای جایگزینی polling است."
            ),
            usageSteps = listOf(
                "قبل از لمس دکمه، Timeline Network Profiler را روی بازه ۱ دقیقه تنظیم کنید.",
                "دکمه \"Start 1-second polling\" را فشار دهید و مطمئن شوید که status روی صفحه تغییر می‌کند.",
                "در Android Studio نمودار throughput را زیر نظر داشته باشید و از بخش Details latency را استخراج کنید.",
                "به کمک Energy Profiler مشاهده کنید که چه مقدار بیدارباش (WakeLock) مصرف می‌شود."
            ),
            profilerInsights = listOf(
                "در نمودار می‌توانید exact period را ببینید؛ اگر jitter دارید یعنی delay یا congestion رخ داده است.",
                "مقایسه با حالت 'Stop polling' نشان می‌دهد چه زمانی شبکه آزاد می‌شود.",
                "تعداد Threads فعال در OkHttpDispatcher نیز باید ثابت بماند؛ افزایش آن نشانه Leak است."
            ),
            links = listOf(
                ScenarioLink(
                    label = "Optimize network requests",
                    url = "https://developer.android.com/topic/performance/network",
                    type = ScenarioLinkType.CHROME
                ),
                ScenarioLink(
                    label = "Why push beats polling",
                    url = "https://www.youtube.com/watch?v=NbuUW9i-MiQ",
                    type = ScenarioLinkType.YOUTUBE
                )
            )
        ),
        ScenarioIds.NETWORK_POLLING_STOP to ScenarioInfo(
            id = ScenarioIds.NETWORK_POLLING_STOP,
            title = "Stop polling",
            summary = "این دکمه Job polling را لغو می‌کند تا بتوانید تفاوت مصرف شبکه و انرژی را قبل و بعد از توقف مشاهده کنید.",
            bodyParagraphs = listOf(
                "Cancel کردن Job باعث می‌شود Dispatcher و Coroutine ها آزاد شوند و باید بلافاصله افت در throughput دیده شود.",
                "این مرحله برای نشان دادن اهمیت Lifecyle-aware بودن عملیات شبکه است؛ بدون cancel ممکن است اپ حتی در پس‌زمینه نیز به polling ادامه دهد."
            ),
            usageSteps = listOf(
                "پس از چند ثانیه polling، دکمه \"Stop polling\" را بزنید.",
                "Timeline Network Profiler را بررسی کنید تا مطمئن شوید دیگر درخواستی ارسال نمی‌شود.",
                "در بخش Threads ببینید آیا هنوز coroutine فعال دارید یا خیر.",
                "از Energy Profiler برای تایید خواب رفتن رادیو استفاده کنید."
            ),
            profilerInsights = listOf(
                "وجود درخواست‌های جدید پس از Stop نشانه Leak یا اشتباه در مدیریت Job است.",
                "System Trace باید نشان دهد که WakeLock آزاد شده است.",
                "در Network Inspector جدولی با وضعیت Canceled = true می‌بینید که تایید می‌کند OkHttp call لغو شده است."
            ),
            links = listOf(
                ScenarioLink(
                    label = "Manage long-running work",
                    url = "https://developer.android.com/topic/performance/power",
                    type = ScenarioLinkType.CHROME
                ),
                ScenarioLink(
                    label = "Job cancellation patterns",
                    url = "https://www.youtube.com/watch?v=BOHK_w09pVA",
                    type = ScenarioLinkType.YOUTUBE
                )
            )
        ),
        ScenarioIds.ENERGY_WAKELOCK to ScenarioInfo(
            id = ScenarioIds.ENERGY_WAKELOCK,
            title = "WakeLock worker",
            summary = "یک WorkManager Job شامل WakeLock و delay مصنوعی اجرا می‌شود تا بتوانید در Energy Profiler مصرف باتری و Background Task Inspector را بررسی کنید.",
            bodyParagraphs = listOf(
                "کارگر DemoWakeLockWorker یک Partial WakeLock درخواست می‌کند و لاگ می‌نویسد تا در System Trace قابل شناسایی باشد.",
                "این سناریو مناسب بررسی این است که آیا WorkManager job constraints رعایت شده و آیا رهاسازی WakeLock به‌موقع انجام می‌شود."
            ),
            usageSteps = listOf(
                "Background Task Inspector را باز کنید و روی WorkManager کلیک کنید.",
                "دکمه \"Enqueue WakeLock worker\" را بزنید و مشاهده کنید job به چه سرعت اجرا می‌شود.",
                "System Trace یا Energy Profiler را ضبط کنید تا WakeLock timeline را ببینید.",
                "پس از اتمام، گزارش مصرف باتری را صادر کنید."
            ),
            profilerInsights = listOf(
                "در Energy Profiler افزایش در CPU و Network در همان بازه زمانی دیده می‌شود.",
                "Background Task Inspector وضعیت موفق/ناموفق و Stacktrace خطاها را ارائه می‌دهد.",
                "استفاده از WakeLock بدون محدودیت زمانی می‌تواند باعث باطری خوری شود، بنابراین حتما زمان‌بندی آزادسازی را بررسی کنید."
            ),
            links = listOf(
                ScenarioLink(
                    label = "Profile energy usage",
                    url = "https://developer.android.com/studio/profile/energy-profiler",
                    type = ScenarioLinkType.CHROME
                ),
                ScenarioLink(
                    label = "WorkManager under the hood",
                    url = "https://www.youtube.com/watch?v=UaY5tpwRf0Y",
                    type = ScenarioLinkType.YOUTUBE
                )
            )
        ),
        ScenarioIds.TRACE_CUSTOM to ScenarioInfo(
            id = ScenarioIds.TRACE_CUSTOM,
            title = "Custom method tracing",
            summary = "Debug.startMethodTracing فایل ‎.trace می‌سازد تا بتوانید آن را در Android Studio باز کرده و زمان دقیق هر متد را اندازه بگیرید.",
            bodyParagraphs = listOf(
                "دکمه موجود در صفحه tracing ابتدا tracing را آغاز می‌کند، سپس حلقه‌ای ۵۰ میلیون‌تایی اجرا کرده و در نهایت فایل custom_trace_demo.trace را در حافظه داخلی ذخیره می‌کند.",
                "باز کردن فایل در Studio (از منوی File > Open) به شما امکان می‌دهد روش سفارشی و مقایسه با Trace های سیستم را یاد بگیرید."
            ),
            usageSteps = listOf(
                "ADB shell یا Device File Explorer را باز کنید و فایل ‎.trace ذخیره‌شده در /sdcard را پیدا کنید.",
                "فایل را در Android Studio باز کنید تا نمایی مشابه Systrace ببینید.",
                "روی متدها زوم کنید و Self Time و Children Time را با هم مقایسه نمایید.",
                "نتیجه را با Trace تولیدشده توسط Perfetto یا System Trace مقایسه کنید تا بفهمید چه زمانی ابزار سفارشی مناسب است."
            ),
            profilerInsights = listOf(
                "Method tracing overhead بیشتری نسبت به Sampled trace دارد؛ لذا فقط در بازه‌های کوتاه استفاده کنید.",
                "با افزودن Debug.startMethodTracingSampling می‌توانید حجم فایل را کم کنید.",
                "فایل خروجی را به هم‌تیمی‌ها بدهید تا بدون نیاز به اجرای سناریو بتوانند مشکل را بازتولید کنند."
            ),
            links = listOf(
                ScenarioLink(
                    label = "Add custom trace events",
                    url = "https://developer.android.com/topic/performance/tracing/custom-event",
                    type = ScenarioLinkType.CHROME
                ),
                ScenarioLink(
                    label = "Perfetto and tracing on Android",
                    url = "https://www.youtube.com/watch?v=x4vGJ8Qw0uE",
                    type = ScenarioLinkType.YOUTUBE
                )
            )
        )
    )

    fun getScenarioInfo(id: String?): ScenarioInfo? = data[id]
}
