package fail.toepic.preferencedelegate.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.facebook.stetho.Stetho
import fail.toepic.preferencedelegate.*
import fail.toepic.preferencedelegate.databinding.ActivityMainBinding
import fail.toepic.preferencedelegate.ui.model.ButtonItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val prefe = TestPrefer {this}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Stetho.initializeWithDefaults(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.diffCallback = object : ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem === newItem
            }
        }

        binding.items = listOf(
            ButtonItem("프리퍼런스 생성 "){
                prefe.text = "123"
                prefe.assignName = "123"
                prefe.defaultValue = "값있음"
            }
            ,ButtonItem("프리퍼런스 보기 "){
                val msg : String = " " +
                        "${prefe.text} / ${prefe.assignName}  / ${prefe.defaultValue}"
                Toast.makeText(this@MainActivity," $msg ",Toast.LENGTH_SHORT).show()
            }
            ,ButtonItem("프리퍼런스 삭제 "){
                prefe.text = null
                prefe.assignName = null
                prefe.defaultValue = null
            }
            ,ButtonItem(" 여러 프리퍼런스 타입 생성"){
                prefe.IntPref  = 100
                prefe.floatPref = 1233.456F
                prefe.LongPref  = 123456789
                prefe.boolPref = false
            }
            ,ButtonItem(" 여러 프리퍼런스 타입 읽기"){
                val msg : String = " " +
                        "${prefe.IntPref} / ${prefe.floatPref}  / ${prefe.LongPref}/ ${prefe.boolPref}"
                Toast.makeText(this@MainActivity," $msg ",Toast.LENGTH_SHORT).show()
            }
            ,ButtonItem(" 여러 프리퍼런스 타입 지우기"){
                prefe.IntPref  = null
                prefe.floatPref = null
                prefe.LongPref  = null
                prefe.boolPref = null
            }
        )

    }
}


class TestPrefer(contextProvider: contextProvider) : PreferenceProvider(contextProvider = contextProvider) {

    var text : String? by StringPreferenceDelegate<TestPrefer>()
    var assignName  : String? by StringPreferenceDelegate<TestPrefer>(propertyName = "이름 지정")
    var defaultValue : String? by StringPreferenceDelegate<TestPrefer>(default = "기본값지정")

    var IntPref : Int? by IntPreferenceDelegate<TestPrefer>(default = 0)
    var floatPref : Float? by FloatPreferenceDelegate<TestPrefer>(default = 0F)
    var LongPref : Long? by LongPreferenceDelegate<TestPrefer>(default = 0L)
    var boolPref : Boolean? by BooleanPreferenceDelegate<TestPrefer>(default = false)


}