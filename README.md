# Progress Circle for Android

![JitPack](https://img.shields.io/jitpack/version/com.github.bornholdtleegmbh/android-progress-circle?color=%2344cc11&style=for-the-badge)
![License](https://img.shields.io/github/license/bornholdtleegmbh/android-progress-circle?color=%230087ff&style=for-the-badge)
![Language](https://img.shields.io/github/languages/top/bornholdtleegmbh/android-progress-circle?color=%23875dff&style=for-the-badge)

This library is providing the progress circle as a Composable and XML-View.

### Look
...

### How to use (Compose)
...

### How to use (XML)
```xml
<de.bornholdtlee.progress_circle.view.ProgressCircleView
    android:id="@+id/progress_circle_view"
    android:layout_width="100dp"
    android:layout_height="100dp"
    app:pcv_colorBackground="@color/primaryVariant"
    app:pcv_colorProgress="@color/primary"
    app:pcv_progress="10"
    app:pcv_strokeWidthBackground="12"
    app:pcv_strokeWidthProgress="8" />
```
If you don't want to draw a background circle behind the progress indicator, just pass `@android:color/transparent` to the `app:pcv_colorBackground` paremter in your XML.
