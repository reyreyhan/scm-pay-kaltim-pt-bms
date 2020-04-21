# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class kotlin.* { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}


-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
#-keep class com.bumptech.glide.integration.okhttp.OkHttpGlideModule
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule
-keep public class * implements java.lang.annotation.Annotation
-keep class com.google.android.* { *; }
############ Crashlytics ################################
#-keepattributes *Annotation*
#-keep class com.crashlytics.** { *; }
#-dontwarn com.crashlytics.**
#-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-printmapping mapping.txt

-keep class com.crashlytics.* { *; }
-dontwarn com.crashlytics.**

-keep class com.google.android.gms.measurement.* { *; }
-dontwarn com.google.android.gms.measurement.**

-keep class com.google.android.gms.measurement.AppMeasurement.* { *; }
#-keep class com.google.android.gms.measurement.AppMeasurement$OnEventListener { *; }

#=========minify=========#


-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable

-keep public class * extends android.support.v4.app.*
-keep public class * extends android.support.v7.app.*
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application


-dontwarn com.google.android.material.**
-keep class com.google.android.material.* { *; }

-dontwarn androidx.**
-keep class androidx.* { *; }
-keep interface androidx.* { *; }


-dontwarn javax.annotation.**
-dontwarn android.annotation.**

-dontwarn android.app.**
-dontwarn android.support.**
-dontwarn android.view.**
-dontwarn android.widget.**
-dontwarn android.animation.**
-dontwarn android.graphics.**

-dontwarn com.google.common.primitives.**

-dontwarn **CompatHoneycomb
-dontwarn **CompatHoneycombMR2
-dontwarn **CompatCreatorHoneycombMR2


#-dontwarn org.apache.commons.**
#-keep class org.apache.http.** { *; }
#-dontwarn org.apache.http.**
#-dontwarn com.squareup.okhttp.**
#-dontwarn okio.**

#-dontwarn org.apache.commons.**
#-keep class org.apache.http.** { *; }
#-dontwarn org.apache.http.**

-dontwarn okhttp3.internal.platform.ConscryptPlatform


#// org.apache
-dontwarn org.apache.**
#// Jackson
-keep class com.fasterxml.jackson.databind.ObjectMapper {
    public <methods>;
    protected <methods>;
}
-keep class com.fasterxml.jackson.databind.ObjectWriter {
    public ** writeValueAsString(**);
}
-keepnames class com.fasterxml.jackson.* { *; }
-dontwarn com.fasterxml.jackson.databind.**
#//OkHttp
-dontwarn com.squareup.**
#// Picasso
-dontwarn com.squareup.picasso.**
#//Okio
-dontwarn okio.**
#//Joda Time Convert
-dontwarn org.joda.convert.**

#//Youtube
-dontwarn com.google.**

#//open mobile
-dontwarn org.simalliance.openmobileapi.**
#//pdf_ium_core
#-dontwarn com.shockwave.pdfium.**


-keep class com.bm.main.fpl.*.* {*;}
#-keep class com.shockwave.pdfium.** {*;}
-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,LineNumberTable, *Annotation*, EnclosingMethod
-dontwarn android.webkit.JavascriptInterface

#//GWT
-dontwarn com.googlecode.**

-keepclasseswithmembernames class * {
 native <methods>;
}

-keepclasseswithmembers class * {
 public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
 public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
 public void *(android.view.View);
}
-keepclassmembers class * extends android.support.v7.app.* {
 public void *(android.view.View);
}
-keepclassmembers class * extends android.support.v4.app.* {
 public void *(android.view.View);
}
-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable.* {
*;
# public static final android.os.Parcelable$Creator.*;
}

-keepclassmembers class **.R$* {
 public static <fields>;
}

-keep public class net.sqlcipher.* {
 *;
}

-keep public class net.sqlcipher.database.* {
 *;
}

########--------Retrofit + RxJava--------#########
-dontwarn retrofit2.*
-keep public class retrofit2.* { *; }
-dontwarn sun.misc.Unsafe
-dontwarn com.octo.android.robospice.retrofit.RetrofitJackson*
-dontwarn retrofit2.appengine.UrlFetchClient
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep public class com.google.gson.* { *; }
-keep public class com.google.inject.* { *; }
-keep public class org.apache.http.* { *; }
-keep public class org.apache.james.mime4j.* { *; }
-keep public class javax.inject.* { *; }
-dontwarn org.apache.http.*
-dontwarn android.net.http.AndroidHttpClient
-dontwarn retrofit2.*

-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
#   long producerNode;
#   long consumerNode;
#}

-keep class com.bm.main.pos.* { *; }

-keep class com.bm.main.pos.models.* { *; }

-keep public class * extends java.io.IOException
-dontwarn com.bm.main.pos.models.*
-dontwarn com.bm.main.pos.rest.*

-keep public class android.net.http.* { *; }

-keep class !com.bm.main.* { *; }

-keepnames @kotlin.Metadata class com.bm.main.pos.*

-keepclassmembers class * {
    @com.squareup.moshi.FromJson <methods>;
    @com.squareup.moshi.ToJson <methods>;
}

#### OkHttp, Retrofit and Moshi
-dontwarn okhttp3.**
-dontwarn retrofit2.Platform$Java8
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier interface *
-dontwarn org.jetbrains.annotations.**
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

-keepclassmembers class * {
    @com.squareup.moshi.FromJson <methods>;
    @com.squareup.moshi.ToJson <methods>;
}

-keepnames @kotlin.Metadata class com.bm.main.pos.models*
-keep class com.bm.main.pos.models* { *; }
-keepclassmembers class com.bm.main.pos.models* { *; }