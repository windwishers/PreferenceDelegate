package fail.toepic.preferencedelegate.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.dino.library.dinorecyclerview.DinoListAdapter
import com.dino.library.dinorecyclerview.ItemViewType
import fail.toepic.preferencedelegate.R
import fail.toepic.preferencedelegate.databinding.ActivityMainBinding
import fail.toepic.preferencedelegate.ui.model.ButtonItem
import java.util.*

class MainActivity : AppCompatActivity() {

    protected lateinit var binding: ActivityMainBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

            }
            ,ButtonItem("프리퍼런스 삭제 "){

            }
        )

    }
}
