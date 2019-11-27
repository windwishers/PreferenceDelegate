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
            }
            ,ButtonItem("프리퍼런스 보기 "){
                val msg : String = " " +
                        "${prefe.text} / ${prefe.assignName} "
                Toast.makeText(this@MainActivity," $msg ",Toast.LENGTH_SHORT).show()
            }
            ,ButtonItem("프리퍼런스 삭제 "){
                prefe.text = null
                prefe.assignName = null
            }
        )

    }
}


class TestPrefer(contextProvider: contextProvider) : PreferenceProvider(contextProvider = contextProvider) {

    var text : String? by StringPreferenceDelegae<TestPrefer>()
    var assignName  : String? by StringPreferenceDelegae<TestPrefer>("이름 지정")



}