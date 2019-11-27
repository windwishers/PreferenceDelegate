package fail.toepic.preferencedelegate

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


typealias contextProvider = ()-> Context?

open class PreferenceProvider(val name: String?=null,val contextProvider: contextProvider){
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


class StringPreferenceDelegae<T : PreferenceProvider> : PreferenceDelegate<T,String?>{
    override fun getValue(thisRef: T, property: KProperty<*>): String? {
         return thisRef.preference?.getString(property.name,"") ?: ""
    }

    override fun setValue(thisRef: T, property: KProperty<*>, value: String?) {
        thisRef.preference?.edit {
            putString(property.name,value)
        }
    }

}
