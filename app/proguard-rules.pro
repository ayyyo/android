## app level
-keepattributes EnclosingMethod

## butter knife
-keep public class * implements butterknife.Unbinder { public <init>(...); }
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

## picasso
-dontwarn com.squareup.okhttp.**

## Timber
-dontwarn org.jetbrains.annotations.**

## Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.quezx.manage.model.** { *; }

## Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

## google classes
-keep class com.google.**
-dontwarn com.google.**

## crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

## Guava 19.0
-dontwarn java.lang.ClassValue
-dontwarn com.google.j2objc.annotations.Weak
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

## Eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

## branch.io
-keep class com.google.android.gms.ads.identifier.** { *; }