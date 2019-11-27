package fail.toepic.preferencedelegate.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.facebook.stetho.Stetho
import fail.toepic.preferencedelegate.*
import fail.toepic.preferencedelegate.databinding.ActivityMainBinding
import fail.toepic.preferencedelegate.ui.model.ButtonItem

class MainActivity : AppCompatActivity() {

    protected lateinit var binding: ActivityMainBinding
        private set

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
            }
            ,ButtonItem("프리퍼런스 삭제 "){
                prefe.text = null
            }
        )

    }
}


class TestPrefer(contextProvider: contextProvider) : PreferenceProvider(contextProvider = contextProvider) {

    var text : String? by StringPreferenceDelegae<TestPrefer>()

}