

package fail.toepic.preferencedelegate

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


typealias contextProvider = ()-> Context?

open class PreferenceProvider(private val name: String?=null, val contextProvider: contextProvider){
    open val preference : SharedPreferences?
        get() = if(name != null){
            contextProvider.invoke()?.getSharedPreferences(name,0)
        }else{
            contextProvider.invoke().let {
                PreferenceManager.getDefaultSharedPreferences(it)
            } ?: null
        }
}

/**  논 스테틱 프로바이더와 다른 점은 프리퍼런스가 최초에  한번만 생성 되고 재생성 되지 않음 */
@Suppress("unused")
class StaticPreferenceProvider(name: String?=null, contextProvider: contextProvider){
    val preference : SharedPreferences?
        = if(name != null){
            contextProvider.invoke()?.getSharedPreferences(name,0)
        }else{
            contextProvider.invoke().let {
                PreferenceManager.getDefaultSharedPreferences(it)
            } ?: null
        }
}

interface PreferenceDelegate<T : PreferenceProvider,R> : ReadWriteProperty<T,R?>

abstract class PreferenceDelegateImp<T : PreferenceProvider,R>(private val propertyName : String?=null, val default : R) : PreferenceDelegate<T,R?> {
    override fun getValue(thisRef: T, property: KProperty<*>): R? {
        val name = propertyName ?: property.name

        return getValueImp(thisRef.preference, name) ?: default
    }

    abstract fun PreferenceDelegateImp<T, R>.getValueImp(preferences: SharedPreferences?, name: String): R

    override fun setValue(thisRef: T, property: KProperty<*>, value: R?) {
        val name = propertyName ?: property.name
        thisRef.preference?.edit {
            setValueImp(name, value)
        }
    }

    abstract fun SharedPreferences.Editor.setValueImp(name: String, value: R?)
}


class StringPreferenceDelegate<T : PreferenceProvider>(propertyName : String?=null, default : String="") : PreferenceDelegateImp<T,String>(propertyName,default){
    override fun PreferenceDelegateImp<T, String>.getValueImp(preferences: SharedPreferences?, name: String): String {
        return preferences?.getString(name,default) ?: default
    }

    override fun SharedPreferences.Editor.setValueImp(name: String, value: String?) {
        putString(name,value)
    }
}

//boolean
class BooleanPreferenceDelegate<T : PreferenceProvider>(
    propertyName : String?=null,
    default : Boolean=false
) : PreferenceDelegateImp<T,Boolean>(propertyName,default){

    override fun PreferenceDelegateImp<T, Boolean>.getValueImp(preferences: SharedPreferences?, name: String): Boolean {
        return preferences?.getBoolean(name,default) ?: default
    }

    override fun SharedPreferences.Editor.setValueImp(name: String, value: Boolean?) {
        if (value == null) {
            remove(name)
        }else{
            putBoolean(name,value)
        }
    }
}

//float
class FloatPreferenceDelegate<T : PreferenceProvider>(
    propertyName : String?=null,
    default : Float=0F
) : PreferenceDelegateImp<T,Float>(propertyName,default) {
    override fun PreferenceDelegateImp<T, Float>.getValueImp(
        preferences: SharedPreferences?,
        name: String
    ): Float {
        return preferences?.getFloat(name,default) ?: default
    }

    override fun SharedPreferences.Editor.setValueImp(name: String, value: Float?) {
        if (value == null) {
            remove(name)
        }else{
            putFloat(name,value)
        }
    }
}

// int
class IntPreferenceDelegate<T : PreferenceProvider>(
    propertyName : String?=null,
    default : Int=0
) : PreferenceDelegateImp<T,Int>(propertyName,default) {
    override fun PreferenceDelegateImp<T, Int>.getValueImp(
        preferences: SharedPreferences?,
        name: String
    ): Int {
        return preferences?.getInt(name,default) ?: default
    }

    override fun SharedPreferences.Editor.setValueImp(name: String, value: Int?) {
        if (value == null) {
            remove(name)
        }else{
            putInt(name,value)
        }
    }
}

//long
class LongPreferenceDelegate<T : PreferenceProvider>(
    propertyName : String?=null,
    default : Long=0L
) : PreferenceDelegateImp<T,Long>(propertyName,default) {
    override fun PreferenceDelegateImp<T, Long>.getValueImp(
        preferences: SharedPreferences?,
        name: String
    ): Long {
        return preferences?.getLong(name,default) ?: default
    }

    override fun SharedPreferences.Editor.setValueImp(name: String, value: Long?) {
        if (value == null) {
            remove(name)
        }else{
            putLong(name,value)
        }
    }
}