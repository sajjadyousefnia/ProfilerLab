# ProfilerLab (`com.sajjady.profilerlab`)

A hands-on Android Studio Profiler lab app that puts **CPU**, **Memory**, **Network**, **Energy / Background** and **Custom tracing** scenarios into a single project.

This app is intentionally full of **good and bad patterns** (blocking main thread, memory leaks, burst HTTP calls, wake locks, allocation storms) to help you _see_ them clearly in the Profiler.

---

- [English](#english)
- [فارسی](#فارسی-persian)

---

## Image placeholders (replace later)

> ℹ️ Replace `https://example.com/...` with your real GitHub raw URLs or relative paths later.

![Profiler overview](https://example.com/docs/images/profiler-overview.png)
![CPU flame chart vs top-down](https://example.com/docs/images/cpu-flame-vs-topdown.png)
![Memory leak vs normal lifecycle](https://example.com/docs/images/memory-leak-vs-normal.png)
![Network burst vs polling](https://example.com/docs/images/network-burst-vs-polling.png)
![WakeLock & Background Task Inspector](https://example.com/docs/images/wakelock-background-task-inspector.png)
![Custom trace flow](https://example.com/docs/images/custom-trace-flow.png)
![ProfilerLab home screen](https://example.com/docs/images/profilerlab-home.png)

---

## English

<details open>
<summary>Click to collapse/expand English section</summary>

### 1. Goals

ProfilerLab is designed to help you:

- Understand what the **Android Studio Profiler** is actually showing.
- Map **real code** to **Profiler timelines** (CPU, memory, network, energy).
- Practice reading:
  - CPU **Flame Chart**, **Top Down** and **Bottom Up** views.
  - Memory **heap dumps**, **allocation records** and **leak patterns**.
  - Network **bursts vs polling**.
  - Background work and **wake locks** in **System Trace** and **Background Task Inspector**.
  - Custom `.trace` files from `Debug.startMethodTracing`.

Application ID: **`com.sajjady.profilerlab`** (prefix `com.sajjady.*` as requested).

---

### 2. Features at a glance

- **CPU Profiler**
  - Freeze UI with a busy loop on main thread.
  - Heavy CPU computation on background threads (from a **separate module** `:heavyscenarios`).
  - Allocation storm to trigger **GC** and CPU activity.

- **Memory Profiler**
  - Deliberate **Activity / Context leak** via `LeakyHolder`.
  - **Bitmap RAM usage** scenario.
  - Short-lived allocation churn for **GC analysis**.

- **Network Profiler**
  - **Burst** of ~20 HTTP requests.
  - **1-second polling** pattern with cancel support.

- **Energy / Background**
  - `WorkManager` job that holds a **partial `WakeLock`** for a short time.
  - Designed to be visible in **System Trace** and **Background Task Inspector**.

- **Custom tracing**
  - `Debug.startMethodTracing("custom_trace_demo")` + heavy loop → `.trace` file you can open in Android Studio.

- **Event timeline**
  - Every screen is interactive so you can correlate **user input**, **lifecycle events** and **resource usage**.

---

### 3. Project structure

```text
ProfilerLab/
 ├─ settings.gradle.kts
 ├─ build.gradle.kts
 ├─ app/
 │   ├─ build.gradle.kts
 │   └─ src/main/
 │       ├─ AndroidManifest.xml
 │       ├─ java/com/sajjady/profilerlab/
 │       │   ├─ MainActivity.kt
 │       │   ├─ ProfilerLabApp.kt (optional Application)
 │       │   ├─ navigation/ProfilerNavHost.kt
 │       │   ├─ ui/HomeScreen.kt
 │       │   ├─ cpu/CpuScenariosScreen.kt
 │       │   ├─ memory/MemoryScenariosScreen.kt
 │       │   ├─ memory/LeakyActivity.kt
 │       │   ├─ memory/MemoryAllocations.kt
 │       │   ├─ network/NetworkScenariosScreen.kt
 │       │   ├─ network/NetworkApi.kt
 │       │   ├─ energy/EnergyScenariosScreen.kt
 │       │   ├─ energy/DemoWakeLockWorker.kt
 │       │   └─ tracing/CustomTraceScreen.kt
 │       └─ res/layout/activity_leaky.xml
 └─ heavyscenarios/
     ├─ build.gradle.kts
     └─ src/main/java/com/sajjady/heavyscenarios/HeavyMath.kt
```

---

### 4. Requirements & setup

- **Android Studio:** Giraffe or newer (any version with the modern Profiler UI).
- **Gradle plugin:** 8.x  
- **Kotlin:** 1.9.x  
- **minSdk:** 24  
- **targetSdk:** 34 (you can update to latest)

#### Clone & open

```bash
git clone <your-repo-url>.git
cd ProfilerLab
```

Open the project in Android Studio, let Gradle sync, then:

1. Select the **`app`** run configuration.
2. Make sure the **build variant** is `debug` (Profiler needs debuggable builds).
3. Run on a **physical device** or emulator with Google APIs.

---

### 5. Attaching the Profiler

1. In Android Studio, go to **View ▸ Tool Windows ▸ Profiler**.
2. Select your connected device.
3. Choose the **ProfilerLab** process (`com.sajjady.profilerlab`).
4. Click on the track you want to record:
   - CPU
   - Memory
   - Network
   - System Trace / Energy (depending on Studio version)
5. Press record, then interact with the app’s screens described below.

---

### 6. CPU Profiler scenarios

**Screen:** `CPU Profiler Scenarios`

Buttons:

1. **Freeze UI (Block Main Thread 2.5s)**  
   - Starts a tight busy loop on the **main (UI) thread** for ~2.5 seconds.
   - **How to use:**
     1. Start a **System trace** recording.
     2. Tap the **Freeze UI** button.
   - **What to look for:**
     - Main thread shows a long, solid block of work.
     - UI feels frozen and unresponsive.
     - In the Threads view, see the main thread stuck in your loop method.

2. **Heavy CPU on Background (from `:heavyscenarios`)**  
   - Calls `HeavyMath.calculatePiWithLeibniz(30_000_000)` on a **background dispatcher**.
   - Code lives in a **separate module** `:heavyscenarios` so you can see method names from another module in the trace.
   - **How to use:**
     1. Record **CPU sample** or **System trace**.
     2. Tap the button.
   - **What to look for:**
     - High CPU usage on a **worker thread**, not on main.
     - Flame Chart shows deep stack from `com.sajjady.heavyscenarios`.

3. **Allocation Storm (CPU + GC)**  
   - Repeatedly allocates large lists to force **GC**.
   - **How to use:**
     1. Record both **CPU** and **Memory**.
     2. Tap **Allocation Storm** and let it run.
   - **What to look for:**
     - Frequent **GC events** in Memory Profiler.
     - CPU time spent in GC / runtime methods.

---

### 7. Memory Profiler scenarios

**Screen:** `Memory Profiler Scenarios`

Buttons:

1. **Open LeakyActivity (Activity/Context leak)**  
   - Opens `LeakyActivity`, which:
     - Stores `this` (the Activity) in a singleton `LeakyHolder`.
     - Optionally allocates large Bitmaps and keeps them in a static list.
   - **How to use:**
     1. Start Memory Profiler.
     2. Tap **Open LeakyActivity**.
     3. Inside LeakyActivity, tap **Allocate big bitmap** a few times.
     4. Press **Back** to finish the Activity.
     5. Take a **heap dump**.
   - **What to look for:**
     - `LeakyActivity` instances are still alive.
     - Filter by **"Activity/Fragment Leaks"** and inspect **Retaining Paths**.

2. **Allocate many Bitmaps**  
   - Allocates a batch of large `Bitmap`s in a local list.
   - **How to use:**
     1. Record in Memory Profiler.
     2. Tap **Allocate many Bitmaps**.
   - **What to look for:**
     - Memory graph jumps up as Bitmaps are created.
     - After some time or orientation change, observe how/if memory goes down.

3. **Short-lived allocations (GC churn)**  
   - Repeats allocation of big collections + small delay.
   - **How to use:**
     1. Enable **Record Java/Kotlin allocations**.
     2. Tap **Short-lived allocations (GC churn)**.
   - **What to look for:**
     - Lots of allocation entries for list types.
     - Many small GC events rather than one big one.

---

### 8. Network Profiler scenarios

**Screen:** `Network Profiler Scenarios`  
Uses Retrofit + OkHttp to call `https://jsonplaceholder.typicode.com/posts`.

Buttons:

1. **Burst 20 requests**  
   - Sends ~20 requests in quick succession.
   - **How to use:**
     1. Open **Network** track, start recording.
     2. Tap **Burst 20 requests** once.
   - **What to look for:**
     - Dense group of HTTP calls on the timeline.
     - All to the same endpoint but potentially overlapping in time.

2. **Start 1-second polling / Stop polling**  
   - Starts a coroutine that calls the API every ~1 second.
   - **How to use:**
     1. Start Network recording.
     2. Tap **Start 1-second polling** and wait ~20–30 seconds.
     3. Tap **Stop polling**.
   - **What to look for:**
     - Clear, regular pattern of network activity.
     - After stopping, the pattern ends abruptly.

---

### 9. Energy & background work

**Screen:** `Energy & Background Scenarios`

Button:

- **Enqueue WakeLock worker (1 run)**  
  - Enqueues a `DemoWakeLockWorker`:
    - Acquires a `PARTIAL_WAKE_LOCK` for a few seconds.
    - Simulates light work with `delay`.
  - **How to use:**
    1. Open **Background Task Inspector** in Android Studio.
    2. Start a **System trace** recording.
    3. Tap **Enqueue WakeLock worker**.
  - **What to look for:**
    - In Background Task Inspector: a `DemoWakeLockWorker` run entry.
    - In System trace / Power profiler: CPU and wakefulness during the worker.

> ⚠️ This is for educational purposes only.  
> Do **not** hold wake locks like this in production apps.

---

### 10. Custom tracing (`Debug.startMethodTracing`)

**Screen:** `Custom Trace (Debug.startMethodTracing)`

Button:

- **Run traced heavy computation**  
  - Wraps a heavy loop between:
    - `Debug.startMethodTracing("custom_trace_demo")`
    - `Debug.stopMethodTracing()`
  - **How to use:**
    1. Tap the button and wait for the status text to say trace is saved.
    2. Locate the `.trace` file (usually under `/sdcard/Android/data/<app>/files` or app’s internal dir; path may vary).
    3. In Android Studio, use **File ▸ Open** or **Profile or debug APK / file** to open `custom_trace_demo.trace`.
  - **What to look for:**
    - Method-level timing info independent from live Profiler recording.
    - A snapshot of just this operation.

---

### 11. Event timeline

For *any* screen:

- While recording CPU/Memory/Network:
  - Tap buttons, scroll lists, rotate device, background/foreground the app.
- In the Profiler’s **event timeline**:
  - See touch events, lifecycle callbacks, orientation changes.
  - Correlate peaks in CPU/memory/network with user actions or lifecycle transitions.

---

### 12. Tips & exercises

Try these extra exercises:

- Change the number of iterations in `HeavyMath` and see how the flame chart evolves.
- Add another button that runs heavy CPU **on the main thread** (for comparison).
- Modify `DemoWakeLockWorker` to remove the wake lock and compare traces.
- Add your own **I/O-bound** scenario (e.g., reading/writing files) and profile it.

---

### 13. Image prompts for **Nano banana**

The prompts below are optimized for **Nano banana** models (e.g. `imagen-3.0-nano-banana`).  
Use them as-is or adapt them when generating images for this README.

> **General style hint for all prompts:**  
> _“Use a clean flat illustration style, soft rounded shapes, high contrast but not neon, light IDE-like background, minimal text labels, high resolution, 16:9 aspect ratio.”_

1. **Android Studio Profiler overview**

   ```text
   Model: Nano banana (imagen-3.0-nano-banana)

   Prompt:
   "Clean flat illustration of Android Studio Profiler with four horizontal tracks labeled CPU, Memory, Network, and System Trace/Energy. Show a laptop screen with an IDE-like dark theme, simple colorful graphs on each track, and small icons for CPU, RAM, network, and battery. Use soft rounded shapes, modern flat style, high resolution, 16:9 aspect ratio, minimal UI text."
   ```

2. **CPU Flame Chart vs Top-Down view**

   ```text
   Model: Nano banana (imagen-3.0-nano-banana)

   Prompt:
   "Split-screen diagram: on the left a CPU flame chart with stacked colorful blocks forming a hot path, on the right a top-down table of methods with one row highlighted. Style it as a flat UI mockup of a profiler tool. Use a dark IDE background, clear labels 'Flame Chart' and 'Top Down' in small text, clean lines, soft corners, 16:9."
   ```

3. **Memory leak vs normal lifecycle**

   ```text
   Model: Nano banana (imagen-3.0-nano-banana)

   Prompt:
   "Two-panel infographic about Android Activity memory behavior. Left panel: normal lifecycle, an Activity box is created, destroyed, then garbage collected, with a green check icon. Right panel: memory leak, the Activity box is still held by a singleton reference, marked with a red 'Leaked' tag. Use arrows with short labels like 'Create', 'Destroy', 'GC', 'Held by singleton'. Flat illustration, simple colors, white background, 16:9."
   ```

4. **Network burst vs polling timeline**

   ```text
   Model: Nano banana (imagen-3.0-nano-banana)

   Prompt:
   "Timeline diagram comparing two HTTP traffic patterns. On the top row show a dense burst of many vertical bars close together labeled 'Burst'. On the bottom row show evenly spaced vertical bars labeled '1s polling'. Use subtle grid lines for time, small icons of clouds or servers at the ends of the lines. Flat design, high resolution, 16:9, minimal text."
   ```

5. **WakeLock & Background Task Inspector**

   ```text
   Model: Nano banana (imagen-3.0-nano-banana)

   Prompt:
   "Illustration of an Android phone staying awake due to a wake lock. Draw a small worker character holding a lock icon near the phone. Next to it, show a simplified window of 'Background Task Inspector' with one row highlighted named 'DemoWakeLockWorker'. Use soft pastel colors, light background, flat UI style, 16:9 aspect ratio."
   ```

6. **Custom trace flow**

   ```text
   Model: Nano banana (imagen-3.0-nano-banana)

   Prompt:
   "Step-by-step flow diagram showing custom tracing with Debug.startMethodTracing in Android. Use 6 boxes connected by arrows: 'Button tap' → 'startMethodTracing' → 'Heavy computation' → 'stopMethodTracing' → '.trace file' → 'Open in Android Studio'. Each box has a small icon (finger tap, code brackets, CPU, file, IDE). Flat modern style, light background, clear readable labels, 16:9."
   ```

7. **ProfilerLab app home screen**

   ```text
   Model: Nano banana (imagen-3.0-nano-banana)

   Prompt:
   "Mockup of a simple Android app home screen named 'ProfilerLab' showing cards for CPU Profiler, Memory Profiler, Network Profiler, Energy & Background, and Custom Tracing. Use Material 3-like design, rounded cards, subtle shadows, English and Persian titles on each card. Clean flat style, 16:9, minimal but realistic UI."
   ```

You can export the generated images and embed them in this README with standard Markdown (update the URLs to your own paths):

```markdown
![Profiler overview](https://example.com/docs/images/profiler-overview.png)
```

</details>

---

## فارسی (Persian)

<details>
<summary>برای مشاهده توضیحات فارسی کلیک کن</summary>

### ۱. هدف پروژه

**ProfilerLab** یک اپ آزمایشگاهی برای یادگیری عملی **Android Studio Profiler** است. با این اپ می‌تونی:

- بفهمی هر نمودار و timeline دقیقاً چه چیزی را نشان می‌دهد.
- بین **کد واقعی** و **رفتار روی نمودار** ارتباط برقرار کنی.
- انواع سناریوهای رایج و پرخطا را ببینی:
  - بلاک شدن Main Thread
  - کارهای سنگین روی Threadهای پس‌زمینه
  - Memory Leak از Activity/Context
  - مصرف زیاد حافظه توسط Bitmapها
  - Burst و Polling در شبکه
  - WakeLock و کارهای پس‌زمینه
  - Trace سفارشی با `Debug.startMethodTracing`

شناسه برنامه: **`com.sajjady.profilerlab`** (با پیشوند `com.sajjady.*`).

---

### ۲. ویژگی‌ها به صورت خلاصه

- **CPU Profiler**
  - Freeze کردن UI با حلقه‌ی سنگین روی Main Thread.
  - محاسبات سنگین روی Threadهای پس‌زمینه (از ماژول جدا `:heavyscenarios`).
  - Allocation‌ سنگین برای ایجاد GCهای زیاد.

- **Memory Profiler**
  - Leak عمدی `LeakyActivity` از طریق Singleton.
  - تست مصرف RAM با Bitmapهای بزرگ.
  - تخصیص‌های کوتاه‌عمر و GCهای متوالی.

- **Network Profiler**
  - Burst چندین درخواست پشت سر هم.
  - Polling با فاصله‌ی ۱ ثانیه و قابلیت Stop.

- **Energy / Background**
  - Worker که چند ثانیه WakeLock نگه می‌دارد.
  - قابل مشاهده در System Trace و Background Task Inspector.

- **Trace سفارشی**
  - تولید فایل `.trace` با `Debug.startMethodTracing` و بررسی آن در Android Studio.

---

### ۳. نحوه‌ی استفاده‌ی کلی

۱. پروژه را در Android Studio باز کن.  
۲. روی Build Variant `debug` اپ را روی یک دستگاه (واقعی یا امولاتور) اجرا کن.  
۳. از منوی **View ▸ Tool Windows ▸ Profiler**، Profiler را باز کن.  
۴. پروسه‌ی **ProfilerLab** را انتخاب کن.  
5. روی ترک مورد نظر (CPU، Memory، Network، System Trace) کلیک و ضبط را شروع کن.  
۶. داخل اپ، بسته به سناریو، روی دکمه‌های هر صفحه کلیک کن و نتیجه را در Profiler ببین.

---

### ۴. سناریوهای مهم (خلاصه)

#### ۴.۱. CPU

- **Freeze UI** → برای دیدن Jank و بلوک شدن Main Thread.  
- **Heavy CPU on Background** → تفاوت Threadهای پس‌زمینه با Main Thread.  
- **Allocation Storm** → ترکیب مصرف CPU و GC.

#### ۴.۲. Memory

- **LeakyActivity** → مثال واضح از Memory Leak در Activity.  
- **Allocate many Bitmaps** → مشاهده‌ی مصرف RAM توسط Bitmap.  
- **Short-lived allocations** → نمونه‌ای از churn شدید در تخصیص حافظه.

#### ۴.۳. Network

- **Burst 20 requests** → الگوی Burst در نمودار شبکه.  
- **Start 1-second polling** → الگوی Polling زمان‌بندی شده.

#### ۴.۴. Energy / Background

- **WakeLock Worker** → تاثیر WakeLock و WorkManager روی System Trace و انرژی.

#### ۴.۵. Trace سفارشی

- **Run traced heavy computation** → تولید فایل `.trace` مستقل از ضبط زنده Profiler.

---

### ۵. نکات پیشنهادی برای تمرین

- تعداد iterationها و اندازه‌ی داده‌ها را عوض کن و دوباره پروفایل بگیر.  
- یک سناریوی I/O (مثل خواندن/نوشتن فایل) اضافه کن.  
- معادل هر سناریو را یک‌بار با الگوی درست (مثلاً بدون Leak، بدون WakeLock) بساز و نتایج را مقایسه کن.

---

### ۶. پرامپت‌های تصویری (Nano banana)

در بخش انگلیسی، برای هر موضوع (Overview، CPU، Memory، Network، Energy، Trace و Home Screen) یک پرامپت مخصوص **Nano banana** نوشته شده.  
همان‌ها را می‌توانی مستقیم به مدل تصویری بدهی و خروجی را در README قرار بدهی.

</details>
