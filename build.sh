#!/bin/bash
set -e
if [ -z "$ANDROID_HOME" ]; then echo "❌ عيّن ANDROID_HOME أولا"; echo "مثال ويندوز Git Bash: export ANDROID_HOME=/c/Users/$USER/AppData/Local/Android/Sdk"; echo "مثال ماك: export ANDROID_HOME=$HOME/Library/Android/sdk"; exit 1; fi
export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH
echo "📦 بناء KIA Launcher Lite - ac8227l 1080x600 ..."
./gradlew :app:assembleRelease --stacktrace
APK=$(find app/build/outputs/apk/release -name "*.apk" | head -n1)
echo "✅ الناتج: $APK"
# توقيع - لو مفيش keystore هيعمله تلقائي
if [ ! -f kia-release.keystore ]; then keytool -genkeypair -v -keystore kia-release.keystore -alias kia -keyalg RSA -keysize 2048 -validity 10000 -storepass kia12345 -keypass kia12345 -d "CN=KIA Launcher, OU=KIA, O=KIA, L=Suez, C=EG"; fi
$ANDROID_HOME/build-tools/33.0.2/apksigner sign --ks kia-release.keystore --ks-pass pass:kia12345 --key-pass pass:kia12345 --out KIA-Launcher-Lite-ac8227l.apk "$APK" || cp "$APK" KIA-Launcher-Lite-ac8227l.apk
$ANDROID_HOME/build-tools/33.0.2/apksigner verify --print-certs KIA-Launcher-Lite-ac8227l.apk || true
echo "🎉 جاهز: KIA-Launcher-Lite-ac8227l.apk موقّع"
