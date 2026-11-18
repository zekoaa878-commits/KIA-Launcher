# KIA Launcher Lite - ac8227l

مواصفات مستهدفة: Android 8.1, ac8227l, 1080x600, RAM 2G, ELM327 White Clone

## خطوات التشغيل على الشاشة
1. اقتران OBDII من إعدادات البلوتوث PIN 1234 / 0000 / 6789
2. قطع الاتصال من النظام واتركه مقترن فقط
3. ثبّت APK واختره كـ Launcher افتراضي
4. دوس اتصال OBD في التطبيق

## البناء
```bash
export ANDROID_HOME=/path/to/android-sdk
bash build.sh # الناتج: KIA-Launcher-Lite-ac8227l.apk موقّع
```

## PIDs
010C RPM (256A+B)/4, 010D Speed, 0105 Coolant A-40, 012F Fuel, 0104 Load, 0111 Throttle, 010F Intake

## تعديلات Lite
- تحديث كل 600ms
- ATZ + انتظار 1000ms للنسخ المضروبة
- Foreground Service START_STICKY ضد قتل ac8227l
- Landscape ثابت + KeepScreenOn
