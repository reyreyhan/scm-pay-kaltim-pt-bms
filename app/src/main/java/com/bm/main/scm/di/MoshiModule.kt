package com.bm.main.scm.di

import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.lang.reflect.Type
import java.util.*
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object MoshiModule {

//    @Reusable
    @Provides
    @Singleton
    fun instance(): Moshi {
        return Moshi.Builder()
            .add(object : JsonAdapter.Factory {
                override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? =
                    try {
                        moshi.nextAdapter<Any>(this, type, annotations).lenient()
                    } catch (_: Exception) {
                        moshi.nextAdapter<Any>(this, type, annotations).lenient()
                    }
            })
            // add adapter to parse single object to list
            .add(object : JsonAdapter.Factory {
                override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<Any>? {
                    val delegateAnnotations = Types.nextAnnotations(annotations, ObjectToList::class.java)
                        ?: return null
                    if (Types.getRawType(type) != List::class.java) throw IllegalArgumentException("Only lists may be annotated with @SingleToArray. Found: $type")
                    val elementType = Types.collectionElementType(type, List::class.java)
                    val delegateAdapter: JsonAdapter<List<Any>> = moshi.adapter(type, delegateAnnotations)
                    val elementAdapter: JsonAdapter<Any> = moshi.adapter(elementType)

                    return ObjectToListAdapter(delegateAdapter, elementAdapter)
                }
            })
            .add(StringToIntAdapter())
            .add(DoubleToIntAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @JsonQualifier
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class StringToInt

    internal class StringToIntAdapter {
        @FromJson
        @StringToInt
        fun fromJson(value: String): Int = try {
            value.toInt()
        } catch (_: Exception) {
            0
        }

        @ToJson
        fun toJson(@StringToInt value: Int): String = value.toString()
    }

    @JsonQualifier
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class DoubleToInt

    internal class DoubleToIntAdapter {
        @FromJson
        @DoubleToInt
        fun fromJson(value: Double): Int = try {
            value.toInt()
        } catch (_: Exception) {
            0
        }

        @ToJson
        fun toJson(@DoubleToInt value: Int): Double = value.toDouble()
    }

    @JsonQualifier
    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.FIELD)
    internal annotation class ObjectToList

    internal class ObjectToListAdapter(
            val delegateAdapter: JsonAdapter<List<Any>>,
            val elementAdapter: JsonAdapter<Any>
    ) : JsonAdapter<Any>() {

        override fun fromJson(reader: JsonReader): Any? =
            if (reader.peek() != JsonReader.Token.BEGIN_ARRAY) {
                Collections.singletonList(elementAdapter.fromJson(reader))
            } else delegateAdapter.fromJson(reader)

        override fun toJson(writer: JsonWriter, value: Any?) {}
    }
}